package de.tfritsch.common;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class ColorXmlAdapterTest {

    private ColorXmlAdapter adapter = new ColorXmlAdapter();

    @Test
    public void testMarshallNull() throws Exception {
        assertNull(adapter.marshal(null));
    }

    @Test
    public void testUnmarshallNull() throws Exception {
        assertNull(adapter.unmarshal(null));
    }

    @Test
    public void testMarshallOrange() throws Exception {
        assertEquals("#ffc800", adapter.marshal(Color.ORANGE));
    }

    @Test
    public void testUnmarshallOrange() throws Exception {
        assertEquals(Color.ORANGE, adapter.unmarshal("#ffc800"));
    }
}
