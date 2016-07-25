package de.tfritsch.astronomy;

import static de.tfritsch.astronomy.DegreeMath.cos;
import static de.tfritsch.astronomy.DegreeMath.sin;
import static de.tfritsch.astronomy.JulianDate.DAYS_PER_CENTURY;
import static de.tfritsch.astronomy.JulianDate.J2000_0;

import java.util.Date;

/**
 * Some well-known sky objects.
 * @author Thomas Fritsch
 */
public final class SkyObjects {

    /**
     * Don't let anyone instantiate this class.
     */
    private SkyObjects() {
    }

    /**
     * The sun. Calculation of positions has a precision of 0.01 degrees within the
     * time range from 1900 to 2100.
     * @see <a
     *      href="http://en.wikipedia.org/wiki/Position_of_the_Sun">Wikipedia:
     *      Position of the Sun</a>
     * @see <a href=
     *      "http://www.amazon.de/Astronomische-Algorithmen-Jean-Meeus/dp/3335004000">
     *      Jean Meeus: Astronomische Algorithmen</a>
     */
    public static final SkyObject SUN = new SkyObject() {
        @Override
        public Point3D getEclipticalPosition(final Date date) {
            double jd = JulianDate.fromDate(date);
            double n = jd - J2000_0; // days since J2000.0
            double l = 280.460 + 0.9856474 * n; // mean longitude
            double m = 357.528 + 0.9856003 * n; // mean anomaly
            double lambda = l // longitude
                    + 1.915 * sin(m)
                    + 0.020 * sin(2 * m);
            double beta = 0; // latitude
            double r = 1.00014 // distance
                    - 0.01671 * cos(m)
                    - 0.00014 * cos(2 * m);
            return Point3D.fromSpherical(lambda, beta, r);
        }
    };

    /**
     * The moon. Calculation of positions has a precision of 0.01 degrees within the
     * time range from 1900 to 2100.
     * @see <a href=
     *      "http://www.amazon.de/Astronomische-Algorithmen-Jean-Meeus/dp/3335004000">
     *      Jean Meeus: Astronomische Algorithmen (Kapitel 45)</a>
     */
    public static final SkyObject MOON = new SkyObject() {
        private final Term[] lTerms = {
            // amplitude, nD, nM, nM_, nF
            new Term(6.2888, 0, 0, 1, 0),
            new Term(1.2740, 2, 0, -1, 0),
            new Term(0.6583, 2, 0, 0, 0),
            new Term(0.2136, 0, 0, 2, 0),
            new Term(-0.1851, 0, 1, 0, 0),
            new Term(-0.1143, 0, 0, 0, 2),
            new Term(0.0588, 2, 0, -2, 0),
            new Term(0.0571, 2, -1, -1, 0),
            new Term(0.0533, 2, 0, 1, 0),
            new Term(0.0458, 2, -1, 0, 0),
            new Term(-0.0409, 0, 1, -1, 0),
            new Term(-0.0347, 1, 0, 0, 0),
            new Term(-0.0304, 0, 1, 1, 0),
            new Term(0.0153, 2, 0, 0, -2),
            new Term(-0.0125, 0, 0, 1, 2),
            new Term(0.0110, 0, 0, 1, -2),
            new Term(0.0107, 4, 0, -1, 0),
            new Term(0.0100, 0, 0, 3, 0),
            new Term(0.0085, 4, 0, -2, 0),
            new Term(-0.0079, 2, 1, -1, 0),
            new Term(-0.0068, 2, 1, 0, 0),
            new Term(-0.0052, 1, 0, -1, 0),
            new Term(0.0050, 1, 1, 0, 0),
            new Term(0.0040, 2, -1, 1, 0),
            new Term(0.0040, 2, 0, 2, 0),
            new Term(0.0039, 4, 0, 0, 0),
            new Term(0.0037, 2, 0, -3, 0),
            new Term(-0.0027, 0, 1, -2, 0),
            new Term(-0.0026, 2, 0, -1, 2),
            new Term(0.0024, 2, -1, -2, 0),
            new Term(-0.0023, 1, 0, 1, 0),
            new Term(0.0022, 2, -2, 0, 0),
            new Term(-0.0021, 0, 1, 2, 0),
            new Term(-0.0021, 0, 2, 0, 0),
            new Term(0.0020, 2, -2, -1, 0),
            new Term(-0.0018, 2, 0, 1, -2),
            new Term(-0.0016, 2, 0, 0, 2),
            new Term(0.0012, 4, -1, -1, 0),
            new Term(-0.0011, 0, 0, 2, 2),
            new Term(-0.0009, 3, 0, -1, 0),
            new Term(-0.0008, 2, 1, 1, 0),
            new Term(0.0008, 4, -1, -2, 0),
            new Term(-0.0007, 0, 2, -1, 0),
            new Term(-0.0007, 2, 2, -1, 0),
            new Term(0.0007, 2, 1, -2, 0),
            new Term(0.0006, 2, -1, 0, -2),
            new Term(0.0005, 4, 0, 1, 0),
            new Term(0.0005, 0, 0, 4, 0),
            new Term(0.0005, 4, -1, 0, 0),
            new Term(-0.0005, 1, 0, -2, 0),
            new Term(-0.0004, 2, 1, 0, -2),
            new Term(-0.0004, 0, 0, 2, -2),
            new Term(0.0004, 1, 1, 1, 0),
            new Term(-0.0003, 3, 0, -2, 0),
            new Term(0.0003, 4, 0, -3, 0),
            new Term(0.0003, 2, -1, 2, 0),
            new Term(-0.0003, 0, 2, 1, 0),
            new Term(0.0003, 1, 1, -1, 0),
            new Term(0.0003, 2, 0, 3, 0),
        };
        private final Term[] rTerms = {
            // amplitude, nD, nM, nM_, nF
            new Term(-20905.4, 0, 0, 1, 0),
            new Term(-3699.1, 2, 0, -1, 0),
            new Term(-2956.0, 2, 0, 0, 0),
            new Term(-569.9, 0, 0, 2, 0),
            new Term(48.9, 0, 1, 0, 0),
            new Term(-3.1, 0, 0, 0, 2),
            new Term(246.2, 2, 0, -2, 0),
            new Term(-152.1, 2, -1, -1, 0),
            new Term(-170.7, 2, 0, 1, 0),
            new Term(-204.6, 2, -1, 0, 0),
            new Term(-129.6, 0, 1, -1, 0),
            new Term(108.7, 1, 0, 0, 0),
            new Term(104.8, 0, 1, 1, 0),
            new Term(10.3, 2, 0, 0, -2),
            new Term(79.7, 0, 0, 1, -2),
            new Term(-34.8, 4, 0, -1, 0),
            new Term(-23.2, 0, 0, 3, 0),
            new Term(-21.6, 4, 0, -2, 0),
            new Term(24.2, 2, 1, -1, 0),
            new Term(30.8, 2, 1, 0, 0),
            new Term(-8.4, 1, 0, -1, 0),
            new Term(-16.7, 1, 1, 0, 0),
            new Term(-12.8, 2, -1, 1, 0),
            new Term(-10.4, 2, 0, 2, 0),
            new Term(-11.6, 4, 0, 0, 0),
            new Term(14.4, 2, 0, -3, 0),
            new Term(-7.0, 0, 1, -2, 0),
            new Term(10.1, 2, -1, -2, 0),
            new Term(6.3, 1, 0, 1, 0),
            new Term(-9.9, 2, -2, 0, 0),
            new Term(5.8, 0, 1, 2, 0),
            new Term(-5.0, 2, -2, -1, 0),
            new Term(4.1, 2, 0, 1, -2),
            new Term(-4.0, 4, -1, -1, 0),
            new Term(3.3, 3, 0, -1, 0),
            new Term(2.6, 2, 1, 1, 0),
            new Term(-1.9, 4, -1, -2, 0),
            new Term(-2.1, 0, 2, -1, 0),
            new Term(2.4, 2, 2, -1, 0),
            new Term(-1.4, 4, 0, 1, 0),
            new Term(-1.1, 0, 0, 4, 0),
            new Term(-1.6, 4, -1, 0, 0),
            new Term(-1.7, 1, 0, -2, 0),
            new Term(4.4, 0, 0, 2, -2),
            new Term(1.2, 0, 2, 1, 0),
            new Term(8.8, 2, 0, -1, -2),
        };
        private final Term[] bTerms = {
            // amplitude, nD, nM, nM_, nF
            new Term(5.1281, 0, 0, 0, 1),
            new Term(0.2806, 0, 0, 1, 1),
            new Term(0.2777, 0, 0, 1, -1),
            new Term(0.1732, 2, 0, 0, -1),
            new Term(0.0554, 2, 0, -1, 1),
            new Term(0.0462, 2, 0, -1, -1),
            new Term(0.0326, 2, 0, 0, 1),
            new Term(0.0172, 0, 0, 2, 1),
            new Term(0.0093, 2, 0, 1, -1),
            new Term(0.0088, 0, 0, 2, -1),
            new Term(0.0082, 2, -1, 0, -1),
            new Term(0.0043, 2, 0, -2, -1),
            new Term(0.0042, 2, 0, 1, 1),
            new Term(-0.0034, 2, 1, 0, -1),
            new Term(0.0025, 2, -1, -1, 1),
            new Term(0.0022, 2, -1, 0, 1),
            new Term(0.0021, 2, -1, -1, -1),
            new Term(-0.0019, 0, 1, -1, -1),
            new Term(0.0018, 4, 0, -1, -1),
            new Term(-0.0018, 0, 1, 0, 1),
            new Term(-0.0017, 0, 0, 0, 3),
            new Term(-0.0016, 0, 1, -1, 1),
            new Term(-0.0015, 1, 0, 0, 1),
            new Term(-0.0015, 0, 1, 1, 1),
            new Term(-0.0014, 0, 1, 1, -1),
            new Term(-0.0013, 0, 1, 0, -1),
            new Term(-0.0013, 1, 0, 0, -1),
            new Term(0.0011, 0, 0, 3, 1),
            new Term(0.0010, 4, 0, 0, -1),
            new Term(0.0009, 4, 0, -1, 1),
            new Term(0.0008, 0, 0, 1, -3),
            new Term(0.0007, 4, 0, -2, 1),
            new Term(0.0006, 2, 0, 0, -3),
            new Term(0.0006, 2, 0, 2, -1),
            new Term(0.0005, 2, -1, 1, -1),
            // TODO: more terms
        };

        @Override
        public Point3D getEclipticalPosition(final Date date) {
            double jd = JulianDate.fromDate(date);
            double t = (jd - J2000_0) / DAYS_PER_CENTURY;
            double lMoon = 218.3165 + 481267.8813 * t - 0.0013 * t * t; // mean longitude of moon
            double d = 297.8502 + 445267.1115 * t - 0.0016 * t * t; // mean elongation of moon
            double m = 357.5291 + 35999.0503 * t - 0.0002 * t * t; // mean anomaly of sun
            double mMoon = 134.9634 + 477198.8676 * t + 0.0090 * t * t; // mean anomaly of moon
            double f = 93.2721 + 483202.0175 * t - 0.0034 * t * t; // mean argument of latitude
            double a1 = 119.75 + 131.849 * t;
            double a2 = 53.09 + 479264.290 * t;
            double a3 = 313.45 + 481266.484 * t;
            double lambda = lMoon; // longitude
            for (Term term : lTerms) {
                lambda += term.evalSin(d, m, mMoon, f);
            }
            lambda += 0.0040 * sin(a1);
            lambda += 0.0020 * sin(lMoon - f);
            lambda += 0.0003 * sin(a2);
            double r = 385000.6; // distance
            for (Term term : rTerms) {
                r += term.evalCos(d, m, mMoon, f);
            }
            double beta = 0; // latidude
            for (Term term : bTerms) {
                beta += term.evalSin(d, m, mMoon, f);
            }
            beta += -0.0022 * sin(lMoon);
            beta += 0.0004 * sin(a3);
            beta += 0.0002 * sin(a1 - f);
            beta += 0.0002 * sin(a1 + f);
            beta += 0.0001 * sin(lMoon - mMoon);
            beta += -0.0001 * sin(lMoon + mMoon);

            return Point3D.fromSpherical(lambda, beta, r);
        }
    };

    private static class Term {
        private final double amplitude;
        private final int n1, n2, n3, n4;

        Term(final double amplitude, final int n1, final int n2, final int n3, final int n4) {
            this.amplitude = amplitude;
            this.n1 = n1;
            this.n2 = n2;
            this.n3 = n3;
            this.n4 = n4;
        }

        double evalCos(final double a1, final double a2, final double a3, final double a4) {
            return amplitude * cos(n1 * a1 + n2 * a2 + n3 * a3 + n4 * a4);
        }

        double evalSin(final double a1, final double a2, final double a3, final double a4) {
            return amplitude * sin(n1 * a1 + n2 * a2 + n3 * a3 + n4 * a4);
        }
    }
}
