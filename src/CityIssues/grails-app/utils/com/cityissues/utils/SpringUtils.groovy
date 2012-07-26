/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cityissues.utils
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.springframework.context.*
import org.springframework.validation.FieldError
import org.springframework.web.context.request.RequestContextHolder

/**
 *
 * @author tbai
 */
class SpringUtils {
	def static getBean(beanName){
        ApplicationHolder.application.mainContext.getBean beanName
	}

    def static getGrailsTagLib(){
        getBean('org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib')
    }

    def static getMessage(String code, args=[]){
        def locale = new Locale("pt","BR")
        def messageSource = getBean('messageSource')
        messageSource.resolveCode(code, locale).format( args.toArray() )
    }

    def static getMessage(FieldError fieldError){
        def locale = new Locale("pt","BR")
        def messageSource = getBean('messageSource')
        messageSource.getMessage(fieldError, locale)
    }

    def static getSession(){
        return RequestContextHolder.currentRequestAttributes()?.getSession()
    }

    def static getRequest(){
        return RequestContextHolder.currentRequestAttributes()?.currentRequest
    }
}

