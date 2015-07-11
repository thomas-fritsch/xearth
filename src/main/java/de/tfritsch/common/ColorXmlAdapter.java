package de.tfritsch.common;

import java.awt.Color;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} to handle {@link Color} values.
 * @author Thomas Fritsch
 */
public class ColorXmlAdapter extends XmlAdapter<String, Color> {

    private static final int RGB_MASK = 0xffffff;

    @Override
    public final String marshal(final Color color) throws Exception {
        if (color == null) {
            return null;
        }
        return "#" + Integer.toHexString(color.getRGB() & RGB_MASK);
    }

    @Override
    public final Color unmarshal(final String s) throws Exception {
        if (s == null) {
            return null;
        }
        return Color.decode(s);
    }
}
