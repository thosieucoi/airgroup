package com.airgroup.domain

class Feedback {
	String name
	String phoneNumber
	String content
	String address
	Date sendDate
	Short status
	static constraints = {
		name blank:false, size:6..100, matches: "^[\\p{L}0-9 .'-]+"
		phoneNumber blank:true, matches:'\\d{6,15}', maxLength:15
		content blank:false, maxSize:500
		address blank:false, maxSize:100
		sendDate nullable:false
	}
	static mapping={
		id generator:"identity"
		table "tbl_feedback"
		version false
	}
}
