class UrlMappings {

	static mappings = {
        "/"(controller:"public", action:"index")
        

        "/user/activate/$token"(controller:"user", action:"activate")
        
	    "/login/$action?"(controller:"login")
        "/logout/$action?"(controller:"logout")
        
        "/login/auth" {
            controller = 'openId'
            action = 'auth'
        }
        "/login/openIdCreateAccount" {
            controller = 'openId'
            action = 'createAccount'
        }

		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		
		"/help"(controller:"public", action:"help")
        "/ajuda"(controller:"public", action:"help")
        
        "/login"(controller:"public", action:"signIn")
        "/signup"(controller:"public", action:"signUp")
        "/cadastro"(controller:"public", action:"signUp")

		//"/"(view:"/index")
		"500"(view:'/error')
	}
}
