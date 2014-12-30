package com.airgroup.controller.location

import grails.converters.JSON

import com.airgroup.domain.Location


class LocationController {

	def getAllLocation = {
//		def locations = Location.list(sort: "level", order: "desc")
		def locations = Location.findAllByLocation_type("airport", [sort: "level", order: "desc"])
		def response = []

		locations.each {
			response << "${it}"
		}
		render response as JSON
	}

//	def index() {
//		redirect(action: "list", params: params)
//	}
}
