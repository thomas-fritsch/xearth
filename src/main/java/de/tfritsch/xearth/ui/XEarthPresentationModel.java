package de.tfritsch.xearth.ui;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.EventObject;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jgoodies.binding.PresentationModel;

import de.tfritsch.common.EclipseIcons;
import de.tfritsch.common.ImageTransferable;
import de.tfritsch.xearth.Settings;
import de.tfritsch.xearth.XEarth;

@SuppressWarnings("serial")
public class XEarthPresentationModel extends PresentationModel<XEarth> {

    private File lastSavedFile;

    private Action saveAction = new SaveAction();
    private Action saveAsAction = new SaveAsAction();
    private Action copyAction = new CopyAction();
    private Action refreshAction = new RefreshAction();
    private Action propertiesAction = new PropertiesAction();
    private Action aboutAction = new AboutAction();
    private Action closeAction = new CloseAction();

    public XEarthPresentationModel(final XEarth xearth) {
        super(xearth);
        addBeanPropertyChangeListener(XEarth.PROPERTY_IMAGE,
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(final PropertyChangeEvent evt) {
                        BufferedImage image = getBean().getImage();
                        saveAction.setEnabled(image != null
                                && lastSavedFile != null);
                        saveAsAction.setEnabled(image != null);
                        copyAction.setEnabled(image != null);
                    }
                });
    }

    public final Action getSaveAction() {
        return saveAction;
    }

    public final Action getSaveAsAction() {
        return saveAsAction;
    }

    public final Action getCopyAction() {
        return copyAction;
    }

    public final Action getRefreshAction() {
        return refreshAction;
    }

    public final Action getPropertiesAction() {
        return propertiesAction;
    }

    public final Action getAboutAction() {
        return aboutAction;
    }

    public final Action getCloseAction() {
        return closeAction;
    }

    protected static Window getWindowSource(final EventObject e) {
        if (!(e.getSource() instanceof Component))
            return null;
        return SwingUtilities.getWindowAncestor((Component) e.getSource());
    }

    private class SaveAction extends AbstractAction {

        public SaveAction() {
            super("Save");
            putValue(SHORT_DESCRIPTION, "Save file");
            putValue(SMALL_ICON, EclipseIcons.SAVE_EDIT);
            setEnabled(false);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            BufferedImage image = getBean().getImage();
            try {
                ImageIO.write(image, "PNG", lastSavedFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class SaveAsAction extends AbstractAction {

        public SaveAsAction() {
            super("Save as...");
            putValue(SHORT_DESCRIPTION, "Save as new file");
            putValue(SMALL_ICON, EclipseIcons.SAVE_AS_EDIT);
            setEnabled(false);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("PNG files",
                    "png"));
            if (chooser.showSaveDialog(getWindowSource(e)) == JFileChooser.APPROVE_OPTION) {
                BufferedImage image = getBean().getImage();
                try {
                    ImageIO.write(image, "PNG", chooser.getSelectedFile());
                    lastSavedFile = chooser.getSelectedFile();
                    saveAction.setEnabled(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private class CopyAction extends AbstractAction implements ClipboardOwner {

        public CopyAction() {
            super("Copy");
            putValue(SHORT_DESCRIPTION, "Copy to clipboard");
            putValue(SMALL_ICON, EclipseIcons.COPY_EDIT);
            setEnabled(false);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            BufferedImage image = getBean().getImage();
            Transferable contents = new ImageTransferable(image);
            Clipboard clipboard = Toolkit.getDefaultToolkit()
                    .getSystemClipboard();
            clipboard.setContents(contents, this);
        }

        @Override
        public void lostOwnership(final Clipboard clipboard,
                final Transferable contents) {
        }
    }

    private class RefreshAction extends AbstractAction {

        public RefreshAction() {
            super("Refresh");
            putValue(SHORT_DESCRIPTION, "Refresh");
            putValue(SMALL_ICON, EclipseIcons.REFRESH);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            getBean().refresh();
        }
    }

    private class PropertiesAction extends AbstractAction {

        public PropertiesAction() {
            super("Properties...");
            putValue(SHORT_DESCRIPTION, "Properties...");
            putValue(SMALL_ICON, EclipseIcons.PROPERTIES);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            Window owner = getWindowSource(e);
            Window window;
            if (owner != null)
                window = new JDialog(owner, "Properties");
            else {
                window = new JFrame("XEarth - Properties");
                window.setIconImage(Icons.EARTH.getImage());
            }
            Settings settings = getBean().getSettings();
            SettingsPanelBuilder builder = new SettingsPanelBuilder(
                    new SettingsPresentationModel(settings));
            JPanel settingsPanel = builder.buildSettingsPanel();
            window.add(settingsPanel);
            window.pack();
            window.setLocationByPlatform(true);
            window.setVisible(true);
        }
    }

    private static class CloseAction extends AbstractAction {

        public CloseAction() {
            super("Close");
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            System.exit(0);
        }
    }

    private static class AboutAction extends AbstractAction {

        public AboutAction() {
            super("About...");
            putValue(SHORT_DESCRIPTION, "About...");
            putValue(SMALL_ICON, EclipseIcons.INFORMATION);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            JEditorPane editorPane = new JEditorPane();
            try {
                editorPane.setPage(getClass().getResource("about.html"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            editorPane.setEditable(false);
            editorPane.setOpaque(false);
            editorPane.addHyperlinkListener(new HyperlinkAdapter());
            JOptionPane.showMessageDialog(getWindowSource(e), editorPane,
                    "About XEarth", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
