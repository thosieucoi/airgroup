package com.airgroup.domain

class CheapFlight {
	String departure
	String airline
	String flight_number
	Date inbound_date
	Date outbound_date
	Integer trip_type
	String arrival
	Double price
	
    static constraints = {
    }
	
	static mapping=
	{
		table 'tbl_cheap_flight'
	}
}
