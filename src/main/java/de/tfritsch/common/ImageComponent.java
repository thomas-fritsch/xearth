package de.tfritsch.common;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

/**
 * @author Thomas Fritsch
 */
@SuppressWarnings("serial")
public class ImageComponent extends JComponent {

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
        if (image != null) {
            Dimension imageSize = new Dimension(image.getWidth(),
                    image.getHeight());
            setPreferredSize(imageSize);
            setSize(imageSize);
            invalidate();
        }
        repaint();
    }

    @Override
    protected final void paintComponent(final Graphics g) {
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
    }

}
