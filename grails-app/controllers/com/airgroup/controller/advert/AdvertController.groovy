package com.airgroup.controller.advert

import java.util.Formatter.DateTime;

import com.airgroup.domain.Advert
import com.airgroup.services.IFlightResponse;

class AdvertController {
	
	def index = {
		redirect(action: "list")
	}

	def list = {
		params.sort = params.sort?:'id'
		params.order = params.order?:'desc'
		def c = Advert.createCriteria()
		def results = c.list(){
			and{
				order(params.sort, params.order)
		}
		}
		[advertInstanceList: results, advertInstanceTotal: Advert.count()]
	}
	def create = {

	}
	def save = {
		def sPic = request.getFile('slidePic')
		def linkAdvert = request.getParameter('linkAdvert')
		def okcontents = ['image/png', 'image/jpeg', 'image/gif', 'image/bmp']
		if (!okcontents.contains(sPic.getContentType())) {
		  flash.message = "${message(code: 'advert.image.type')}"
		  redirect(action: "create")
		  return;
		}
		
		def advert = new Advert()
		advert.slidePic = sPic.getBytes()
		advert.linkAdvert = linkAdvert
		advert.slidePicFileName = sPic.originalFilename
		advert.activeTime = new Date() 
		advert.status = 1
		if(!advert.save()){
			flash.message = "${message(code: 'advert.over.size')}"
			redirect(action: "create")
			return
		}
		redirect(action: "list")
	}

	def delete = {
		def advertInstance = Advert.get(params.id)
		if (advertInstance) {
			try {
				advertInstance.delete(flush: true)
				flash.message = "${message(code: 'advert.deleted.message')}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'advert.not.deleted.message')}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'advert.not.found.message')}"
			redirect(action: "list")
		}
	}
	
	def activeDeactiveAdvert = {
		if ((params.status).toShort() == 0) {
			if(Advert.countByStatus((params.status).toShort()) == 4) {
				flash.message = "${message(code: 'advert.active.full')}"
				redirect(action: "list")
			}else{
				def currentAdvert = Advert.findById(params.advertId)
				currentAdvert.status = (params.status).toShort()
				currentAdvert.activeTime = new Date()
				if (currentAdvert.save(flush: true)) {
					if(params.int('status'))
						flash.message = "${message(code: 'advert.status.change.fault')}"
					else
						flash.message = "${message(code: 'advert.status.change.success')}"
					redirect(action: "list", id: currentAdvert.id)
				}
			}
		}else{
			def currentAdvert = Advert.findById(params.advertId)
			currentAdvert.status = (params.status).toShort()
			currentAdvert.activeTime = new Date()
			if (currentAdvert.save(flush: true)) {
				if(params.int('status'))
					flash.message = "${message(code: 'advert.status.change.success')}"
				else
					flash.message = "${message(code: 'advert.status.change.fault')}"
				redirect(action: "list", id: currentAdvert.id)
			}
		}
	}
	def showSlideImage = {
		def advert = Advert.get(params.id)
		response.outputStream << advert.slidePic // write the image to the outputstream
		response.outputStream.flush()
	  }
}
