package com.airgroup.domain

class Category {
	static hasMany=[tours:Tour]
	String name
	String description
	Short status
	static constraints={
		name blank:false, maxSize:45
		description blank:false, maxSize:200
	}
	static mapping={
		table 'tbl_category'
		id generator:'identity'
		version false
	}
}
