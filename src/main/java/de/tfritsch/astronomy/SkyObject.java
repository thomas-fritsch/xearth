package de.tfritsch.astronomy;

import static de.tfritsch.astronomy.JulianDate.DAYS_PER_CENTURY;
import static de.tfritsch.astronomy.JulianDate.J2000_0;

import java.util.Date;

/**
 * An object in the sky. Calculation of positions has a precision of 0.01
 * degrees within the time range from 1900 to 2100.
 * @author Thomas Fritsch
 * @see <a href=
 *      "http://en.wikipedia.org/wiki/Celestial_coordinate_system">Wikipedia:
 *      Celestial coordinate system</a>
 */
public abstract class SkyObject {

    /**
     * Calculates the object's position in ecliptical coordinates.
     * @param date
     *            the date for which to calculate the position
     * @return a point from which you can get ecliptical longitude and latitude
     */
    public abstract Point3D getEclipticalPosition(final Date date);

    /**
     * Calculates the object's position in equatorial coordinates.
     * @param date
     *            the date for which to calculate the position
     * @return a point from which you can get right ascension and declination
     */
    public Point3D getEquatorialPosition(final Date date) {
        return getEclipticalPosition(date).rotateAroundXAxis(
                getObliquityOfEcliptic(date));
    }

    /**
     * Calculates the object's position in geographical coordinates.
     * @param date
     *            the date for which to calculate the position
     * @return a point from which you can get geographical longitude and
     *         latitude
     */
    public final Point3D getGeographicalPosition(final Date date) {
        return getEquatorialPosition(date).rotateAroundZAxis(
                -getGreenwichMeanSiderealTime(date));
    }

    /**
     * Calculates the object's position in horizontal coordinates.
     * @param date
     *            the date for which to calculate the position
     * @param geoLongitude
     *            the geographical longitude of the observer
     * @param geoLatitude
     *            the geographical latitude of the observer
     * @return a point from which you can get azimuth and height
     */
    public final Point3D getHorizontalPosition(final Date date,
            final double geoLongitude, final double geoLatitude) {
        return getGeographicalPosition(date).rotateAroundZAxis(-geoLongitude)
                .rotateAroundXAxis(-geoLatitude);
    }

    /**
     * Gets the angle between ecliptic and equator. It varies slowly over
     * centuries.
     * @param date
     *            the date for which to get the obliquity
     * @see <a href=
     *      "http://en.wikipedia.org/wiki/Ecliptic#Obliquity_of_the_ecliptic">
     *      Obliquity of the ecliptic</a>
     */
    public static double getObliquityOfEcliptic(final Date date) {
        double jd = JulianDate.fromDate(date);
        double t = (jd - J2000_0) / DAYS_PER_CENTURY;
        return 23.4393 - 0.0130 * t;
    }

    /**
     * Gets the Greenwich mean sidereal time. It proceeds 360 degrees per
     * 23:56:04.
     * @param date
     *            the date for which to get the sidereal time
     * @return sidereal time measured in degrees
     * @see <a href=
     *      "http://de.wikipedia.org/wiki/Sternzeit#Sternzeit_in_Greenwich">
     *      Sternzeit in Greenwich</a>
     */
    private static double getGreenwichMeanSiderealTime(final Date date) {
        double jd = JulianDate.fromDate(date);
        double n = jd - J2000_0; // days since J2000.0
        return 280.4606 + 360.9856474 * n;
    }
}
