/**
 * geocode result types:
 *
 *
 */

bai.MapUtils = (function(){
    var geocoder;
    try{
        geocoder = new google.maps.Geocoder();
    } catch(e){
        
    }

    var that = {
        
        guessUserLocation: function(callbackFn, scope){
            var initialLocation;
            var cancelRequest = false;
            // Try W3C Geolocation (Preferred)
            //if(navigator.geolocation) {
            if(geo_position_js.init()){
                setTimeout(cancel, 10000);
                //navigator.geolocation.getCurrentPosition(function(position) {
                geo_position_js.getCurrentPosition( 
                    function(position){ 
                        if(cancelRequest) return;
                        initialLocation = new google.maps.LatLng(position.coords.latitude,position.coords.longitude);
                        callbackFn.call(scope, initialLocation);
                    }, 
                    function(error) {
                        if(cancelRequest) return;
                        handleNoGeolocation();
                    }, 
                    { maximumAge:Infinity, timeout:5000 }
                );
            // Browser doesn't support Geolocation
            } else {
                alert("handle no geolocation");
                handleNoGeolocation();
            }
            
            function cancel(){
                cancelRequest = true;
                callbackFn.call(scope);
            }

            function handleNoGeolocation() {
                callbackFn.call(scope, null);
            }
        },

        getIssueLatLng: function(issue){
            var result = null;
            if(issue){
                if(issue.addr){
                    result = new google.maps.LatLng(issue.addr.lat, issue.addr.lng);
                }
            }
            return result;
        },

        geocode: function(address, callback){
            var opt = {
                address: address,
                bounds: null,
                language: "pt",
                region: "BR"
            };
            geocoder.geocode(opt, callback);
        },

        geocodeByLatLng: function(latlng, callback){
            var opt = {
                address: "",
                bounds: null,
                language: "pt",
                location:latlng,
                region: "BR"
            };
            geocoder.geocode(opt, callback);
        },
        
        getAddressFromGeocodeResult: function(geocodeResult){
            
            var result = {
                geocodeResult:geocodeResult,
                formatted: geocodeResult.formatted_address,
                lat:geocodeResult.geometry.location.lat(),
                lng: geocodeResult.geometry.location.lng(),
                state:null,
                neighborhood:null,
                city:null,
                country:null,
                cep:null,
                street:null,
                number:null
            };

            // parser para encontrar rua, bairro, cidade, estado e país
            var addr, types;
            for(var i=0; i<geocodeResult.address_components.length; i++){
                addr = geocodeResult.address_components[i];
                types = addr.types;
                if(types.length == 1 && types[0] == "route" ){
                    result.street = addr.long_name;
                }
                else if(types.length == 2 && types[0] == "sublocality" && types[1] == "political"){
                    result.neighborhood = addr.long_name;
                }
                else if(types.length == 2 && types[0] == "locality" && types[1] == "political"){
                    result.city = addr.long_name;
                }
                else if(types.length == 2 && types[0] == "administrative_area_level_1" && types[1] == "political"){
                    result.state = addr.short_name;
                }
                else if(types.length == 2 && types[0] == "country" && types[1] == "political"){
                    result.country = addr.short_name;
                    result.country_long_name = addr.long_name;
                }
                else if(types.length == 1 && types[0] == "postal_code" ){
                    result.cep = addr.long_name;
                }
                else if(types.length == 1 && types[0] == "street_number" ){
                    result.number = addr.long_name;
                }
            }

            return result;
        }
    }
    return that;
})();
