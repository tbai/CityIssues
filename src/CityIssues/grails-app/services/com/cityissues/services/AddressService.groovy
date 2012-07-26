package com.cityissues.services

import com.cityissues.models.*

class AddressService {

    static transactional = true

    def googleMapsService
    
    def create(addressText) {
        def geocode = googleMapsService.geocode(addressText)
        
        if( geocode.results.size() >0 ){
            // get the first result
            def map = googleMapsService.getAddressFromGeocodeResult(geocode.results[0])
        
            def address = new Address(map)
            if(address.save(flush:true)){
                return address
            } else {
                log.error "Unable to create new address from: $addressText"
            }
        }
        
        return null
    }
    
    /**
     * Return a map with address information only if a perfect match was found
     * @return Map with address info
     */
    def findPerfectAddressText(addrText){
        def geocode = googleMapsService.geocode(addrText)
        log.debug "${geocode.results.size()} geocode results found for address: $addrText"
        def types
        for( geocodeResult in geocode.results ){
            types = geocodeResult.types 
            if( "route" in types || "street_address" in types || "sublocality" in types ){
                return googleMapsService.getAddressFromGeocodeResult(geocodeResult)
            }
        }
        return null
    }
}
