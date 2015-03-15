package com.airgroup.controller.contactus

import org.weceem.content.*

import com.airgroup.domain.ContactUs
import com.airgroup.domain.Policy
import org.weceem.auth.CMSUser

class ContactUsController {

	def index = {
		if(!WcmContent.findByTitle('Lien He')){
			redirect(uri:"/admin/editor/create?parent.id=&type=com.airgroup.domain.ContactUs&space.id=" + WcmSpace.findByName("Default").id)
		}
		else{
			redirect(uri:"/admin/editor/edit/" + WcmContent.findWhere(title:'Lien He').id)
		}
	}

	def infor = {
		def contactUs = ContactUs.findByTitle('Lien He')
		def users = CMSUser.findAllByCallCenterStatusAndStatus((short)1,(short)1)
		
		session.title = "${message(code:'home.og.title')}"
		session.description = "${message(code:'home.og.description')}"
		session.url = "${message(code:'home.og.url')}"
		session.image = "${message(code:'home.og.image')}"
		
		[contact: contactUs, users: users]
	}

	def policyFrontEnd = {
		def policyURI = Policy.executeQuery('SELECT aliasURI FROM Policy').get(0)
		println(policyURI)
		redirect(uri:'/home/'+policyURI+'')
	}
}
