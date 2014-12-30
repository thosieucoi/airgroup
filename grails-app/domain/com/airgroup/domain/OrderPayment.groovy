package com.airgroup.domain
/**
 * 
 * @author tanghia
 * Payment at home case.
 * This table stores information about receiver
 */
class OrderPayment {
	Long orderId
	Long paymentId
    String person
	String address
	String phoneNumber
	String city
	static constraints = {
		orderId nullable:false
		paymentId nullable:false
		person nullable:true
		address nullable:true
		phoneNumber nullable:true
		city nullable:true
    }
	static mapping = {
		id generator:'identity'
		table "tbl_order_payment"
		version false
	}
}
