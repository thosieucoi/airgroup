package com.airgroup.domain

import org.weceem.auth.CMSUser


class OrderEmployee {
	static belongsTo=[bookEmp:CMSUser,processEmp:CMSUser,order:Order]
	Date executeTime
	Short status
	Date timeLimit
	Date notification
	String moneyCode

	static constraints = {
		executeTime nullable:true
		timeLimit nullable:true
		notification nullable:true
		moneyCode nullable:true
		bookEmp nullable:true
		processEmp nullable:true
	}
	static mapping={
		table 'tbl_order_employee'
		id column:'order_id',generator:'foreign',params:[property:'order']
		order insertable: false, updateable: false
		version false
	}
}
