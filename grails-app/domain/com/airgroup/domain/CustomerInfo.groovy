package com.airgroup.domain


class CustomerInfo {

	String phoneNumber
	String departure
	String arrival
	Date outboundDate
	Date inboundDate

	static constraints = {
		phoneNumber nullable:true
		inboundDate nullable:true
	}

	static mapping = {
		table "tbl_customer_info"
		id generator:'identity'
		version false
	}
}
