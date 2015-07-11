package de.tfritsch.xearth.image;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FlatMapTest {

    private static FlatMap flatMap;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        flatMap = new FlatMap();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        flatMap = null;
    }

    @Test
    public void testInside() {
        flatMap.getRGB(-180, 0);
        flatMap.getRGB(180, 0);
        flatMap.getRGB(0, 90);
        flatMap.getRGB(0, -90);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutsideWest() {
        flatMap.getRGB(-180.01, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutsideEast() {
        flatMap.getRGB(180.01, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutsideNorth() {
        flatMap.getRGB(0, 90.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutsideSouth() {
        flatMap.getRGB(0, -90.01);
    }

}
