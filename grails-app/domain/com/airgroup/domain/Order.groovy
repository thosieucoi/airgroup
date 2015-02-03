package com.airgroup.domain

import org.weceem.auth.CMSUser

class Order {
	static belongsTo=[customer:Customer,payment:Payment,userCreated: CMSUser]
	static hasOne=[bill:Bill,orderEmployee:OrderEmployee]
	static hasMany = [passengers:Passenger,orderDetails:OrderDetail,remarks:Remark]
	static fetchMode=[passengers:'lazy',orderDetails:'lazy']
	Short billStatus = 0
	BigDecimal price
	Date orderTime
	Short kidNumber
	Short adultNumber
	Short infantNumber
	Short status
	String goingDescription
	String returnDescription
	String specialRequire
	Short showStatus
	Short isDomestic
	String reservationCode
	String departureLocation;
	String arrivalLocation;
	static constraints = {
		billStatus nullable:false
		price blank:false, nullable:false, scale:0
		orderTime nullable:false
		kidNumber nullable:false
		adultNumber nullable:false
		infantNumber nullable:false
		status nullable:false
		specialRequire maxSize: 500, nullable:true
		goingDescription maxSize: 2500, nullable:true
		returnDescription maxSize: 2500, nullable:true
		departureLocation maxSize: 200, nullable:true
		arrivalLocation maxSize:200, nullable:true
		bill nullable:true
		orderDetails nullable: true
		remarks nullable:true
		orderEmployee nullable: true
		passengers nullable: true
		showStatus nullable:false
		isDomestic nullnable:false
		reservationCode nullable: true
		userCreated nullable:true
	}
	static mapping ={
		table 'tbl_order'
		id generator:'identity'
		orderDetails sort: 'outboundDate', order: 'asc'
		version false
	}
}
