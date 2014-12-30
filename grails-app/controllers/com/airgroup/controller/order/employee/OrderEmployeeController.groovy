package com.airgroup.controller.order.employee

import grails.converters.JSON

class OrderEmployeeController {
	
	def orderEmployeeService
	
    def index = { 
		redirect(action:'list')
	}
	
	def list = {
		orderEmployeeService.getRecallAndTimeLimitList(params)
	}
	
	def done = {
		orderEmployeeService.finishOrder(params)
		redirect(action:'index')
	}
	
	def getQuantityOfRecallAndTimeLimit = {
		def recallAndTimeLimitNums = orderEmployeeService.getRecallAndTimeLimitNumber()
		if(recallAndTimeLimitNums != null){
			render recallAndTimeLimitNums as JSON
		}
	}
}