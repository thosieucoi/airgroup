/**
 * 
 */
package com.airgroup.model;

/**
 * @author linhnd1
 *
 */
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Segment {
	private String id;
	public static final String SEGMENT_JOINT = "-";
	private String departureCode;
	private String arrivalCode;
	private String departureName;
	private String arrivalName;
	private DateTime departureTime;
	private DateTime arrivalTime;
	private Cabin cabin;
	private String designatorCode;
	private String airlineCode;
	private String flightNumber;
	private String operatingAirlineCode;
	private String operatingFlightNumber;
	private String aircraftType;
	private String carrier;

	public String getDepartureName() {
		return departureName;
	}

	public void setDepartureName(String departureName) {
		this.departureName = departureName;
	}

	public String getArrivalName() {
		return arrivalName;
	}

	public void setArrivalName(String arrivalName) {
		this.arrivalName = arrivalName;
	}

	public String getId() {
		return ((this.id == null) ? createId() : this.id);
	}

	public String createId() {
		return (this.id = StringUtils.join(new String[] { getDesignatorCode(), "-", FORMATTER
			.print(getDepartureTime()) }));
	}

	public String getDepartureCode() {
		return this.departureCode;
	}

	public void setDepartureCode(String departureCode) {
		this.departureCode = departureCode;
	}

	public String getArrivalCode() {
		return this.arrivalCode;
	}

	public void setArrivalCode(String arrivalCode) {
		this.arrivalCode = arrivalCode;
	}

	public String getCarrier() {
		return this.carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public DateTime getDepartureTime() {
		return this.departureTime;
	}

	public void setDepartureTime(DateTime departureTime) {
		this.departureTime = departureTime;
	}

	public DateTime getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(DateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Cabin getCabin() {
		return this.cabin;
	}

	public void setCabin(Cabin cabin) {
		this.cabin = cabin;
	}

	public String getDesignatorCode() {
		return ((this.designatorCode != null) ? this.designatorCode : createDesignatorCode());
	}

	public String createDesignatorCode() {
		return StringUtils.join(new String[] { getAirlineCode(), getFlightNumber() });
	}

	public String getAirlineCode() {
		return this.airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = StringUtils.strip(airlineCode);
	}

	public String getFlightNumber() {
		return this.flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = StringUtils.strip(flightNumber);
	}

	public String getOperatingAirlineCode() {
		return this.operatingAirlineCode;
	}

	public void setOperatingAirlineCode(String operatingAirlineCode) {
		this.operatingAirlineCode = operatingAirlineCode;
	}

	public String getOperatingFlightNumber() {
		return this.operatingFlightNumber;
	}

	public void setOperatingFlightNumber(String operatingFlightNumber) {
		this.operatingFlightNumber = operatingFlightNumber;
	}

	public String getAircraftType() {
		return this.aircraftType;
	}

	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}

	// public ToStringBuilder inspect() {
	// return new NoDefaultReflectionToStringBuilder(this);
	// }

	// public String toString() {
	// return inspect().toString();
	// }
	public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("ddHH");
}
