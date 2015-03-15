package com.airgroup.controller.register

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices
import org.weceem.auth.CMSUser
import grails.converters.JSON
import org.springframework.security.authentication.AuthenticationManager;

class RegisterController {

	static allowedMethods = [save: "POST"]
	def daoAuthenticationProvider
	def rememberMeServices
	def springSecurityService
	def ajaxAwareAuthenticationSuccessHandler
	def authenticationManager
	
	def index = {
		if (springSecurityService.isLoggedIn())
			redirect uri: "/home"
		def CMSUserInstance = new CMSUser()
		CMSUserInstance.properties = params
		
		session.title = "${message(code:'home.og.title')}"
		session.description = "${message(code:'home.og.description')}"
		session.url = "${message(code:'home.og.url')}"
		session.image = "${message(code:'home.og.image')}"
		
		return [CMSUserInstance: CMSUserInstance]
	}

	def save = {
		def CMSUserInstance = new CMSUser(params)
		CMSUserInstance.regDate = new Date()
		CMSUserInstance.lastAccessTime = null
		CMSUserInstance.status = (short)1
		CMSUserInstance.callCenterStatus = (short)1
		CMSUserInstance.callCenterStatus = params.callCenter.equals("on") ? '1'.toShort() : '0'.toShort()
		CMSUserInstance.code = UUID.randomUUID().toString();

		if (CMSUserInstance.save(flush: true)) {
			if (params.password)
				CMSUserInstance.setPass(params.password)
			
			autoLogin(CMSUserInstance.username, params.password);
			redirect action: thanks, model: [CMSUserInstance: CMSUserInstance]
		} else {
			render(view: "index", model: [CMSUserInstance: CMSUserInstance])
		}
	}
	
	def loginsignupface = {
		if(params.sendData)
		{
			def data = JSON.parse(params.sendData)
			if(data.email)
			{
				CMSUser user = CMSUser.findByUsername(data.email)
				if(user)
				{
					if(user.facebookid)
					{
						autoLogin(user);
						render([success: true] as JSON)
					}
					else
					{
						render([fail: true, username : user.username] as JSON)
					}
				}
				else
				{
					def CMSUserInstance = new CMSUser()
					CMSUserInstance.username = data.email
					def password = data.id;
					CMSUserInstance.setPass(password);
					CMSUserInstance.facebookid = data.id;
					CMSUserInstance.name = data.name
					CMSUserInstance.gender = data.gender
					CMSUserInstance.regDate = new Date()
					CMSUserInstance.lastAccessTime = null
					CMSUserInstance.status = (short)1
					CMSUserInstance.callCenterStatus = '0'.toShort()
					CMSUserInstance.code = UUID.randomUUID().toString();
					if (CMSUserInstance.save(flush: true)) {
						autoLogin(CMSUserInstance.username, password);
						render([success: true] as JSON)
					} else {
						render([fail: true] as JSON)
					}
				}
			}
			else
			{
				render([fail: true] as JSON)
			}
		}
	}
	
	private void autoLogin(String username, String password)
	{
		def auth = new UsernamePasswordAuthenticationToken(username, password);
		def authtoken = daoAuthenticationProvider.authenticate(auth)
		SecurityContextHolder.getContext().setAuthentication(authtoken)
		request.session.setAttribute("j_username", username)
		authenticationManager.authenticate(authtoken)
		rememberMeServices.loginSuccess(request, response, authtoken)
	}
	
	private void autoLogin(CMSUser user)
	{
		def authToken =  new UsernamePasswordAuthenticationToken(
				user.username, '', user.getAuthorities())
		SecurityContextHolder.context.authentication = authToken
		request.session.setAttribute("j_username", user.username )
		springSecurityService.reauthenticate(user.username);
		
		try
		{
			authenticationManager.authenticate(authToken)
		}
		catch (BadCredentialsException ex)
		{
			log.info(ex.toString());
		}
		catch (BadCredentialsException ex)
		{
			log.info(ex.toString());
		}
		
		rememberMeServices.loginSuccess(request, response, authToken)
	}
	
	
	def thanks = {
		if (!springSecurityService.isLoggedIn())
			redirect uri: "/home"
	}
}
