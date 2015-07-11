package de.tfritsch.astronomy;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class SkyObjectTest {

    @Test
    public void testGetEquatorialPosition_1() {
        SkyObject skyObject = new SkyObject() {
            @Override
            public Point3D getEclipticalPosition(Date date) {
                return Point3D.fromSpherical(0, 0, 1); // Spring equinox
            }
        };
        Point3D point = skyObject.getEquatorialPosition(new Date());
        assertEquals(0, point.getLongitude(), 0.001);
        assertEquals(0, point.getLatitude(), 0.001);
        assertEquals(1, point.getRadius(), 0.0001);
    }

    @Test
    public void testGetEquatorialPosition_2() {
        SkyObject skyObject = new SkyObject() {
            @Override
            public Point3D getEclipticalPosition(Date date) {
                return Point3D.fromSpherical(90, 0, 1); // Summer solstice
            }
        };
        Point3D point = skyObject.getEquatorialPosition(new Date());
        assertEquals(90, point.getLongitude(), 0.001);
        double obliquity = SkyObject.getObliquityOfEcliptic(new Date());
        assertEquals(obliquity, point.getLatitude(), 0.001);
        assertEquals(1, point.getRadius(), 0.0001);
    }

    @Test
    public void testGetEquatorialPosition_3() {
        SkyObject skyObject = new SkyObject() {
            @Override
            public Point3D getEclipticalPosition(Date date) {
                return Point3D.fromSpherical(180, 0, 1); // Autumn equinox
            }
        };
        Point3D point = skyObject.getEquatorialPosition(new Date());
        assertEquals(180, point.getLongitude(), 0.001);
        assertEquals(0, point.getLatitude(), 0.001);
        assertEquals(1, point.getRadius(), 0.0001);
    }

    @Test
    public void testGetEquatorialPosition_4() {
        SkyObject skyObject = new SkyObject() {
            @Override
            public Point3D getEclipticalPosition(Date date) {
                return Point3D.fromSpherical(270, 0, 1); // Winter solstice
            }
        };
        Point3D point = skyObject.getEquatorialPosition(new Date());
        assertEquals(-90, point.getLongitude(), 0.001);
        double obliquity = SkyObject.getObliquityOfEcliptic(new Date());
        assertEquals(-obliquity, point.getLatitude(), 0.001);
        assertEquals(1, point.getRadius(), 0.0001);
    }

    @Test
    public void testGetObliquityOfEcliptic_1900() {
        @SuppressWarnings("deprecation")
        Date date = new Date("1 Jan 1900");
        assertEquals(23.452, SkyObject.getObliquityOfEcliptic(date), 0.001);
    }

    @Test
    public void testGetObliquityOfEcliptic_2000() {
        @SuppressWarnings("deprecation")
        Date date = new Date("1 Jan 2000");
        assertEquals(23.439, SkyObject.getObliquityOfEcliptic(date), 0.001);
    }

    @Test
    public void testGetObliquityOfEcliptic_2100() {
        @SuppressWarnings("deprecation")
        Date date = new Date("1 Jan 2100");
        assertEquals(23.426, SkyObject.getObliquityOfEcliptic(date), 0.001);
    }

}
