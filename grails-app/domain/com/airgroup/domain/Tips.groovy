package com.airgroup.domain

class Tips {
	
	long lastDomesticBookedTips
	long lastDomesticProcessTips
	long lastInternationalBookedTips
	long lastInternationalProcessTips
	
	static constraints = {
		lastDomesticBookedTips(blank:true, nullable: true)
		lastDomesticProcessTips(blank:true, nullable: true)
		lastInternationalBookedTips(blank:true, nullable: true)
		lastInternationalProcessTips(blank:true, nullable: true)
	}
	
	static mapping = {
		table 'tbl_tips'
		id generator:'identity'
		version false
	}
}
