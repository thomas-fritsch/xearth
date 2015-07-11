package de.tfritsch.xearth;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.xml.bind.JAXB;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.beans.PropertyConnector;

import de.tfritsch.xearth.image.ImageCreator;

@SuppressWarnings("serial")
public class XEarth extends Model {

    private File settingsFile = new File(System.getProperty("user.home"),
            "xearth-settings.xml");

    private ImageCreator imageCreator;

    public XEarth() {
        if (settingsFile.exists())
            settings = JAXB.unmarshal(settingsFile, Settings.class);
        else
            settings = new Settings();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                JAXB.marshal(settings, settingsFile);
            }
        });
    }

    public void start() {
        imageCreator = new ImageCreator(settings);
        PropertyConnector.connect(imageCreator, ImageCreator.PROPERTY_IMAGE,
                this, PROPERTY_IMAGE);
        new Thread(imageCreator, "XEarth-ImageCreator").start();
    }

    /**
     * The name of the read-only property <code>settings</code>.
     * @see #getSettings()
     */
    public static final String PROPERTY_SETTINGS = "settings";

    private Settings settings;

    public final Settings getSettings() {
        return settings;
    }

    /**
     * The name of the read-write property <code>image</code>.
     * @see #getImage()
     * @see #setImage(BufferedImage)
     */
    public static final String PROPERTY_IMAGE = "image";

    private BufferedImage image;

    public final BufferedImage getImage() {
        return image;
    }

    public final void setImage(final BufferedImage newValue) {
        BufferedImage oldValue = image;
        image = newValue;
        firePropertyChange(PROPERTY_IMAGE, oldValue, newValue);
    }

}
