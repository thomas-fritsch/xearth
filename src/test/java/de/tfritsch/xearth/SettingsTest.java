package de.tfritsch.xearth;

import javax.xml.bind.JAXB;
import org.junit.Test;

public class SettingsTest {

    @Test
    public void testMarshal() {
        Settings settings = new Settings();
        JAXB.marshal(settings, System.out);
    }
}
