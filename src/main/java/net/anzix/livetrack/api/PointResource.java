package net.anzix.livetrack.api;

import com.google.inject.name.Named;
import com.google.sitebricks.At;
import com.google.sitebricks.channel.ChannelListener;
import com.google.sitebricks.channel.Observe;
import com.google.sitebricks.channel.Switchboard;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Service;
import net.anzix.livetrack.Point;
import net.anzix.livetrack.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Service to store and retrieve geo coordinates to a specific key.
 */
@At("/api/point/:key")
@Service
@Singleton
public class PointResource {

    private static final Logger LOG = LoggerFactory.getLogger(PointResource.class);

    private final List<String> connected = new CopyOnWriteArrayList<String>();

    private String lat;

    private String lon;

    private String alt;

    @Inject
    private Switchboard switchboard;

    @Inject
    Store store;

    @com.google.sitebricks.http.Get()
    public Reply<net.anzix.livetrack.Point> get(@Named("key") String key) {
        return Reply.with(store.getLastPoint(key)).as(Json.class);
    }

    @com.google.sitebricks.http.Post
    public Reply<String> update(@Named("key") String key) {
        if (key == null) {
            return Reply.with("Key is required.").status(500).as(Json.class);
        }
        if (lat == null) {
            return Reply.with("lat is required.").status(500).as(Json.class);
        }
        if (lat == null) {
            return Reply.with("alt is required.").status(500).as(Json.class);
        }

        store.addPoint(key, new Point(lat, lon, alt));
        for (String socketId : connected) {
            switchboard.named(socketId).send(key + "," + lat + "," + lon);
        }
        return Reply.with("OK").status(200).as(Json.class);
    }

    @Observe
    public void receiveUpdate(String message) {
        for (String socketId : connected) {

        }
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

    @Singleton
    public static class Listener implements ChannelListener {

        @Inject
        private PointResource resource;

        @Override
        public void connected(Switchboard.Channel channel) {
            LOG.debug("New client is connected: " + channel.getName());
            resource.connected.add(channel.getName());

        }

        @Override
        public void disconnected(Switchboard.Channel channel) {
            LOG.debug("Client is disconnected: " + channel.getName());
            resource.connected.remove(channel.getName());
        }


    }
}
