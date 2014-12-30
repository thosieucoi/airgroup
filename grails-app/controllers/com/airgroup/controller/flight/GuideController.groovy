package com.airgroup.controller.flight

import org.weceem.auth.CMSUser

class GuideController {

    def searchGuide={
		def users = CMSUser.findAllByCallCenterStatusAndStatus((short)1,(short)1)
		[users: users]
	}
}
