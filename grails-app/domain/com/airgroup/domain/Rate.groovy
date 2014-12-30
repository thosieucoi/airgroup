package com.airgroup.domain

class Rate {
	String code
	String rate
	String description
	
	static constraints = {
		rate blank:false, matches:/[0-9\,.]+/, maxSize:10
		description blank:true, nullable:true
	}
	static mapping={
		table 'tbl_rate'
		id generator:'identity'
		version false
	}
}
