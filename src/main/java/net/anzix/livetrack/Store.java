package net.anzix.livetrack;

import net.anzix.livetrack.Point;
import java.lang.String;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory store to save Points.
 */
public class Store {

    /**
     *  Last valid position per key.
     */
    private Map<String, Point> points = new ConcurrentHashMap<String,Point>();


    public void addPoint(String key, Point p){
        points.put(key,p);
    }

    public Point getLastPoint(String key){
        return points.get(key);
    }
}
