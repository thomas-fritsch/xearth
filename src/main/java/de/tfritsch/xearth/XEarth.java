package de.tfritsch.xearth;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.xml.bind.JAXB;

import com.jgoodies.binding.beans.Model;

import de.tfritsch.xearth.image.ImageCreator;

@SuppressWarnings("serial")
public class XEarth extends Model implements Runnable, PropertyChangeListener {

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
        settings.addPropertyChangeListener(this);
        imageCreator = new ImageCreator(settings);
    }

    @Override
    public synchronized void propertyChange(final PropertyChangeEvent e) {
        notifyAll();
    }

    @Override
    public final synchronized void run() {
        while (true) {
            setImage(imageCreator.createImage());
            System.out.println("waiting");
            try {
                wait((long) (settings.getDisplayUpdateInterval() * 60 * 1000));
            } catch (InterruptedException e) {
                // do nothing
            }
        }
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
