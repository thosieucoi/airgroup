package com.airgroup.controller.login
import grails.converters.JSON

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.weceem.auth.CMSUser;

import com.airgroup.domain.Ipconfig;

class LoginController {
	
	static allowedMethod = [checkMasterCode:"POST", auth: "POST"]

	/**
	 * Dependency injection for the authenticationTrustResolver.
	 */
	def authenticationTrustResolver

	/**
	 * Dependency injection for the springSecurityService.
	 */
	def springSecurityService

	/**
	 * Default action; redirects to 'defaultTargetUrl' if logged in, /login/auth otherwise.
	 */
	def index = {
		if (springSecurityService.isLoggedIn()) {
			
			redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
		}
		else {
			redirect action: auth, params: params
		}
	}

	/**
	 * Show the login page.
	 */
	def auth = {
		def superCode = Ipconfig.executeQuery("select code from Ipconfig")[0]
		def ipRemote = request.getRemoteAddr().trim()
		int isInDomain = 1
		def isActive = Ipconfig.executeQuery("select status from Ipconfig")[1]
		if (isActive == 1){
			boolean isIp = false
			def ipPermit = Ipconfig.executeQuery("select ip from Ipconfig")[1]
			String tmp = ipPermit.toString()
			def arrayIp = tmp.split("\\;")
			for (int i = 0; i < arrayIp.size();i++){
				if (arrayIp[i].trim().equals(ipRemote)){
					isIp = true
					break	
				}
			}		
			
			if (isIp == true){
				isInDomain = 0
				def config = SpringSecurityUtils.securityConfig
				if (springSecurityService.isLoggedIn()) {
					CMSUser user=CMSUser.findByUsername(springSecurityService.authentication.name)
					user.setLastAccessTime(new Date())
					user.save(flush:true)
					redirect uri: config.successHandler.defaultTargetUrl
					return
				}
						
				String view = 'auth'
				String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
				render view: view, model: [postUrl: postUrl, rememberMeParameter: config.rememberMe.parameter, isInDomain:isInDomain]
			}
			else {
				isInDomain = 1
//				if(params.master_password == null){
//					isInDomain = 2
//				}
				if(params.master_password != null){
					def password = Ipconfig.executeQuery("select code from Ipconfig")[1]
					if(!password.equals(params.master_password) && !superCode.equals(params.master_password)){
						isInDomain = 2
					}
					else {
						isInDomain = 0
					}
				}
				[isInDomain:isInDomain]
			}
		}
		
		else {
			isInDomain = 0
			def config = SpringSecurityUtils.securityConfig
			if (springSecurityService.isLoggedIn()) {
				CMSUser user=CMSUser.findByUsername(springSecurityService.authentication.name)
				user.setLastAccessTime(new Date())
				user.save(flush:true)
				redirect uri: config.successHandler.defaultTargetUrl
				return
			}
					
			String view = 'auth'
			String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
				
			render view: view, model: [postUrl: postUrl,
										 rememberMeParameter: config.rememberMe.parameter,isInDomain:isInDomain]
		}
	}
	
	/**
	 * Show denied page.
	 */
	def denied = {
		if (springSecurityService.isLoggedIn() &&
				authenticationTrustResolver.isRememberMe(SCH.context?.authentication)) {
			// have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
			redirect action: full, params: params
			//render view: 'denied', params: params	
		}
	}

	/**
	 * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
	 */
	def full = {
		def config = SpringSecurityUtils.securityConfig
		render view: 'auth', params: params,
			model: [hasCookie: authenticationTrustResolver.isRememberMe(SCH.context?.authentication),
			        postUrl: "${request.contextPath}${config.apf.filterProcessesUrl}"]
	}

	/**
	 * Callback after a failed login. Redirects to the auth page with a warning message.
	 */
	def authfail = {

		def username = session[UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY]
		String msg = ''
		def exception = session[AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY]
		if (exception) {
			if (exception instanceof AccountExpiredException) {
				msg = SpringSecurityUtils.securityConfig.errors.login.expired
			}
			else if (exception instanceof CredentialsExpiredException) {
				msg = SpringSecurityUtils.securityConfig.errors.login.passwordExpired
			}
			else if (exception instanceof DisabledException) {
				msg = SpringSecurityUtils.securityConfig.errors.login.disabled
			}
			else if (exception instanceof LockedException) {
				msg = SpringSecurityUtils.securityConfig.errors.login.locked
			}
			else {
				msg = "${message(code: 'error.login.fail')}"
			}
		}

		if (springSecurityService.isAjax(request)) {
			render([error: msg] as JSON)
		}
		else {
			flash.message = msg
			redirect action: auth, params: params
		}
	}

	/**
	 * The Ajax success redirect url.
	 */
	def ajaxSuccess = {
		render([success: true, username: springSecurityService.authentication.name] as JSON)
	}

	/**
	 * The Ajax denied redirect url.
	 */
	def ajaxDenied = {
		render([error: 'access denied'] as JSON)
	}
}
