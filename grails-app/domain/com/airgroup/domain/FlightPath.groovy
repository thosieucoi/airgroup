package com.airgroup.domain

class FlightPath {
	
	String domesticFlightPath1
	String domesticFlightPath2
	String domesticFlightPath3
	String domesticFlightPath4
	String domesticFlightPath5
	String internationalFlightPath1
	String internationalFlightPath2
	String internationalFlightPath3
	String internationalFlightPath4
	String internationalFlightPath5
	
    static constraints = {
		domesticFlightPath1(blank:true, nullable:true)
		domesticFlightPath2(blank:true, nullable:true)
		domesticFlightPath3(blank:true, nullable:true)
		domesticFlightPath4(blank:true, nullable:true)
		domesticFlightPath5(blank:true, nullable:true)
		internationalFlightPath1(blank:true, nullable:true)
		internationalFlightPath2(blank:true, nullable:true)
		internationalFlightPath3(blank:true, nullable:true)
		internationalFlightPath4(blank:true, nullable:true)
		internationalFlightPath5(blank:true, nullable:true)
    }
	
	static mapping = {
		table 'tbl_flight_path'
		id generator:'identity'
		version false
	}

}
