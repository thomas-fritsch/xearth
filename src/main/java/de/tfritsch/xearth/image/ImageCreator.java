package de.tfritsch.xearth.image;

import static de.tfritsch.astronomy.DegreeMath.cos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import com.jgoodies.common.bean.Bean;

import de.tfritsch.astronomy.Point3D;
import de.tfritsch.astronomy.SkyObjects;
import de.tfritsch.common.GammaOp;
import de.tfritsch.xearth.Marker;
import de.tfritsch.xearth.Quake;
import de.tfritsch.xearth.Settings;

@SuppressWarnings("serial")
public class ImageCreator extends Bean {

    private final Settings settings;

    private FlatMap flatMap;

    private int imageWidth;
    private int imageHeight;
    private double imageRadius;
    private double viewpointLongitude;
    private double viewpointLatitude;
    private GammaOp gammaOp;
    private Point3D normalizedSunPosition;

    public ImageCreator(final Settings settings) {
        this.settings = settings;
        try {
            flatMap = new FlatMap();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() {
        if (settings.isUseScreenSize()) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            imageWidth = screenSize.width;
            imageHeight = screenSize.height;
        } else {
            imageWidth = settings.getImageWidth();
            imageHeight = settings.getImageHeight();
        }
        imageRadius = settings.getImageMagnification()
                * Math.min(imageWidth, imageHeight) / 2;
        gammaOp = new GammaOp(settings.getGammaCorrection());
        Point3D viewpoint = settings.getViewpointType().getViewpoint(settings);
        viewpointLongitude = viewpoint.getLongitude();
        viewpointLatitude = viewpoint.getLatitude();
        normalizedSunPosition = SkyObjects.SUN.getGeographicalPosition(
                new Date()).normalize();
    }

    public synchronized BufferedImage createImage() {
        init();
        BufferedImage image = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_INT_RGB);
        drawEarth(image);
        Graphics g = image.createGraphics();
        if (settings.isLegendVisible())
            drawLegend(g);
        if (settings.isGridVisible())
            drawGrid(image);
        if (settings.isMarkersVisible())
            drawMarkers(g);
        if (settings.isQuakesVisible())
            drawQuakes(g);
        g.dispose();
        return image;
    }

    /**
     * Converts an image pixel position to a geographical position on earth. It
     * is the inverse conversion of {@link #getImagePoint(double, double)}.
     * @param x
     *            the X coordinate of the pixel (in range [0, imageWidth - 1])
     * @param y
     *            the Y coordinate of the pixel (in range [0, imageHeight - 1])
     * @return a geographical position on earth (normalized to
     *         {@link Point3D#getRadius()} == 1),<br>
     *         or null if the image position is outside of the earth
     */
    protected Point3D getGeographicalPoint(final int x, final int y) {
        // transform to range [-1, +1]
        double geoY = (x - imageWidth / 2.0) / imageRadius;
        // transform to range [-1, +1]
        double geoZ = (imageHeight / 2.0 - y) / imageRadius;
        double geoX = Math.sqrt(1 - geoY * geoY - geoZ * geoZ);
        if (Double.isNaN(geoX)) // outside of the earth
            return null;
        return new Point3D(geoX, geoY, geoZ) //
                .rotateAroundYAxis(-viewpointLatitude) //
                .rotateAroundZAxis(viewpointLongitude);
    }

    /**
     * Converts a geographical position on earth to an image pixel position. It
     * is the inverse conversion of {@link #getGeographicalPoint(int, int)}.
     * @param longitude
     *            geographical longitude on earth (measured in degrees)
     * @param latitude
     *            geographical latitude on earth (measured in degrees)
     * @return an image pixel position (with {@link Point#x} in range [0,
     *         imageWidth -1] and {@link Point#y} in range [0, imageHeight -
     *         1]),<br>
     *         or null if the position is invisible (i.e. on back side of the
     *         earth) or would be outside of the image rectangle
     */
    protected Point getImagePoint(final double longitude, final double latitude) {
        Point3D point3D = Point3D.fromSpherical(longitude, latitude, 1)
                .rotateAroundZAxis(-viewpointLongitude)
                .rotateAroundYAxis(viewpointLatitude);
        if (point3D.getX() < 0) // on back side of earth
            return null;
        Point point = new Point();
        point.x = (int) (imageWidth / 2.0 + imageRadius * point3D.getY());
        point.y = (int) (imageHeight / 2.0 - imageRadius * point3D.getZ());
        if (point.x < 0 || point.x >= imageWidth) // outside of image rectangle
            return null;
        if (point.y < 0 || point.y >= imageHeight) // outside of image rectangle
            return null;
        return point;
    }

    /**
     * Draws earth and stars.
     * @param image
     *            the Image where to draw
     */
    protected void drawEarth(final BufferedImage image) {
        int starRGB = Color.WHITE.getRGB();
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                Point3D point = getGeographicalPoint(x, y);
                if (point != null) {
                    // point on earth
                    int rgb = flatMap.getRGB(point.getLongitude(),
                            point.getLatitude());
                    if (settings.isShadingVisible()) {
                        double cosPhi = point.dotProduct(normalizedSunPosition);
                        rgb = shadeRGB(rgb, cosPhi);
                    }
                    image.setRGB(x, y, rgb);
                } else {
                    // point in sky
                    if (settings.isStarsVisible()
                            && Math.random() < settings.getStarFrequency()) {
                        image.setRGB(x, y, starRGB);
                    }
                }
            }
        }
        gammaOp.filter(image, image);
    }

    /**
     * Calculate shading.
     * @param rgb
     *            original RGB value of a point on earth
     * @param cosPhi
     *            cosine of angle between sun position and point on earth
     * @return shaded RGB value of the point on earth
     */
    private int shadeRGB(final int rgb, final double cosPhi) {
        double factor = Math.max(0, cosPhi);
        // transform from [0, 1] to [nightIntensity/100, daylightIntensity/100]
        factor = (1 - factor) * settings.getNightIntensity() / 100 + factor
                * settings.getDaylightIntensity() / 100;
        int r = (rgb & 0xff0000) >> 16;
        int g = (rgb & 0xff00) >> 8;
        int b = rgb & 0xff;
        r = (int) (r * factor);
        g = (int) (g * factor);
        b = (int) (b * factor);
        return 0xff000000 | (r << 16) | (g << 8) | b;
    }

    /**
     * Draws the legend.
     * @param g
     *            the Graphics context where to draw
     */
    protected void drawLegend(final Graphics g) {
        g.setColor(Color.WHITE);
        DateFormat dateFormat = DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM, DateFormat.SHORT);
        DecimalFormat numberFormat = new DecimalFormat("##0.0");
        Date time = new Date();
        Point3D sunPosition = SkyObjects.SUN.getGeographicalPosition(time);
        String s1 = "Time: " + dateFormat.format(time);
        String s2 = "View: " + numberFormat.format(Math.abs(viewpointLatitude))
                + "\u00b0" + (viewpointLatitude < 0 ? "S" : "N") + "  "
                + numberFormat.format(Math.abs(viewpointLongitude)) + "\u00b0"
                + (viewpointLongitude < 0 ? "W" : "E");
        String s3 = "Sun: "
                + numberFormat.format(Math.abs(sunPosition.getLatitude()))
                + "\u00b0" + (sunPosition.getLatitude() < 0 ? "S" : "N") + "  "
                + numberFormat.format(Math.abs(sunPosition.getLongitude()))
                + "\u00b0" + (sunPosition.getLongitude() < 0 ? "W" : "E");
        FontMetrics metrics = g.getFontMetrics();
        int h = metrics.getHeight();
        int x, y;
        switch (settings.getLegendPosition()) {
        case TOP_LEFT:
            x = 2;
            y = metrics.getAscent();
            g.drawString(s1, x, y);
            g.drawString(s2, x, y + h);
            g.drawString(s3, x, y + 2 * h);
            break;
        case TOP_RIGHT:
            x = imageWidth - 2;
            y = metrics.getAscent();
            g.drawString(s1, x - metrics.stringWidth(s1), y);
            g.drawString(s2, x - metrics.stringWidth(s2), y + h);
            g.drawString(s3, x - metrics.stringWidth(s3), y + 2 * h);
            break;
        case BOTTOM_LEFT:
            x = 2;
            y = imageHeight - 2 - metrics.getDescent();
            g.drawString(s1, x, y - 2 * h);
            g.drawString(s2, x, y - h);
            g.drawString(s3, x, y);
            break;
        case BOTTOM_RIGHT:
            x = imageWidth - 2;
            y = imageHeight - 2 - metrics.getDescent();
            g.drawString(s1, x - metrics.stringWidth(s1), y - 2 * h);
            g.drawString(s2, x - metrics.stringWidth(s2), y - h);
            g.drawString(s3, x - metrics.stringWidth(s3), y);
            break;
        default:
            // should not happen
            break;
        }
    }

    /**
     * Draws grid lines.
     * @param image
     *            the Image where to draw
     */
    protected void drawGrid(final BufferedImage image) {
        int gridRGB = settings.getGridColor().getRGB();
        double dLongitude = 90.0 / settings.getGridLinesPerQuadrant();
        double dLatitude = dLongitude / settings.getDotsPerGridLine();
        for (double longitude = -180; longitude < 180; longitude += dLongitude) {
            for (int latitude = -90; latitude <= 90; latitude += dLatitude) {
                Point point = getImagePoint(longitude, latitude);
                if (point != null)
                    image.setRGB(point.x, point.y, gridRGB);
            }
        }
        dLatitude = 90.0 / settings.getGridLinesPerQuadrant();
        for (double latitude = -90; latitude <= 90; latitude += dLatitude) {
            dLongitude = dLatitude
                    / Math.round(settings.getDotsPerGridLine() * cos(latitude));
            for (double longitude = -180; longitude < 180; longitude += dLongitude) {
                Point point = getImagePoint(longitude, latitude);
                if (point != null)
                    image.setRGB(point.x, point.y, gridRGB);
            }
        }
    }

    /**
     * Draws city markers.
     * @param g
     *            the Graphics context where to draw
     */
    protected void drawMarkers(final Graphics g) {
        g.setColor(settings.getMarkerColor());
        for (Marker marker : settings.getMarkers()) {
            Point point = getImagePoint(marker.getLongitude(),
                    marker.getLatitude());
            if (point != null) {
                int d = 6;
                g.drawOval(point.x - d / 2, point.y - d / 2, d, d);
                g.drawString(marker.getName(), point.x + 6, point.y);
            }
        }
    }

    /**
     * Draws earthquake markers.
     * @param g
     *            the Graphics context where to draw
     */
    protected void drawQuakes(final Graphics g) {
        g.setColor(settings.getQuakeColor());
        for (Quake quake : settings.getQuakes()) {
            Point point = getImagePoint(quake.getLongitude(),
                    quake.getLatitude());
            if (point != null) {
                int d = (int) (Math.exp(quake.getMagnitude()) / 30);
                g.drawOval(point.x - d / 2, point.y - d / 2, d, d);
            }
        }
    }

}
