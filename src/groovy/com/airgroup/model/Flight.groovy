package com.airgroup.model
import org.joda.time.DateTime
class Flight {
	String departureAirport
	String arrivalAirport
	String departureCity
	String arrivalCity
	String airlineCode
	String flightNumber
	String departureCode
	String departureDate
	String arrivalDate
	String arrivalCode
	String departureTime
	String arrivalTime
	String designatorCode;
	String operatingAirlineCode;
	String operatingFlightNumber;
	String aircraftType;
	String carrier
	String cabin	public Flight(){
	}
	public Flight(String airlineCode, String flightNumber,
	String departureCode, String arrivalCode, String departureTime,
	String arrivalTime, String carrier) {
		super();
		this.airlineCode = airlineCode;
		this.flightNumber = flightNumber;
		this.departureCode = departureCode;
		this.arrivalCode = arrivalCode;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.carrier = carrier;
	}
}
