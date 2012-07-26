package com.cityissues.services
import javax.servlet.http.HttpSession

import com.cityissues.models.sec.*
import com.cityissues.models.*
import com.cityissues.utils.*
import ru.perm.kefir.asynchronousmail.AsynchronousMailService

class UserService {

    static transactional = true

    def springSecurityService
    def mailService
    AsynchronousMailService asynchronousMailService

    def user = {
        def user = null
        if( springSecurityService.isLoggedIn() ){
            user = User.findByUsername(springSecurityService.principal.username)
        }
        user
    }

    def create(email, password, name, sendEmail=true, isAdmin=false, isLocked=false, isEnabled=false){
        def result = [success:false]

        if(password){
            password = springSecurityService.encodePassword(password)
        } else {
            password = springSecurityService.encodePassword(
                "${(Math.random()*10000)}"
            )
        }

        def activationToken = springSecurityService.encodePassword( 
            "${email}${name}${(Math.random()*10000)}"
        )

        def user = new User(
            username: email, name:name, email:email, password: password,
            activationToken:activationToken, enabled: isEnabled, accountLocked:isLocked)
        
        result.user = user
        
        if(!user.save()){
            result.error = "saveUserError"
            result.errors = user.errors.allErrors
            return result
        } else if( sendEmail ){
            def g = SpringUtils.getGrailsTagLib()
            def url = g.createLink(absolute:true, controller:"user", action:"activate", id:activationToken)
            log.debug "Activation url for $email: $url"           
            // Send activation email
            asynchronousMailService.sendAsynchronousMail {
                from "Suricato Urbano<suricatourbano@gmail.com>"
                to email
                subject SpringUtils.getMessage("signUp.activationEmail.subject")
                body (view:"/email/userActivation", model:[name:name, url:url])
            }
        }

        SecUserSecRole.create(user, SecRole.findByAuthority("ROLE_USER"), true)
        if(isAdmin){
            SecUserSecRole.create(user, SecRole.findByAuthority("ROLE_ADMIN"), true)
        }
        
        
        result.success = true
        result
    }

    def activate(token){
        def result = [success:false]
        def user = User.findByActivationToken(token)
        def model = [:]
        if(user){
            result.user = user
            user.enabled = true
            user.activationToken = null
            user.save()
            result.success = true
        }
        return result
    }

    def updateUserData(map){
        def result = [success:true]

        def user = this.user()
        map.each{ key, value ->
            if(key.equals("password")){
                user[key] = springSecurityService.encodePassword(value)
            } else {
                user[key] = value
            }
        }

        if(!user.save()){
            result.errors = AjaxUtils.toJsonErrors(user.errors.allErrors)
        }
        result
    }
}
