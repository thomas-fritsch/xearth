package de.tfritsch.xearth.ui;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public final class Icons {

    private static ImageIcon getIcon(final String name) {
        URL url = Icons.class.getResource("icons/" + name);
        if (url == null) {
            Thread.dumpStack();
            return null;
        }
        return new ImageIcon(url);
    }

    public static final ImageIcon EARTH = getIcon("earth.png");
    public static final Icon DISPLAY = getIcon("display.png");
    public static final Icon DOTS = getIcon("dots.png");
    public static final Icon IMAGE = getIcon("image.png");
    public static final Icon LABELS = getIcon("labels.png");
    public static final Icon QUAKES = getIcon("quakes.png");
    public static final Icon SHADING = getIcon("shading.png");
    public static final Icon SUN = getIcon("sun.png");
    public static final Icon TIME = getIcon("time.png");
    public static final Icon VIEWPOINT = getIcon("viewpoint.png");

    /**
     * Don't let anyone instantiate this class.
     */
    private Icons() {
    }
}
