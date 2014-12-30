package com.airgroup.service.airline

import grails.converters.JSON

import com.fasterxml.jackson.databind.node.NodeCursor.Array;
import com.airgroup.domain.Airline;
import org.codehaus.groovy.grails.commons.ApplicationHolder;


class AirlineService {

	static transactional = true

	def saveAirline() {
		def filePath = "resources/airlines.json"
		def airlinesReader = ApplicationHolder.application.getParentContext().getResource("classpath:$filePath").getInputStream().getText()

		def airlineParsedData = JSON.parse(airlinesReader)
		def airlineList = new ArrayList<Airline>()
		airlineParsedData.each { airlineItem ->
					def airline = new Airline(
					airlineItem."id",
					airlineItem."name",
					airlineItem."code"
					)
					airline.save(flush:true)
		}		
	}
}
