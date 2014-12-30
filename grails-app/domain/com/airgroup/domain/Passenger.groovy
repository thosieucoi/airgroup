package com.airgroup.domain

class Passenger {
	static belongsTo=[order:Order]
	static hasMany=[luggage:Luggage]
	String name
	Short gender
	Date dateOfBirth
	Order order
	String seatNumber
	String ticketNumber
	static constraints = {
		name blank:false, maxSize:100
		gender nullable:false
		dateOfBirth nullable:true
		seatNumber nullable:true
		ticketNumber nullable:true
	}
	static mapping={
		table 'tbl_passenger'
		id generator:'identity'
		version false
	}
}
