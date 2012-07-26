package com.cityissues.services
import javax.servlet.http.HttpSession

import com.cityissues.models.sec.*
import com.cityissues.models.*
import com.cityissues.utils.*
import ru.perm.kefir.asynchronousmail.AsynchronousMailService

class RecoveryService {

    static transactional = true

    def springSecurityService
    def mailService
    AsynchronousMailService asynchronousMailService

    def recoverPassword(email){
        def result = [success:false]
        def user = User.findByEmail(email)
        if(!user){
            result.errorMessage = SpringUtils.getMessage("recovery.username.notFound")
        } else {
            def recoverToken = springSecurityService.encodePassword(
                "${email}${user.name}${(Math.random()*10000)}"
            )
            def g = SpringUtils.getGrailsTagLib()
            def url = g.createLink(absolute:true, controller:"recovery", action:"edit", id:recoverToken)
            user.recoverToken = recoverToken
            if(user.save()){
                // Send activation email
                asynchronousMailService.sendAsynchronousMail {
                    from "Suricato Urbano<suricatourbano@gmail.com>"
                    to email
                    subject SpringUtils.getMessage("recovery.email.subject")
                    body (view:"/email/recoverPassword", model:[name:user.name?:'', url:url])
                }
                log.info "Recover password link: $url"
                result.success=true
            } else {
                log.error "Error saving recoverToken.", user.errors.allErrors
                result.errorMessage = SpringUtils.getMessage("recovery.default.error")
            }
        }
        result
    }

    def validateToken(token){
        def user = User.findByRecoverToken(token)
        if(user){
            true
        } else {
            false
        }
    }

    def updatePassword(token, password){
        def result = [success:false, status:""]
        def user = User.findByRecoverToken(token)
        log.info "Updating password for using token $token"
        if(!user){
            result.status = "invalidToken"
        } else {            
            user.password = springSecurityService.encodePassword(password)
            if(user.validate()){
                user.recoverToken = null
                result.success = true
                user.save()
            } else {
                result.errors = user.errors.allErrors
            }
        }
        result
    }
}
