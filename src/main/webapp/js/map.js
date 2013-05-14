var lastData;
var markers = {};
var popups = {};
var markerLayer

function createMarker(id, center, updated) {
    var size = new OpenLayers.Size(21,25);
    var offset = new OpenLayers.Pixel(-(size.w/2), -size.h);
    var icon = new OpenLayers.Icon('http://www.openlayers.org/dev/img/marker.png', size, offset);
    marker = new OpenLayers.Marker(center,icon);
    var popup = new OpenLayers.Popup("Popup",
				     center,
				     new OpenLayers.Size(350,60),
				     '<div>TODO</div>',
				     false);
    map.addPopup(popup);
    popup.hide();
    //here add mouseover event
    marker.events.register('mouseover', marker, function(evt) {
	   popup.show();
    });

    //here add mouseout event
    marker.events.register('mouseout', marker, function(evt) {popup.hide();});
    marker.display(false);
    markerLayer.addMarker(marker);
    markers[id] = marker;
    popups[id] = popup;
}

function updateMarker(id, lonLat, updated) {
        if (!markers[id]) {
           createMarker(id,lonLat, updated)
        }
        var popup = popups[id]
        var marker = markers[id]
	    popup.moveTo(map.getLayerPxFromLonLat(lonLat))
	    marker.moveTo(map.getLayerPxFromLonLat(lonLat))
	    popup.setContentHTML("<center><p>id: " + id + "</p><p>updated: "+updated.toISOString()+"</p></center>");
	    map.setCenter (lonLat, map.getZoom());
	    marker.display(true);
	    markerLayer.redraw()

}


function createMap(key){
    map = new OpenLayers.Map("map");
    map.addLayer(new OpenLayers.Layer.OSM());
    markerLayer = new OpenLayers.Layer.Markers( "Position" );
    map.addLayer(markerLayer);






    var center = new OpenLayers.LonLat(0,0).transform(
        new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
        map.getProjectionObject() // to Spherical Mercator Projection
    );


    
    map.setCenter (center, 12);


    

    var refresh = function(id, lon,lat,updated){
        var lonLat = new OpenLayers.LonLat(lon, lat);
        lonLat = lonLat.transform(new OpenLayers.Projection("EPSG:4326"),map.getProjectionObject());
        updateMarker(id, lonLat, updated)

    }
    sitebricks.BASE_URL = "http://localhost:8080/livetracker/"
    sock = new sitebricks.Channel('/livetracker');
    sock.connect();

    sock.on('message', function(data) {
       console.log(data)
       lastData = JSON.parse(JSON.parse(data));
       console.log(lastData)
       refresh(lastData.id,lastData.point.lon,lastData.point.lat, new Date(lastData.point.date))

    });
    sock.on('connect', function() {
      console.log('websocket connected!');
      sock.send(null, "SUB " + sitebricks.SOCKET_ID +",qwe")
    });
    sock.on('disconnect', function() {
      console.log('websocket disconnected!');
    });

    
}
