package de.tfritsch.xearth;

import static de.tfritsch.astronomy.DegreeMath.asin;

import java.util.Date;

import de.tfritsch.astronomy.JulianDate;
import de.tfritsch.astronomy.Point3D;
import de.tfritsch.astronomy.SkyObject;
import de.tfritsch.astronomy.SkyObjects;

/**
 * Viewpoint types.
 * @author Thomas Fritsch
 */
public enum ViewpointType {

    DEFAULT {
        @Override
        public Point3D getViewpoint(final Settings settings) {
            return SkyObjects.SUN.getGeographicalPosition(new Date());
        }
    },

    FIXED {
        @Override
        public Point3D getViewpoint(final Settings settings) {
            double longitude = settings.getFixedLongitude();
            double latitude = settings.getFixedLatitude();
            return Point3D.fromSpherical(longitude, latitude, 1);
        }
    },

    SUN_RELATIVE {
        @Override
        public Point3D getViewpoint(final Settings settings) {
            Point3D sunPosition = SkyObjects.SUN
                    .getGeographicalPosition(new Date());
            double longitude = sunPosition.getLongitude()
                    + settings.getSunRelativeLongitude();
            double latitude = sunPosition.getLatitude()
                    + settings.getSunRelativeLatitude();
            return Point3D.fromSpherical(longitude, latitude, 1);
        }
    },

    ORBIT {
        @Override
        public Point3D getViewpoint(final Settings settings) {
            Orbiter orbiter = new Orbiter(settings.getOrbitPeriod(),
                    settings.getOrbitInclination());
            return orbiter.getGeographicalPosition(new Date());
        }
    },

    MOON {
        @Override
        public Point3D getViewpoint(final Settings settings) {
            return SkyObjects.MOON.getGeographicalPosition(new Date());
        }
    },

    RANDOM {
        @Override
        public Point3D getViewpoint(final Settings settings) {
            double longitude = Math.random() * 360 - 180;
            double latitude = asin(Math.random() * 2 - 1);
            return Point3D.fromSpherical(longitude, latitude, 1);
        }
    };

    public abstract Point3D getViewpoint(final Settings settings);

    private static class Orbiter extends SkyObject {

        private double period;
        private double inclination;

        public Orbiter(final double period, final double inclination) {
            this.period = period;
            this.inclination = inclination;
        }

        @Override
        public Point3D getEquatorialPosition(final Date date) {
            double t = JulianDate.fromDate(date) - JulianDate.J2000_0;
            double phi = t * 24 / period * 360;
            return Point3D.fromSpherical(phi, 0, 1).rotateAroundYAxis(
                    inclination);
        }

        @Override
        public Point3D getEclipticalPosition(final Date date) {
            throw new UnsupportedOperationException();
        }
    }
}
