package com.airgroup.controller.news

import org.apache.commons.lang.StringUtils;
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
		
		session.title = "${message(code:'home.og.title')}"
		session.description = "${message(code:'home.og.description')}"
		session.url = "${message(code:'home.og.url')}"
		session.image = "${message(code:'home.og.image')}"
		
		[newsListTT:newsListTT, totalTT:totalTT]
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
			bigCategory = "${message(code:'navigation.admin.share')}"
		}else if(bigCategory.equals("news")){
			bigCategory = "${message(code:'navigation.admin.news')}"
		}
		print("bigCategory =" + bigCategory)
		
		def newsList = NewsContent.findAllByActiveAndCategory("Active", bigCategory ?:[max:10, offset:0, sort:"createdOn", order:"desc"])

		params.offset = null
		params.max = null
		
		session.title = content.title
		session.description = content.introduction
		session.url = "http://ahotua.vn/news/detailsInfo?infoid=" + content.id
		def image = content.image
		image= image.substring(image.indexOf("src=\"") + 5)
		session.image = "http://ahotua.vn" + image.substring(0, image.indexOf("\""))
		
		[content : content, bigCategory:bigCategory, newsList:newsList]
	}
}
