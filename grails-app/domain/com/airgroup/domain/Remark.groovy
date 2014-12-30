package com.airgroup.domain

import org.weceem.auth.CMSUser;

class Remark {
	static belongsTo=[order:Order,employee:CMSUser]
	String content
	Date remarkDate
	Order order
	CMSUser employee
    static constraints = {
		content maxSize:1000
    }
	static mapping={
		table "tbl_remark"
		id generator: 'identity'
		version false
	}
}
