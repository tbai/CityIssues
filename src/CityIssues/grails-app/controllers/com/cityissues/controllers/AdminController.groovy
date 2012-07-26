package com.cityissues.controllers

import grails.plugins.springsecurity.Secured
import com.cityissues.models.*
import com.cityissues.models.sec.*
import grails.converters.*

class AdminController {
    def issueService


    @Secured(['ROLE_ADMIN'])
    def parseEPTCRadars = {
        
    }

    @Secured(['ROLE_ADMIN'])
    def saveEPTCRadars = {
        def result = [success:false]
        def issueList = request.JSON

        def success = true
        def res
        issueList.each{ issue ->
            log.debug "Creating issue: $issue"
            res = issueService.createIssue("trafficcamera", issue.desc,
                [
                    formatted:  issue.address.formatted,
                    lat:        issue.address.lat?.toFloat(),
                    lng:        issue.address.lng?.toFloat(),
                    country:    issue.address.country,
                    state:      issue.address.state,
                    city:       issue.address.city
                ],[
                    dateStart: new Date(issue.date.toLong()),
                    dateFinish: (new Date(issue.date.toLong()))+1
                ]
            )
            success = success && res.success
        }
        result.success = success
        render result as JSON
    }

    @Secured(['ROLE_ADMIN'])
    def geocode = {
        
    }
}
