package de.tfritsch.xearth;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import com.jgoodies.binding.beans.PropertyConnector;

import de.tfritsch.xearth.ui.FrameBuilder;
import de.tfritsch.xearth.ui.TrayIconBuilder;
import de.tfritsch.xearth.ui.XEarthPresentationModel;

/**
 * Main class.
 * @author Thomas Fritsch
 */
public final class Main {

    /**
     * @param args
     *            the command line arguments
     * @throws AWTException
     *             if the desktop system tray is missing
     */
    public static void main(final String[] args) throws AWTException {
        XEarth xearth = new XEarth();
        XEarthPresentationModel presentationModel = new XEarthPresentationModel(
                xearth);
        if (args.length >= 1 && args[0].equals("-root")) {
            TrayIcon trayIcon = new TrayIconBuilder(presentationModel)
                    .buildTrayIcon();
            SystemTray.getSystemTray().add(trayIcon);
            PropertyConnector.connect(xearth, XEarth.PROPERTY_IMAGE,
                    new ImageFileWriter(), ImageFileWriter.PROPERTY_IMAGE);
        } else {
            JFrame frame = new FrameBuilder(presentationModel).buildFrame();
            frame.setVisible(true);
        }
        new Thread(xearth, "XEarth").start();
    }

    /**
     * Don't let anyone instantiate this class.
     */
    private Main() {
    }

    public static class ImageFileWriter {
        /**
         * The name of the property <code>image</code>.
         */
        public static final String PROPERTY_IMAGE = "image";

        private BufferedImage image;

        public final BufferedImage getImage() {
            return image;
        }

        public final void setImage(final BufferedImage image) {
            this.image = image;
            System.out.println("saving");
            try {
                ImageIO.write(image, "PNG", new File("xearth.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
