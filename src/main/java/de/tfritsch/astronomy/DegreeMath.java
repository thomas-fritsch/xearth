package de.tfritsch.astronomy;

/**
 * Trigonometric functions for angles in degrees.
 * @author Thomas Fritsch
 * @see Math
 */
public final class DegreeMath {

    /**
     * Don't let anyone instantiate this class.
     */
    private DegreeMath() {
    }

    /**
     * Similar to {@link Math#sin}.
     * @param a
     *            an angle, in degrees
     * @return the cosine of the angle
     */
    public static double sin(final double a) {
        return Math.sin(Math.toRadians(a));
    }

    /**
     * Similar to {@link Math#cos}.
     * @param a
     *            an angle, in degrees
     * @return the cosine of the angle
     */
    public static double cos(final double a) {
        return Math.cos(Math.toRadians(a));
    }

    /**
     * Similar to {@link Math#tan}.
     * @param a
     *            an angle, in degrees
     * @return the cosine of the angle
     */
    public static double tan(final double a) {
        return Math.tan(Math.toRadians(a));
    }

    /**
     * Similar to {@link Math#asin}.
     * @param a
     *            the value whose arc sine is to be returned.
     * @return the arc sine of the argument, in degrees
     */
    public static double asin(final double a) {
        return Math.toDegrees(Math.asin(a));
    }

    /**
     * Similar to {@link Math#acos}.
     * @param a
     *            the value whose arc cosine is to be returned.
     * @return the arc cosine of the argument, in degrees
     */
    public static double acos(final double a) {
        return Math.toDegrees(Math.acos(a));
    }

    /**
     * Similar to {@link Math#atan}.
     * @param a
     *            the value whose arc tangent is to be returned.
     * @return the arc tangent of the argument, in degrees.
     */
    public static double atan(final double a) {
        return Math.toDegrees(Math.atan(a));
    }

    /**
     * Similar to {@link Math#atan2}.
     * @param y
     *            the ordinate coordinate
     * @param x
     *            the abscissa coordinate
     * @return the <i>theta</i> component (in degrees) of the point
     *         (<i>r</i>,&nbsp;<i>theta</i>) in polar coordinates that
     *         corresponds to the point (<i>x</i>,&nbsp;<i>y</i>) in Cartesian
     *         coordinates.
     */
    public static double atan2(final double y, final double x) {
        return Math.toDegrees(Math.atan2(y, x));
    }
}
