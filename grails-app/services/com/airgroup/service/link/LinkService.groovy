package com.airgroup.service.link

import com.airgroup.domain.Link
import org.weceem.auth.CMSUser
import com.airgroup.domain.Category;
class LinkService {

    static transactional = true

		
    def getLinkListBackEnd(def params){
		params.max = 20
		def linkList = Link.findAll([max:params.max, offset: params.offset, sort:"createdOn", order:"desc"])
		if(params.sort != null && params.order != null){
			linkList = Link.findAll([max:params.max, offset: params.offset, sort:params.sort, order:params.order])
		}
		def totalLink = Link.findAll().size()
		[linkBackEndList:linkList, total:totalLink, offset: params.offset, max: params.max]
	}
	
	def deleteLink(def link){
		def linkInstance = Link.get(link)
		linkInstance.delete(flush: true)
	}
	
	def getContentDetails(int contentId){
		def contentDetails = Link.get(contentId)
		[contentDetails:contentDetails]
	}
	
	
}
