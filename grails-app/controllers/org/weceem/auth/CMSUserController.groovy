package org.weceem.auth

import groovyx.gpars.dataflow.DataFlowExpression.BindDataFlow;

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.security.authentication.encoding.PasswordEncoder

import com.airgroup.domain.Customer;
import com.airgroup.domain.Order
import com.airgroup.domain.OrderEmployee;
class CMSUserController {
	def springSecurityService
    static allowedMethods = [save: "POST", update: "POST"]

    def index = {

        redirect(action: "list", params: params)
    }

    def list = {
		def user = springSecurityService.currentUser
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
		params.offset = params.offset? params.offset.toInteger():0
		params.sort = params.sort?:'id'
		params.order = params.order?:'desc'
        [CMSUserInstanceList: CMSUser.list(params), CMSUserInstanceTotal: CMSUser.count(),currentUID: user.id]
    }	
	def listEmpCall = {
		def users = CMSUser.findAllByCallCenterStatus((short)1)
		[EmpCallInstanceList: users]
	}
    def create = {
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
		// verify passwords
		if (params.password == params.verifiedPassword) {
			if (CMSUserInstance.save(flush: true)) {
				if (params.password)
					CMSUserInstance.setPass(params.password)
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'CMSUser.label', default: 'CMSUser'), CMSUserInstance.id])}"
				redirect(action: "list", id: CMSUserInstance.id)
			} else {
				render(view: "create", model: [CMSUserInstance: CMSUserInstance])
			}

		} else {
			flash.message = "${message(code: 'CMSUser.password.notEqual')}"
			render(view: "create", model: [CMSUserInstance: CMSUserInstance])
		}
    }

    def show = {
        def CMSUserInstance = CMSUser.get(params.id)
		
		//Customize sort column for order employee
		params.max = Math.min( params.max ? params.max.toInteger() : 20,  100)
		params.offset = params.offset? params.offset.toInteger():0
		params.sort = params.sort?:'id'
		params.order = params.order?:'desc'
		def lst = OrderEmployee.createCriteria().list(
			max:    params.max,
			offset: params.offset
			){
			or {
				eq('processEmp', CMSUserInstance)
				eq('bookEmp', CMSUserInstance)
			}
			
		  createAlias ('order', 'o')

		  if (params.sort == 'order') {
          	order ('o.id', params.order)
			order ('o.orderTime', params.order)
			order ('o.status', params.order)
		  } else {
          	order (params.sort, params.order)
		  }
		}
		def totalCount = OrderEmployee.executeQuery("from OrderEmployee o where o.bookEmp = ? or o.processEmp = ?", [CMSUserInstance, CMSUserInstance]).size()
        if (!CMSUserInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'CMSUser.label', default: 'CMSUser'), params.id])}"
            redirect(action: "list")
        }
        else {
            [CMSUserInstance: CMSUserInstance, orderInstanceList: lst, orderTotal:totalCount]
        }
    }

    def edit = {
        def CMSUserInstance = CMSUser.get(params.id)
        if (!CMSUserInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'CMSUser.label', default: 'CMSUser'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [CMSUserInstance: CMSUserInstance]
        }
    }

    def update = {
        def CMSUserInstance = CMSUser.get(params.id)
		String pattern = /[a-zA-Z0-9\-_.!@#$%^&*(){},;]+/
        if (CMSUserInstance) {
			if (params.newPassword) {
				CMSUser user = CMSUser.findByUsername(params.username)
				// validate that the entered confirmation password matches the users existing password
				
				if (params.newPassword.length() < 6) {
					flash.message = "${message(code:'CMSUser.password.minSize.notmet')}"
					render(view: "edit", model: [CMSUserInstance: CMSUserInstance])
					return
				}
				if (!params.newPassword.matches(pattern)) {
					flash.message = "${message(code:'CMSUser.password.matches.invalid')}"
					render(view: "edit", model: [CMSUserInstance: CMSUserInstance])
					return
				}
				if (params.newPassword != params.verifiedPassword) {
					flash.message = "${message(code:'CMSUser.password.notEqual')}"
					render(view: "edit", model: [CMSUserInstance: CMSUserInstance])
					return
				}
			}
			params.callCenterStatus = params.callCenterStatus.equals("on") ? '1'.toShort() : '0'.toShort()
			CMSUserInstance.properties = params
			if (params.newPassword)
				CMSUserInstance.setPass(params.newPassword)
            if (!CMSUserInstance.hasErrors() && CMSUserInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'CMSUser.label', default: 'CMSUser'), CMSUserInstance.id])}"
                redirect(action: "list", id: CMSUserInstance.id)
            }
            else {
                render(view: "edit", model: [CMSUserInstance: CMSUserInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'CMSUser.label', default: 'CMSUser'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
		def CMSUserInstance
        try {
			if (params.id) {
				CMSUserInstance = CMSUser.get(params.id)
				CMSUserInstance.status = params.status.toShort()
				if (CMSUserInstance) {
					CMSUserInstance.save(flush: true)
					flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'CMSUser.label', default: 'CMSUser'), params.id])}"
					redirect(action: "list")
				}	
			}            
        }
        catch (org.springframework.dao.DataIntegrityViolationException e) {
            flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'CMSUser.label', default: 'CMSUser'), params.id])}"
            redirect(action: "list", id: params.id)
        }
    }
	
	def changePassword = {
		def user = springSecurityService.currentUser
		return [user : user]
	}
	
	def editpass={
		def getUser = springSecurityService.currentUser
		def CMSUserInstance = CMSUser.get(getUser.id)

		String password = params.oldPassword
		if (CMSUserInstance) {

			if (!CMSUserInstance.hasErrors() && CMSUserInstance.save(flush: true)) {
				if(!springSecurityService.encodePassword(params.oldPassword).equals(getUser.password)){
					flash.message = "${message(code: 'current.password.doesnt.match.existing', args: [message(code: 'CMSUser.label', default: 'CMSUser'), CMSUserInstance.id])}"
					redirect(action: "changePassword")
				}
				else if(params.oldPassword.equals("")){
					flash.message = "${message(code: 'user.header.checkblankpass', args: [message(code: 'CMSUser.label', default: 'CMSUser'), CMSUserInstance.id])}"
					redirect(action: "changePassword")
				}
				else if(params.newPassword.equals("")){
					flash.message = "${message(code: 'user.header.checkblanknewpass', args: [message(code: 'CMSUser.label', default: 'CMSUser'), CMSUserInstance.id])}"
					redirect(action: "changePassword")
				}
				else if(params.verifiedPassword.equals("")){
					flash.message = "${message(code: 'user.header.checkblankverifypass', args: [message(code: 'CMSUser.label', default: 'CMSUser'), CMSUserInstance.id])}"
					redirect(action: "changePassword")
				}
				else if(!params.newPassword.equals(params.verifiedPassword)){
					flash.message = "${message(code: 'user.header.checkblanknotequalpass', args: [message(code: 'CMSUser.label', default: 'CMSUser'), CMSUserInstance.id])}"
					redirect(action: "changePassword")
				}
				else if(params.newPassword.size() < 6 || params.verifiedPassword.size() < 6 || params.newPassword.size() >32 || params.verifiedPassword.size() > 32){
					flash.message = "${message(code: 'user.header.limitpassword', args: [message(code: 'CMSUser.label', default: 'CMSUser'), CMSUserInstance.id])}"
					redirect(action: "changePassword")
				}
				else{
					CMSUser user = CMSUser.findByUsername(params.username)
					// validate that the entered confirmation password matches the users existing password
					if (!springSecurityService.passwordEncoder.isPasswordValid(user.password, password, null /*salt*/)) {
					   flash.message = "${message(code:'current.password.doesnt.match.existing')}"
					   render(view: "changePassword", model: [CMSUserInstance: CMSUserInstance])
					}
					if (params.newPassword != params.verifiedPassword) {
						flash.message = "${message(code:'CMSUser.password.notEqual')}"
						render(view: "changePassword", model: [CMSUserInstance: CMSUserInstance])
					}
					CMSUserInstance.setPass(params.newPassword)
					redirect(action: "show", id: CMSUserInstance.id)
				}
				
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'CMSUser.label', default: 'CMSUser'), params.id])}"
			redirect(action: "show", id: CMSUserInstance.id)
		}
	}
	
}
