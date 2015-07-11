package de.tfritsch.common;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * General icons.
 * @author Thomas Fritsch
 */
public final class EclipseIcons {

    private static ImageIcon getIcon(final String name) {
        URL url = EclipseIcons.class.getResource("icons/" + name);
        if (url == null) {
            Thread.dumpStack();
            return null;
        }
        return new ImageIcon(url);
    }

    public static final Icon ADD_OBJ = getIcon("add_obj.png");
    public static final Icon COPY_EDIT = getIcon("copy_edit.png");
    public static final Icon DELETE_OBJ = getIcon("delete_obj.png");
    public static final Icon EXPORT_WIZ = getIcon("export_wiz.png");
    public static final Icon IMPORT_WIZ = getIcon("import_wiz.png");
    public static final Icon INFORMATION = getIcon("information.gif");
    public static final Icon PROPERTIES = getIcon("properties.png");
    public static final Icon REFRESH = getIcon("refresh.png");
    public static final Icon RESET = getIcon("reset.gif");
    public static final Icon SAVE_AS_EDIT = getIcon("saveas_edit.png");
    public static final Icon SAVE_EDIT = getIcon("save_edit.png");

    /**
     * Don't let anyone instantiate this class.
     */
    private EclipseIcons() {
    }
}
