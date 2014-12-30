package com.airgroup.domain

import com.airgroup.domain.CustomerInfo;

class CustomerInfoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [customerInfoInstanceList: CustomerInfo.list(params), customerInfoInstanceTotal: CustomerInfo.count()]
    }

    def create = {
        def customerInfoInstance = new CustomerInfo()
        customerInfoInstance.properties = params
        return [customerInfoInstance: customerInfoInstance]
    }

    def save = {
        def customerInfoInstance = new CustomerInfo(params)
        if (customerInfoInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'customerInfo.label', default: 'CustomerInfo'), customerInfoInstance.id])}"
            redirect(action: "show", id: customerInfoInstance.id)
        }
        else {
            render(view: "create", model: [customerInfoInstance: customerInfoInstance])
        }
    }

    def show = {
        def customerInfoInstance = CustomerInfo.get(params.id)
        if (!customerInfoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'customerInfo.label', default: 'CustomerInfo'), params.id])}"
            redirect(action: "list")
        }
        else {
            [customerInfoInstance: customerInfoInstance]
        }
    }

    def edit = {
        def customerInfoInstance = CustomerInfo.get(params.id)
        if (!customerInfoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'customerInfo.label', default: 'CustomerInfo'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [customerInfoInstance: customerInfoInstance]
        }
    }

    def update = {
        def customerInfoInstance = CustomerInfo.get(params.id)
        if (customerInfoInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (customerInfoInstance.version > version) {
                    
                    customerInfoInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'customerInfo.label', default: 'CustomerInfo')] as Object[], "Another user has updated this CustomerInfo while you were editing")
                    render(view: "edit", model: [customerInfoInstance: customerInfoInstance])
                    return
                }
            }
            customerInfoInstance.properties = params
            if (!customerInfoInstance.hasErrors() && customerInfoInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'customerInfo.label', default: 'CustomerInfo'), customerInfoInstance.id])}"
                redirect(action: "show", id: customerInfoInstance.id)
            }
            else {
                render(view: "edit", model: [customerInfoInstance: customerInfoInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'customerInfo.label', default: 'CustomerInfo'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def customerInfoInstance = CustomerInfo.get(params.id)
        if (customerInfoInstance) {
            try {
                customerInfoInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'customerInfo.label', default: 'CustomerInfo'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'customerInfo.label', default: 'CustomerInfo'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'customerInfo.label', default: 'CustomerInfo'), params.id])}"
            redirect(action: "list")
        }
    }
}
