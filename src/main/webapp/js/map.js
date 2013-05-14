var lastData;
var marker;
function createMap(key){
    map = new OpenLayers.Map("map");
    map.addLayer(new OpenLayers.Layer.OSM());
    var markers = new OpenLayers.Layer.Markers( "Position" );
    map.addLayer(markers);
    var marker

    var size = new OpenLayers.Size(21,25);
    var offset = new OpenLayers.Pixel(-(size.w/2), -size.h);
    var icon = new OpenLayers.Icon('http://www.openlayers.org/dev/img/marker.png', size, offset);


    var center = new OpenLayers.LonLat(0,0).transform(
        new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
        map.getProjectionObject() // to Spherical Mercator Projection
    );


    
    map.setCenter (center, 12);

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
    markers.addMarker(marker);
    

    var refresh = function(lon,lat,updated){
        var lonLat = new OpenLayers.LonLat(lon, lat);
        lonLat = lonLat.transform(new OpenLayers.Projection("EPSG:4326"),map.getProjectionObject());

	    popup.moveTo(map.getLayerPxFromLonLat(lonLat))
	    marker.moveTo(map.getLayerPxFromLonLat(lonLat))
	    popup.setContentHTML("<center><p>updated: "+updated.toISOString()+"</p></center>");
	    map.setCenter (lonLat, map.getZoom());
	    marker.display(true);
	    markers.redraw()
    }

    sock = new sitebricks.Channel('/livetracker');
    sock.connect();

    sock.on('message', function(data) {
       console.log(data)
       lastData = JSON.parse(JSON.parse(data));
       console.log(lastData)
       refresh(lastData.point.lon,lastData.point.lat, new Date(lastData.point.date))

    });
    sock.on('connect', function() {
      console.log('websocket connected!');
      sock.send(null, "SUB " + sitebricks.SOCKET_ID +",qwe")
    });
    sock.on('disconnect', function() {
      console.log('websocket disconnected!');
    });

    
}
