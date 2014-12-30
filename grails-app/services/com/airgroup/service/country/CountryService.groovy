package com.airgroup.service.country

import grails.converters.JSON

import org.codehaus.groovy.grails.commons.ApplicationHolder

import com.airgroup.domain.Country

class CountryService {

	static transactional = true

	def saveCountry() {
		def filePath = "resources/continentals.json"
		def countryReader = ApplicationHolder.application.getParentContext().getResource("classpath:$filePath").getInputStream().getText()

		def countryParsedData = JSON.parse(countryReader)
		def countryList = new ArrayList<Country>()
		countryParsedData.each { countryItem ->
			def country = new Country(
					countryItem."name",
					countryItem."code",
					countryItem."continental"
					)
			country.save(flush:true)
		}
	}
}
