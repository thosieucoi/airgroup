package com.airgroup.controller.info

import com.airgroup.domain.CustomerInfo

class CustomerInfoController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def info = {
		params.max = Math.min(params.max ? params.int('max') : 20, 100)
		params.offset = params.offset? params.offset.toInteger():0
		params.sort = params.sort?:'id'
		params.order = params.order?:'desc'
		[customerInfoInstanceList: CustomerInfo.list(params), customerInfoInstanceTotal: CustomerInfo.count()]
	}
}
