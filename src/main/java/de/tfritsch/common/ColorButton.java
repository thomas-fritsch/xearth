package de.tfritsch.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;

/**
 * A component for selecting a {@link Color} value.
 * @author Thomas Fritsch
 */
@SuppressWarnings("serial")
public class ColorButton extends JButton {

    /**
     * The name of the read-write property <code>color</code>.
     */
    public static final String PROPERTY_COLOR = "color";

    private Color color;

    public final Color getColor() {
        return color;
    }

    public final void setColor(final Color newValue) {
        Color oldValue = color;
        color = newValue;
        firePropertyChange(PROPERTY_COLOR, oldValue, newValue);
        repaint();
    }

    public ColorButton() {
        setAction(new ColorChooseAction());
    }

    private class ColorIcon implements Icon {
        @Override
        public void paintIcon(final Component c, final Graphics g, final int x,
                final int y) {
            if (color != null) {
                g.setColor(isEnabled() ? color : Color.LIGHT_GRAY);
                g.fillRect(x, y, getIconWidth(), getIconHeight());
                g.setColor(isEnabled() ? Color.BLACK : Color.GRAY);
                g.drawRect(x, y, getIconWidth(), getIconHeight());
            }
        }

        @Override
        public int getIconWidth() {
            return 48;
        }

        @Override
        public int getIconHeight() {
            return 16;
        }
    }

    private class ColorChooseAction extends AbstractAction {
        public ColorChooseAction() {
            putValue(SMALL_ICON, new ColorIcon());
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            Color newColor = JColorChooser.showDialog(ColorButton.this,
                    "Color", color);
            if (newColor != null)
                setColor(newColor);
        }
    }
}
