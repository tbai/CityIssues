package com.cityissues.controllers
import java.util.ResourceBundle

class JsController {
    def messages = {
        def result = ""
        result+= 'window._message = {};'
        def filter = params.filter.split(",")
        ResourceBundle rb = ResourceBundle.getBundle("grails-app/i18n/messages", new Locale("pt", "BR"));
        def e
        for( e = rb.getKeys(); e.hasMoreElements(); ){
            def key = e.nextElement();
            filter.each{
                if(key.startsWith(it)){
                    result+= "_message['${key}']='${g.message(code:key)}';";
                }
            }
        }
        render result
    }
}
