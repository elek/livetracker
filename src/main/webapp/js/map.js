var lastData;
var markers = {};
var popups = {};
var markerLayer
var sock
var sendingTask
var map;
var mapId = window.location.href.substr(window.location.href.search("map")+4);

function sendPosition(position){
   sock.send(null,"POS " + mapId + "," + sitebricks.SOCKET_ID + "," + position.coords.latitude + "," + position.coords.longitude +",0")
}

function sending(){
   navigator.geolocation.getCurrentPosition(sendPosition);
}
function initMap() {
   map  = L.map('map');
   var osmUrl='http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
   var osmAttrib='Map data © OpenStreetMap contributors';
   var osm = new L.TileLayer(osmUrl, {minZoom: 6    , maxZoom: 15, attribution: osmAttrib});
   map.addLayer(osm);
   map.setView([51.505, -0.09], 13);

   var refresh = function(id, lon,lat,updated){
           updateMarker(id, lon,lat, updated)

       }

       context = document.location.pathname.substring(0,document.location.pathname.search("map")-1)
       if (context.length == 0) {
          context = "/"
       }
       sock = new sitebricks.Channel(context);
       sock.connect();

       sock.on('message', function(data) {
          console.log(data)
          lastData = JSON.parse(JSON.parse(data));
          console.log(lastData)
          refresh(lastData.id,lastData.point.lon,lastData.point.lat, new Date(lastData.point.date))

       });
       sock.on('connect', function() {
         console.log('websocket connected!');
         sock.send(null, "SUB " + sitebricks.SOCKET_ID +"," + mapId)
       });
       sock.on('disconnect', function() {
         console.log('websocket disconnected!');
       });

       $("#share").click(function(){
          if (!sendingTask) {
             $("#share").removeClass("ui-body-a").addClass("ui-body-c");
             sendingTask = window.setInterval(sending, 5000)
          } else {
             $("#share").removeClass("ui-body-c").addClass("ui-body-a");
             window.clearInterval(sendingTask)
             sendingTask = null
          }

       });
}

function fixContentHeight() {
    var footer = $("div[data-role='footer']:visible"),
    content = $("div[data-role='content']:visible:visible"),
    viewHeight = $(window).height(),
    contentHeight = viewHeight - footer.outerHeight();

    if ((content.outerHeight() + footer.outerHeight()) !== viewHeight) {
        contentHeight -= (content.outerHeight() - content.height() + 1);
        content.height(contentHeight);
        $("#map").height(contentHeight);
    }

    if (map) {
        map.invalidateSize();
    } else {
        // initialize map
        initMap()
    }
}

$('#mappage').live('pageshow',function (){
    fixContentHeight();
});

$(window).bind("orientationchange resize pageshow", fixContentHeight);

function createMarker(id, lon, lat, updated) {
    marker = L.marker([lat,lon])
    markers[id] = marker;
    marker.addTo(map);
}

function updateMarker(id, lon, lat, updated) {
        if (!markers[id]) {
           map.setView([lat,lon],15)
           createMarker(id,lon,lat, updated)
        }
        var marker = markers[id]
        marker.bindPopup("<p>id: " + id + "</p>");
        marker.setLatLng([lat,lon])
}






