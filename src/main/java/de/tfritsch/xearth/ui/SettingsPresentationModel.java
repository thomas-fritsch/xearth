package de.tfritsch.xearth.ui;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.beans.PropertyConnector;

import de.tfritsch.common.EclipseIcons;
import de.tfritsch.xearth.Marker;
import de.tfritsch.xearth.Quake;
import de.tfritsch.xearth.Settings;
import de.tfritsch.xearth.Utilities;

@SuppressWarnings("serial")
public class SettingsPresentationModel extends PresentationModel<Settings> {

    private Action okAction = new OkAction();
    private Action cancelAction = new CancelAction();
    private Action applyAction = new ApplyAction();
    private Action addMarkerAction = new AddMarkerAction();
    private Action removeMarkerAction = new RemoveMarkerAction();
    private Action importMarkersAction = new ImportMarkersAction();
    private Action exportMarkersAction = new ExportMarkersAction();
    private Action resetMarkersAction = new ResetMarkersAction();
    private Action updateQuakesAction = new UpdateQuakesAction();

    private ComboBoxAdapter<String> quakesFeedComboBoxAdapter;

    public SettingsPresentationModel(final Settings settings) {
        super(settings);
        String baseURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/";
        quakesFeedComboBoxAdapter = new ComboBoxAdapter<String>(Arrays.asList(
                baseURL + "significant_hour.csv", baseURL + "4.5_hour.csv",
                baseURL + "significant_day.csv", baseURL + "4.5_day.csv",
                baseURL + "significant_week.csv", baseURL + "4.5_week.csv",
                baseURL + "significant_month.csv", baseURL + "4.5_month.csv"),
                getModel(Settings.PROPERTY_QUAKES_FEED_URL));
    }

    public final Action getOkAction() {
        return okAction;
    }

    public final Action getCancelAction() {
        return cancelAction;
    }

    public final Action getAddMarkerAction() {
        return addMarkerAction;
    }

    public final Action getRemoveMarkerAction() {
        return removeMarkerAction;
    }

    public final Action getApplyAction() {
        return applyAction;
    }

    public final Action getImportMarkersAction() {
        return importMarkersAction;
    }

    public final Action getExportMarkersAction() {
        return exportMarkersAction;
    }

    public final Action getResetMarkersAction() {
        return resetMarkersAction;
    }

    public final Action getUpdateQuakesAction() {
        return updateQuakesAction;
    }

    public final AbstractTableAdapter<Marker> createMarkerTableAdapter() {
        return new MarkerTableAdapter(null);
    }

    public final AbstractTableAdapter<Quake> createQuakeTableAdapter() {
        return new QuakeTableAdapter(null);
    }

    public final ComboBoxAdapter<String> getQuakesFeedComboBoxAdapter() {
        return quakesFeedComboBoxAdapter;
    }

    private void closeWindowOf(final Component component) {
        Window window = SwingUtilities.getWindowAncestor(component);
        window.setVisible(false);
        window.dispose();
        // allow PresentationModel to be garbage-collected
        release();
        setBean(null);
    }

    private class OkAction extends AbstractAction {

        public OkAction() {
            super("OK");
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            triggerCommit();
            if (e.getSource() instanceof Component) {
                closeWindowOf((Component) e.getSource());
            }
        }
    }

    private class CancelAction extends AbstractAction {

        public CancelAction() {
            super("Cancel");
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            triggerFlush();
            if (e.getSource() instanceof Component) {
                closeWindowOf((Component) e.getSource());
            }
        }
    }

    private class ApplyAction extends AbstractAction {

        public ApplyAction() {
            super("Apply");
            setEnabled(false);
            PropertyConnector.connect(SettingsPresentationModel.this,
                    PROPERTY_BUFFERING, ApplyAction.this, "enabled");
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            triggerCommit();
        }
    }

    private class AddMarkerAction extends AbstractAction {
        public AddMarkerAction() {
            super("Add...");
            putValue(SMALL_ICON, EclipseIcons.ADD_OBJ);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            // TODO add marker
        }
    }

    private class RemoveMarkerAction extends AbstractAction {
        public RemoveMarkerAction() {
            super("Remove");
            putValue(SMALL_ICON, EclipseIcons.DELETE_OBJ);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            // TODO remove marker
        }
    }

    private class ImportMarkersAction extends AbstractAction {

        public ImportMarkersAction() {
            super("Import...");
            putValue(SMALL_ICON, EclipseIcons.IMPORT_WIZ);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            Component component = (Component) e.getSource();
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("CSV files",
                    "csv"));
            if (chooser.showOpenDialog(component) == JFileChooser.APPROVE_OPTION) {
                try {
                    List<Marker> markers = Utilities.readMarkers(chooser
                            .getSelectedFile());
                    getBufferedModel(Settings.PROPERTY_MARKERS).setValue(
                            markers);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(component, ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class ExportMarkersAction extends AbstractAction {

        public ExportMarkersAction() {
            super("Export...");
            putValue(SMALL_ICON, EclipseIcons.EXPORT_WIZ);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            Component component = (Component) e.getSource();
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("CSV files",
                    "csv"));
            if (chooser.showSaveDialog(component) == JFileChooser.APPROVE_OPTION) {
                try {
                    @SuppressWarnings("unchecked")
                    List<Marker> markers = (List<Marker>) getBufferedModel(
                            Settings.PROPERTY_MARKERS).getValue();
                    Utilities.writeMarkers(markers, chooser.getSelectedFile());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(component, ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class ResetMarkersAction extends AbstractAction {

        public ResetMarkersAction() {
            super("Reset");
            putValue(SMALL_ICON, EclipseIcons.RESET);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            getBufferedModel(Settings.PROPERTY_MARKERS).setValue(
                    Utilities.getInitialMarkers());
        }
    }

    private class UpdateQuakesAction extends AbstractAction {

        public UpdateQuakesAction() {
            super("Update now");
            putValue(SMALL_ICON, EclipseIcons.IMPORT_WIZ);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                getBean().updateQuakes();
            } catch (IOException ex) {
                Component component = (Component) e.getSource();
                JOptionPane.showMessageDialog(component, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static class MarkerTableAdapter extends
            AbstractTableAdapter<Marker> {

        private static final int COLUMN_NAME = 0;
        private static final int COLUMN_LATITUDE = 1;
        private static final int COLUMN_LONGITUDE = 2;

        private Class<?>[] columnClasses = {String.class, Float.class,
                Float.class};

        public MarkerTableAdapter(final ListModel<Marker> listModel) {
            super(listModel, "Name", "Latitude", "Longitude");
        }

        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            Marker marker = getRow(rowIndex);
            switch (columnIndex) {
            case COLUMN_NAME:
                return marker.getName();
            case COLUMN_LATITUDE:
                return marker.getLatitude();
            case COLUMN_LONGITUDE:
                return marker.getLongitude();
            default:
                return null;
            }
        }

        @Override
        public Class<?> getColumnClass(final int columnIndex) {
            return columnClasses[columnIndex];
        }

        @Override
        public boolean isCellEditable(final int rowIndex, final int columnIndex) {
            return true;
        }

        @Override
        public void setValueAt(final Object value, final int rowIndex,
                final int columnIndex) {
            Marker marker = getRow(rowIndex);
            switch (columnIndex) {
            case COLUMN_NAME:
                marker.setName((String) value);
                break;
            case COLUMN_LATITUDE:
                marker.setLatitude((Float) value);
                break;
            case COLUMN_LONGITUDE:
                marker.setLongitude((Float) value);
                break;
            default:
                // should not happen
                break;
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    private static class QuakeTableAdapter extends AbstractTableAdapter<Quake> {

        private static final int COLUMN_TIME = 0;
        private static final int COLUMN_LATITUDE = 1;
        private static final int COLUMN_LONGITUDE = 2;
        private static final int COLUMN_MAGNITUDE = 3;
        private static final int COLUMN_LOCATION = 4;

        private Class<?>[] columnClasses = {Date.class, Float.class,
                Float.class, Float.class, String.class};

        public QuakeTableAdapter(final ListModel<Quake> listModel) {
            super(listModel, "Time (" + TimeZone.getDefault().getID() + ")",
                    "Latitude", "Longitude", "Magnitude", "Location");
        }

        @Override
        public Object getValueAt(final int rowIndex, final int columnIndex) {
            Quake quake = getRow(rowIndex);
            switch (columnIndex) {
            case COLUMN_TIME:
                return quake.getTime();
            case COLUMN_LATITUDE:
                return quake.getLatitude();
            case COLUMN_LONGITUDE:
                return quake.getLongitude();
            case COLUMN_MAGNITUDE:
                return quake.getMagnitude();
            case COLUMN_LOCATION:
                return quake.getPlace();
            default:
                return null;
            }
        }

        @Override
        public Class<?> getColumnClass(final int columnIndex) {
            return columnClasses[columnIndex];
        }
    }
}
