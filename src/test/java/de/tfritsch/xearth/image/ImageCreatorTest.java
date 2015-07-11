package de.tfritsch.xearth.image;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import de.tfritsch.xearth.Settings;

public class ImageCreatorTest {

    private Settings settings;
    private ImageCreator imageCreator;

    @Before
    public void setUp() throws Exception {
        settings = new Settings();
        // settings.setQuakes(Utilities.readRecentQuakes());
        settings.setGridVisible(true);
        imageCreator = new ImageCreator(settings);
    }

    @Test
    public void testCreateImage() throws IOException {
        Dimension dim = new Dimension(1500, 1000);
        settings.setImageWidth(dim.width);
        settings.setImageHeight(dim.height);
        BufferedImage image = imageCreator.createImage();
        assertEquals(dim.width, image.getWidth());
        assertEquals(dim.height, image.getHeight());
        ImageIO.write(image, "PNG", new File("target/out.png"));
    }

}
