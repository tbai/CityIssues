$(document).ready(function() {
    $("#saveButton").button();
    $("#cancelButton").button();
    $("#selectTypeBytton").button().next()
				.button( {
					text: false,
					icons: {
						primary: "ui-icon-triangle-1-s"
					}
				})
				.click(function() {
					alert( "Could display a menu to select an action" );
				})
				.parent()
					.buttonset();

    var sandbox = new bai.Sandbox("issueCreate", function(){
        var geocoder = new google.maps.Geocoder();
        return {
            geocode: function(address, callback){
                var opt = {
                    address: address,
                    bounds: null,
                    language: "pt",
                    region: "BR"
                };
                geocoder.geocode(opt, callback);
            },
            getAddressFromGeocodeResult: function(result){
                return {
                    formatted: result.formatted_address,
                    lat:result.geometry.location.lat(),
                    lng: result.geometry.location.lng()
                }
            }
        }
    });
    
    $("#saveButton").click(function(ev){
        ev.preventDefault();
        sandbox.geocode($("#addressField").val(), function(results, status){
            if (status != google.maps.GeocoderStatus.OK) {
                alert("Endereço inválido.");
                return;
            }
            var validResult = sandbox.getAddressFromGeocodeResult(results[0]);
            $("#addressField").val(validResult.formatted);
            
            var formData = $("#formIssueCreate").serialize();
            return true;    
            sandbox.requestJson("POST", "issue/save", function(json){

            }, this, formData);
            
        });
    });
});