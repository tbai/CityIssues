package cityissues



class SecurityFilters {

    def filters = {
        /*
        ajaxAuth(controller:'*', action:'*') {
            def found = false
            before = {
                // get saved request
                def savedAjaxRequest = session["savedAjaxRequest"]
                if(savedAjaxRequest){
                    // check if the current request is the same as the saved one
                    // before the ajax login
                    if( request.getServletPath().contains(savedAjaxRequest.getServletPath()) ){
                        found = true
                        def savedParams = savedAjaxRequest.getParameterMap()
                        // put the saved params in the new request
                        savedParams.each{ key, value ->
                            params[key] = value
                        }
                    }
                }

            }
            after = {
                if(found){
                    session["savedAjaxRequest"] = null
                }
            }
            afterView = {
                
            }
        }
        */
    }
    
}
