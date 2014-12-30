package com.airgroup.controller.ipconfig

import com.airgroup.domain.Ipconfig;

class IpconfigController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        render template:'ipconfig', model:[ipconfigInstance: Ipconfig.list(params).getAt(1)]
    }

    def save = {
		def ipconfigInstance
		if (params?.id)
			ipconfigInstance = Ipconfig.get(params.id)
		else
		    ipconfigInstance = new Ipconfig(params)
		
		ipconfigInstance.properties = params
        if (!ipconfigInstance.hasErrors() && ipconfigInstance.save(flush: true)) {
            flash.message = "${message(code:'ipconfig.save.success')}"
            redirect action: list
        }
        else {
            render(view: "_ipconfig", model: [ipconfigInstance: ipconfigInstance])
        }
    }
}
