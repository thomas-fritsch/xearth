package de.tfritsch.xearth.ui;

import java.text.DateFormat;

@SuppressWarnings("serial")
public class DateTableCellRenderer extends ToolTipTableCellRenderer {

    private DateFormat dateFormat = DateFormat.getDateTimeInstance(
            DateFormat.MEDIUM, DateFormat.SHORT);

    @Override
    protected void setValue(final Object value) {
        setText((value == null) ? "" : dateFormat.format(value));
    }
}
