package com.airgroup.controller.information

import org.weceem.content.WcmSpace

import com.airgroup.domain.Tour

class InformationController{
	def tourService
	static allowedMethods = [delete: ["POST","GET"]]

    def index = {
//		def tourIntroList = tourService.getTourIntro()
		listFrontEndIndex()
//		def tourInstanceList = tourService.getTourListFrontEnd(params)
	}

	def list = {
		def tourInstanceList = getTourListFrontEndCategory(params)
	}

	def tourDetail = {
		long tourId = Long.valueOf(params.id)
		def tourInstance = tourService.getTourDetail(tourId)
		def cat = transCategory(tourInstance.bigCategory)
		print("cat = " + cat)
		[tourDetail:tourInstance.tourDetail, bigCategory:cat]
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
		def tourList
		def bigCategory
		def total
		
		if(cat.equals("DomesticNews")){
			tourList = Tour.findAllByCategoryInListAndTourStatus(["Domestic news"],(short) 1, [max:params.max, offset: params.offset, sort:"createdOn", order:"desc"])
			total = Tour.findAllByCategoryInListAndTourStatus(["Domestic news"],(short) 1).size()
		}else if(cat.equals("InternationalNews")){
			tourList = Tour.findAllByCategoryInListAndTourStatus(["International"],(short) 1, [max:params.max, offset: params.offset, sort:"createdOn", order:"desc"])
			total = Tour.findAllByCategoryInListAndTourStatus(["International"],(short) 1).size()
		}else if(cat.equals("Special")){
//			tourList = Tour.findAllByCategoryInListAndTourStatus(["Special"],(short) 1, [max:4, offset: params.offset, sort:"createdOn", order:"desc"])
			tourList = Tour.executeQuery("from Tour t where t.category in ('Special') and t.tourStatus=1 order by createdOn desc",[max:params.max, offset: params.offset])
			total = Tour.findAllByCategoryInListAndTourStatus(["Special"],(short) 1).size()
			print("totalx=" + tourList )
		}else if(cat.equals("TourList")){
			tourList = Tour.executeQuery("from Tour t where t.category in ('North','Middle', 'South','Asean', 'Asia', 'Euro', 'America', 'Africa') and t.tourStatus=1 order by createdOn desc",[max:params.max, offset: params.offset])
			total = Tour.executeQuery("from Tour t where t.category in ('North','Middle', 'South','Asean', 'Asia', 'Euro', 'America', 'Africa') and t.tourStatus=1 order by createdOn desc").size()
		}else{
			tourList = Tour.findAllByCategoryInListAndTourStatus([cat],(short) 1, [max:params.max, offset: params.offset, sort:"createdOn", order:"desc"])
			total = Tour.findAllByCategoryInListAndTourStatus([cat],(short) 1).size()
		}
		
		bigCategory = transCategory(bigCategory)
		[listCategory:tourList,total:total,bigCategory:bigCategory, offset: params.offset, max: params.max, cat:cat]
	}
	def listFrontEndIndex = {
		def tourInstanceList = tourService.getTourListFrontEndIndex(params)
	}
	def listDomesticNews = {
		params.category = "DomesticNews"
		print("CATEGORY = " + params.category)
		def tourInstanceList = tourService.getTourListFrontEnd(params)
	}
	def listInternationalNews = {
		params.category = "InternationalNews"
		print("CATEGORY = " + params.category)
		def tourInstanceList = tourService.getTourListFrontEnd(params)
	}
	def listSpecialTour = {
		params.category = "Special"
		print("CATEGORY = " + params.category)
		def tourInstanceList = tourService.getTourListFrontEnd(params)
	}
	def listTour = {
		params.category = "Tour"
		print("CATEGORY = " + params.category)
		def tourInstanceList = tourService.getTourListFrontEnd(params)
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
	
	def transCategory(def category){
		if(category.equals("Hotel")){
			category = "${message(code:'tour.category.Hotel')}"
		}else if(category.equals("Taxi")){
			category = "${message(code:'tour.category.Taxi')}"
		}
		else if(category.equals("Destination")){
			category = "${message(code:'tour.category.Destination')}"
		}
		else if(category.equals("Preprareforfly")){
			category = "${message(code:'tour.category.Preprareforfly')}"
		}
		else if(category.equals("Guide")){
			category = "${message(code:'tour.category.Guide')}"
		}
		else if(category.equals("MapOfAirport")){
			category = "${message(code:'tour.category.MapOfAirport')}"
		}
		else
		{
			category = "${message(code:'tour.category.None')}"
		}
	
		return category
	}
}
