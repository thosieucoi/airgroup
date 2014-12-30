package com.airgroup.domain

import java.util.Date;

class MoneyCode {
	
//	Long id
	String fileName
	Date createDate

	static constraints = {
	}

	static mapping={
		table 'tbl_MoneyCode'
		id generator:'identity'
		version false
	}

}