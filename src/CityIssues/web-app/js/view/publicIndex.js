/*
  TODO: separar ajax da lista dos marcadores;
   - Mapa:
      - Cada item deve ter apenas o id e a posicao;
      - Salvar o bounding box atual e carregar mais itens apenas se passar deste,
        seja por zoom out ou por mover para fora;
   - Lista: 
      - ser paginada e com o html inteiro de cada item - tentar comprimir;
      - testar o centro do mapa para saber quando mudar de cidade;
 */ 


$(document).ready(function() {
    
    var me = this;
    
    var PING_INTERVAL = 60000;
    // render the plus one button
    gapi.plusone.render("plusone-main", {"size": "standard", "count": "true"});
    
    var issueRequest = {
        maxResults:15,
        postData:{},
        nMarkers:0,
        nListItems:0,
        hasMoreMarkers:true,
        hasMoreListItems:true,
        inProgress:false,
        lastUpdated: null
    };
    
    $("#reportButton").button({icons: {primary:'ui-icon-pin-s'}});
    $("#searchButton").button({icons: {primary:'ui-icon-search'}, create:function(){
        $(this).removeClass("ui-corner-all");
        $(this).addClass("ui-corner-right");
    }}); 

    function enableSearchButton(enable){
        if(enable){
            $("#searchButton").removeClass("ui-state-disabled");
            $("#searchButton button").removeAttr("disabled");            
        } else {
            $("#searchButton").addClass("ui-state-disabled");
            $("#searchButton button").attr("disabled", true);
        }
    }

    // add corners for ie < 9    
    function createIEBorders(){
        if($.browser.msie && parseInt($.browser.version, 10) < 9){
            $(".ui-corner-tr").corner("tr 3px");
            $("#issueListHeader").css("border-bottom", "2px solid #A6C9E2");
            $("#reportButton").corner("3px");
            $("#location-container .ui-corner-right, #shortcuts-container .ui-corner-right").corner("right 3px");
            $("#location-container .ui-corner-left, #shortcuts-container .ui-corner-left").corner("left 3px");
            //$("#issueListSortByForm label").css("border", "2px solid #79B7E7")
        }
    }
    createIEBorders();
    
    var sandbox = new bai.Sandbox("index", function(){
        var issues = [];
        
        var sandboxSelf = {
            currentAddress: null,
            
            // create the dialogs
            createDialogs: function(){
                this.addDialog("createIssue", new bai.CreateIssueDialog(this));
                this.addDialog("selectAddress", new bai.SelectAddressDialog(this));
                this.addDialog("comments", new bai.CommentDialog(this));
                var dialog;
                for(var n in this.getDialogs()){
                    dialog = this.getDialogs()[n];
                    dialog.on("hide", sandboxSelf.resize);
                    dialog.on("create", function(){
                        setTimeout( function(){
                            sandboxSelf.resize();
                        }, 100);
                    });
                }
            },

            resize: function(){
                //$(document.body).css("overflow", "hidden");
                var minWidth = 900;
                var minHeight = 600;
                
                //alert(bodyContainer.offset().top);
                // set height
                var offsetTop = document.getElementById("body-container").offsetTop;
                var height = $(window).height() - offsetTop -11 - $("#footer").height(); // - $("#addsline").height()
                if(height < minHeight) height = minHeight;
                $("#map_canvas").height( (height /*- $("#addsline").height()*/) + "px");
                $("#issueListBody").height((height - $("#issueListHeader").height() -12) + "px");
                $(document.body).css("overflow", "auto");
                
                // set width
                var width = $(window).width();
                if(width < minWidth){                    
                    width = minWidth;
                } 
                width -=2;
                $("#body-container,#layoutHeader,#location-container").width( width +"px");
            },

            addIssue: function(issue){
                issues.push(issue);
            },
            
            removeIssueById: function(issueId){
                var issue = null;
                for(var i=0; i < issues.length; i++){
                    if(issues[i].id == issueId){
                        issue = issues[i];
                        issues.splice(i, 1);
                        break;
                    }
                }
                return issue;
            },

            clearIssues: function(){
                issues.length = 0;
            },

            getIssues: function(){
                return issues;
            },

            findIssueById: function(issueId){
                var issue = null;
                for(var i=0; i < issues.length; i++){
                    if(issues[i].id == issueId){
                        issue = issues[i];
                        break;
                    }
                }
                return issue;
            },

            getFilterType: function(){
                try{
                var list = $("#shortcutsViewForm").serializeArray();
                return list[1].value;
                } catch(e) {
                    return "all";
                }
            },
            
            getFilterDate: function(){
                var list = $("#shortcutsViewForm").serializeArray();               
                return list[0].value;
            },
            
            resolveAddress: function(address, callback, scope){
                bai.MapUtils.geocode(address, function(result, status){
                    if(result.length == 0){
                        callback.call(scope, null);
                    } else if(result.length == 1){
                        callback.call(scope, bai.MapUtils.getAddressFromGeocodeResult(result[0]));
                    } else {
                        sandbox.showDialog("selectAddress", [result, function(index){
                           callback.call(scope, bai.MapUtils.getAddressFromGeocodeResult(result[index]));
                        }, me]);
                    }
                });
            },
            
            // Last searched map location saved in cookies
            saveLocationCookie: function(address, mapZoom){
                if(address){
                    address.geocodeResult = null;
                    var zoom = mapZoom ? mapZoom : 13;
                    var jsonObj = {address:address,mapZoom:zoom};
                    // stringify obj
                    sandbox.writeCookie("location", $.toJSON(jsonObj));
                    sandbox.writeCookie("appVersion", window._appVersion);
                }
            },
            
            // Load last searched map location saved in cookies
            getLocationCookie: function(){
                // parse json obj
                var jsonStr = sandbox.readCookie("location");
                var appVersion = sandbox.readCookie("appVersion");
                
                // only use cookie if the version is higher then 0.30
                if(appVersion){
                    try{
                        appVersion = parseFloat(appVersion);
                        if( appVersion > 0.3 ){
                            var json = $.evalJSON(jsonStr);
                            return json;
                        }
                    }catch(e){
                        return null;
                    }
                }
                return null;
            }
        };
        return sandboxSelf;
    });
    sandbox.createDialogs();
    sandbox.preloadImages([ 
        "arrow-short.png", "arrow.png", "crime.png", "info.png", "all.png", 
        "trafficcamera.png", "transportation.png", "restaurants.png", 
        "entertainment.png"
    ], "/images/markers/");
    sandbox.preloadImages(["thumbs.png"], "/images/");
       
    // create views    
    var mapView;
    
    var issueList = new bai.module.IssueListView("issueList", sandbox);
    var shortcutsView = new bai.module.ShortcutsView("shortcuts", sandbox);
    
    /*var locationCookie = sandbox.getLocationCookie();
    // Find the initial position to start the mapview
    // First we check for the cookie information stored in the last access of this user.
    if( locationCookie ){
        initMapView(
            new google.maps.LatLng( locationCookie.address.lat, locationCookie.address.lng )
            , locationCookie.address, locationCookie.mapZoom
        );
    } else */ 
    if( !window._addr && window._geoIpLocation ){
        // guess location using geoip        
        initMapView(new google.maps.LatLng(_geoIpLocation.lat, _geoIpLocation.lng));
    } else {
        // use porto alegre if geoip location fails
        var address = "Porto Alegre - Rio Grande do Sul, Brazil";
        if( window._addr ) {
            address = window._addr;
        }
        sandbox.resolveAddress(address, function(addressResolved){
            var zoom;
            if(window._zoom){
                try{
                    zoom = parseInt(window._zoom);
                } catch (e){
                    zoom = 13;
                }
            }
            var viewport = null;
            try{
                viewport = addressResolved.geocodeResult.geometry.viewport;
            } catch(e){}
            
            initMapView( addressResolved.geocodeResult.geometry.location, addressResolved, zoom, viewport );
        }, me);
        /*
        // guess location using HTML5 
        bai.MapUtils.guessUserLocation(function(latLng){
            if(latLng){
                initMapView(latLng);
            } else {
                // the browser could not find the user location, use Porto Alegre instead
                var address = "Porto Alegre - Rio Grande do Sul, Brazil";
                sandbox.resolveAddress(address, function(addressResolved){
                    initMapView( addressResolved.geocodeResult.geometry.location, addressResolved );
                }, me);
            }
        });*/
    }
    
    /**
     * @param latLng <google.maps.LatLng> required
     * @param address <Object> optional
     * @param zoom <Integer> zoom value for map
     */
    function initMapView(latLng, address, zoom, fitBounds){
        var mapOptions = {latLng:latLng};
        if(zoom != null) mapOptions.zoom = zoom;
        if(fitBounds != null) mapOptions.fitBounds = fitBounds
            
        mapView = bai.module.MapView("map_canvas", sandbox, mapOptions);
        
        initEvents();
        if(address && address.formatted){
            $("#addressField").val(address.formatted);
            loadIssues(address, true);
        } else {
            // find the formatted address to put in the address bar
            bai.MapUtils.geocodeByLatLng(latLng, function(result){
                // find the best geocode result
                var resultAddr;
                for(var i=result.length-1; i >= 0; i--){
                    resultAddr =  bai.MapUtils.getAddressFromGeocodeResult(result[i]);
                    if(resultAddr.city){
                        break;
                    }
                }
                $("#addressField").val(resultAddr.formatted);
                loadIssues(resultAddr, true);
            }, me);
        }
        // load the issues
        showInitialDialog();
        $("#body-container").removeClass("loading");
    }
    
    
    function showInitialDialog(){
        if(window._showDialog){
            var dialog = sandbox.getDialog(_showDialog);
            if(_showDialog == "signIn"){
                sandbox.showSignIn();
            } else {
                dialog.show();
            }
            if(window._fields){
                dialog.setValues(_fields);
            }
        }
    }

    
    function initEvents(){
        sandbox.resize();
        $(window).resize(function(){
            sandbox.resize();
        });

        var createIssueDialog = sandbox.getDialog("createIssue");
        var commentsDialog = sandbox.getDialog("comments");

        $("#reportButton").click(function(){
            sandbox.showDialog("createIssue", [$("#addressField").val()]);
        });

        $("#whantToHelpLink").click(function(){
            sandbox.showFeedback();
            $("#feedbackTextField").html("Escreva aqui como você gostaria de ajudar.");
        });

        $("#searchButton").click(search);
        $("#addressField").keyup( function(ev){
            if (ev.keyCode == '13') {
                ev.preventDefault();
                search();
            }
        });

        commentsDialog.on("hide", function(){
            mapView.showIssueInfo(commentsDialog.getCurrentIssue());
        });

        $("#infoWindowCommentsLink").live( "click", function(){
            sandbox.showDialog("comments",[mapView.getCurrentIssue()]);
            return false;
        });

        $("#infoWindowDeleteLink").live("click", function(){
            //alert(this.href);
            //alert("deletar issue: " + mapView.getCurrentIssue());
            var href = this.href,
                issueId = href.substring(href.indexOf("#")+1);
                
            var confirmMsg = "Deseja remover permanentemente esta mensagem?"
            if(confirm(confirmMsg)){
                sandbox.requestJson("POST", "/issue/delete/" + issueId, function(json){
                    if(json.success){
                        var issue = sandbox.removeIssueById(issueId);

                        // remove do mapview
                        mapView.removeMarker(issue.marker);

                        // remove da listview
                        issueList.removeIssue(issue.id);

                    } else {
                        alert("Ocorreu um erro, tente novamente mais tarde \nou nos envie um feedback informando o que aconteceu.");
                    }
                }, me, null);
            }
        });

        // Sort list by
        issueList.on("sortBy", function(o){
            loadIssues(sandbox.currentAddress, true);
        }, this);

        // Filter type icons
        $("#shortcutsViewForm").change(function(){
            // Verifica se é um dado aberto 
            if ( sandbox.getFilterType().indexOf("od-") == 0 ){
                loadOpenData(sandbox.currentAddress);
            } else {
                loadIssues(sandbox.currentAddress, true);
            }
        });

        issueList.on("mouseoverIssue", function(o){
            //if(window.isOpenDataFilter) return;
            var issue = sandbox.findIssueById(o.issueId);
            mapView.startMarkerAnimation(issue.marker, "BOUNCE");
        }, this);
        
        issueList.on("mouseoutIssue", function(o){
            //if(window.isOpenDataFilter) return;
            var issue = sandbox.findIssueById(o.issueId);
            mapView.stopMarkerAnimation(issue.marker);
        }, this);

        issueList.on("clickIssue", function(o){
            //if(window.isOpenDataFilter) return;
            var issue = sandbox.findIssueById(o.issueId);
            mapView.stopMarkerAnimation(issue.marker);
            mapView.showIssueInfo(issue);
        });
        
        issueList.on("requestIssues", function(o){
            if(issueRequest.hasMoreListItems && !issueRequest.inProgress){
                requestIssues("/issue/listItems", issueRequest.nListItems, issueRequest.maxResults);
            }
        });

        var showAddressMarker = function(){
            mapView.autoDropAddAddressMarker();
        }
        $("#addressBarMarkerLink").click(showAddressMarker);
        createIssueDialog.on("addressMarkerClick", showAddressMarker);
        
        mapView.on("expandMap", function(expand){
            if(expand){
                $("#body-container").addClass("expandMap");
            } else {
                $("#body-container").removeClass("expandMap");
            }
            mapView.triggerMapEvent("resize");
        });
        mapView.on("closeInfoWindow", function(o){
           var issue = o.issue;
           issueList.unselectAll();
        });
        mapView.on("markerClick", function(o){
           var issue = o.issue;
           mapView.showIssueInfo(issue);
        });
        mapView.on("addressMarkerClick", function(o){
           sandbox.showDialog("createIssue", [$("#addressField").val()]);
        });
        mapView.on("addressMarkerDragend", function(o){
           var address = o.address;
           $("#addressField").val(address.formatted);
           try{
            console.log("lat: " + address.lat + " lng: " + address.lng);
           } catch(e){}
        });

        createIssueDialog.on("createIssue", function(o){
            var requestResult = o.result;
            var issue = requestResult.issue;
            sandbox.addIssue(issue);
            issue.marker = mapView.addMarker(issue);
            issueList.appendIssueHtml(requestResult.listItemHtml, issue);
            mapView.showIssueInfo(issue);
            issueList.highlight(issue, true);
            ping();
        });

        mapView.on("votePositive", function(o){
           vote(o.issue, true);
        });
        mapView.on("voteNegative", function(o){
           vote(o.issue, false);
        });
        
        // ping for more issues
        setInterval(ping, PING_INTERVAL); // every 1 minute
    }

    /**
     * Go button click action
     */
    function search(){
        if($("#searchButton").hasClass("ui-state-disabled"))
            return;
        
        enableSearchButton(false);
        sandbox.resolveAddress($("#addressField").val(), function(address){
            if(address && address.formatted){
                $("#addressField").val(address.formatted);
                loadIssues(address);
            } else {
                enableSearchButton(true);
            }
        }, me);
    }

    /**
     * Post a vote request
     * @param issue
     * @param positive <Boolean>
     */
    function vote(issue, positive){
        var url = "issue/vote/" + issue.id;
        sandbox.requestJson("POST", url, function(json){
            if(json.success){
                $("#infoWindow .vote-container").html(json.html.infoWindowThumbs);
                issueList.updateIssueHtml(json.issueId, json.html.listItem);
            } else {
                //alert(json.errorMessage);
            }
        }, me, "positive="+positive);
    }
    
        
    function loadIssues(address, kipViewport){
        window.isOpenDataFilter = false;
        // define the location to be placed in the header of the list view
        var filterLocationType = "all";
        var location = "Todo mundo";
        if(address.city && address.state){
            location = address.city + " - " + address.state;
            filterLocationType = "city";
        } else if(address.state){
            location = address.state + " - " + address.country_long_name;
            filterLocationType = "state";
        } else if(address.country_long_name){
            filterLocationType = "country";
            location = address.country_long_name;
        }
        $("#issueListLocation").html(location);
                
        // post data
        var data;
        if(address && address.lat && address.lng){
            data = {formatted:address.formatted, filterLocationType:filterLocationType, filterLocation:address[filterLocationType], lat:address.lat, lng:address.lng};
            data.sortListBy = issueList.getSortByValue();
            data.filterType = sandbox.getFilterType();
            data.filterDate = sandbox.getFilterDate();
        }
        data.listLocationFormatted = location;
        
        
        issueRequest.postData = data;
        mapView.clearMarkers();
        sandbox.clearIssues();
        mapView.addAddressMarker(address);
        sandbox.currentAddress = address;
        issueList.clear();
        issueRequest.nMarkers = 0;
        issueRequest.nListItems = 0;
        
        var requestCallbackFn = function(json){
            enableSearchButton(true);
            if(!kipViewport){
                kipViewport=true;
                mapView.goTo(sandbox.currentAddress);
            }
            // show the issue requested by param
            var issue = null;
            if(window._showIssue){
                issue = sandbox.findIssueById(window._showIssue);
                if(issue){
                    mapView.showIssueInfo(issue);
                    window._showIssue = null;
                }
            }
            
            sandbox.saveLocationCookie(sandbox.currentAddress, mapView.getZoom());
        }
        requestIssues("/issue/list", 0, issueRequest.maxResults, requestCallbackFn, me);
        
        // show the footer
        $("#footer").css("visibility", "visible");
        $(document.body).removeClass("loading");
        sandbox.resize();
    }
    
    function ping(){
        if(!window.isOpenDataFilter){
            requestIssues("/issue/list", 0, issueRequest.maxResults, null, me, issueRequest.lastUpdated);
        }
    }
   
    /**
     * Request issues end show in the list
     */
    function requestIssues(url, firstResult, maxResults, callbackFn, callbackScope, dateStart){
        var postData = issueRequest.postData;
        postData.firstResult = firstResult;
        postData.maxResults = maxResults;
        if(dateStart){
            postData.dateStart = dateStart;
        } else {
            postData.dateStart = "";
        }
        
        issueRequest.inProgress = true;
        sandbox.requestJson("POST", url, function(json){
            if(json.lastUpdated)
                issueRequest.lastUpdated = json.lastUpdated;
            
            issueRequest.inProgress = false;
            if(json.issues){
                var issues = json.issues, issue;
                for(var i = 0; i< issues.length; i++){
                    issue = issues[i];
                    issue.marker = mapView.addMarker(issues[i]);
                    sandbox.addIssue(issue);
                }
                issueRequest.nMarkers += issues.length;
                // we are loading all markers at once
                issueRequest.hasMoreMarkers = false;
            }
            
            if(dateStart){
                // loading new issues
                if(json.issues && json.issues.length >0){
                    issueList.renderIssues(json.html, true);
                }
            } else if(json.html){
                // here we can load all issues or the empty message
                issueList.renderIssues(json.html);
                issueRequest.nListItems += json.totalResults;
                if(issueRequest.maxResults > json.totalResults){
                    issueRequest.hasMoreListItems = false;
                } else {
                    issueRequest.hasMoreListItems = true;                    
                }
            }
            
            if(callbackFn){
                var scope = callbackScope ? callbackScope : me;
                callbackFn.call(scope, json);
            }
        }, me, issueRequest.postData );
    }

    function loadOpenData(address){
        window.isOpenDataFilter = true;
        mapView.clearMarkers();
        sandbox.clearIssues();
        mapView.addAddressMarker(address);
        sandbox.currentAddress = address;
        issueList.clear();
        issueRequest.nMarkers = 0;
        issueRequest.nListItems = 0;

        var typeFilter = sandbox.getFilterType().substring(3)
        
        var postData = "type=" + typeFilter
        sandbox.requestJson("POST", "/issue/listOpenData", function(json){
            if(json.status == "success"){
                issueList.renderIssues(json.listHtml);

                var record, issue, num, fakeId=99999;
                for(var i=0; i<json.records.length; i++){
                    record = json.records[i];
                    num = record[2];
                    //for(var j=0; j<num; j++){
                        issue = {
                            id:record[0]
                            , addr: { lat: record[5], lng:record[6] }
                            , desc: "( " +num+ " ) " + record[3] + " - " + record[4]
                            , type: record[1]
                        };

                        issue.marker = mapView.addMarker(issue);
                        sandbox.addIssue(issue);
                    //}
                    //setTimeout(function(){}, 1);
                }
            }
        }, me, postData);        
    }
});
