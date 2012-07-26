package com.cityissues.controllers

import grails.plugins.springsecurity.Secured
import com.cityissues.models.*
import com.cityissues.utils.*
import com.cityissues.models.sec.*
import grails.converters.*


class UserController {
    def userService


    /**
     * Ajax action to save a new user
     * @return json
     */
    def save = {
        log.info "Saving user: ${params.email}"
        def result = userService.create(params.email, params.password, params.name)
        if(result.success){
            result.html = g.render(template:"/login/signUpDialogResult")
        } else {
            def errors = [:]
            result.errors = AjaxUtils.toJsonErrors(result.errors)
        }
        render result as JSON
    }

    def activate = {
        def result = userService.activate(params.token)
        def model = [:]
        if(result.success){
            log.info "User ${result.user.email} activated"
            model.showDialog = "signIn"
            model.fields = [[name:"j_username",value:result.user.email]]
        } else if(result.errors){
            log.error "Erro ao ativar usuário: " + result.errors
        }
        flash.model = model
        redirect controller:"public", action:"index"
    }
}
