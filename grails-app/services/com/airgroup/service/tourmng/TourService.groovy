package com.airgroup.service.tourmng

import com.airgroup.domain.Tour;
import com.airgroup.domain.Category;
import com.airgroup.domain.TourIntro;
import org.weceem.auth.CMSUser;

class TourService {

    static transactional = true
	
	def getTourListFrontEndIndex(def params){
		params.max = 4
		def tourList
		def domesticNews
		def internationalNews
		def special
//		tourList = Tour.findAllByCategoryInListAndTourStatus(["North","Middle", "South","Asean", "Asia", "Euro", "America", "Africa"],(short) 1, [max:params.max, offset: params.offset, sort:"createdOn", order:"desc"])
//		special = Tour.findAllByCategoryInListAndTourStatus(["Special"],(short) 1, [max:4,sort:"createdOn", order:"rand()"])
		special = Tour.executeQuery("from Tour t where t.category='Special' and t.status=1 order by rand()", [max: 4])
		domesticNews = Tour.findAllByCategoryInListAndTourStatus(["Domestic news"],(short) 1, [max:params.max, offset: params.offset, sort:"createdOn", order:"desc"])
		internationalNews = Tour.findAllByCategoryInListAndTourStatus(["International"],(short) 1, [max:params.max, offset: params.offset, sort:"createdOn", order:"desc"])
		tourList = Tour.executeQuery("from Tour t where t.category in ('North','Middle', 'South','Asean', 'Asia', 'Euro', 'America', 'Africa') and t.status=1 order by rand()", [max: 4])
		print("Special = " + special)
		[tourInstanceList:tourList,domesticNews:domesticNews,internationalNews:internationalNews,special:special, offset: params.offset, max: params.max]
	}
	
	def getTourDetail(long id){
		def tourDetail = Tour.get(id)
		def cat = transCategory(tourDetail.category)
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
	
	def initiateCategory(){
		for(int i=0; i<5; i++){
			Category cateogry = new Category()
			if(i == 0){
				cateogry.name = 'Asia'
				cateogry.description = 'Tour description'
				cateogry.status = (short)0
			} else if(i == 1){
				cateogry.name = 'America'
				cateogry.description = 'Tour description'
				cateogry.status = (short)0
			} else if(i == 2){
				cateogry.name = 'Europe'
				cateogry.description = 'Tour description'
				cateogry.status = (short)0
			} else if(i == 3){
				cateogry.name = 'Domestic'
				cateogry.description = 'Tour description'
				cateogry.status = (short)0
			} else if(i == 4){
				cateogry.name = 'South East Asia'
				cateogry.description = 'Tour description'
				cateogry.status = (short)0
			}
			cateogry.save(flush:true)
		}
	}
	
	def getCategoryList(){
		def categoryList = Category.findAll()
	}
	def transCategory(def category){
		if(category.equals("Special")){
			category = "Ä�áº·c biá»‡t"
		}else if(category.equals("North")){
			category = "Miá»�n Báº¯c"
		}
		else if(category.equals("Middle")){
			category = "Miá»�n Trung"
		}
		else if(category.equals("South")){
			category = "Miá»�n Nam"
		}
		else if(category.equals("Asean")){
			category = "ChÃ¢u Ã�"
		}
		else if(category.equals("Asian")){
			category = "Ä�Ã´ng Nam Ã�"
		}
		else if(category.equals("Euro")){
			category = "ChÃ¢u Ã‚u"
		}
		else if(category.equals("America")){
			category = "ChÃ¢u Má»¹"
		}
		else if(category.equals("Africa")){
			category = "ChÃ¢u Phi"
		}
		else if(category.equals("Domestic news")){
			category = "Tin trong nÆ°á»›c"
		}else if(category.equals("International")){
			category = "Tin quá»‘c táº¿"
		}
		return category
	}
}