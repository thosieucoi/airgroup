

package com.airgroup.domain

class Bill {
	String companyName
	String companyAddress
	String taxSerial
	String address
	static belongsTo=[order:Order]
	static constraints = {
		companyName blank:true, maxSize:45
		companyAddress blank:true, maxSize:100
		taxSerial blank:true, maxSize:45
		address blank:true, maxSize:100
	}
	static mapping={
		table 'tbl_bill'
		id column:'order_id',generator:'foreign',params:[property:'order']
		order insertable: false, updateable: false
		version false
	}
}
