package net.anzix.livetrack;

import com.google.inject.name.Named;
import com.google.sitebricks.At;
import com.google.sitebricks.http.Get;



@At("/map/:key")
public class Map {

    private String key;

    @Get
    public void get(@Named("key") String key) {
        this.key = key;

    }

    public String getKey() {
        return key;
    }
}
