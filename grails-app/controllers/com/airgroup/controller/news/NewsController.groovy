package com.airgroup.controller.news
import org.weceem.content.*
import com.airgroup.domain.*;
class NewsController {
	def newsService
	def index = {
		if(params.type=='0'){
			def newsPagination = [max: params.max, offset: params.offset]
			session.newsPagination = newsPagination
		}else if(params.type=='1'){
			def promotionPagination = [max: params.max, offset: params.offset]
			session.promotionPagination = promotionPagination
		}
		def totalTT = NewsContent.findAllByActiveAndCategory("Active","${message(code:'navigation.admin.news')}").size();
		def newsListTT = NewsContent.findAllByActiveAndCategory("Active","${message(code:'navigation.admin.news')}",session.newsPagination ?:[max:8, offset: 0, sort:"createdOn", order:"desc"])
		params.offset = null
		params.max = null
		[newsListTT:newsListTT, totalTT:totalTT]
	}
	
	def saleoff = {
		if(params.type=='0'){
			def newsPagination = [max: params.max, offset: params.offset]
			session.newsPagination = newsPagination
		}else if(params.type=='1'){
			def promotionPagination = [max: params.max, offset: params.offset]
			session.promotionPagination = promotionPagination
		}
		def totalKM = NewsContent.findAllByActiveAndCategory("Active","${message(code:'navigation.admin.promotion')}").size();
		def newsListKM = NewsContent.findAllByActiveAndCategory("Active","${message(code:'navigation.admin.promotion')}",session.promotionPagination ?:[max:8, offset:0, sort:"createdOn", order:"desc"])
		params.offset = null
		params.max = null
		[newsListKM:newsListKM,totalKM:totalKM]
	}

	def list = {
		def newsList = newsService.listBackEnd(params)
	}
	def listFrontEnd = {
		def newsList = newsService.listFrontEnd(params)
	}
	def edit = {
		def getId = params.id.toLong()
		redirect(uri:"/admin/editor/edit/" + getId)
	}
	def create = {
		redirect(uri:"/admin/editor/create?parent.id=&type=com.airgroup.domain.NewsContent&space.id=" + WcmSpace.findByName("Default").id)
	}
	def delete = {
		def getId = params.id.toLong()
		def newsInstance = WcmContent.findWhere(id:getId)
		if (newsInstance) {
			try {
				newsInstance.delete(flush: true)
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				redirect(action: "list", id: params.id)
			}
		}
	}
	def detailsInfo={
		def infoid=Long.valueOf(params.infoid);
		def content = NewsContent.get(infoid)
		def bigCategory = content.category
		if(bigCategory.equals("promotion")){
			bigCategory = "${message(code:'navigation.admin.promotion')}"
		}else if(bigCategory.equals("news")){
			bigCategory = "${message(code:'navigation.admin.news')}"
		}
		print("bigCategory =" + bigCategory)
		
		def newsList = NewsContent.findAllByActiveAndCategory("Active", bigCategory ?:[max:10, offset:0, sort:"createdOn", order:"desc"])

		params.offset = null
		params.max = null
		
		[content : content, bigCategory:bigCategory, newsList:newsList]
	}
}
