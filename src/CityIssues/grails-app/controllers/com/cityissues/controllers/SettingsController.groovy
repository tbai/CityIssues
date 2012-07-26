package com.cityissues.controllers

import com.cityissues.models.*
import grails.plugins.springsecurity.Secured
import grails.converters.*
import org.apache.commons.validator.EmailValidator

class SettingsController {

    def userService
    /**
     * 
     */
    def index = {
        
    }

    def updatePassword = {
        def result = userService.updateUserData([password:params.password])
        if(result.success){
            result.successMsg = message(code:"settings.sub.password.success")
        }
        render result as JSON
    }

    def updateUser = {
        def result = userService.updateUserData([name:params.name])
        if(result.success){
            result.successMsg = message(code:"settings.sub.user.success")
        }
        render result as JSON
    }
}
