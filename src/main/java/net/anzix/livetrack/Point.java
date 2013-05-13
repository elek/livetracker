package net.anzix.livetrack;

import java.util.Date;

/**
 * All parameter of a saved position.
 */
public class Point {
    private String lat;
    private String lon;
    private String alt;
    private Date date;

    public Point(String lat, String lon, String alt) {
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
        this.date = new Date();
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Date getDate() {
        return this.date;
    }

    public String encode() {
        return lat + "," + lon + "," + lon;
    }


}
