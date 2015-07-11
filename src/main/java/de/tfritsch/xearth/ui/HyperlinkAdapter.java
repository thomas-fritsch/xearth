package de.tfritsch.xearth.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class HyperlinkAdapter implements HyperlinkListener {

    @Override
    public void hyperlinkUpdate(final HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            try {
                URI uri = e.getURL().toURI();
                if (uri.getScheme().equals("mailto"))
                    Desktop.getDesktop().mail(uri);
                else
                    Desktop.getDesktop().browse(uri);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
    }

}
