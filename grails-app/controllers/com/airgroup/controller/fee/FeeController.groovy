package com.airgroup.controller.fee

import com.airgroup.domain.Fee

class FeeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [feeInstance: Fee.list(params)]
    }

    def save = {
		def feeInstance
		def vietnamairline
		def vietjetair
		def jetstar
		def asiaPrice
		def asianPrice
		def americaPrice
		def europePrice
		def otherPrice
		
		try{
			vietnamairline = new BigDecimal(params.VN)
			vietjetair = new BigDecimal(params.VJ)
			jetstar = new BigDecimal(params.BL)
			asiaPrice = new BigDecimal(params.AS)
			asianPrice = new BigDecimal(params.SEA)
			americaPrice = new BigDecimal(params.AM)
			europePrice = new BigDecimal(params.EU)
			otherPrice = new BigDecimal(params.OT)
			if(params.VN){
				feeInstance = Fee.findByCode("VN")
				feeInstance.setPrice(vietnamairline)
				if (!feeInstance.hasErrors() && feeInstance.save(flush: true)) {
					flash.message = "${message(code:'fee.save.success')}"
				}
			}
			if(params.VJ){
				feeInstance = Fee.findByCode("VJ")
				feeInstance.setPrice(vietjetair)
				if (!feeInstance.hasErrors() && feeInstance.save(flush: true)) {
					flash.message = "${message(code:'fee.save.success')}"
				}
			}
			if(params.BL){
				feeInstance = Fee.findByCode("BL")
				feeInstance.setPrice(jetstar)
				if (!feeInstance.hasErrors() && feeInstance.save(flush: true)) {
					flash.message = "${message(code:'fee.save.success')}"
				}
			}
			
			if(params.AS){
				feeInstance = Fee.findByCode("AS")
				feeInstance.setPrice(asiaPrice)
				if (!feeInstance.hasErrors() && feeInstance.save(flush: true)) {
					flash.message = "${message(code:'fee.save.success')}"
				}
			}
			if(params.SEA){
				feeInstance = Fee.findByCode("SEA")
				feeInstance.setPrice(asianPrice)
				if (!feeInstance.hasErrors() && feeInstance.save(flush: true)) {
					flash.message = "${message(code:'fee.save.success')}"
				}
			}
			if(params.EU){
				feeInstance = Fee.findByCode("EU")
				feeInstance.setPrice(europePrice)
				if (!feeInstance.hasErrors() && feeInstance.save(flush: true)) {
					flash.message = "${message(code:'fee.save.success')}"
				}
			}
			if(params.AM){
				feeInstance = Fee.findByCode("AM")
				feeInstance.setPrice(americaPrice)
				if (!feeInstance.hasErrors() && feeInstance.save(flush: true)) {
					flash.message = "${message(code:'fee.save.success')}"
				}
			}
			if(params.OT){
				feeInstance = Fee.findByCode("OT")
				feeInstance.setPrice(otherPrice)
				if (!feeInstance.hasErrors() && feeInstance.save(flush: true)) {
					flash.message = "${message(code:'fee.save.success')}"
				}
			}
			
		}catch(Exception ex) {
				flash.message = ex
		}
		redirect action: index
    }
}
