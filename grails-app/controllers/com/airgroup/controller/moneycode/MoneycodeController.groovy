package com.airgroup.controller.moneycode

import java.util.Formatter.DateTime;
import com.airgroup.domain.MoneyCode
import com.airgroup.services.IFlightResponse;

class MoneycodeController {
	
	def index = {
		redirect(action: "list")
	}

	def list = {
		params.sort = params.sort?:'id'
		params.order = params.order?:'desc'
		def c = MoneyCode.createCriteria()
		def results
		if(params.fromDate != null){
			c = MoneyCode.findByCreateDate(Date.parse('dd/MM/yyyy', params.fromDate))
			results = c
		}else{
			 results = c.list(){
				and{
					order(params.sort, params.order)
			}
		}
		}
		[moneyCodeInstanceList: results, moneyCodeInstanceTotal: MoneyCode.count()]
	}
	
	def edit = {
		def moneyCodeInstance = MoneyCode.get(params.id)
		[moneyCodeId: moneyCodeInstance.id, moneyCodeDate: moneyCodeInstance.createDate]
	}
	
	def create = {
		String check = "0"
		if(params.check!=null){
			check = params.check
		}
		[check: check]
	}
	
	def save = {
		def file
		file = request.getFile('file')
		def okcontents = ['application/vnd.ms-excel','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet']
		if (!okcontents.contains(file.getContentType())) {
		  flash.message = "${message(code: 'moneyCode.file.type')}"
		  redirect(action: "create")
		  return;
		}
		def moneyCodeCheck = MoneyCode.findByCreateDate(Date.parse('dd/MM/yyyy', params.date))
		if(moneyCodeCheck!=null){
			def tempfile = new File('file/'+moneyCodeCheck.fileName)
			tempfile.delete()
			new FileOutputStream('file/'+file.originalFilename).leftShift(params.file.getInputStream());
			moneyCodeCheck.fileName = file.originalFilename
			if(!moneyCodeCheck.save()){
				flash.message = "${message(code: 'moneyCode.file.unsave')}"
				redirect(action: "create")
				return
			}
			redirect(action: "list")
		}else{
			def moneyCodeCheckName = MoneyCode.findByFileName(file.originalFilename)
			if(moneyCodeCheckName!=null){
				flash.message = "${message(code: 'moneyCode.file.name')}"
				redirect(action: "create")
				return
			}
			new FileOutputStream('file/'+file.originalFilename).leftShift(params.file.getInputStream());
			def moneycode = new MoneyCode()
			moneycode.fileName = file.originalFilename
			moneycode.createDate = Date.parse('dd/MM/yyyy', params.date)
			if(!moneycode.save()){
				flash.message = "${message(code: 'moneyCode.file.unsave')}"
				redirect(action: "create")
				return
			}
			redirect(action: "list")
		}
	}
	
	def update = {
		def moneyCode = MoneyCode.get(params.id)
		if(moneyCode){
			def file = request.getFile('file')
			def okcontents = ['application/vnd.ms-excel','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet']
			if (!okcontents.contains(file.getContentType())) {
			  flash.message = "${message(code: 'moneyCode.file.type')}"
			  redirect(action: "create")
			  return;
			}
			def tempfile = new File('file/'+moneyCode.fileName)
			tempfile.delete()
			new FileOutputStream('file/'+file.originalFilename).leftShift(params.file.getInputStream());
			moneyCode.fileName = file.originalFilename
			if(!moneyCode.save()){
				flash.message = "${message(code: 'moneyCode.file.unsave')}"
				redirect(action: "create")
				return
			}
			redirect(action: "list")
		}
	}
	
	def fileExist= {
		boolean check = true
		def moneyCodeCheck = MoneyCode.findByCreateDate(Date.parse('dd/MM/yyyy', params.getDate))
		if(moneyCodeCheck!=null){
			check = false
		}
		render check
	}
	
	def downloadFile = {
		String fileName = params.fileName
		def file = new File("file/"+fileName)
		
		if (file.exists()) {
		   response.setContentType("application/octet-stream")
		   response.setHeader("Content-disposition", "filename=${file.name}")
		   response.outputStream << file.bytes
		   return
		}
	}
	def delete = {
		def moneyCodeInstance = MoneyCode.get(params.id)
		if (moneyCodeInstance) {
			try {
				def tempfile = new File('file/'+moneyCodeInstance.fileName)
				tempfile.delete()
				moneyCodeInstance.delete(flush: true)
				flash.message = "${message(code: 'moneyCode.deleted.message')}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'moneyCode.not.deleted.message')}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'moneyCode.not.found.message')}"
			redirect(action: "list")
		}
	}
}
