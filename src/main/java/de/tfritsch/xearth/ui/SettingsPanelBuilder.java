package de.tfritsch.xearth.ui;

import static com.jgoodies.binding.adapter.BasicComponentFactory.createCheckBox;
import static com.jgoodies.binding.adapter.BasicComponentFactory.createFormattedTextField;
import static com.jgoodies.binding.adapter.BasicComponentFactory.createIntegerField;
import static com.jgoodies.binding.adapter.BasicComponentFactory.createRadioButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

import de.tfritsch.common.ColorButton;
import de.tfritsch.xearth.ImageProjection;
import de.tfritsch.xearth.LegendPosition;
import de.tfritsch.xearth.Marker;
import de.tfritsch.xearth.Quake;
import de.tfritsch.xearth.Settings;
import de.tfritsch.xearth.ViewpointType;

public class SettingsPanelBuilder {

    private final SettingsPresentationModel presentationModel;

    public SettingsPanelBuilder(
            final SettingsPresentationModel presentationModel) {
        this.presentationModel = presentationModel;
    }

    private JLabel createHeaderLabel(final String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.BLUE);
        label.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 36));
        return label;
    }

    public JPanel buildSettingsPanel() {
        PanelBuilder builder = new PanelBuilder(new FormLayout("default:grow", // columns
                "pref, 4dlu, fill:pref:grow, 4dlu, pref")); // rows
        builder.border(Borders.TABBED_DIALOG);
        builder.add(createHeaderLabel("XEarth for Java"), CC.xy(1, 1, "c, c"));
        builder.add(buildTabbedPane(), CC.xy(1, 3));
        builder.add(buildButtonBar(), CC.xy(1, 5));
        return builder.build();
    }

    protected JTabbedPane buildTabbedPane() {
        JTabbedPane pane = new JTabbedPane();
        pane.addTab("Image", Icons.IMAGE, buildImagePanel());
        pane.addTab("Viewpoint", Icons.VIEWPOINT, buildViewpointPanel());
        pane.addTab("Sun", Icons.SUN, buildSunPanel());
        pane.addTab("Labels", Icons.LABELS, buildLabelsPanel());
        pane.addTab("Quakes", Icons.QUAKES, buildQuakesPanel());
        pane.addTab("Dots", Icons.DOTS, buildDotsPanel());
        pane.addTab("Shading", Icons.SHADING, buildShadingPanel());
        pane.addTab("Time", Icons.TIME, buildTimePanel());
        pane.addTab("Display", Icons.DISPLAY, buildDisplayPanel());
        return pane;
    }

    protected JPanel buildButtonBar() {
        JButton okButton = new JButton(presentationModel.getOkAction());
        JButton cancelButton = new JButton(presentationModel.getCancelAction());
        JButton applyButton = new JButton(presentationModel.getApplyAction());
        okButton.registerKeyboardAction(okButton.getAction(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        cancelButton.registerKeyboardAction(cancelButton.getAction(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        return new ButtonBarBuilder().addGlue()
                .addButton(okButton, cancelButton, applyButton).build();
    }

    protected JPanel buildImagePanel() {
        JRadioButton orthographicRadioButton = createRadioButton(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_IMAGE_PROJECTION),
                ImageProjection.ORTHOGRAPHIC, "Orthographic");
        JRadioButton mercatorRadioButton = createRadioButton(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_IMAGE_PROJECTION),
                ImageProjection.MERCATOR, "Mercator");
        JRadioButton cylindricalRadioButton = createRadioButton(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_IMAGE_PROJECTION),
                ImageProjection.CYLINDRICAL, "Cylindrical");
        JTextField magnificationTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_IMAGE_MAGNIFICATION),
                NumberFormat.getNumberInstance());
        JCheckBox useScreenSizeCheckBox = createCheckBox(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_USE_SCREEN_SIZE),
                "Use screen size");
        JTextField widthTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_IMAGE_WIDTH),
                NumberFormat.getNumberInstance());
        JTextField heightTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_IMAGE_HEIGHT),
                NumberFormat.getNumberInstance());
        disableWhenSelected(useScreenSizeCheckBox, widthTextField,
                heightTextField);
        PanelBuilder builder = new PanelBuilder(new FormLayout(
                "pref, 4dlu, pref:grow, 4dlu, pref, 4dlu, pref:grow", // columns
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref")); // rows
        builder.border(Borders.DLU4);
        builder.add(orthographicRadioButton, CC.xy(1, 3));
        builder.add(mercatorRadioButton, CC.xy(3, 3));
        builder.add(cylindricalRadioButton, CC.xyw(5, 3, 3));
        builder.addLabel("Magnification:", CC.xy(1, 5));
        builder.add(magnificationTextField, CC.xy(3, 5));
        builder.add(useScreenSizeCheckBox, CC.xy(3, 5));
        builder.addLabel("Width:", CC.xy(1, 9));
        builder.add(widthTextField, CC.xy(3, 9));
        builder.addLabel("Height:", CC.xy(5, 9));
        builder.add(heightTextField, CC.xy(7, 9));
        return builder.build();
    }

    protected JPanel buildViewpointPanel() {
        JRadioButton defaultRadioButton = createRadioButton(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_VIEWPOINT_TYPE),
                ViewpointType.DEFAULT, "Default");
        JRadioButton fixedRadioButton = createRadioButton(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_VIEWPOINT_TYPE),
                ViewpointType.FIXED, "Fixed");
        JTextField fixedLatitudeTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_FIXED_LATITUDE),
                NumberFormat.getNumberInstance());
        JTextField fixedLongitudeTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_FIXED_LONGITUDE),
                NumberFormat.getNumberInstance());
        enableWhenSelected(fixedRadioButton, fixedLatitudeTextField,
                fixedLongitudeTextField);
        JRadioButton sunRelativeRadioButton = createRadioButton(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_VIEWPOINT_TYPE),
                ViewpointType.SUN_RELATIVE, "Sun-relative");
        JTextField sunRelativeLatitudeTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_SUN_RELATIVE_LATITUDE),
                NumberFormat.getNumberInstance());
        JTextField sunRelativeLongitudeTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_SUN_RELATIVE_LONGITUDE),
                NumberFormat.getNumberInstance());
        enableWhenSelected(sunRelativeRadioButton,
                sunRelativeLatitudeTextField, sunRelativeLongitudeTextField);
        JRadioButton orbitRadioButton = createRadioButton(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_VIEWPOINT_TYPE),
                ViewpointType.ORBIT, "Orbit");
        JTextField orbitPeriodTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_ORBIT_PERIOD),
                NumberFormat.getNumberInstance());
        JTextField orbitInclinationTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_ORBIT_INCLINATION),
                NumberFormat.getNumberInstance());
        enableWhenSelected(orbitRadioButton, orbitPeriodTextField,
                orbitInclinationTextField);
        JRadioButton moonRadioButton = createRadioButton(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_VIEWPOINT_TYPE),
                ViewpointType.MOON, "Moon");
        JRadioButton randomRadioButton = createRadioButton(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_VIEWPOINT_TYPE),
                ViewpointType.RANDOM, "Random");
        PanelBuilder builder = new PanelBuilder(
                new FormLayout(
                        "pref, 4dlu, pref, 4dlu, pref:grow, 4dlu, pref, 4dlu, pref:grow", // columns
                        "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref")); // rows
        builder.border(Borders.DLU4);
        builder.add(defaultRadioButton, CC.xy(1, 1));
        builder.add(fixedRadioButton, CC.xy(1, 3));
        builder.addLabel("Latitude:", CC.xy(3, 3));
        builder.add(fixedLatitudeTextField, CC.xy(5, 3));
        builder.addLabel("Longitude:", CC.xy(7, 3));
        builder.add(fixedLongitudeTextField, CC.xy(9, 3));
        builder.add(sunRelativeRadioButton, CC.xy(1, 5));
        builder.addLabel("Latitude:", CC.xy(3, 5));
        builder.add(sunRelativeLatitudeTextField, CC.xy(5, 5));
        builder.addLabel("Longitude:", CC.xy(7, 5));
        builder.add(sunRelativeLongitudeTextField, CC.xy(9, 5));
        builder.add(orbitRadioButton, CC.xy(1, 7));
        builder.addLabel("Period:", CC.xy(3, 7));
        builder.add(orbitPeriodTextField, CC.xy(5, 7));
        builder.addLabel("Inclination:", CC.xy(7, 7));
        builder.add(orbitInclinationTextField, CC.xy(9, 7));
        builder.add(moonRadioButton, CC.xy(1, 9));
        builder.add(randomRadioButton, CC.xy(1, 11));
        return builder.build();
    }

    protected JPanel buildSunPanel() {
        JCheckBox useActualPosition = createCheckBox(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_SUN_USE_ACTUAL_POSITION),
                "Use actual position");
        JTextField latitudeTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_SUN_LATITUDE),
                NumberFormat.getInstance());
        JTextField longitudeTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_SUN_LONGITUDE),
                NumberFormat.getInstance());
        disableWhenSelected(useActualPosition, latitudeTextField,
                longitudeTextField);
        PanelBuilder builder = new PanelBuilder(new FormLayout(
                "pref, 4dlu, pref:grow, 4dlu, pref, 4dlu, pref:grow", // columns
                "pref, 4dlu, pref")); // rows
        builder.border(Borders.DLU4);
        builder.add(useActualPosition, CC.xyw(1, 1, 7));
        builder.addLabel("Latitude:", CC.xy(1, 3));
        builder.add(latitudeTextField, CC.xy(3, 3));
        builder.addLabel("Longitude:", CC.xy(5, 3));
        builder.add(longitudeTextField, CC.xy(7, 3));
        return builder.build();
    }

    protected JPanel buildLabelsPanel() {
        JCheckBox displayLegendCheckBox = createCheckBox(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_LEGEND_VISIBLE),
                "Display legend");
        JRadioButton topLeftRadioButton = createRadioButton(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_LEGEND_POSITION),
                LegendPosition.TOP_LEFT, "Top Left");
        JRadioButton topRightRadioButton = createRadioButton(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_LEGEND_POSITION),
                LegendPosition.TOP_RIGHT, "Top Right");
        JRadioButton bottomLeftRadioButton = createRadioButton(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_LEGEND_POSITION),
                LegendPosition.BOTTOM_LEFT, "Bottom Left");
        JRadioButton bottomRightRadioButton = createRadioButton(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_LEGEND_POSITION),
                LegendPosition.BOTTOM_RIGHT, "Bottom Right");
        enableWhenSelected(displayLegendCheckBox, topLeftRadioButton,
                topRightRadioButton, bottomLeftRadioButton,
                bottomRightRadioButton);
        JCheckBox displayMarkersCheckBox = createCheckBox(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_MARKERS_VISIBLE),
                "Display city markers");
        JButton colorButton = createColorButton(presentationModel
                .getBufferedModel(Settings.PROPERTY_MARKER_COLOR));
        JTable table = createTable(new SelectionInList<Marker>(
                presentationModel.getBufferedModel(Settings.PROPERTY_MARKERS)),
                presentationModel.createMarkerTableAdapter());
        table.setPreferredScrollableViewportSize(new Dimension(270, 100));
        JButton addButton = new JButton(presentationModel.getAddMarkerAction());
        JButton removeButton = new JButton(
                presentationModel.getRemoveMarkerAction());
        JButton importButton = new JButton(
                presentationModel.getImportMarkersAction());
        JButton exportButton = new JButton(
                presentationModel.getExportMarkersAction());
        JButton resetButton = new JButton(
                presentationModel.getResetMarkersAction());
        enableWhenSelected(displayMarkersCheckBox, colorButton, table,
                addButton, removeButton, importButton, exportButton,
                resetButton);
        PanelBuilder builder = new PanelBuilder(
                new FormLayout("pref, 4dlu, pref:grow, 4dlu, pref", // columns
                        "pref, pref, pref, 4dlu, pref, 4dlu, pref, 4dlu, fill:pref:grow")); // rows
        builder.border(Borders.DLU4);
        builder.add(displayLegendCheckBox, CC.xy(1, 1));
        builder.add(topLeftRadioButton, CC.xy(1, 2));
        builder.add(topRightRadioButton, CC.xy(3, 2));
        builder.add(bottomLeftRadioButton, CC.xy(1, 3));
        builder.add(bottomRightRadioButton, CC.xy(3, 3));
        builder.add(displayMarkersCheckBox, CC.xy(1, 5));
        builder.addLabel("Color:", CC.xy(1, 7));
        builder.add(colorButton, CC.xy(3, 7));
        builder.add(new JScrollPane(table), CC.xyw(1, 9, 3));
        builder.add(
                new ButtonStackBuilder().addButton(addButton, removeButton,
                        importButton, exportButton, resetButton).build(),
                CC.xy(5, 9));
        return builder.build();
    }

    protected JPanel buildQuakesPanel() {
        JCheckBox showQuakesCheckBox = createCheckBox(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_QUAKES_VISIBLE),
                "Show recent earthquakes");
        JTextField updateIntervalTextField = createIntegerField(presentationModel
                .getBufferedModel(Settings.PROPERTY_QUAKES_UPDATE_INTERVAL));
        JTextField lastUpdatedTextField = createFormattedTextField(
                presentationModel
                        .getModel(Settings.PROPERTY_QUAKES_LAST_UPDATED),
                DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
                        DateFormat.SHORT));
        lastUpdatedTextField.setEditable(false);
        JButton updateButton = new JButton(
                presentationModel.getUpdateQuakesAction());
        JComboBox feedComboBox = createComboBox(presentationModel
                .getQuakesFeedComboBoxAdapter());
        JButton colorButton = createColorButton(presentationModel
                .getBufferedModel(Settings.PROPERTY_QUAKE_COLOR));
        JTable table = createTable(
                new SelectionInList<Quake>(
                        presentationModel.getModel(Settings.PROPERTY_QUAKES)),
                presentationModel.createQuakeTableAdapter());
        table.setDefaultRenderer(Date.class, new DateTableCellRenderer());
        table.setDefaultRenderer(String.class, new ToolTipTableCellRenderer());
        table.setPreferredScrollableViewportSize(new Dimension(300, 100));
        enableWhenSelected(showQuakesCheckBox, updateIntervalTextField,
                colorButton);
        PanelBuilder builder = new PanelBuilder(
                new FormLayout("pref, 4dlu, pref:grow, 4dlu, pref", // columns
                        "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, fill:pref:grow")); // rows
        builder.border(Borders.DLU4);
        builder.add(showQuakesCheckBox, CC.xyw(1, 1, 3));
        builder.addLabel("Update interval (hours):", CC.xy(1, 3));
        builder.add(updateIntervalTextField, CC.xy(3, 3));
        builder.addLabel("Last updated:", CC.xy(1, 5));
        builder.add(lastUpdatedTextField, CC.xy(3, 5));
        builder.add(updateButton, CC.xy(5, 5));
        builder.addLabel("Feed URL:", CC.xy(1, 7));
        builder.add(feedComboBox, CC.xyw(3, 7, 3));
        builder.addLabel("Color:", CC.xy(1, 9));
        builder.add(colorButton, CC.xy(3, 9));
        builder.add(new JScrollPane(table), CC.xyw(1, 11, 5));
        return builder.build();
    }

    protected JPanel buildDotsPanel() {
        JCheckBox displayStarsCheckBox = createCheckBox(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_STARS_VISIBLE),
                "Display stars");
        JTextField starFrequencyTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_STAR_FREQUENCY),
                NumberFormat.getNumberInstance());
        JTextField percentBigStarsTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_PERCENT_BIG_STARS),
                NumberFormat.getNumberInstance());
        enableWhenSelected(displayStarsCheckBox, starFrequencyTextField,
                percentBigStarsTextField);
        JCheckBox displayGridCheckBox = createCheckBox(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_GRID_VISIBLE),
                "Display grid");
        JTextField gridLinesPerQuadrantTextField = createIntegerField(presentationModel
                .getBufferedModel(Settings.PROPERTY_GRID_LINES_PER_QUADRANT));
        JTextField dotsPerGridLineTextField = createIntegerField(presentationModel
                .getBufferedModel(Settings.PROPERTY_DOTS_PER_GRID_LINE));
        JButton gridColorButton = createColorButton(presentationModel
                .getBufferedModel(Settings.PROPERTY_GRID_COLOR));
        enableWhenSelected(displayGridCheckBox, gridLinesPerQuadrantTextField,
                dotsPerGridLineTextField, gridColorButton);
        PanelBuilder builder = new PanelBuilder(
                new FormLayout("pref, 4dlu, pref:grow", // columns
                        "pref, 4dlu, pref, 4dlu, pref, 8dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref")); // rows
        builder.border(Borders.DLU4);
        builder.add(displayStarsCheckBox, CC.xyw(1, 1, 3));
        builder.addLabel("Star frequency:", CC.xy(1, 3));
        builder.add(starFrequencyTextField, CC.xy(3, 3));
        builder.addLabel("Percent big stars:", CC.xy(1, 5));
        builder.add(percentBigStarsTextField, CC.xy(3, 5));
        builder.add(displayGridCheckBox, CC.xyw(1, 7, 3));
        builder.addLabel("Grid lines per quadrant:", CC.xy(1, 9));
        builder.add(gridLinesPerQuadrantTextField, CC.xy(3, 9));
        builder.addLabel("Dots per grid line:", CC.xy(1, 11));
        builder.add(dotsPerGridLineTextField, CC.xy(3, 11));
        builder.addLabel("Color:", CC.xy(1, 13));
        builder.add(gridColorButton, CC.xy(3, 13));
        return builder.build();
    }

    protected JPanel buildShadingPanel() {
        JCheckBox displayShadingCheckBox = createCheckBox(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_SHADING_VISIBLE),
                "Display shading");
        JTextField daylightIntensityTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_DAYLIGHT_INTENSITY),
                NumberFormat.getNumberInstance());
        JTextField nightIntensityTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_NIGHT_INTENSITY),
                NumberFormat.getNumberInstance());
        JTextField terminatorFallOffTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_TERMINATOR_FALL_OFF),
                NumberFormat.getNumberInstance());
        enableWhenSelected(displayShadingCheckBox, daylightIntensityTextField,
                nightIntensityTextField, terminatorFallOffTextField);
        PanelBuilder builder = new PanelBuilder(new FormLayout(
                "pref, 4dlu, pref:grow", // columns
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref")); // rows
        builder.border(Borders.DLU4);
        builder.add(displayShadingCheckBox, CC.xyw(1, 1, 3));
        builder.addLabel("Daylight intensity:", CC.xy(1, 3));
        builder.add(daylightIntensityTextField, CC.xy(3, 3));
        builder.addLabel("Night intensity:", CC.xy(1, 5));
        builder.add(nightIntensityTextField, CC.xy(3, 5));
        builder.addLabel("Terminator fall-off:", CC.xy(1, 7));
        builder.add(terminatorFallOffTextField, CC.xy(3, 7));
        return builder.build();
    }

    protected JPanel buildTimePanel() {
        JCheckBox useCurrentTimeCheckBox = createCheckBox(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_TIME_USE_CURRENT),
                "Use current time");
        JTextField timeToUseTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_TIME_TO_USE),
                DateFormat.getDateTimeInstance());
        JTextField timeWarpFactorTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_TIME_WARP_FACTOR),
                NumberFormat.getNumberInstance());
        disableWhenSelected(useCurrentTimeCheckBox, timeToUseTextField,
                timeWarpFactorTextField);
        PanelBuilder builder = new PanelBuilder(new FormLayout(
                "pref, 4dlu, pref:grow", // columns
                "pref, 4dlu, pref, 4dlu, pref")); // rows
        builder.border(Borders.DLU4);
        builder.add(useCurrentTimeCheckBox, CC.xyw(1, 1, 3));
        builder.addLabel("Time to use:", CC.xy(1, 3));
        builder.add(timeToUseTextField, CC.xy(3, 3));
        builder.addLabel("Time warp factor", CC.xy(1, 5));
        builder.add(timeWarpFactorTextField, CC.xy(3, 5));
        return builder.build();
    }

    protected JPanel buildDisplayPanel() {
        JTextField updateIntervalTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_DISPLAY_UPDATE_INTERVAL),
                NumberFormat.getInstance());
        JTextField gammaCorrectionTextField = createFormattedTextField(
                presentationModel
                        .getBufferedModel(Settings.PROPERTY_GAMMA_CORRECTION),
                NumberFormat.getInstance());
        PanelBuilder builder = new PanelBuilder(new FormLayout(
                "pref, 4dlu, pref:grow", // columns
                "pref, 4dlu, pref")); // rows
        builder.border(Borders.DLU4);
        builder.addLabel("Update interval (minutes):", CC.xy(1, 1));
        builder.add(updateIntervalTextField, CC.xy(3, 1));
        builder.addLabel("Gamma correction:", CC.xy(1, 3));
        builder.add(gammaCorrectionTextField, CC.xy(3, 3));
        return builder.build();
    }

    /**
     * Enables some components whenever a toggle button is selected.
     * @param toggleButton
     *            the button controlling the enablement of the components
     * @param components
     *            the components to be enabled when the toggle button selected
     */
    private static void enableWhenSelected(final JToggleButton toggleButton,
            final JComponent... components) {
        enableWhenSelected(toggleButton, false, components);
    }

    /**
     * Disables some components whenever a toggle button is selected.
     * @param toggleButton
     *            the button controlling the enablement of the components
     * @param components
     *            the components to be disabled when the toggle button selected
     */
    private static void disableWhenSelected(final JToggleButton toggleButton,
            final JComponent... components) {
        enableWhenSelected(toggleButton, true, components);
    }

    private static void enableWhenSelected(final JToggleButton toggleButton,
            final boolean invert, final JComponent... components) {
        for (JComponent component : components) {
            component.setEnabled(toggleButton.isSelected() ^ invert);
        }
        toggleButton.getModel().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent e) {
                for (JComponent component : components) {
                    component.setEnabled(toggleButton.isSelected() ^ invert);
                }
            }
        });
    }

    /**
     * Creates and returns a table that turns the SelectionInList's row data
     * into a TableModel using the given adapter. The table's selection is bound
     * to the SelectionInList's single selection. Similar to
     * <code>BasicComponentFactory.createTable</code> except the table is
     * created with RowSorter.
     * @param <E>
     *            the row data type
     * @param selectionInList
     *            provides the row data and selection index
     * @param tableAdapter
     *            converts row data to TableModel
     * @return the bound table
     */
    protected static <E> JTable createTable(
            final SelectionInList<E> selectionInList,
            final AbstractTableAdapter<E> tableAdapter) {
        JTable table = new JTable(tableAdapter);
        // Must do setAutoCreateRowSorter before binding, otherwise the table
        // would get a wrong ListSelectionModel.
        table.setAutoCreateRowSorter(true);
        Bindings.bind(table, selectionInList);
        return table;
    }

    /**
     * @param <E>
     *            the type of the combo box items
     * @param comboBoxAdapter
     *            converts item data to ComboBoxModel
     * @return the bound combo box
     */
    protected static <E> JComboBox createComboBox(
            final ComboBoxAdapter<E> comboBoxAdapter) {
        JComboBox comboBox = new JComboBox();
        Bindings.bind(comboBox, comboBoxAdapter);
        comboBox.setRenderer(new ShorteningListCellRenderer());
        return comboBox;
    }

    /**
     * Creates and returns a button that is bound to the Color value of the
     * given ValueModel.
     * @param valueModel
     *            the model that provides a Color value
     * @return a bound button
     * @throws NullPointerException
     *             if the valueModel is {@code null}
     */
    protected static JButton createColorButton(final ValueModel valueModel) {
        JButton colorButton = new ColorButton();
        Bindings.bind(colorButton, ColorButton.PROPERTY_COLOR, valueModel);
        return colorButton;
    }
}
