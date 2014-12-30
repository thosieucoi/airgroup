package com.airgroup.domain

class Country {

	String continental
	String name
	String code

	static mapping={
		table 'tbl_country'
		version false
	}

	public Country(){
	}

	Country(name, code, continental){
		this.name = name
		this.code = code
		this.continental = continental
	}
}
