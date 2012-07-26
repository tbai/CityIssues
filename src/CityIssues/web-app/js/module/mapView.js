bai.module.MapView = function(id, sandbox, options){
    var that = new bai.Module(id),
        el = that.el;
    var map, infoWindow, markers = [], addressMarker, adUnit;
    var mc;

    var pinMarkerShadow, pinMarkerIcon;
    var markerIcons = {};
    var markerShadow;

    MarkerClusterer.prototype.MARKER_CLUSTER_IMAGE_PATH_ = '/js/markerclusterer/images/m';

    
    $.extend(that, {
        
        getCurrentIssue: function(){
            if(infoWindow && infoWindow.issue)
                return infoWindow.issue;
            else return null;
        },
        
        getZoom: function(){
            return map.getZoom();
        },

        render: function(){
            try{               
                var defaultOptions = {
                    zoom: options.zoom ? options.zoom : 10,
                    center: options.latLng,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };

                map = new google.maps.Map( document.getElementById(id), defaultOptions );
                
                if(options.fitBounds){
                    map.fitBounds(options.fitBounds);
                }
                mc = new MarkerClusterer(map, [], {gridSize:1});
                
                infoWindow = new google.maps.InfoWindow({ maxWidth:"400px",
                    content: "<div>info</div>"
                });

                that.createCustomControls();
                that.initEvents();
                //that.configAdSense();
                that.fireEvent("render");
            }catch(e){console.error("Exception", e);}
        },

        configAdSense:function(){
            var adUnitDiv = document.createElement('div');
            var adUnitOptions = {
                //format: google.maps.adsense.AdFormat.VERTICAL_BANNER,
                //position: google.maps.ControlPosition.RIGHT_BOTTOM,
                format: google.maps.adsense.AdFormat.BUTTON,
                position: google.maps.ControlPosition.RIGHT_BOTTOM,
                map: map,
                visible: true,
                publisherId: 'pub-6509899034752146'
            }
            adUnit = new google.maps.adsense.AdUnit(adUnitDiv, adUnitOptions);
        },

        initEvents: function(){
            google.maps.event.addListener(infoWindow, 'closeclick', function() {
                that.fireEvent("closeInfoWindow", {issue:infoWindow.issue});
            });
            google.maps.event.addListener(mc, 'clusterclick', function(cluster){
                if( map.getZoom() < 20 )
                    return;

                that.showClusterInfoWindow(cluster.getMarkers());
                
            });

            $(document.body).delegate("#infoWindow a.vote-positive", "click", function(ev){
                if(!$(this).hasClass("selected"))
                    that.fireEvent("votePositive", {issue:infoWindow.issue});
            });
            $(document.body).delegate("#infoWindow a.vote-negative", "click", function(ev){
                if(!$(this).hasClass("selected"))
                    that.fireEvent("voteNegative", {issue:infoWindow.issue});
            });

            $(document.body).delegate("a.clusterIssueItem", "click", function(ev){
                infoWindow.close();
                var href = this.href,
                issueId = href.substring(href.indexOf("#")+1),
                issue = sandbox.findIssueById(issueId);
                that.showIssueInfo(issue);
            });
            
            that.createMarkerIcons();
        },

        showClusterInfoWindow: function(markerList){
            var html = '</br>', issue;
            for(var i=0; i< markerList.length; i++){
                issue = markerList[i].issue;
                html += '<a href="#'+issue.id+'" class="clusterIssueItem issueType-'+issue.type+'">' +
                            issue.desc + '</a>';
            }
            that.closeInfoWindow();
            
            infoWindow.setContent(html);
            infoWindow.open(map,markerList[0]);
            
        },

        createMarkerIcons: function(){
            markerShadow = new google.maps.MarkerImage(
                "../images/markers/shadow.png",
                new google.maps.Size(60, 40),
                new google.maps.Point(0, 0),
                new google.maps.Point(10, 25)
            );

            var names = window._issueTypeList;
            names.push("pointer");
            var name;
            for(var i in names){
                name = names[i];
                markerIcons[name] = new google.maps.MarkerImage(
                    "../images/markers/"+name+".png",
                    new google.maps.Size(40, 40),
                    new google.maps.Point(0, 0)
                );
            }
            /*
            pinMarkerIcon = markerIcons["pointer"];
            pinMarkerShadow = markerShadow;*/

            pinMarkerShadow = new google.maps.MarkerImage(
                "/images/markers/arrowshadow.png",
                new google.maps.Size(38, 34),
                new google.maps.Point(0, 0),
                new google.maps.Point(10, 34)
            );

            pinMarkerIcon = new google.maps.MarkerImage(
                "/images/markers/arrow.png",
                new google.maps.Size(22, 34),
                new google.maps.Point(0, 0),
                new google.maps.Point(10, 34)
            );
        },

        goTo: function(address){
            if(address && address.geocodeResult ){
                map.setCenter(address.geocodeResult.geometry.location);
                map.fitBounds(address.geocodeResult.geometry.viewport);
            }
        },
        
        clearMarkers: function(){
            for (var i in markers){
                that.removeMarker(markers[i]);
            }
            markers.length = 0;
            mc.clearMarkers();
        },
        
       

        createAddressMarker: function(latLng, animation){
            if(addressMarker){
                // remove first
                google.maps.event.removeListener(addressMarker.listener);
                addressMarker.setMap(null);
            }
            
            var options = {
                map:map,
                draggable:true,
                position: latLng,
                title: "Criar alerta aqui.",
                icon: pinMarkerIcon,
                shadow:pinMarkerShadow
            }
            if(animation){
                options.animation = animation;
            }
            addressMarker = new google.maps.Marker(options);
            markers.push(addressMarker);
        },

        /**
         * Start address marker, the green one
         * @param address <bai.MapUtils address>
         */
        addAddressMarker: function(address){
            var latLng = null;
            if(address.geocodeResult && address.geocodeResult.geometry){
                latLng = address.geocodeResult.geometry.location;
            } else {
                latLng = new google.maps.LatLng( address.lat, address.lng );
            }
            that.createAddressMarker(latLng);
            that.setupAddressMarkerEvents(address);
        },

        setupAddressMarkerEvents: function(address){
            addressMarker.listener = google.maps.event.addListener(addressMarker, 'click', function(ev){
                that.fireEvent("addressMarkerClick", {address:address});
            });
            addressMarker.listener = google.maps.event.addListener(addressMarker, 'dragstart', function(ev){
                if(infoWindow.currentMarker == addressMarker){
                    that.closeInfoWindow();
                }
            });
            addressMarker.listener = google.maps.event.addListener(addressMarker, 'dragend', function(ev){
                that.getMarkerAddress(addressMarker, function(address){
                    that.fireEvent("addressMarkerDragend", {address:address});
                });
            });
        },

        // add an address marker in the center of the map
        autoDropAddAddressMarker: function(){
            that.createAddressMarker(map.getCenter(), google.maps.Animation.DROP);
            setTimeout(function(){
                // get the address of the marker
                that.getMarkerAddress(addressMarker, function(address){
                    that.setupAddressMarkerEvents(address);
                    that.fireEvent("addressMarkerDragend", {address:address});
                });
                // show the info window with the tip
                infoWindow.currentMarker = addressMarker;
                infoWindow.setContent("<br/>Arraste o marcador <img src=\"/images/markers/arrow-short-green.png\"></img> para escolher um endereço,<br/>ou clique nele para postar um alerta.</br></br>");
                infoWindow.open(map,addressMarker);
            }, 600);
        },

        getMarkerAddress: function(marker, callback, callbackScope){
            bai.MapUtils.geocodeByLatLng(marker.getPosition(), function(result){
               callback.call(callbackScope, bai.MapUtils.getAddressFromGeocodeResult(result[0]));
            });
        },

        addMarker: function(issue){
            var marker = null;
            if(issue && google){
                // check if this marker already exists
                for(var i=0; i < markers.length; i++){
                    marker = markers[i];
                    if(marker.issue && marker.issue.id == issue.id){
                        // remove the marker found
                        marker.issue = issue;
                        return;
                    }
                }
                
                
                
                marker = new google.maps.Marker({
                    map:map,
                    position: bai.MapUtils.getIssueLatLng(issue),
                    title: issue.desc
                    ,icon: markerIcons[issue.type]
                    ,shadow:markerShadow
                });
                marker.issue = issue;
                markers.push(marker);

                marker.listener = google.maps.event.addListener(marker, 'click', function(ev){
                    that.fireEvent("markerClick", {issue:issue})
                });
                mc.addMarker(marker);
            }
            return marker;
        },
        
        closeInfoWindow: function(){
            infoWindow.close();
            infoWindow.issue = null;
            infoWindow.currentMarker = null;
        },
        
        removeMarker: function(marker){
            if(marker){
                // hide info window if it is pointed to the marker
                if(infoWindow.currentMarker == marker){
                    that.closeInfoWindow();
                }
                
                // remove the marker from the map
                google.maps.event.removeListener(marker.listener);
                marker.setMap(null);
                mc.removeMarker(marker);
            }
        },

        /**
         * @param animation BOUNCE or DROP
         */
        startMarkerAnimation: function(marker, animation){
            if(marker && google){
                marker.setAnimation(google.maps.Animation[animation]);
            }
        },
        stopMarkerAnimation: function(marker){
            if(marker && marker.getAnimation()){
                marker.setAnimation(null);
            }
        },

        showIssueInfo: function(issue){
            if(!issue) return;
            // Show the waiting icon before
            infoWindow.issue = issue;
            infoWindow.setContent('<div class="wait" style="text-align:left; width:300px; height:160px;"><img src="/images/wait.gif"/></div>');
            infoWindow.open(map,issue.marker);
            infoWindow.currentMarker = issue.marker;
            //return;
            sandbox.requestHtml("POST", "/issue/infoWindow/" + issue.id, function(html){
                infoWindow.issue = issue;
                infoWindow.setContent(html);                      
                infoWindow.currentMarker = issue.marker;                    
                //infoWindow.open(map,issue.currentMarker);
            }, this);
            
            sandbox.requestJson("POST", "/issue/numberOfComments/" + issue.id, function(json){
                try{
                    $('#infoWindowCommentsLink[href="#'+json.issueId+'"] span.number').html("("+json.numberOfComments+")");
                }catch(e){}
            }, this);
        },

        createCustomControls: function(){
            // Create expand panel button in the center top of the map
            var controlDiv = document.createElement("div");
            controlDiv.className = "cButton";
            var imgDiv = controlDiv.appendChild(document.createElement("div"));
            imgDiv.title = sandbox.msg("js.map.expand.title.1");
            imgDiv.className = "cExpandMapImg";
            map.controls[google.maps.ControlPosition.TOP_CENTER].push(controlDiv);

            // add click event listener
            google.maps.event.addDomListener(controlDiv, 'click', function() {
                $(controlDiv).toggleClass("expanded");
                if($(controlDiv).hasClass("expanded")){
                    that.fireEvent("expandMap", true);
                    imgDiv.title = sandbox.msg("js.map.expand.title.2");
                } else {
                    that.fireEvent("expandMap", false);
                    imgDiv.title = sandbox.msg("js.map.expand.title.1");
                }
            });
        },

        triggerMapEvent: function(evname){
            google.maps.event.trigger(map, evname);
        }
    });

    that.render();
    
    return that;
};
