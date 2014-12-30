package com.airgroup.domain


class Location {
	Long id
	String name
	String location_type
	String code
	String city_id
	String time_zone
	String country_code
	String country_name
	String zone
	String vi
	int level

	static constraints = {
	}

	static mapping={
		table 'tbl_location'
		id generator:'assigned'
		version false
	}
	public Location(){
	}

	Location(id, name, location_type, code, city_id, time_zone, country_code, country_name, zone, level, vi){
		this.id = id
		this.name = name
		this.location_type = location_type
		this.code = code
		this.city_id = city_id
		this.time_zone = time_zone
		this.country_code = country_code
		this.country_name = country_name
		this.zone = zone
		this.level = level
		this.vi = vi
	}

	//	def String toString(){
	//		return "ID: " + this.id + " " +
	//		"Name: " + this.name + " " +
	//		"Location Type: " + this.location_type + " " +
	//		"Code: " + this.code + " " +
	//		"Time Zone: " + this.time_zone + " " +
	//		"Country Code: " + this.country_code
	//	}

	def String toString(){
		return this.vi + " (" + this.code + ")" + " - " + this.country_name
	}
}
