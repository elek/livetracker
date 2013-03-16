package net.anzix.livetrack.api;

import com.google.sitebricks.At;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import net.anzix.livetrack.Point;
import net.anzix.livetrack.Store;
import org.codehaus.jackson.map.util.JSONPObject;

import javax.inject.Inject;

@At("/api/update")
@Service
public class Update {
    private String lat;
    private String lon;
    private String alt;
    private String key;

    @Inject
    Store store;


    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
