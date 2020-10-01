<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="stylesheets.jsp" %>
        <title>Hythe civic society - Planning</title>
        <script>
            var addresses = [   {   name:"20/1118/FH",
                                    address:"35 Sandgate Esplanade, Sandgate, Folkestone, CT20 3EA",
                                    type:"Full Planning Permission",
                                    status:"Under construction"},
                                {   name:"20/0183/FH",
                                    address:"17 Brockhill Road, Hythe, CT21 4AE",
                                    type:"Full Planning Permission",
                                    status:""},
                                {   name:"Y17/1042/SH",
                                    address:"Princes Parade Promenade,Princes Parade,Hythe,Kent",
                                    type:"Closed",
                                    status:""}];

            function findName(name) {
                var index = -1;
                for (var i = 0; i < addresses.length; i++) {
                    if (addresses[i].name == name) {
                        index = i;
                        break;
                    }
                }
                document.getElementById("selected").value = index;
            }

            function initMap() {
                var mapProp= {
                    center:new google.maps.LatLng(51.073334,1.078582),
                    zoom:13,
                };

                var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);
                var geocoder = new google.maps.Geocoder();

                for (var i = 0; i < addresses.length; i++) {
                    geocodeAddress(i, geocoder, map);
                }
            }

            function geocodeAddress(
                index,
                geocoder,
                resultsMap
            ) {
                geocoder.geocode({ address: addresses[index].address }, (results, status) => {
                    if (status === "OK") {
                      marker = new google.maps.Marker({
                        map: resultsMap,
                        position: results[0].geometry.location
                      });
                      marker.addListener('click', function() {
                        selected=findName(addresses[index].name);
                        renderTable();
                      });
                    } else {
                      alert("Geocode was not successful for the following reason: " + status);
                    }
                  });
            }

            function renderTable() {
                var html = "<table border='2' width='100%'><tr><th>Name</th><th>Address</th><th>Type</th><th>Status</th></tr>";
                var selected = document.getElementById("selected").value;
                if (selected >= 0) {
                    var entry = addresses[selected];
                    html += "<tr><td bgcolor='lightgreen'><b>"+entry.name+"</b></td><td bgcolor='lightgreen'><b>"+entry.address+"</b></td><td bgcolor='lightgreen'><b>"+entry.type+"</b></td><td bgcolor='lightgreen'><b>"+entry.status+"</b></td></tr>";
                }
                for (var i = 0; i < addresses.length; i++) {
                    if (i == selected) {
                        continue;
                    }
                    var entry = addresses[i];
                    html += "<tr><td>"+entry.name+"</td><td>"+entry.address+"</td><td>"+entry.type+"</td><td>"+entry.status+"</td></tr>"
                }
                html += "</table>";
                var ele = document.getElementById("table");
                ele.innerHTML = html;
            }
        </script>
    </head>
    <body onload="renderTable()">
        <%@ include file="nav-bar.jsp" %>
        <main role="main" class="container">
            <form>
                <input type="hidden" name="selected" id="selected" value="-1" />
            </form>
            <div class="starter-template">
                <p class="lead">Planning</p>
                <div id="googleMap" style="width:100%;height:400px;"></div>
                <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA6jkU5BYfycxtzfFOL3WA9zKAAqD6y-og&callback=initMap"></script>
            </div>
            <div id="table">
            </div>
            <p></p>
        </main>
    </body>
</html>