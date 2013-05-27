package net.anzix.livetrack;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory store to save Points.
 */
public class Store {

    /**
     * Last valid position per key.
     */
    private Map<String, Map<String, Point>> points = new ConcurrentHashMap<String, Map<String, Point>>();


    public void addPoint(String mapKey, String clientId, Point p) {
        Map<String, Point> mapPoints = points.get(clientId);
        if (mapPoints == null) {
            mapPoints = new ConcurrentHashMap<String, Point>();
            points.put(mapKey, mapPoints);
        }
        mapPoints.put(mapKey, p);
    }

    public Point getLastPoint(String mapKey, String clientId) {
        if (!points.containsKey(mapKey)) {
            return null;
        } else {
            return points.get(mapKey).get(clientId);
        }
    }

    public Map<String, Point> getLastPoint(String mapKey) {
        return points.get(mapKey);
    }
}
