package com.airgroup.controller.share

import com.airgroup.domain.NewsContent;

class ShareController {

    def index = { 
		if(params.type=='0'){
			def newsPagination = [max: params.max, offset: params.offset]
			session.newsPagination = newsPagination
		}else if(params.type=='1'){
			def promotionPagination = [max: params.max, offset: params.offset]
			session.promotionPagination = promotionPagination
		}
		def totalShare = NewsContent.findAllByActiveAndCategory("Active","${message(code:'navigation.admin.share')}").size();
		def newsListShare = NewsContent.findAllByActiveAndCategory("Active","${message(code:'navigation.admin.share')}",session.promotionPagination ?:[max:8, offset:0, sort:"createdOn", order:"desc"])
		params.offset = null
		params.max = null
		[newsListShare:newsListShare,totalShare:totalShare]
	}
}
