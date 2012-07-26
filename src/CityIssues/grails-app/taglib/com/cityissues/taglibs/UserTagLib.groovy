package com.cityissues.taglibs
import com.cityissues.models.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class UserTagLib {
    static namespace = "user"

    def userService

    def userInfo = { attrs->
        def user = userService.user()
        if(user && attrs.name){
            out << user[attrs.name]
        } else {
            out << ""
        }
    }
}
