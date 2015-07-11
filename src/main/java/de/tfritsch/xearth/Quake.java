package de.tfritsch.xearth;

import java.util.Date;

/**
 * Marker info of an earthquake.
 * @author Thomas Fritsch
 */
public class Quake {

    private Date time;

    public final Date getTime() {
        return time;
    }

    public final void setTime(final Date time) {
        this.time = time;
    }

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

    private float magnitude;

    public final float getMagnitude() {
        return magnitude;
    }

    public final void setMagnitude(final float magnitude) {
        this.magnitude = magnitude;
    }

    private String place;

    public final String getPlace() {
        return place;
    }

    public final void setPlace(final String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[time=" + time + ", latitude="
                + latitude + ", longitude=" + longitude + ", mag=" + magnitude
                + ", place=\"" + place + "\"]";
    }
}
