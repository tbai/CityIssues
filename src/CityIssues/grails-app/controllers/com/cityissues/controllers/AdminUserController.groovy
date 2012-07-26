package com.cityissues.controllers

import grails.plugins.springsecurity.Secured
import com.cityissues.models.*
import com.cityissues.models.sec.*
import grails.converters.*

import com.cityissues.models.sec.*
import com.cityissues.models.*


class AdminUserController {
    static scaffold = User

    def userService
    
    def save = {
        
        def check = { obj->
            obj?.toLowerCase().equals("on")
        }
        
        def result = userService.create(
            params.email, 
            params.password, 
            params.name, 
            false, // send email ?
            check(params.isAdmin),
            check(params.accountLocked),
            check(params.enabled)
        )
        def userInstance = result.user
        if(result.success){
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])}"
            redirect(action: "show", id: userInstance.id)
        } else {
            render view: "create", model:[ userInstance : userInstance ] 
        }
    }
    
    def update = {
        def userInstance = User.get(params.id)
        if (userInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (userInstance.version > version) {
                    
                    userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'user.label', default: 'User')] as Object[], "Another user has updated this User while you were editing")
                    render(view: "edit", model: [userInstance: userInstance])
                    return
                }
            }
            
            userInstance.properties = params
            
            def userRoles = userInstance.getAuthorities()*.authority
            println "userRoles = $userRoles"
            println "isAdmin = $params.isAdmin"
            if(params.isAdmin?.toLowerCase().equals("on")){
                // add admin role
                if(! ("ROLE_ADMIN" in userRoles)){
                    SecUserSecRole.create(userInstance, SecRole.findByAuthority("ROLE_ADMIN"), true)
                }
                 
            } else if( "ROLE_ADMIN" in userRoles ){
                // remove admin role
                SecUserSecRole.findBySecUserAndSecRole(userInstance, SecRole.findByAuthority("ROLE_ADMIN"))?.delete()
            }
            
            if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])}"
                redirect(action: "show", id: userInstance.id)
            }
            else {
                render(view: "edit", model: [userInstance: userInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
    }
}
