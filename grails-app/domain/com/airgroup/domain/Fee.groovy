package com.airgroup.domain

class Fee {
	BigDecimal price
	String description
	String code

	def springSecurityService
	static constraints = {
		price blank: false,nullable:false
		description nullable:false,maxSize:200
	}
	static mapping={
		table 'tbl_fee'
		id generator:'identity'
		version false
	}
}
