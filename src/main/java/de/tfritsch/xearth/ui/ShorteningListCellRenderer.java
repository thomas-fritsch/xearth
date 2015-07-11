package de.tfritsch.xearth.ui;

import javax.swing.DefaultListCellRenderer;

@SuppressWarnings("serial")
public class ShorteningListCellRenderer extends DefaultListCellRenderer {

    private int maxLength = 44;

    @Override
    public void setText(final String text) {
        String s;
        if (text == null) {
            s = "";
        } else if (text.length() > maxLength) {
            s = text.substring(0, maxLength / 2) + "..."
                    + text.substring(text.length() - maxLength / 2);
        } else {
            s = text;
        }
        super.setText(s);
    }
}
