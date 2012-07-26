package com.cityissues.services
import javax.servlet.http.HttpSession
import  javax.servlet.http.Cookie
import org.springframework.web.context.request.RequestContextHolder

class CookieService {
    static transactional = true

    def set(response, name, value){
        if(!response || !name || !value)
            return false
        
        def cookie = new Cookie(name, value.encodeAsURL())
        cookie.path = "/"
        cookie.maxAge = 60*60*24*365*5  // 5 years in seconds
        response.addCookie(cookie)
        return true
    }

    def get(name){
        def request = RequestContextHolder.getRequestAttributes().getRequest()
        def result = request?.cookies?.findAll{it.name == name}
        return result?.value?.decodeURL()
    }
}
