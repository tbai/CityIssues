package com.cityissues.services
import com.maxmind.geoip.*
import org.springframework.web.context.request.RequestContextHolder
import org.codehaus.groovy.grails.commons.*

class NetworkService {

    def getIp(){
        def request = RequestContextHolder.currentRequestAttributes()
        def ipAddr = request.getHeader("Client-IP")
        if(!ipAddr){
            ipAddr = request.getHeader("X-Forwarded-For")
        }
        /*
        if(!ipAddr){
            ipAddr = request.getRemoteAddr()
        }*/
        
        if(ipAddr){
            def index = ipAddr.indexOf(",")
            if(index >0){
                ipAddr = ipAddr.substring(0, index-1)
            }
        }
        
        ipAddr
    }
    
    def getGeoIpLocation(ip = null) {
        def request = RequestContextHolder.currentRequestAttributes()
        def ipAddr = ip?: this.getIp()
        if(!ipAddr){
            return null;            
        } 
        
        println ConfigurationHolder.config.geoip.data.path
        LookupService ls = new LookupService(
            ConfigurationHolder.config.geoip.data.path,
			LookupService.GEOIP_MEMORY_CACHE );
        
        def location = ls.getLocation(ipAddr);
        return location;
    }
}
