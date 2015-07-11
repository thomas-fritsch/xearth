package de.tfritsch.astronomy;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class MoonTest {

    private SkyObject moon = SkyObjects.MOON;

    @Test
    public void testGetEclipticalPosition() {
        @SuppressWarnings("deprecation")
        Date date = new Date("12 April 1992 00:00 GMT");
        Point3D point = moon.getEclipticalPosition(date);
        assertEquals(133.163, point.getLongitude(), 0.001);
        assertEquals(-3.229, point.getLatitude(), 0.001);
        assertEquals(368408, point.getRadius(), 10);
    }

}
