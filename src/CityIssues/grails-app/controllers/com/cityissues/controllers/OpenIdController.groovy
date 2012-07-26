package com.cityissues.controllers

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.plugins.springsecurity.openid.OpenIdAuthenticationFailureHandler as OIAFH

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.savedrequest.DefaultSavedRequest

import com.cityissues.models.User
import com.cityissues.models.sec.SecUser
import com.cityissues.models.sec.SecRole
import com.cityissues.models.sec.SecUserSecRole

import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.context.SecurityContextHolder as SCH

import grails.converters.JSON

/**
 * Manages associating OpenIDs with application users, both by creating a new local user
 * associated with an OpenID and also by associating a new OpenID to an existing account.
 */
class OpenIdController {
    
    def userService

	/** Dependency injection for daoAuthenticationProvider. */
	def daoAuthenticationProvider

	/** Dependency injection for OpenIDAuthenticationFilter. */
	def openIDAuthenticationFilter

	/** Dependency injection for the springSecurityService. */
	def springSecurityService

	static defaultAction = 'auth'

	/**
	 * Shows the login page. The user has the choice between using an OpenID and a username
	 * and password for a local account. If an OpenID authentication is successful but there
	 * is no corresponding local account, they'll be redirected to createAccount to create
	 * a new account, or click through to linkAccount to associate the OpenID with an
	 * existing local account.
	 */
	def auth = {
        log.debug "openid auth"
		def config = SpringSecurityUtils.securityConfig

		if (springSecurityService.isLoggedIn()) {
			redirect uri: config.successHandler.defaultTargetUrl
			return
		}

		[
         //openIdPostUrl: createLink(controller:"openId", action:"form"),
         openIdPostUrl: "${request.contextPath}$openIDAuthenticationFilter.filterProcessesUrl",
		 daoPostUrl:    "${request.contextPath}${config.apf.filterProcessesUrl}",
		 persistentRememberMe: config.rememberMe.persistent,
		 rememberMeParameter: config.rememberMe.parameter,
		 openidIdentifier: config.openid.claimedIdentityFieldName]
	}
    
    def popup = {}
    def close = {
        def map = [success: true, username: springSecurityService.authentication.name]

        map.updateElements = [:]
        map.updateElements.headerLinksContainer = g.render(template:"/shared/headerLinks")
        def json = map as JSON
		map.json = json
        map
    }

	/**
	 * Initially we're redirected here after a UserNotFoundException with a valid OpenID
	 * authentication. This action is specified by the openid.registration.createAccountUri
	 * attribute.
	 * <p/>
	 * The GSP displays the OpenID that was received by the external provider and keeps it
	 * in the session rather than passing it between submits so the user has no opportunity
	 * to change it.
	 */
	def createAccount = { OpenIdRegisterCommand command ->
        log.info "Creating new account using google" 
        
        String openId = session[OIAFH.LAST_OPENID_USERNAME]
		if (!openId) {
            log.error "OpenId not found"
			flash.error = 'Sorry, an OpenID was not found'
			return [command: command]
		}

        def email, name
        try{
            def openIdAttrs = session[OIAFH.LAST_OPENID_ATTRIBUTES]
            email = openIdAttrs[0]?.getValues()[0]
            name = openIdAttrs[1].getValues()[0]
            
            def user = User.findByEmail(email)
            
            if(!user){
                def fakePassword = springSecurityService.encodePassword(
                    "${email}${name}${(Math.random()*10000)}"
                )
                def result = userService.create(email, fakePassword, name, false, false, false, true)
                if(result.success){
                    user = result.user
                    log.info "New user created using google account: $email"
                } else {
                    return [command: command, openId: openId]
                    log.error "Error creating new user from google account." + result.user.errors.allErrors.collect{it.toString()}
                }                
            } else {
                log.info "User $email found. Associating it with openid."
            }
            
            user.addToOpenIds(url: openId)
            user.save()
            
        } catch(Exception e){
            log.error "Error creating user with open id. ", e
            e.printStackTrace()
            return [command: command, openId: openId]
        }
        
		authenticateAndRedirect email
	}
    
   

	/**
	 * The registration page has a link to this action so an existing user who successfully
	 * authenticated with an OpenID can associate it with their account for future logins.
	 */
	def linkAccount = { OpenIdLinkAccountCommand command ->
        log.debug "Link account"
		String openId = session[OIAFH.LAST_OPENID_USERNAME]
		if (!openId) {
			flash.error = 'Sorry, an OpenID was not found'
			return [command: command]
		}

		if (!request.post) {
			// show the form
			command.clearErrors()
			return [command: command, openId: openId]
		}

		if (command.hasErrors()) {
			return [command: command, openId: openId]
		}

		try {
			registerAccountOpenId command.username, command.password, openId
		}
		catch (AuthenticationException e) {
			flash.error = 'Sorry, no user was found with that username and password'
			return [command: command, openId: openId]
		}

        
		authenticateAndRedirect command.username
	}

	/**
	 * Authenticate the user for real now that the account exists/is linked and redirect
	 * to the originally-requested uri if there's a SavedRequest.
	 *
	 * @param username the user's login name
	 */
	private void authenticateAndRedirect(String username) {
        log.debug "authenticateAndRedirect: $username"
		session.removeAttribute OIAFH.LAST_OPENID_USERNAME
		session.removeAttribute OIAFH.LAST_OPENID_ATTRIBUTES

		springSecurityService.reauthenticate username
        
		def config = SpringSecurityUtils.securityConfig

		def savedRequest = session[DefaultSavedRequest.SPRING_SECURITY_SAVED_REQUEST_KEY]
		if (savedRequest && !config.successHandler.alwaysUseDefault) {
			redirect url: savedRequest.redirectUrl
		}
		else {
			redirect uri: config.successHandler.defaultTargetUrl
		}
	}


	/**
	 * Associates an OpenID with an existing account. Needs the user's password to ensure
	 * that the user owns that account, and authenticates to verify before linking.
	 * @param username  the username
	 * @param password  the password
	 * @param openId  the associated OpenID
	 */
	private void registerAccountOpenId(String username, String password, String openId) {
        log.debug "registerAccountOpenId $username"
		// check that the user exists, password is valid, etc. - doesn't actually log in or log out,
		// just checks that user exists, password is valid, account not locked, etc.
		/*daoAuthenticationProvider.authenticate(
				new UsernamePasswordAuthenticationToken(username, password))
                */
		SecUser.withTransaction { status ->
			def user = SecUser.findByUsername(username)
			user.addToOpenIds(url: openId)
			if (!user.validate()) {
				status.setRollbackOnly()
			}
		}
	}

	/**
	 * For the initial form display, copy any registered AX values into the command.
	 * @param command  the command
	 */
	private void copyFromAttributeExchange(OpenIdRegisterCommand command) {
		List attributes = session[OIAFH.LAST_OPENID_ATTRIBUTES] ?: []
		for (attribute in attributes) {
			// TODO document
			String name = attribute.name
            
			if (command.hasProperty(name)) { 
				command."$name" = attribute.values[0]
			}
		}
	}
}

class OpenIdRegisterCommand {

    String email = ""
    String name = ""
	String username = ""
	String password = ""
	String password2 = ""

	static constraints = {
		username blank: false, email:true, validator: { String username, command ->
			SecUser.withNewSession { session ->
				if (username && SecUser.countByUsername(username)) {
					return 'openIdRegisterCommand.username.error.unique'
				}
			}
		}
		password blank: false, minSize: 8, maxSize: 64, validator: { password, command ->
			if (command.username && command.username.equals(password)) {
				return 'openIdRegisterCommand.password.error.username'
			}

			if (password && password.length() >= 8 && password.length() <= 64 &&
					(!password.matches('^.*\\p{Alpha}.*$') ||
					!password.matches('^.*\\p{Digit}.*$') ||
					!password.matches('^.*[!@#$%^&].*$'))) {
				return 'openIdRegisterCommand.password.error.strength'
			}
		}
		password2 validator: { password2, command ->
			if (command.password != password2) {
				return 'openIdRegisterCommand.password2.error.mismatch'
			}
		}
	}
}

class OpenIdLinkAccountCommand {

	String username = ""
	String password = ""

	static constraints = {
		username blank: false
		password blank: false
	}
}
