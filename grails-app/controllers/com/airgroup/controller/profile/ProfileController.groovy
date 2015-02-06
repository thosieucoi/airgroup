package com.airgroup.controller.profile

import org.weceem.auth.CMSUser;

class ProfileController {
	static allowedMethods = [save: "POST"]
	def springSecurityService

	def index = {
		if (!springSecurityService.isLoggedIn())
			redirect uri: "/home"

		def CMSUserInstance = springSecurityService.getCurrentUser()
		[CMSUserInstance: CMSUserInstance]
	}

	def save = {
		if (!springSecurityService.isLoggedIn())
			redirect uri: "/home"
		def CMSUserInstance = springSecurityService.getCurrentUser()
		CMSUserInstance.name = params.name
		CMSUserInstance.username = params.username
		CMSUserInstance.phoneNumber = params.phoneNumber

		if (CMSUserInstance.save(flush: true)) {
			render (view: "index", model: [success: true, CMSUserInstance: CMSUserInstance])
		} else {
			render(view: "index", model: [CMSUserInstance: CMSUserInstance])
		}
	}

	def changepassword = {
		if (!springSecurityService.isLoggedIn())
			if (params.uid && params.code) {
				def CMSUserInstance
				render(view: "newpassword", model: [CMSUserInstance: CMSUserInstance])
			}
			else
				redirect uri: "/home"
		else {
			def CMSUserInstance = springSecurityService.getCurrentUser()
			return [CMSUserInstance : CMSUserInstance]
		}
	}

	def savepassword = {
		if (!springSecurityService.isLoggedIn())
			redirect uri: "/home"

		def getUser = springSecurityService.currentUser
		def CMSUserInstance = CMSUser.get(getUser.id)

		String password = params.current_password
		if (CMSUserInstance) {
			if(params.current_password.equals("")){
				flash.message = "${message(code: 'user.header.checkblankpass')}"
				redirect(action: "changepassword")
			}
			else if(!springSecurityService.encodePassword(params.current_password).equals(getUser.password)){
				flash.message = "${message(code: 'current.password.doesnt.match.existing')}"
				redirect(action: "changepassword")
			}
			else if(params.new_password.equals("")){
				flash.message = "${message(code: 'user.header.checkblanknewpass')}"
				redirect(action: "changepassword")
			}
			else if(params.confirm_new_password.equals("")){
				flash.message = "${message(code: 'user.header.checkblankverifypass')}"
				redirect(action: "changepassword")
			}
			else if(!params.new_password.equals(params.confirm_new_password)){
				flash.message = "${message(code: 'user.header.checkblanknotequalpass')}"
				redirect(action: "changepassword")
			}
			else if(params.new_password.size() < 6 || params.confirm_new_password.size() < 6 || params.new_password.size() >32 || params.confirm_new_password.size() > 32){
				flash.message = "${message(code: 'user.header.limitpassword')}"
				redirect(action: "changepassword")
			}
			else{
				CMSUserInstance.setPass(params.new_password)
				if(CMSUserInstance.save(flush: true))
				{
					redirect action: success
				}
				else
				{
					flash.message =  "${message(code: 'user.header.error')}"
					redirect(action: "changepassword")
				}
			}
		}
		else {
			redirect uri: "/home"
		}
	}

	def newpassword = {
	}
	
	def success = {
		if (!springSecurityService.isLoggedIn())
			redirect uri: "/home"
	}
}
