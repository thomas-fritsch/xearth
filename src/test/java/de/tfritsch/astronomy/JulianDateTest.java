package de.tfritsch.astronomy;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class JulianDateTest {

    @Test
    public void test1970() {
        Date date = new Date(0L);
        double jd = JulianDate.fromDate(date);
        assertEquals(2440587.5, jd, 0.0001);
        assertEquals(date, JulianDate.toDate(jd));
    }

    @Test
    public void test_1() {
        @SuppressWarnings("deprecation")
        Date date = new Date("2000 Jan 1 12:00 GMT");
        double jd = JulianDate.fromDate(date);
        assertEquals(2451545.0, jd, 0.0001);
        assertEquals(date, JulianDate.toDate(jd));
    }

    @Test
    public void test_2() {
        @SuppressWarnings("deprecation")
        Date date = new Date("1987 Jan 27 00:00 GMT");
        double jd = JulianDate.fromDate(date);
        assertEquals(2446822.5, jd, 0.0001);
        assertEquals(date, JulianDate.toDate(jd));
    }
}
