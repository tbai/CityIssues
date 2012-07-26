package com.cityissues.jobs

import com.cityissues.services.*
import com.cityissues.models.*

import org.slf4j.LoggerFactory
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class BroadcastJob {

    def static log = LoggerFactory.getLogger(BroadcastJob.class)
    
    def broadcastService
    
    static triggers = {
        // every five minutes
        //simple name: 'broadcastTrigger', startDelay: 60000, repeatInterval: 600000   
        simple name: 'broadcastTrigger', 
        startDelay: 60000, 
        repeatInterval: ConfigurationHolder.config.broadcast.jobPeriod   
    }
  

    def execute() {
        log.debug "Broadcast job"
        def updatePeriod
        def makeRequest = true
        return 
        Broadcast.list().each{ broadcast ->
            log.debug "Broadcast job: $broadcast.name"
            updatePeriod = broadcast.updatePeriod
            def now
            if(updatePeriod){
                makeRequest = false
                def lastUpdated = broadcast.lastUpdated
                def sp
                log.debug "${broadcast.name} indexof=" + updatePeriod.indexOf(".")
                if(updatePeriod.indexOf(".") > 0){
                    now = new Date()
                    sp = updatePeriod.split("\\.")
                    // use time
                    use( [groovy.time.TimeCategory] ){
                        log.debug "${broadcast.name}: now=$now next=${(lastUpdated + sp[0].toInteger()[sp[1]])}"
                        if( (lastUpdated + sp[0].toInteger()[sp[1]]) <= now )
                            makeRequest = true
                    }
                } 
            } else {
                makeRequest = true
            }
            
            if(makeRequest){
                log.debug "make request for $broadcast.name"
                broadcastService.makeRequest(broadcast)
            }
        }
    }
}
