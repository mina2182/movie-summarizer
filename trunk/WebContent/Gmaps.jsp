
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>My Locations</title>
   <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=true&amp;key=ABQIAAAA8B3kWBNcSBdYgqOALe6CrhTwM0brOpm-All5BF6PoaKBxRWWERRuBhh8sMpZkkwf5goj7Q8gxnU1fQ" type="text/javascript"></script>

<script language="Javascript" type="text/javascript">
function initialize() {
	  var map = new GMap2(document.getElementById("map_canvas"));
	  map.setCenter(new GLatLng(-6.36350, 106.82814), 14);
	  //http://universimmedia.pagesperso-orange.fr/geo/loc.htm
}
 </script>
 </head>
<body onload="initialize()" onunload="GUnload()">
<div id="map_canvas" style="width: 500px; height: 400px"></div>
</body>
</html>
