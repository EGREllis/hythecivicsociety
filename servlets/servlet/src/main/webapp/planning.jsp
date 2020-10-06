<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="stylesheets.jsp" %>
        <title>Hythe civic society - Planning</title>
        <script>
            var addresses = <c:out value="${planning_application_json}" escapeXml="false"/>

            function clearClientForm() {
                document.getElementById("client_name").value = "";
                document.getElementById("client_address").value = "";
                document.getElementById("client_type").value = "";
                document.getElementById("client_status").value = "";
            }

            function getRecordFromForm() {
                var clientName = document.getElementById("client_name").value;
                var clientAddress = document.getElementById("client_address").value;
                var clientType = document.getElementById("client_type").value;
                var clientStatus = document.getElementById("client_status").value;
                return { name: clientName, address: clientAddress, type: clientType, status: clientStatus}
            }

            function ajaxCallback(data, textStatus, jqXHR) {
                alert("Ajax callback (data): "+data+"\nAjax callback(textStatus): "+textStatus);

            }

            function performAjaxRequest() {
                var record = getRecordFromForm();
                alert("Calling ajax with record: "+record);
                $.post( "./ajax.html",
                        record,
                        ajaxCallback,
                        "text");
            }

            function addClientPin() {
                clientJson = getRecordFromForm();
                addresses[addresses.length] = clientJson;
                alert(addresses.length);
                initMap();
                renderTable();
                clearClientForm();
            }

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
                <script src="https://maps.googleapis.com/maps/api/js?key=${google_key}&callback=initMap"></script>
            </div>
            <p>Client side table</p>
            <div id="table">
            </div>
            <p>Server side table:</p>
            <table border="2" width="100%">
                <tr><th>Name</th><th>Address</th><th>Type</th><th>Status</th></tr>
                <c:forEach items="${planning_application}" var="plan">
                    <tr><td><c:out value="${plan.name}" /></td><td><c:out value="${plan.address}" /></td><td><c:out value="${plan.type}" /></td><td><c:out value="${plan.status}" /></td></tr>
                </c:forEach>
            </table>
            <p>Client side form:</p>
            <form id="clientSide">
                <table border="2" width="100%">
                    <tr><td>Name</td><td><input type="text" id="client_name" /></td></tr>
                    <tr><td>Address</td><td><input type="text" id="client_address" /></td></tr>
                    <tr><td>Type</td><td><input type="text" id="client_type" /></td></tr>
                    <tr><td>Status</td><td><input type="text" id="client_status" /></td></tr>
                    <tr><td><input type="button" value="Ajax" onclick="performAjaxRequest()" /></td><td><input type="button" value="Add client pin" onclick="addClientPin()"/></td></tr>
                </table>
            </form>
            <p>Server side form:</p>
            <form id="serverSide" method="POST" action="./planning.html">
                <table border="2" width="100%">
                    <tr><td>Name</td><td><input type="text" id="name" name="name"/></td></tr>
                    <tr><td>Address</td><td><input type="text" id="address" name="address" /></td></tr>
                    <tr><td>Type</td><td><input type="text" id="type" name="type" /></td></tr>
                    <tr><td>Status</td><td><input type="text" id="status" name="status" /></td></tr>
                    <tr><td></td><td><input type="submit" value="Add server pin" /></td></tr>
                </table>
            </form>
        </main>
    </body>
</html>