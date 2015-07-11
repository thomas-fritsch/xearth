package de.tfritsch.astronomy;

import static de.tfritsch.astronomy.DegreeMath.asin;
import static de.tfritsch.astronomy.DegreeMath.atan2;
import static de.tfritsch.astronomy.DegreeMath.cos;
import static de.tfritsch.astronomy.DegreeMath.sin;

/**
 * A point representing a location in 3-dimensional space. Instances of this
 * class are immutable; their values cannot be changed after they are created.
 * The API of this class uses <b>degrees</b> as unit of angles.
 * @author Thomas Fritsch
 * @see <a href=
 *      "http://www.euclideanspace.com/maths/geometry/space/coordinates/polar/spherical/">
 *      Polar Spherical coordinates</a>
 */
public class Point3D {

    /**
     * Creates a point from spherical coordinates.
     * @param longitude
     *            the longitude
     * @param latitude
     *            the latitude
     * @param radius
     *            the radius
     * @return a new point
     */
    public static Point3D fromSpherical(final double longitude,
            final double latitude, final double radius) {
        double x = radius * cos(latitude) * cos(longitude);
        double y = radius * cos(latitude) * sin(longitude);
        double z = radius * sin(latitude);
        return new Point3D(x, y, z);
    }

    private final double x;
    private final double y;
    private final double z;

    /**
     * Creates a point from Cartesian coordinates.
     * @param x
     *            The X coordinate
     * @param y
     *            The Y coordinate
     * @param z
     *            The Z coordinate
     */
    public Point3D(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public final double getX() {
        return x;
    }

    public final double getY() {
        return y;
    }

    public final double getZ() {
        return z;
    }

    /**
     * Returns a string representation of this point and its location in the
     * {@code (x,y,z)} coordinate space. This method is intended to be used only
     * for debugging purposes.
     */
    @Override
    public final String toString() {
        return getClass().getName() + "[x=" + x + ", y=" + y + ", z=" + z + "]";
    }

    /**
     * @return the magnitude of the vector
     */
    public final double getRadius() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * @return the longitude measured in degrees (in range [-180, +180])
     */
    public final double getLongitude() {
        return atan2(y, x);
    }

    /**
     * @return the latitude measured in degrees (in range [-90, +90])
     */
    public final double getLatitude() {
        return asin(z / getRadius());
    }

    /**
     * Returns a point resulting from rotating this point around the X axis.
     * Rotating by a positive angle theta rotates points on the positive Y axis
     * toward the positive Z axis.
     * @param theta
     *            the angle of rotation measured in degrees
     * @return the rotated point
     */
    public final Point3D rotateAroundXAxis(final double theta) {
        double cos = cos(theta);
        double sin = sin(theta);
        return new Point3D(x, cos * y - sin * z, sin * y + cos * z);
    }

    /**
     * Returns a point resulting from rotating this point around the Y axis.
     * Rotating by a positive angle theta rotates points on the positive Z axis
     * toward the positive X axis.
     * @param theta
     *            the angle of rotation measured in degrees
     * @return the rotated point
     */
    public final Point3D rotateAroundYAxis(final double theta) {
        double cos = cos(theta);
        double sin = sin(theta);
        return new Point3D(sin * z + cos * x, y, cos * z - sin * x);
    }

    /**
     * Returns a point resulting from rotating this point around the Z axis.
     * Rotating by a positive angle theta rotates points on the positive X axis
     * toward the positive Y axis.
     * @param theta
     *            the angle of rotation measured in degrees
     * @return the rotated point
     */
    public final Point3D rotateAroundZAxis(final double theta) {
        double cos = cos(theta);
        double sin = sin(theta);
        return new Point3D(cos * x - sin * y, sin * x + cos * y, z);
    }

    public final Point3D multiply(final double factor) {
        return new Point3D(x * factor, y * factor, z * factor);
    }

    public final Point3D normalize() {
        return multiply(1.0 / getRadius());
    }

    public final double dotProduct(final Point3D point) {
        return x * point.x + y * point.y + z * point.z;
    }
}
