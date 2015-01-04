package com.airgroup.controller.policy

import org.weceem.content.*

import com.airgroup.domain.Policy

class PolicyController {

	def index = {
		if(!WcmContent.findByTitle('Policy')){
			redirect(uri:"/admin/editor/create?parent.id=&type=com.airgroup.domain.Policy&space.id=" + WcmSpace.findByName("Default").id)
		}
		else{
			redirect(uri:"/admin/editor/edit/" + WcmContent.findWhere(title:'Policy').id)
		}
	}

	def guide = {
		def policy = Policy.findByTitle('Policy')
		[policy: policy]
	}

	def policyFrontEnd = { redirect(uri:'/index') }
}
