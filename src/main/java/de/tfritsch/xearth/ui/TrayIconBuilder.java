package de.tfritsch.xearth.ui;

import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TrayIcon;

import javax.swing.Action;
import javax.swing.UIManager;

public class TrayIconBuilder {

    private final XEarthPresentationModel presentationModel;

    public TrayIconBuilder(final XEarthPresentationModel presentationModel) {
        this.presentationModel = presentationModel;
    }

    public TrayIcon buildTrayIcon() {
        TrayIcon trayIcon = new TrayIcon(Icons.EARTH.getImage());
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("xearth");
        trayIcon.addActionListener(presentationModel.getPropertiesAction());
        trayIcon.setPopupMenu(buildPopupMenu());
        return trayIcon;
    }

    protected PopupMenu buildPopupMenu() {
        Font boldFont = UIManager.getFont("Menu.font").deriveFont(Font.BOLD);
        PopupMenu popupMenu = new PopupMenu();
        popupMenu.add(createMenuItem(presentationModel.getRefreshAction()));
        popupMenu.add(createMenuItem(presentationModel.getCloseAction()));
        popupMenu.add(createMenuItem(presentationModel.getAboutAction()));
        popupMenu.addSeparator();
        popupMenu.add(createMenuItem(presentationModel.getPropertiesAction()))
                .setFont(boldFont);
        return popupMenu;
    }

    protected MenuItem createMenuItem(final Action action) {
        MenuItem menuItem = new MenuItem((String) action.getValue(Action.NAME));
        // AWT MenuItem doesn't support icon
        menuItem.addActionListener(action);
        return menuItem;
    }
}
