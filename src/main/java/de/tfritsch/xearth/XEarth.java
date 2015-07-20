package de.tfritsch.xearth;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.bind.JAXB;

import com.jgoodies.binding.beans.Model;

import de.tfritsch.xearth.image.ImageCreator;

@SuppressWarnings("serial")
public class XEarth extends Model {

    private static final int MSEC_PER_MINUTE = 60 * 1000;

    private static final int MSEC_PER_HOUR = 60 * 60 * 1000;

    private static final int DELAY = 500;

    private File settingsFile = new File(System.getProperty("user.home"),
            "xearth-settings.xml");

    private ImageCreator imageCreator;

    private TimerTask imageTimerTask;

    private TimerTask quakesTimerTask;

    private Timer timer;

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
        imageCreator = new ImageCreator(settings);
        imageTimerTask = new ImageTimerTask();
        quakesTimerTask = new QuakesTimerTask();
        timer = new Timer("XEarth");
        timer.schedule(imageTimerTask, 0,
                (long) (settings.getDisplayUpdateInterval() * MSEC_PER_MINUTE));
        timer.schedule(quakesTimerTask,
                (long) (settings.getQuakesUpdateInterval() * MSEC_PER_HOUR),
                (long) (settings.getQuakesUpdateInterval() * MSEC_PER_HOUR));
        settings.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent e) {
                if (Settings.PROPERTY_QUAKES_UPDATE_INTERVAL.equals(e
                        .getPropertyName()))
                    updateQuakesTimer();
                else
                    refresh();
            }
        });
    }

    // if this method is called again within less than DELAY msec,
    // then ImageTimerTask.run will be called only once.
    public final synchronized void refresh() {
        imageTimerTask.cancel();
        imageTimerTask = new ImageTimerTask();
        timer.schedule(imageTimerTask, DELAY,
                (long) (settings.getDisplayUpdateInterval() * MSEC_PER_MINUTE));
    }

    private synchronized void updateQuakesTimer() {
        quakesTimerTask.cancel();
        quakesTimerTask = new QuakesTimerTask();
        timer.schedule(quakesTimerTask,
                (long) (settings.getQuakesUpdateInterval() * MSEC_PER_HOUR),
                (long) (settings.getQuakesUpdateInterval() * MSEC_PER_HOUR));
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

    private class ImageTimerTask extends TimerTask {
        @Override
        public void run() {
            setImage(imageCreator.createImage());
        }
    }

    private class QuakesTimerTask extends TimerTask {
        @Override
        public void run() {
            try {
                settings.updateQuakes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
