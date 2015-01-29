package com.airgroup.controller.register

import org.weceem.auth.CMSUser

class RegisterController {

	static allowedMethods = [save: "POST"]

	def index = {
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
			render(view: "succsess", model: [CMSUserInstance: CMSUserInstance])
		} else {
			render(view: "index", model: [CMSUserInstance: CMSUserInstance])
		}
	}
}
