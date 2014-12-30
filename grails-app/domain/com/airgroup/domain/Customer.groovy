package com.airgroup.domain

class Customer {
	String name
	String email
	String phoneNumber
	String address
	String city
	Short gender
	Short status
	static hasMany=[orders:Order]
	static mappedBy=[oders:"customer"] 
	static constraints = {
		name maxSize:100,blank:false
		email nullable:true,maxSize:50
		phoneNumber matches:'\\d+',maxSize:45
		address blank:true, maxSize:100
		city blank:true, maxSize:100
		orders nullable:true
	}
	static mapping={
		table 'tbl_customer'
		id generator:'identity'
		version false
		
	}
}
