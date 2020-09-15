<html>
    <head>
        <%@ include file="stylesheets.jsp" %>
        <title>Hythe civic society - Planning</title>
        <script>
            var addresses = [   {   name:"20/1118/FH";
                                    address:"35 Sandgate Esplanade, Sandgate, Folkestone, CT20 3EA";
                                    type:"Full Planning Permission";
                                    status:"Under construction";},
                                {   name:"20/0183/FH";
                                    address:"17 Brockhill Road, Hythe, CT21 4AE";
                                    type:"Full Planning Permission";},
                                {   name:"Y17/1042/SH";
                                    address:"Princes Parade Promenade,Princes Parade,Hythe,Kent";
                                    type:"Closed";}]
            function myMap() {
                var mapProp= {
                    center:new google.maps.LatLng(51.073334,1.078582),
                    zoom:14,
                };
                var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);
            }
        </script>
    </head>
    <body>
        <%@ include file="nav-bar.jsp" %>
        <main role="main" class="container">
            <div class="starter-template">
                <p>Planning</p>
                <div id="googleMap" style="width:100%;height:400px;"></div>
                <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA6jkU5BYfycxtzfFOL3WA9zKAAqD6y-og&callback=myMap"></script>
            </div>
        </main>
    </body>
</html>