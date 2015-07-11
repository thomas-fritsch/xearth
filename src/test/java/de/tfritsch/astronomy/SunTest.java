package de.tfritsch.astronomy;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class SunTest {

    private SkyObject sun = SkyObjects.SUN;

    @Test
    public void testGetEclipticalPosition() {
        @SuppressWarnings("deprecation")
        Date date = new Date("13 Oct 1992 00:00 GMT");
        Point3D point = sun.getEclipticalPosition(date);
        assertEquals(-160.10, point.getLongitude(), 0.01);
        assertEquals(0.00, point.getLatitude(), 0.001);
        assertEquals(0.9976, point.getRadius(), 0.0001);
    }

    @Test
    public void testGetEclipticalPosition_1() {
        @SuppressWarnings("deprecation")
        Date date = new Date("20 Mar 2015 22:45 GMT"); // Spring equinox
        Point3D point = sun.getEclipticalPosition(date);
        assertEquals(0, point.getLongitude(), 0.01);
        assertEquals(0, point.getLatitude(), 0.001);
    }

    @Test
    public void testGetEclipticalPosition_2() {
        @SuppressWarnings("deprecation")
        Date date = new Date("21 Jun 2015 16:37 GMT"); // Summer solstice
        Point3D point = sun.getEclipticalPosition(date);
        assertEquals(90, point.getLongitude(), 0.01);
        assertEquals(0, point.getLatitude(), 0.01);
    }

    @Test
    public void testGetEclipticalPosition_3() {
        @SuppressWarnings("deprecation")
        Date date = new Date("23 Sep 2015 08:20 GMT"); // Autumn equinox
        Point3D point = sun.getEclipticalPosition(date);
        assertEquals(180, point.getLongitude(), 0.01);
        assertEquals(0, point.getLatitude(), 0.001);
    }

    @Test
    public void testGetEclipticalPosition_4() {
        @SuppressWarnings("deprecation")
        Date date = new Date("22 Dec 2015 04:48 GMT"); // Winter solstice
        Point3D point = sun.getEclipticalPosition(date);
        assertEquals(-90, point.getLongitude(), 0.01);
        assertEquals(0, point.getLatitude(), 0.001);
    }

    @Test
    public void testGetGeographicalPosition_1() {
        @SuppressWarnings("deprecation")
        Date date = new Date("13 Oct 1992 00:00 GMT");
        Point3D point = sun.getGeographicalPosition(date);
        assertEquals(176.57, point.getLongitude(), 0.01);
        assertEquals(-7.78, point.getLatitude(), 0.01);
    }

    @Test
    public void testGetGeographicalPosition_2() {
        @SuppressWarnings("deprecation")
        Date date = new Date("13 Oct 1992 06:00 GMT");
        Point3D point = sun.getGeographicalPosition(date);
        assertEquals(86.56, point.getLongitude(), 0.01);
        assertEquals(-7.88, point.getLatitude(), 0.01);
    }

    @Test
    public void testGetGeographicalPosition_3() {
        @SuppressWarnings("deprecation")
        Date date = new Date("13 Oct 1992 12:00 GMT");
        Point3D point = sun.getGeographicalPosition(date);
        assertEquals(-3.46, point.getLongitude(), 0.01);
        assertEquals(-7.97, point.getLatitude(), 0.01);
    }

    @Test
    public void testGetGeographicalPosition_4() {
        @SuppressWarnings("deprecation")
        Date date = new Date("13 Oct 1992 18:00 GMT");
        Point3D point = sun.getGeographicalPosition(date);
        assertEquals(-93.47, point.getLongitude(), 0.01);
        assertEquals(-8.06, point.getLatitude(), 0.01);
    }
}
