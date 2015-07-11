package de.tfritsch.astronomy;

import java.util.Date;

/**
 * Julian Date is a continuous count of days, primarily used in astronomy.
 * @author Thomas Fritsch
 * @see <a href="http://en.wikipedia.org/wiki/Julian_day">Wikipedia: Julian
 *      Day</a>
 */
public final class JulianDate {

    /**
     * Don't let anyone instantiate this class.
     */
    private JulianDate() {
    }

    /**
     * Julian date at Unix Epoch (1970-01-01 00:00 GMT).
     */
    private static final double UNIX_EPOCH = 2440587.5;

    /**
     * Julian date at J2000.0 (2000-01-01 12:00 GMT).
     * @see <a href=
     *      "http://en.wikipedia.org/wiki/Epoch_%28astronomy%29#Julian_years_and_J2000">
     *      Wikipedia: Julian year and J2000</a>
     */
    public static final double J2000_0 = 2451545.0;

    /**
     * Milliseconds per day.
     */
    private static final double MSEC_PER_DAY = 24 * 60 * 60 * 1000;

    /**
     * Days per Julian century.
     */
    public static final int DAYS_PER_CENTURY = 36525;

    /**
     * Converts a {@link java.util.Date} to a Julian Date.
     * @param date
     *            a <code>Date</code>
     * @return the corresponding Julian date.
     */
    public static double fromDate(final Date date) {
        return date.getTime() / MSEC_PER_DAY + UNIX_EPOCH;
    }

    /**
     * Converts a Julian Date to a {@link java.util.Date}.
     * @param jd
     *            a Julian date
     * @return the corresponding <code>Date</code>
     */
    public static Date toDate(final double jd) {
        double msec = (jd - UNIX_EPOCH) * MSEC_PER_DAY;
        return new Date((long) msec);
    }
}
