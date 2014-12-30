package com.airgroup.domain

class OrderDetail  {
	static belongsTo=[order:Order]
	String departure
	String arrival
	String flightNumber
	String airline
	Date outboundDate
	Date inboundDate
	Short tripType
	Order order
	static constraints = {
		departure blank:false, maxSize:45
		arrival blank:false, maxSize:45
		flightNumber blank:false, maxSize:45
		airline blank:false, maxSize:45
		outboundDate blank:false, nullable:false
		inboundDate blank:false, nullable:false
		tripType  blank:true, nullable:true
		}
	static mapping = {
		table "tbl_order_detail"
		id generator:'identity'
		sort outboundDate:"asc"
		version false
	}
}
