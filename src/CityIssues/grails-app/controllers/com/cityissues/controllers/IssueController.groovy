package com.cityissues.controllers

import grails.plugins.springsecurity.Secured
import com.cityissues.models.*
import com.cityissues.models.sec.*
import grails.converters.*
import groovyx.net.http.ContentType
import groovy.time.*

class IssueController {
    def issueService
    //def cookieService


    /**
     * Render view to create new issue
     */
    def create = {
        def issueTypes = IssueType.list().collect {
            [name:message(code:"issueType.${it.name}"), id:it.id]
        }
        [issueTypes:issueTypes]
    }

    /**
     * Ajax action to save an issue
     * @return json
     */
    @Secured(['ROLE_USER'])
    def save = {
        log.info( "Creating issue: address=$params.address")
        def result = issueService.createIssue(
            params.issueType,
            params.description,
            [
                formatted:  params.address,
                lat:        params.lat?.toFloat(),
                lng:        params.lng?.toFloat(),
                country:    params.country,
                state:      params.state,
                city:       params.city,
                number:     params.number,
                cep:        params.cep,
                neighborhood:params.neighborhood
            ]
        )
        if(result.success){
            result.listItemHtml = g.render(template:"/issue/listItem", model:[issue:result.issue])
            result.issue = issueService.toJson(result.issue)
        }
        render result as JSON
    }

    /**
     * @param firstResult
     * @param maxResults
     * @param filterLocationType
     * @param filterLocation
     * @param sortListBy
     * @param filterType
     * @param filterDate
     */
    def list = {
        log.info "Issue list - $params"
        def maxResults = params.maxResults ? params.maxResults.toInteger() : 0

        def filterLocationType = params.filterLocationType ? params.filterLocationType.toLowerCase() : "all"
        def filterLocationValue = params.filterLocation ? params.filterLocation.toLowerCase() : null

        def list = issueService.list(
            0, //params.firstResult ? params.firstResult.toInteger() : 0,
             maxResults, 
            //0,
            params.filterType, 
            params.filterDate, 
            params.sortListBy,
            filterLocationType,
            filterLocationValue,
            params.dateStart && params.dateStart.size() > 0? params.dateStart:null
        )

        def issues = []
        
        def issueItem
        def htmlList = []
        def counter = 0
        list.each{
            issueItem = issueService.toJson(it)
            issues.push(issueItem)
            if( 
                counter < maxResults && (
                    filterLocationType?.equals("all") ||
                    it.address[filterLocationType]?.toLowerCase().equals(filterLocationValue) 
                )
            ){
                htmlList.push(it)
                counter++
            }
        }
        def html
        if(htmlList.size() > 0){
            html = g.render(template:"/issue/listBody", model:[issues:htmlList])
        } else {
            html = g.render(template:"/issue/listBodyEmpty", model:[requestParams:params])
        }
        def map = [issues:issues, totalResults:htmlList.size(), html:html, lastUpdated: (new Date()).getTime()]
        render map as JSON
    }
    
    def listMarkers = {        
    }
    
    /**
     * Used when scrolling down in the list of items
     * 
     * @param firstResult
     * @param maxResults
     * @param filterLocationType
     * @param filterLocation
     * @param sortListBy
     * @param filterType
     * @param filterDate
     */
    def listItems = {
        def maxResults = params.maxResults ? params.maxResults.toInteger() : 0
        def filterLocationType = params.filterLocationType ? params.filterLocationType.toLowerCase() : "all"
        def filterLocationValue = params.filterLocation ? params.filterLocation.toLowerCase() : null
        def list = issueService.list(
            params.firstResult ? params.firstResult.toInteger() : 0,
            maxResults,
            params.filterType, 
            params.filterDate, 
            params.sortListBy,
            filterLocationType,
            filterLocationValue
        )
        
        def map = [totalResults:list.size(), html:g.render(template:"/issue/listBody", model:[issues:list])]
        render map as JSON
    }
    
    @Secured(['ROLE_USER'])
    def delete = {
        log.info "Delete issue: $params.id"
        def result = issueService.deleteIssue(params.id)
        render result as JSON
    }

    @Secured(['ROLE_USER'])
    def vote = {
        log.info "Vote in issue: $params.id, positive:$params.positive"
        def result = issueService.vote(params.id, params.positive.equals("true"))
        if(result.success){
            result.issueId = result.issue.id
            result.html = [
                infoWindowThumbs:g.render(template:"/issue/infoWindowThumbs", model:[issue:result.issue]),
                listItem:g.render(template:"/issue/listItem", model:[issue:result.issue]),
            ]
        }
        render result as JSON
    }

    def infoWindow = {
        log.info "Info window - id: $params.id"
        def issue = Issue.get(params.id)
        def model = [issue:issue]
        render(template:"infoWindow",contentType:"text/xml",encoding:"UTF-8", model:model)
    }

    def numberOfComments = {
        def url = "${bai.serverURL()}?i=${params.id}"
        def num = 0
        withHttp(uri: "https://graph.facebook.com", contentType:ContentType.JSON) {
           def json = get(path: '/', query: [ids:url])
           num = json?."$url"?.comments ?:0
        }
        def json = [numberOfComments:num, issueId:params.id]
        render json as JSON
    }

    def listOpenData = {
        def records = Issue.withCriteria{
            projections { 
                property "id"
                type{
                    property "icon"
                }
                count("id")
                property "description"
                address {
                    property "formatted"
                    groupProperty "lat"
                    groupProperty "lng"
                }
            }
            type {
                eq "isOpenData", true
                eq "name", params.type
            }
        }

        def issues = []
        records.each {
            def issue = Issue.get(it[0])

            issues.push([
                id:issue.id,
                type:[name:issue.type.name, icon:issue.type.icon, isOpenData:true],
                address:[formatted:issue.address.formatted],
                dateStart:issue.dateStart,
                totalVotes:0,
                description : "( " +it[2]+ " ) " + it[3] + " - " + it[4]
            ])
        }

        def html = g.render(template:"/issue/listBody", model:[issues:issues])
        def json = [status:"success", records:records, listHtml:html]
        render json as JSON
    }
}
