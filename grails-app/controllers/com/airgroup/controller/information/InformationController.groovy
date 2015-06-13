package com.airgroup.controller.information

import org.weceem.content.WcmSpace

import com.airgroup.domain.Tour

class InformationController{
	def tourService
	static allowedMethods = [delete: ["POST","GET"]]

    def index = {
		session.title = "${message(code:'home.og.title')}"
		session.description = "${message(code:'home.og.description')}"
		session.url = "${message(code:'home.og.url')}"
		session.image = "${message(code:'home.og.image')}"
		listFrontEndIndex()
	}

	def search = {
		def tourInstanceList = getTourListFrontEndCategory(params)
	}

	def details = {
		long informationId = Long.valueOf(params.id)
		def tourInstance = tourService.getTourDetail(informationId)
		def cat = tourInstance.bigCategory
		[detail:tourInstance.tourDetail, bigCategory:cat]
	}

	def getTourListFrontEndCategory = {
		if(params.max == null){
			params.max = 8
		}
		//params.max = 4
		if(params.offset == null){
			params.offset = 0
		}
		
		def cat = params.category
		def city = params.city
		def tourList
		def bigCategory
		def total
		
		tourList = Tour.executeQuery("from Tour t where t.category = '" + cat + "' and t.informationStatus=1 and t.location='" + city + "' order by createdOn desc",[max:params.max, offset: params.offset])
		total = Tour.executeQuery("from Tour t where t.category = 'Destination' and t.informationStatus=1 and t.location='HAN'").size()
		
		[listCategory:tourList,total:total,bigCategory:bigCategory, offset: params.offset, max: params.max, cat:cat, city:city]
	}
	
	def listFrontEndIndex = {
		def tourInstanceList = tourService.getTourListFrontEndIndex(params)
	}
	
	def listBackEnd = {
		def tourList = tourService.getTourListBackEnd(params)
	}

	def show = {
		def tourInfo = Tour.get(params.id)
		[tourInfo : tourInfo]
	}

	def create = {
		//def parent_id =Long.parseLong(WcmContent.findAllWhere(identifier:"TourDirectory").get(0).toString().split(":")[1].trim());
		//redirect(uri:"/admin/editor/create?parent.id="+parent_id+"&type=com.airgroup.domain.Tour&space.id=" + WcmSpace.findByName("Default").id)
		redirect(uri:"/admin/editor/create?type=com.airgroup.domain.Tour&space.id=" + WcmSpace.findByName("Default").id)
	}

	def edit  = {
		redirect(uri:"/admin/editor/edit/" + params.id)
	}

	def delete = {
		def delTourList = params.list("tour")
		try{
			if(params.id){
				tourService.deleteTour(params.id)
			} else{
				for(tour in delTourList){
					tourService.deleteTour(tour)
				}
			}
			if(params.id){
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'CMSUser.label', default: 'Tour'), params.id])}"
			} else {
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'CMSUser.label', default: 'Tour'), delTourList])}"
			}
		} catch(org.springframework.dao.DataIntegrityViolationException e){
			flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'CMSUser.label', default: 'Tour'), params.id])}"
		}
		redirect(action:"listBackEnd")
	}
}
