package com.airgroup.service.newsmng

import com.airgroup.domain.NewsContent;

class NewsService{
	def listBackEnd(def params){
		params.max = 20
		def newsList = NewsContent.findAll([max:params.max, offset: params.offset, sort:"createdOn", order:"desc"])
		if(params.sort != null && params.order != null){
			newsList = NewsContent.findAll([max:params.max, offset: params.offset, sort:params.sort, order:params.order])
		}
		def totalNews = NewsContent.findAll().size()
		print("Call this")
		[newsList:newsList, total:totalNews, offset: params.offset, max: params.max]
	}
	def listFrontEnd(def params){
		/*if(params.max == null){
			params.max = 3
		}
		def newsListKM = NewsContent.findAllByActiveAndCategory("Active","promotion",[max:params.max, offset: params.offsetKM, sort:"createdOn", order:"desc"])
		def totalKM = NewsContent.findAllByActiveAndCategory("Active","promotion").size();
		def newsListTT = NewsContent.findAllByActiveAndCategory("Active","news",[max:params.max, offset: params.offsetTT, sort:"createdOn", order:"desc"])
		def totalTT = NewsContent.findAllByActiveAndCategory("Active","news").size();
		if(params.type.toString().equals('1')){
			newsListKM = NewsContent.findAllByActiveAndCategory("Active","promotion",[max:params.max, offset: params.offsetKM, sort:"createdOn", order:"desc"])
			totalKM = NewsContent.findAllByActiveAndCategory("Active","promotion").size();
		}else if(params.type.toString().equals('0')){
			newsListTT = NewsContent.findAllByActiveAndCategory("Active","news",[max:params.max, offset: params.offsetTT, sort:"createdOn", order:"desc"])
			totalTT = NewsContent.findAllByActiveAndCategory("Active","news").size();
		}*/

		/*if(params.type=='1'){
			def newsPagination = [max: params.max, offset: params.offset]
			session.newsPagination = newsPagination
		}else if(params.type=='0'){
			def promotionPagination = [max: params.max, offset: params.offset]
			session.promotionPagination = promotionPagination
		}
		def totalTT = NewsContent.findAllByActiveAndCategory("Active","news").size();
		def totalKM = NewsContent.findAllByActiveAndCategory("Active","promotion").size();
		def newsListKM = NewsContent.findAllByActiveAndCategory("Active","promotion",session.newsPagination ?:[max:3, offset:0, sort:"createdOn", order:"desc"])
		def newsListTT = NewsContent.findAllByActiveAndCategory("Active","news",session.newsPagination ?:[max:3, offset: 0, sort:"createdOn", order:"desc"])
		params.offset = null
		params.max = null
		[newsListKM:newsListKM,newsListTT:newsListTT, totalTT:totalTT,totalKM:totalKM]*/
	}
}