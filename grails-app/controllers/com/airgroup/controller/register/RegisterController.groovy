package com.airgroup.controller.register

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices
import org.weceem.auth.CMSUser
import grails.converters.JSON

class RegisterController {

	static allowedMethods = [save: "POST"]
	def daoAuthenticationProvider
	def rememberMeServices
	def springSecurityService
	
	def index = {
		if (springSecurityService.isLoggedIn())
			redirect uri: "/home"
		def CMSUserInstance = new CMSUser()
		CMSUserInstance.properties = params
		return [CMSUserInstance: CMSUserInstance]
	}

	def save = {
		def CMSUserInstance = new CMSUser(params)
		CMSUserInstance.regDate = new Date()
		CMSUserInstance.lastAccessTime = null
		CMSUserInstance.status = (short)1
		CMSUserInstance.callCenterStatus = (short)1
		CMSUserInstance.callCenterStatus = params.callCenter.equals("on") ? '1'.toShort() : '0'.toShort()

		if (CMSUserInstance.save(flush: true)) {
			if (params.password)
				CMSUserInstance.setPass(params.password)
			
			def auth = new UsernamePasswordAuthenticationToken(CMSUserInstance.username, params.password);
			def authtoken = daoAuthenticationProvider.authenticate(auth)
			SecurityContextHolder.getContext().setAuthentication(authtoken)
			rememberMeServices.loginSuccess(request, response, authtoken)
			redirect action: thanks, model: [CMSUserInstance: CMSUserInstance]
		} else {
			render(view: "index", model: [CMSUserInstance: CMSUserInstance])
		}
	}
	
	def loginsignupface = {
		if(params.sendData)
		{
			def data = JSON.parse(params.sendData)
			CMSUser user = CMSUser.findByUsername(data.email)
			if(user)
			{
				if(user.facebookid)
				{
					render([success: true, username : user.username, password : data.id] as JSON)
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
				if (CMSUserInstance.save(flush: true)) {
					render([success: true, username : CMSUserInstance.username, password : password] as JSON)
				} else {
					render([fail: true] as JSON)
				}
			}
		}
	}
	
	
	def thanks = {
		if (!springSecurityService.isLoggedIn())
			redirect uri: "/home"
	}
}
