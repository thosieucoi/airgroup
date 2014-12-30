package com.airgroup.domain

class Luggage {
	static belongsTo=[passenger:Passenger]

	String airlineCode
	Long baggage
	Short isDeparture

	static constraints = {
		airlineCode blank:false, nullable:false
		baggage nullable:false
		isDeparture nullable:false
	}

	static mapping={
		table 'tbl_luggage'
		id generator:'identity'
		version false
	}
}
