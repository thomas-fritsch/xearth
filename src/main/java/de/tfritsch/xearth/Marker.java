package de.tfritsch.xearth;

/**
 * Marker info of a city.
 * @author Thomas Fritsch
 */
public class Marker {

    private float latitude;

    public final float getLatitude() {
        return latitude;
    }

    public final void setLatitude(final float latitude) {
        this.latitude = latitude;
    }

    private float longitude;

    public final float getLongitude() {
        return longitude;
    }

    public final void setLongitude(final float longitude) {
        this.longitude = longitude;
    }

    private String name;

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[latitude=" + latitude + ", longitude="
                + longitude + ", name=\"" + name + "\"]";
    }
}
