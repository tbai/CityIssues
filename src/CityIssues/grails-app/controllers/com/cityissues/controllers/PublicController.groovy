package com.cityissues.controllers

import com.cityissues.models.*
import com.cityissues.services.*
import grails.plugins.springsecurity.Secured
import grails.converters.*
import org.apache.commons.validator.EmailValidator
import ru.perm.kefir.asynchronousmail.AsynchronousMailService


class PublicController {
    def networkService
    def cookieService
    def googleMapsService
    
    AsynchronousMailService asynchronousMailService
    
    /**
     * @param sb Sort list by "date" | "score"
     * @param i Issue id
     * @param ft Filter issues by type "type1,type2,..."
     * @param fd Filter issues by date ""
     */
    def index = {
        log.info "Index - ip:${networkService.getIp()}, params=$params"
        def model = flash.model ?: [:]
        
        /*
        if(params.addr){
            def geoResult = googleMapsService.geocode(params.addr)
            if(geoResult?.results?.size() >0){
                def addr = googleMapsService.getAddressFromGeocodeResult(geoResult.results[0])
                println "addr=$addr"
                model.geoIpLocation = [
                    addressFormatted: addr.formatted,
                    lat: addr.lat,
                    lng: addr.lng
                ].encodeAsJSON()
            }
        }*/
        
        //if(!model.geoIpLocation){ 
            def location = networkService.getGeoIpLocation()
            if(location){
                model.geoIpLocation = [
                    addressFormatted: location.city + ", " + location.countryName,
                    lat: location.latitude,
                    lng: location.longitude
                ].encodeAsJSON()
            }
        //}
       
        model
        //[showDialog:"signIn", fields:[[name:"j_username", value:"tiagoxbai@gmail.com"]]]
    }
    
    def termsOfUse = {
        log.info "Terms Of Use - ip:${networkService.getIp()}"
    }
    
    def signUp = {
        log.info "Sign up - ip:${networkService.getIp()}"
        def model = flash.model ?: [:]
        model.showDialog = "signUp"
        render view:"index", model:model
    }
    
    def signIn = {
        log.info "Sign in - ip:${networkService.getIp()}"
        def model = flash.model ?: [:]
        model.showDialog = "signIn"
        render view:"index", model:model
    }

    def feedback = {
        log.info "Feedback - ip:${networkService.getIp()}"
        def result = [success:false]
        if(params.text?.size() && params.email?.size()){
            def emailValidator = EmailValidator.getInstance()
            if(!emailValidator.isValid(params.email)){
                result.errors = [email:message(code:'email.invalid')]
            } else {
                try{
                    asynchronousMailService.sendAsynchronousMail {
                      from params.email + "<suricatourbano@gmail.com>"
                      replyTo params.email
                      to "suricatourbano@gmail.com"
                      subject "[Suricato Feedback] de ${params.email}"
                      body params.text
                    }
                    result.html = g.render(template:"/public/feedbackDialogResult")
                    result.success=true
                } catch(Exception e){
                    log.error "Error sending feedback", e
                }
            }
        }
        render result as JSON
    }

    def help = {
        log.info "Help - ip:${networkService.getIp()}"
    }
    def comments = {}
}
