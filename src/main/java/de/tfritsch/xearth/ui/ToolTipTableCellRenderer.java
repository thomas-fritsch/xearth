package de.tfritsch.xearth.ui;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class ToolTipTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(final JTable table,
            final Object value, final boolean isSelected,
            final boolean hasFocus, final int row, final int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                row, column);
        int w = table.getColumnModel().getColumn(column).getWidth();
        setToolTipText(w < getPreferredSize().getWidth() ? getText() : null);
        return this;
    }

}
