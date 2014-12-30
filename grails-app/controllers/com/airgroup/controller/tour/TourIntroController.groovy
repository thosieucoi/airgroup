package com.airgroup.controller.tour

import com.airgroup.domain.TourIntro;
import org.weceem.content.WcmContent
import org.weceem.content.WcmSpace;

class TourIntroController {

    def index = {
		if(!WcmContent.findByTitle('Tour Intro')){
			redirect(uri:"/admin/editor/create?parent.id&type=com.airgroup.domain.TourIntro&space.id=" + WcmSpace.findByName("Default").id)
		}
		else{
			redirect(uri:"/admin/editor/edit/" + WcmContent.findWhere(title:'Tour Intro').id)
		}
	}
}