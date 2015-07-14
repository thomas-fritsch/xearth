package de.tfritsch.xearth;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.jgoodies.binding.beans.Model;

import de.tfritsch.common.ColorXmlAdapter;

/**
 * XEarth settings.
 * @author Thomas Fritsch
 */
@SuppressWarnings("serial")
@XmlRootElement
public class Settings extends Model {

    public static final String PROPERTY_IMAGE_PROJECTION = "imageProjection";

    private ImageProjection imageProjection = ImageProjection.ORTHOGRAPHIC;

    public final ImageProjection getImageProjection() {
        return imageProjection;
    }

    public final void setImageProjection(final ImageProjection newValue) {
        ImageProjection oldValue = imageProjection;
        imageProjection = newValue;
        firePropertyChange(PROPERTY_IMAGE_PROJECTION, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_IMAGE_MAGNIFICATION = "imageMagnification";

    private double imageMagnification = 1;

    public final double getImageMagnification() {
        return imageMagnification;
    }

    public final void setImageMagnification(final double newValue) {
        double oldValue = imageMagnification;
        this.imageMagnification = newValue;
        firePropertyChange(PROPERTY_IMAGE_MAGNIFICATION, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_USE_SCREEN_SIZE = "useScreenSize";

    private boolean useScreenSize = false;

    public final boolean isUseScreenSize() {
        return useScreenSize;
    }

    public final void setUseScreenSize(final boolean newValue) {
        boolean oldValue = useScreenSize;
        this.useScreenSize = newValue;
        firePropertyChange(PROPERTY_USE_SCREEN_SIZE, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_IMAGE_WIDTH = "imageWidth";

    private int imageWidth = 800;

    public final int getImageWidth() {
        return imageWidth;
    }

    public final void setImageWidth(final int newValue) {
        int oldValue = imageWidth;
        this.imageWidth = newValue;
        firePropertyChange(PROPERTY_IMAGE_WIDTH, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_IMAGE_HEIGHT = "imageHeight";

    private int imageHeight = 600;

    public final int getImageHeight() {
        return imageHeight;
    }

    public final void setImageHeight(final int newValue) {
        int oldValue = imageHeight;
        this.imageHeight = newValue;
        firePropertyChange(PROPERTY_IMAGE_HEIGHT, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_VIEWPOINT_TYPE = "viewpointType";

    private ViewpointType viewpointType = ViewpointType.DEFAULT;

    public final ViewpointType getViewpointType() {
        return viewpointType;
    }

    public final void setViewpointType(final ViewpointType newValue) {
        ViewpointType oldValue = viewpointType;
        viewpointType = newValue;
        firePropertyChange(PROPERTY_VIEWPOINT_TYPE, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_FIXED_LONGITUDE = "fixedLongitude";

    private double fixedLongitude;

    public final double getFixedLongitude() {
        return fixedLongitude;
    }

    public final void setFixedLongitude(final double newValue) {
        double oldValue = fixedLongitude;
        fixedLongitude = newValue;
        firePropertyChange(PROPERTY_FIXED_LONGITUDE, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_FIXED_LATITUDE = "fixedLatitude";

    private double fixedLatitude;

    public final double getFixedLatitude() {
        return fixedLatitude;
    }

    public final void setFixedLatitude(final double newValue) {
        double oldValue = fixedLatitude;
        fixedLatitude = newValue;
        firePropertyChange(PROPERTY_FIXED_LATITUDE, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_SUN_RELATIVE_LONGITUDE = "sunRelativeLongitude";

    private double sunRelativeLongitude;

    public final double getSunRelativeLongitude() {
        return sunRelativeLongitude;
    }

    public final void setSunRelativeLongitude(final double newValue) {
        double oldValue = sunRelativeLongitude;
        sunRelativeLongitude = newValue;
        firePropertyChange(PROPERTY_SUN_RELATIVE_LONGITUDE, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_SUN_RELATIVE_LATITUDE = "sunRelativeLatitude";

    private double sunRelativeLatitude;

    public final double getSunRelativeLatitude() {
        return sunRelativeLatitude;
    }

    public final void setSunRelativeLatitude(final double newValue) {
        double oldValue = sunRelativeLatitude;
        sunRelativeLatitude = newValue;
        firePropertyChange(PROPERTY_SUN_RELATIVE_LATITUDE, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_ORBIT_PERIOD = "orbitPeriod";

    private double orbitPeriod = 1;

    public final double getOrbitPeriod() {
        return orbitPeriod;
    }

    public final void setOrbitPeriod(final double newValue) {
        double oldValue = orbitPeriod;
        orbitPeriod = newValue;
        firePropertyChange(PROPERTY_ORBIT_PERIOD, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_ORBIT_INCLINATION = "orbitInclination";

    private double orbitInclination;

    public final double getOrbitInclination() {
        return orbitInclination;
    }

    public final void setOrbitInclination(final double newValue) {
        double oldValue = orbitInclination;
        orbitInclination = newValue;
        firePropertyChange(PROPERTY_ORBIT_INCLINATION, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_SUN_USE_ACTUAL_POSITION = "sunUseActualPosition";

    private boolean sunUseActualPosition = true;

    public final boolean getSunUseActualPosition() {
        return sunUseActualPosition;
    }

    public final void setSunUseActualPosition(final boolean newValue) {
        boolean oldValue = sunUseActualPosition;
        this.sunUseActualPosition = newValue;
        firePropertyChange(PROPERTY_SUN_USE_ACTUAL_POSITION, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_SUN_LATITUDE = "sunLatitude";

    private double sunLatitude = 0;

    public final double getSunLatitude() {
        return sunLatitude;
    }

    public final void setSunLatitude(final double newValue) {
        double oldValue = sunLatitude;
        sunLatitude = newValue;
        firePropertyChange(PROPERTY_SUN_LATITUDE, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_SUN_LONGITUDE = "sunLongitude";

    private double sunLongitude = 0;

    public final double getSunLongitude() {
        return sunLongitude;
    }

    public final void setSunLongitude(final double newValue) {
        double oldValue = sunLongitude;
        sunLongitude = newValue;
        firePropertyChange(PROPERTY_SUN_LONGITUDE, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_LEGEND_VISIBLE = "legendVisible";

    private boolean legendVisible = false;

    public final boolean isLegendVisible() {
        return legendVisible;
    }

    public final void setLegendVisible(final boolean newValue) {
        boolean oldValue = legendVisible;
        legendVisible = newValue;
        firePropertyChange(PROPERTY_LEGEND_VISIBLE, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_LEGEND_POSITION = "legendPosition";

    private LegendPosition legendPosition = LegendPosition.TOP_LEFT;

    public final LegendPosition getLegendPosition() {
        return legendPosition;
    }

    public final void setLegendPosition(final LegendPosition newValue) {
        LegendPosition oldValue = legendPosition;
        legendPosition = newValue;
        firePropertyChange(PROPERTY_LEGEND_POSITION, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_MARKERS_VISIBLE = "markersVisible";

    private boolean markersVisible = true;

    public final boolean isMarkersVisible() {
        return markersVisible;
    }

    public final void setMarkersVisible(final boolean newValue) {
        boolean oldValue = markersVisible;
        markersVisible = newValue;
        firePropertyChange(PROPERTY_MARKERS_VISIBLE, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_MARKER_COLOR = "markerColor";

    private Color markerColor = Color.RED;

    @XmlJavaTypeAdapter(ColorXmlAdapter.class)
    public final Color getMarkerColor() {
        return markerColor;
    }

    public final void setMarkerColor(final Color newValue) {
        Color oldValue = markerColor;
        markerColor = newValue;
        firePropertyChange(PROPERTY_MARKER_COLOR, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_MARKERS = "markers";

    private List<Marker> markers = Utilities.getInitialMarkers();

    @XmlElementWrapper(name = "markers")
    @XmlElement(name = "marker")
    public final List<Marker> getMarkers() {
        return markers;
    }

    public final void setMarkers(final List<Marker> newValue) {
        List<Marker> oldValue = markers;
        markers = newValue;
        firePropertyChange(PROPERTY_MARKERS, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_QUAKES_VISIBLE = "quakesVisible";

    private boolean quakesVisible = false;

    public final boolean isQuakesVisible() {
        return quakesVisible;
    }

    public final void setQuakesVisible(final boolean newValue) {
        boolean oldValue = quakesVisible;
        quakesVisible = newValue;
        firePropertyChange(PROPERTY_QUAKES_VISIBLE, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_QUAKES_UPDATE_INTERVAL = "quakesUpdateInterval";

    private int quakesUpdateInterval = 3;

    public final int getQuakesUpdateInterval() {
        return quakesUpdateInterval;
    }

    public final void setQuakesUpdateInterval(final int newValue) {
        int oldValue = quakesUpdateInterval;
        quakesUpdateInterval = newValue;
        firePropertyChange(PROPERTY_QUAKES_UPDATE_INTERVAL, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_QUAKES_FEED_URL = "quakesFeedURL";

    private String quakesFeedURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/significant_week.csv";

    public final String getQuakesFeedURL() {
        return quakesFeedURL;
    }

    public final void setQuakesFeedURL(final String newValue) {
        String oldValue = quakesFeedURL;
        quakesFeedURL = newValue;
        firePropertyChange(PROPERTY_QUAKES_FEED_URL, oldValue, newValue);
    }

    public static final String PROPERTY_QUAKES_LAST_UPDATED = "quakesLastUpdated";

    // -----------------------------------------------------------------

    private Date quakesLastUpdated;

    public final Date getQuakesLastUpdated() {
        return quakesLastUpdated;
    }

    public final void setQuakesLastUpdated(final Date newValue) {
        Date oldValue = quakesLastUpdated;
        quakesLastUpdated = newValue;
        firePropertyChange(PROPERTY_QUAKES_LAST_UPDATED, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_QUAKE_COLOR = "quakeColor";

    private Color quakeColor = Color.YELLOW;

    @XmlJavaTypeAdapter(ColorXmlAdapter.class)
    public final Color getQuakeColor() {
        return quakeColor;
    }

    public final void setQuakeColor(final Color newValue) {
        Color oldValue = quakeColor;
        quakeColor = newValue;
        firePropertyChange(PROPERTY_QUAKE_COLOR, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_QUAKES = "quakes";

    private List<Quake> quakes = new ArrayList<Quake>();

    @XmlElementWrapper(name = "quakes")
    @XmlElement(name = "quake")
    public final List<Quake> getQuakes() {
        return quakes;
    }

    public final void setQuakes(final List<Quake> newValue) {
        List<Quake> oldValue = quakes;
        quakes = newValue;
        firePropertyChange(PROPERTY_QUAKES, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_STARS_VISIBLE = "starsVisible";

    private boolean starsVisible = true;

    public final boolean isStarsVisible() {
        return starsVisible;
    }

    public final void setStarsVisible(final boolean newValue) {
        boolean oldValue = starsVisible;
        starsVisible = newValue;
        firePropertyChange(PROPERTY_STARS_VISIBLE, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_STAR_FREQUENCY = "starFrequency";

    private double starFrequency = 0.002;

    public final double getStarFrequency() {
        return starFrequency;
    }

    public final void setStarFrequency(final double newValue) {
        double oldValue = starFrequency;
        starFrequency = newValue;
        firePropertyChange(PROPERTY_STAR_FREQUENCY, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_PERCENT_BIG_STARS = "percentBigStars";

    private double percentBigStars = 0;

    public final double getPercentBigStars() {
        return percentBigStars;
    }

    public final void setPercentBigStars(final double newValue) {
        double oldValue = percentBigStars;
        percentBigStars = newValue;
        firePropertyChange(PROPERTY_PERCENT_BIG_STARS, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_GRID_VISIBLE = "gridVisible";

    private boolean gridVisible = false;

    public final boolean isGridVisible() {
        return gridVisible;
    }

    public final void setGridVisible(final boolean newValue) {
        boolean oldValue = gridVisible;
        gridVisible = newValue;
        firePropertyChange(PROPERTY_GRID_VISIBLE, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_GRID_COLOR = "gridColor";

    private Color gridColor = Color.WHITE;

    @XmlJavaTypeAdapter(ColorXmlAdapter.class)
    public final Color getGridColor() {
        return gridColor;
    }

    public final void setGridColor(final Color newValue) {
        Color oldValue = gridColor;
        gridColor = newValue;
        firePropertyChange(PROPERTY_GRID_COLOR, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_GRID_LINES_PER_QUADRANT = "gridLinesPerQuadrant";

    private int gridLinesPerQuadrant = 6;

    public final int getGridLinesPerQuadrant() {
        return gridLinesPerQuadrant;
    }

    public final void setGridLinesPerQuadrant(final int newValue) {
        int oldValue = gridLinesPerQuadrant;
        gridLinesPerQuadrant = newValue;
        firePropertyChange(PROPERTY_GRID_LINES_PER_QUADRANT, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_DOTS_PER_GRID_LINE = "dotsPerGridLine";

    private int dotsPerGridLine = 15;

    public final int getDotsPerGridLine() {
        return dotsPerGridLine;
    }

    public final void setDotsPerGridLine(final int newValue) {
        int oldValue = dotsPerGridLine;
        dotsPerGridLine = newValue;
        firePropertyChange(PROPERTY_DOTS_PER_GRID_LINE, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_SHADING_VISIBLE = "shadingVisible";

    private boolean shadingVisible = true;

    public final boolean isShadingVisible() {
        return shadingVisible;
    }

    public final void setShadingVisible(final boolean newValue) {
        boolean oldValue = shadingVisible;
        shadingVisible = newValue;
        firePropertyChange(PROPERTY_SHADING_VISIBLE, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_DAYLIGHT_INTENSITY = "daylightIntensity";

    private double daylightIntensity = 100;

    public final double getDaylightIntensity() {
        return daylightIntensity;
    }

    public final void setDaylightIntensity(final double newValue) {
        double oldValue = daylightIntensity;
        daylightIntensity = newValue;
        firePropertyChange(PROPERTY_DAYLIGHT_INTENSITY, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_NIGHT_INTENSITY = "nightIntensity";

    private double nightIntensity = 5;

    public final double getNightIntensity() {
        return nightIntensity;
    }

    public final void setNightIntensity(final double newValue) {
        double oldValue = nightIntensity;
        nightIntensity = newValue;
        firePropertyChange(PROPERTY_NIGHT_INTENSITY, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_TERMINATOR_FALL_OFF = "terminatorFallOff";

    private double terminatorFallOff = 1;

    public final double getTerminatorFallOff() {
        return terminatorFallOff;
    }

    public final void setTerminatorFallOff(final double newValue) {
        double oldValue = terminatorFallOff;
        terminatorFallOff = newValue;
        firePropertyChange(PROPERTY_TERMINATOR_FALL_OFF, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_TIME_USE_CURRENT = "timeUseCurrent";

    private boolean timeUseCurrent = true;

    public final boolean getTimeUseCurrent() {
        return timeUseCurrent;
    }

    public final void setTimeUseCurrent(final boolean newValue) {
        boolean oldValue = timeUseCurrent;
        timeUseCurrent = newValue;
        firePropertyChange(PROPERTY_TIME_USE_CURRENT, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_TIME_TO_USE = "timeToUse";

    private Date timeToUse = new Date();

    public final Date getTimeToUse() {
        return timeToUse;
    }

    public final void setTimeToUse(final Date newValue) {
        Date oldValue = timeToUse;
        timeToUse = newValue;
        firePropertyChange(PROPERTY_TIME_TO_USE, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_TIME_WARP_FACTOR = "timeWarpFactor";

    private double timeWarpFactor = 1;

    public final double getTimeWarpFactor() {
        return timeWarpFactor;
    }

    public final void setTimeWarpFactor(final double newValue) {
        double oldValue = timeWarpFactor;
        timeWarpFactor = newValue;
        firePropertyChange(PROPERTY_TIME_WARP_FACTOR, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_DISPLAY_UPDATE_INTERVAL = "displayUpdateInterval";

    private double displayUpdateInterval = 5;

    public final double getDisplayUpdateInterval() {
        return displayUpdateInterval;
    }

    public final void setDisplayUpdateInterval(final double newValue) {
        double oldValue = displayUpdateInterval;
        displayUpdateInterval = newValue;
        firePropertyChange(PROPERTY_DISPLAY_UPDATE_INTERVAL, oldValue, newValue);
    }

    // -----------------------------------------------------------------

    public static final String PROPERTY_GAMMA_CORRECTION = "gammaCorrection";

    private double gammaCorrection = 1;

    public final double getGammaCorrection() {
        return gammaCorrection;
    }

    public final void setGammaCorrection(final double newValue) {
        double oldValue = gammaCorrection;
        gammaCorrection = newValue;
        firePropertyChange(PROPERTY_GAMMA_CORRECTION, oldValue, newValue);
    }

}
