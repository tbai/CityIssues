package com.cityissues.services

import com.cityissues.models.*
import com.cityissues.models.BroadcastMethod as BM

import com.cityissues.services.*
import grails.converters.*
import groovyx.net.http.ContentType
import groovy.time.*

class BroadcastService {
    
    def googleMapsService
    def addressService
    def issueService

    static transactional = true
    
    def FIRST_STREET_REGEX = /(?i)(avenida|av|av\.|rua|bairro|r\.) (?-i)(de |da |do )?\p{Lu}\p{Ll}+( ((do|da|de) )?\p{Lu}\p{Ll}+)?/
    
    def ADDR_STARTER_REGEX = /(?i)(avenida|av|av\.|rua|r\.|trav.|travessa|bairro|estrada)(\.| )/
    def INTERSECTION_REGEX = /(?i)(prox\.|esq\.|esquina|próximo a|no cruzamento)/
    
    def makeRequest(broadcast){
        
        if(broadcast?.type == "twitter"){
            makeTwitterRequest(broadcast)
        }
    }
    
    def twitterRequest(query, sinceId = null, rpp = 20, page=1){
        def json = [:]
        def reqQuery = [q:query, rpp:rpp, page:page, since_id:sinceId]
        withHttp(uri: "http://search.twitter.com", contentType:ContentType.JSON) {
           json = get(path: '/search.json', query: reqQuery)
        }
        
        json
    }
    
    def makeTwitterRequest( broadcast ){
        // request last twitts 
        def json = twitterRequest(broadcast.query, broadcast.sinceId)
        
        if( !json?.results?.size() )
            return
            
        log.debug "Broadcast request name:${broadcast?.name}, type:${broadcast.type}, results:${json.results.size()}"
            
        def addrs, geocode
        def successResults = 0
        for(twit in json.results ) {            
            if( proccessResult(broadcast, twit) ){
                successResults ++
                if(successResults >= broadcast.maxResults){
                    break
                }
            }
        }
        
        broadcast.sinceId = json.results[0].id_str
        broadcast.save()
    }
    
    def proccessResult(broadcast, resultObj){
        def text = resultObj.text
        log.debug "Creating new issue from text: '${text}'"

        def numberOfIssuesCreated = 0
        def method = broadcast.method
        if(method == BM.USE_ADDRESS){
            // create one issue for the address in broadcast object
            if(broadcast.address){
                // force expire for other issues from this broadcast
                def now = new Date()
                broadcast.issues.each{ issue ->
                    issue.dateFinish = now
                    issue.save()
                }
                createIssue(broadcast, broadcast.address, text)
                numberOfIssuesCreated++
            }
        } else {
            // find address from text
            def addrs = findAddress(text, broadcast)
            log.debug "Address list: ${addrs}"
            
            def searchAddress
            def address
            
            if( method == BM.ONE_FOR_FIRST_ADDRESS ) {                
                if( addrs.size() > 1 && text.find(INTERSECTION_REGEX)){
                    log.debug "Looking for intersection: " + text
                    // try to find an intersection address
                    searchAddress = addrs[0] + " & " + addrs[1] + " - " + broadcast.address.city + " - " + broadcast.address.state + ", Brazil"
                    address = addressService.findPerfectAddressText(searchAddress)
                    if(address){
                        log.debug "Intersection found: " + searchAddress
                    }
                } 
                
                if( !address && addrs.size() >0){
                    searchAddress = addrs[0] + " - " + broadcast.address.city + " - " + broadcast.address.state + ", Brazil"
                    address = addressService.findPerfectAddressText(searchAddress)
                }
                if(address && createIssue(broadcast, address, text)){
                    numberOfIssuesCreated ++
                }
            } else if( method == BM.ONE_FOR_EACH_ADDRESS ){
                // create one issue for each address found
                for(addrText in addrs){
                    searchAddress = addrText
                    
                    searchAddress += " - " + broadcast.address.city + " - " + broadcast.address.state + ", Brazil"
                    address = addressService.findPerfectAddressText(searchAddress)
                    if(address && createIssue(broadcast, address, text)){
                        numberOfIssuesCreated ++
                    }
                }
            }
        }
        return numberOfIssuesCreated
    }
    
    def createIssue(broadcast, address, text){
        if(address && broadcast && text){
            def factor = 0.00000005f
            def v1 = (new Random()).nextInt(1000) * factor
            if( (new Random()).nextInt(2) == 0 )
            v1 = v1 * -1
            address.lat += v1
            
            def issueTypeName = broadcast.issueTypeName
            def ownerUser = User.findByEmail("suricatourbano@gmail.com")
            def schedule = getIssueSchedule(broadcast)
            
            // sligtly change the position of the icon in order to avoid several markers to be in the same place
            def v2 = (new Random()).nextInt(1000) * factor
            if( (new Random()).nextInt(2) == 0 )
                v2 = v2 * -1
            address.lng += v2
            
            def result = issueService.createIssue(issueTypeName, text, address, schedule, ownerUser)
            if(result.success){
                broadcast.addToIssues(result.issue)
                log.info "Issue [id:${result.issue.id}, type:$issueTypeName] created from broadcast request."
                return true
            }
        }
        return false
    }
    
    def getIssueSchedule(broadcast){
        def schedule = [ dateStart:new Date() ]
        try{
            use( [groovy.time.TimeCategory] ){
                def sp = broadcast.lifeTime.split("\\.")
                schedule.dateFinish = (new Date()) + sp[0].toInteger()[sp[1]]
            }
        } catch(Exception e){
            log.error "Can't parse time duration from string: [${broadcast.lifeTime}]"
            schedule.dateFinish = (new Date())+1
        }
        schedule
    }
   
    def findAddress(text, broadcast=null) {
        def regex = broadcast?.addressRegex ?: FIRST_STREET_REGEX
        // filter results and append the street starter e.g "av. " in each result
        text.findAll(regex).collect{
            if(it.size() > 2){
                if( !it.find(ADDR_STARTER_REGEX) ){
                    "av $it"
                } else {
                    it
                }
            }
        }
    }
    
}
