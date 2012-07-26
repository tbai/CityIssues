package com.cityissues.controllers

import grails.plugins.springsecurity.Secured
import com.cityissues.models.*
import com.cityissues.models.sec.*
import grails.converters.*

class AdminIssueController {
    static scaffold = Issue
    
    def filterPaneService
    
    def filter = {
        if(!params.max) params.max = 10
        
        render( view:'list', 
            model:[ issueInstanceList: filterPaneService.filter( params, Issue ), 
            issueInstanceTotal: filterPaneService.count( params, Issue ), 
            filterParams: org.grails.plugin.filterpane.FilterPaneUtils.extractFilterParams(params), 
            params:params ] )
    }
   
}
