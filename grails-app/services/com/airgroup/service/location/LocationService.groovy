package com.airgroup.service.location

import grails.converters.JSON

import org.codehaus.groovy.grails.commons.ApplicationHolder

import com.airgroup.domain.Location

class LocationService {

	static transactional = true
	def saveLocation() {
		def filePath = "resources/locations.json"
		def filePath2="resources/countries.json"
		def locationReader = ApplicationHolder.application.getParentContext().getResource("classpath:$filePath").getInputStream().getText()
		def countryFileReader= ApplicationHolder.application.getParentContext().getResource("classpath:$filePath2").getInputStream().getText()
		def locationParsedData = JSON.parse(locationReader)
		def countryParseData = JSON.parse(countryFileReader)
		def locationList = new ArrayList<Location>()
		locationParsedData.each { locationItem ->
			countryParseData.each{ countryItem ->
				if(locationItem.country_code == countryItem.code){
					def location = new Location(
							locationItem.id,
							locationItem.name,
							locationItem.location_type,
							locationItem.code,
							locationItem.city_id,
							locationItem.time_zone,
							locationItem.country_code,
							countryItem.name,
							"",
							1,
							locationItem.names.vi != null ? locationItem.names.vi : locationItem.name)
					location.save(flush:true)
					//locationList.add(location)
				}
			}
		}
	}

	/**
	 *
	 * Insert new Location if some location are not exist
	 *
	 * */
	def updateNewLocation(String code, String airportName, String countryName, String countryCode, String locationType,  int level) {
		def lstCurrentLocation = Location.findAll(sort:'id', order:'desc')
		def lstLocation = Location.find('from Location l where l.name=:code and l.name=:name and l.location_type=:locationType',[code : code, name : airportName, locationType : locationType])
		Long currentMaxLocationId
		if(lstCurrentLocation != null && lstCurrentLocation.toList().size() > 0){
			currentMaxLocationId = lstCurrentLocation.toList().get(0).id
		} else {
			currentMaxLocationId = 1
		}
		if(lstLocation == null){
			Location newLocation = new Location(
					currentMaxLocationId + 1,
					airportName,
					"airport",
					code,
					"",
					"",
					countryCode,
					countryName,
					"",
					level,
					airportName
					)
			newLocation.save(flush:true)
		}
	}
}
