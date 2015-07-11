package de.tfritsch.xearth.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import com.jgoodies.binding.adapter.Bindings;

import de.tfritsch.common.ImageComponent;
import de.tfritsch.xearth.Settings;
import de.tfritsch.xearth.XEarth;

public class FrameBuilder {

    private final XEarthPresentationModel presentationModel;

    public FrameBuilder(final XEarthPresentationModel presentationModel) {
        this.presentationModel = presentationModel;
    }

    public JFrame buildFrame() {
        JFrame frame = new JFrame();
        frame.setTitle("Xearth for Java");
        frame.setIconImage(Icons.EARTH.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(buildToolBar(), BorderLayout.NORTH);
        frame.add(new JScrollPane(createImageComponent()), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationByPlatform(true);
        return frame;
    }

    protected JToolBar buildToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(presentationModel.getSaveAction());
        toolBar.add(presentationModel.getSaveAsAction());
        toolBar.add(presentationModel.getCopyAction());
        toolBar.add(presentationModel.getRefreshAction());
        toolBar.add(presentationModel.getPropertiesAction());
        toolBar.add(presentationModel.getAboutAction());
        return toolBar;
    }

    protected JComponent createImageComponent() {
        JComponent imageComponent = new ImageComponent();
        Settings settings = presentationModel.getBean().getSettings();
        imageComponent.setPreferredSize(new Dimension(settings.getImageWidth(),
                settings.getImageHeight()));
        Bindings.bind(imageComponent, ImageComponent.PROPERTY_IMAGE,
                presentationModel.getModel(XEarth.PROPERTY_IMAGE));
        return imageComponent;
    }
}
