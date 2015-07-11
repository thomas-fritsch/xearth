package de.tfritsch.common;

import java.net.URL;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} to handle {@link URL} values.
 * @author Thomas Fritsch
 */
public class URLXmlAdapter extends XmlAdapter<String, URL> {

    @Override
    public final String marshal(final URL url) throws Exception {
        if (url == null) {
            return null;
        }
        return url.toString();
    }

    @Override
    public final URL unmarshal(final String s) throws Exception {
        if (s == null) {
            return null;
        }
        return new URL(s);
    }
}
