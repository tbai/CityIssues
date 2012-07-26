package com.cityissues.services

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import com.cityissues.google.UrlSigner
import groovyx.net.http.URIBuilder
import com.cityissues.models.*


class GoogleMapsService {

    static transactional = true
    
    def googleApiUrl = "https://maps.googleapis.com"
    def geocodePath = "/maps/api/geocode/json"

    def geocode(address) {
        def uri = new URIBuilder(googleApiUrl)
         uri.path = geocodePath
         uri.query =  [sensor: "false", address:address ]
        
        def url = new URL(uri.toString())
        UrlSigner signer = new UrlSigner(ConfigurationHolder.config.googleApi.key);
        String request = signer.signRequest(url.getPath(),url.getQuery());
        
        def result = [:]
        def http = new HTTPBuilder( googleApiUrl )
        http.get( uri:url ) { resp, json ->
            if(resp.statusLine.statusCode == 200){
                result = json
            }
        }
        result
    }
    
    def getAddressFromGeocodeResult(geocodeResult){
        if(! geocodeResult) 
            return null

        def result = [
            geocodeResult:geocodeResult,
            formatted: geocodeResult.formatted_address,
            lat:geocodeResult.geometry.location.lat,
            lng: geocodeResult.geometry.location.lng,
            state:null,
            neighborhood:null,
            city:null,
            country:null,
            cep:null,
            street:null,
            number:null
        ];

        // parser para encontrar rua, bairro, cidade, estado e país
        def types;
        geocodeResult.address_components.each { addr ->
            types = addr.types;
            if(types.size() == 1 && types[0] == "route" ){
                result.street = addr.long_name;
            }
            else if(types.size() == 2 && types[0] == "sublocality" && types[1] == "political"){
                result.neighborhood = addr.long_name;
            }
            else if(types.size() == 2 && types[0] == "locality" && types[1] == "political"){
                result.city = addr.long_name;
            }
            else if(types.size() == 2 && types[0] == "administrative_area_level_1" && types[1] == "political"){
                result.state = addr.short_name;
            }
            else if(types.size() == 2 && types[0] == "country" && types[1] == "political"){
                result.country = addr.short_name;
                result.country_long_name = addr.long_name;
            }
            else if(types.size() == 1 && types[0] == "postal_code" ){
                result.cep = addr.long_name;
            }
            else if(types.size() == 1 && types[0] == "street_number" ){
                result.number = addr.long_name;
            }
        }

        return result;
    }
    
    def search(location, types = null, radius = null, sensor = null) {
        def searchPath = "/maps/api/place/search/json"
        def detailsPath = "/maps/api/place/details/json"
    
        def defaultRadius = "2000"
        def defaultTypes = "store|food|park|food|city_hall|church|casino|car_rental|cafe|bus_station|airport"

        
        def http = new HTTPBuilder( googleApiUrl )
        
        def query = [
            location: location,
            types: types ?:defaultTypes,
            radius: radius ?: defaultRadius,
            sensor: sensor?:"false",
            key: ConfigurationHolder.config.googleApi.key            
        ]
        
        def result = [:]
        http.get( path: searchPath, query:query ) { resp, json ->
            if(resp.statusLine.statusCode == 200){
                result = json
            }
        }
        result 
    }
}
