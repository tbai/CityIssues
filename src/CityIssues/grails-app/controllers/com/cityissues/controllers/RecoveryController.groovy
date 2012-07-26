package com.cityissues.controllers
import com.cityissues.utils.*
import grails.converters.*


class RecoveryController {
    def recoveryService
    
    def index = {

    }

    def password = {
        flash.email=params.email

        def result = recoveryService.recoverPassword(params.email)
        if(result.success){
            []
        } else {
            flash.errorMessage=result.errorMessage
            redirect action:"index"
        }
    }

    /**
     * Edit password view
     */
    def edit = {
        if(recoveryService.validateToken(params.id)){
            [token:params.id]
        } else {
            render view:"invalidToken"
        }
    }

    /**
     * Post action for the edit password action
     */
    def update = {
        //return [:]
        def result = recoveryService.updatePassword(params.id, params.password)
        if(result.success){
            []
        } else if(result.status == "invalidToken"){
            render view:"invalidToken"
        } else {
            render view:"edit", model:[errors:AjaxUtils.toJsonErrors(result.errors)]
        }
    }

    //def invalidToken = {}
}
