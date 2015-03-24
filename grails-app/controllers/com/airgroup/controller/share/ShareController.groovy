package com.airgroup.controller.share

import com.airgroup.domain.NewsContent;

class ShareController {

    def index = { 
		if(params.type=='0'){
			def sharePagination = [max: params.max, offset: params.offset , sort:"createdOn", order:"desc"]
			session.sharePagination = sharePagination
		}
		
		def totalShare = NewsContent.findAllByActiveAndCategory("Active","${message(code:'navigation.admin.share')}").size();
		def newsListShare = NewsContent.findAllByActiveAndCategory("Active","${message(code:'navigation.admin.share')}",session.sharePagination ?:[max:8, offset:0, sort:"createdOn", order:"desc"])
		params.offset = null
		params.max = null
		
		session.title = "${message(code:'home.og.title')}"
		session.description = "${message(code:'home.og.description')}"
		session.url = "${message(code:'home.og.url')}"
		session.image = "${message(code:'home.og.image')}"
		
		[newsListShare:newsListShare,totalShare:totalShare]
	}
}
