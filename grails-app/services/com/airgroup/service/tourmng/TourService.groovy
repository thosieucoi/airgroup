package com.airgroup.service.tourmng

import com.airgroup.domain.Tour;
import com.airgroup.domain.Category;
import com.airgroup.domain.TourIntro;
import org.weceem.auth.CMSUser;

class TourService {

    static transactional = true
	
	def getTourListFrontEndIndex(def params){
		params.max = 4
		def destinations
		def specialities
		def hotels
		def taxis
		def tours
		
		destinations = Tour.executeQuery("from Tour t where t.category='Destination' and t.informationStatus=1 order by rand()", [max: 4])
		specialities = Tour.executeQuery("from Tour t where t.category='Speciality' and t.informationStatus=1 order by rand()", [max: 4])
		hotels = Tour.findAllByCategoryInListAndInformationStatus(["Hotel"],(short) 1, [max:params.max, offset: params.offset, sort:"createdOn", order:"desc"])
		taxis = Tour.findAllByCategoryInListAndInformationStatus(["Taxi"],(short) 1, [max:params.max, offset: params.offset, sort:"createdOn", order:"desc"])
		tours = Tour.findAllByCategoryInListAndInformationStatus(["Tour"],(short) 1, [max:params.max, offset: params.offset, sort:"createdOn", order:"desc"])
		[tours:tours,hotels:hotels,taxis:taxis,destinations:destinations, specialities:specialities, offset: params.offset, max: params.max]
	}
	
	def getTourDetail(long id){
		def tourDetail = Tour.get(id)
		def cat = tourDetail.category
		[tourDetail : tourDetail, bigCategory:cat]
	}

	def getTourListBackEnd(def params){
		params.max = 20
		def tourList = Tour.findAll([max:params.max, offset: params.offset, sort:"createdOn", order:"desc"])
		if(params.sort != null && params.order != null){
			tourList = Tour.findAll([max:params.max, offset: params.offset, sort:params.sort, order:params.order])
		}
		def totalTour = Tour.findAll().size()
		[tourBackEndList:tourList, total:totalTour, offset: params.offset, max: params.max]
	}
	
	def deleteTour(def tour){
		def tourInstance = Tour.get(tour)
		tourInstance.delete(flush: true)
	}
	
	def getTourIntro(){
		def tourIntroList = TourIntro.findAll()
		def users = CMSUser.findAllByCallCenterStatusAndStatus((short)1,(short)1)
		[tourIntroInstanceList:tourIntroList, users: users]
	}
}