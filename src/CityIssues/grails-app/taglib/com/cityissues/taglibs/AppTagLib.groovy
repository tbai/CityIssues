package com.cityissues.taglibs
import com.cityissues.models.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import java.util.ResourceBundle
import java.lang.Math
import grails.util.Environment
import com.cityissues.utils.*

class AppTagLib {
    static namespace = "bai"
    
    def issueService

    def isNonSupportedBrowser = { attrs, body ->
        def request = SpringUtils.getRequest()
        // detect if need to whow the browser warning
        def isNotSupported = false
        def userAgent = request.getHeader("user-agent")
        def pos, browserVersion
        if (userAgent && (pos = userAgent?.indexOf("MSIE")) >= 0) {
            browserVersion = userAgent.substring(pos + 5).trim();
            if (browserVersion.indexOf(" ") > 0)
                browserVersion = browserVersion.substring(0, browserVersion.indexOf(" "));
            if (browserVersion.indexOf(";") > 0)
                browserVersion = browserVersion.substring(0, browserVersion.indexOf(";"));
                browserVersion = Float.parseFloat(browserVersion)
            isNotSupported = browserVersion < 8
            log.debug("Browser type: MSIE " + browserVersion);
        }

        if(isNotSupported){
            out << body()
        }
    }

    def serverURL = {
        out << ConfigurationHolder.config.grails.serverURL
    }

    def config = { attrs ->
        out << ConfigurationHolder.config.flatten().getProperty(attrs.key)
    }

    def appVersion = {
        out << grailsApplication.metadata['app.version']
    }

    def jsMessages = { attrs->
        def lang = "pt_BR"
        def params = [filter:attrs.filter, v:appVersion(), lang:lang]
        if(Environment.current == Environment.DEVELOPMENT){
            params.c = Math.random()
        }
        def url = g.createLink(controller:"js", action:"messages", params:params )
        out << "<script type=\"text/javascript\" src=\"${url}\"></script>"
    }
    
}
