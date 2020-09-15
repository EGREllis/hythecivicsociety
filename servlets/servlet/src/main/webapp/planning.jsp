<html>
    <head>
        <%@ include file="stylesheets.jsp" %>
        <title>Hythe civic society - Planning</title>
    </head>
    <body>
        <%@ include file="nav-bar.jsp" %>
        <main role="main" class="container">
            <div class="starter-template">
                <p>Planning</p>
                <div id="googleMap" style="width:100%;height:400px;"></div>
                <script>
                    function myMap() {
                        var mapProp= {
                            center:new google.maps.LatLng(51.073334,1.078582),
                            zoom:14,
                        };
                        var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);
                    }
                </script>

        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA6jkU5BYfycxtzfFOL3WA9zKAAqD6y-og&callback=myMap"></script>
    </body>
</html>