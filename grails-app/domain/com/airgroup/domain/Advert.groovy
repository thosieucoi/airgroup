package com.airgroup.domain

import java.util.Date;

class Advert {
	
//	Long id
	byte[] slidePic
	Short status
	String slidePicFileName
	String linkAdvert
	Date activeTime

	static constraints = {
		slidePic maxSize: 2097152
	}

	static mapping={
		table 'tbl_Advert'
		id generator:'identity'
		version false
	}
//	public Advert(){
//	}
//
//	Advert(myFile){
//		this.myFile = myFile
//	}

}