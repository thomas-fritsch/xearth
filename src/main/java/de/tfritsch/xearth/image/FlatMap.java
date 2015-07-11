package de.tfritsch.xearth.image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * A model of the colored surface of the earth. It is based on an
 * equirectangular projection of the earth.
 * @author Thomas Fritsch
 * @see <a href= "http://en.wikipedia.org/wiki/Equirectangular_projection">
 *      Wikipedia: Equirectangular projection</a>
 * @see <a href= "http://visibleearth.nasa.gov/view_cat.php?categoryID=1484">
 *      NASA Visible Earth: Blue Marble</a>
 * @see <a href="http://www.radcyberzine.com/xglobe/">Xglobe/Xplanet maps</a>
 */
public class FlatMap {

    /**
     * A local copy of the <a href=
     * "http://eoimages.gsfc.nasa.gov/images/imagerecords/57000/57730/land_ocean_ice_2048.jpg"
     * > Blue Marble</a> image.
     * @see <a href= "http://visibleearth.nasa.gov/view.php?id=57730">NASA
     *      Visible Earth: The Blue Marble</a>
     */
    public static final URL DEFAULT_IMAGE_URL = FlatMap.class
            .getResource("land_ocean_ice_2048.jpg");

    private BufferedImage image;
    private int imageWidth;
    private int imageHeight;

    /**
     * Creates a flat map from {@link #DEFAULT_IMAGE_URL}.
     * @throws IOException
     *             if an error occurs during reading the image
     */
    public FlatMap() throws IOException {
        this(DEFAULT_IMAGE_URL);
    }

    /**
     * Creates a flat map from an image URL.
     * @param url
     *            from where to read the image
     * @throws IOException
     *             if an error occurs during reading the image
     */
    public FlatMap(final URL url) throws IOException {
        image = ImageIO.read(url);
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
    }

    /**
     * Gets the color of a point on the earth's surface.
     * @param longitude
     *            the geographical longitude measured in degrees
     * @param latitude
     *            the geographical latitude measured in degrees
     * @return the RGB value (as specified by {@link java.awt.Color#getRGB})
     * @throws IllegalArgumentException
     *             if longitude is outside [-180, +180] or latitude is outside
     *             [-90, +90]
     */
    public final int getRGB(final double longitude, final double latitude) {
        if (longitude < -180 || longitude > 180)
            throw new IllegalArgumentException("longitude=" + longitude);
        if (latitude < -90 || latitude > 90)
            throw new IllegalArgumentException("latitude=" + latitude);
        // transform to range [0, imageWidth - 1]
        int x = (int) ((180.0 + longitude) / 360.0 * imageWidth - 1e-4 * longitude);
        // transform to range [0, imageHeight - 1]
        int y = (int) ((90.0 - latitude) / 180.0 * imageHeight + 1e-4 * latitude);
        return image.getRGB(x, y);
    }

}
