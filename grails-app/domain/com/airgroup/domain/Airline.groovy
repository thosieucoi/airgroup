package com.airgroup.domain

class Airline {

    Long id
	String name
	String code

	static constraints = {
	}

	static mapping={
		table 'tbl_airline'
		id generator:'assigned'
		version false
	}
	public Airline(){
	}

	Airline(id, name, code){
		this.id = id
		this.name = name
		this.code = code
	}
	def String toString(){
		return this.name + " (" + this.code + ")"
	}
}
