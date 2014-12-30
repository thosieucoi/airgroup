package com.airgroup.domain

class Payment {
	static hasMany=[orders:Order]
	String paymentName
	String description
	Short status
    static constraints = {
		paymentName nullable:false, maxSize:100, unique:true
		
    }
	static mapping={
		table "tbl_payment"
		id generator: 'identity'
		version false
	}
	String toString() {
		"${paymentName}"
	}
}
