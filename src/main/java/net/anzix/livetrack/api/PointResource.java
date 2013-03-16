package net.anzix.livetrack.api;

import com.google.inject.name.Named;
import com.google.sitebricks.At;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import net.anzix.livetrack.Point;
import net.anzix.livetrack.Store;

import javax.inject.Inject;



/**
 * Service to store and retrieve geo coordinates to a specific key.
 */
@At("/api/point/:key")
@Service
public class PointResource {

    private String lat;

    private String lon;

    private String alt;

    @Inject
    Store store;

    @com.google.sitebricks.http.Get()
    public Reply<net.anzix.livetrack.Point> get(@Named("key") String key) {
        return Reply.with(store.getLastPoint(key)).as(Json.class);
    }

    @com.google.sitebricks.http.Post
    public Reply<String> update(@Named("key") String key) {
        if (key == null){
            return Reply.with("Key is required.").status(500).as(Json.class);
        }
        if (lat == null){
            return Reply.with("lat is required.").status(500).as(Json.class);
        }
        if (lat == null){
            return Reply.with("alt is required.").status(500).as(Json.class);
        }

        store.addPoint(key, new Point(lat, lon, alt));
        return Reply.with("OK").status(200).as(Json.class);
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
