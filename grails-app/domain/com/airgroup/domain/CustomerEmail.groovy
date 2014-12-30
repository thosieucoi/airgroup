package com.airgroup.domain

class CustomerEmail {
	
	
	String email
	Date dateRegister = new Date()

	static mapping={
		table 'tbl_customer_email'
		version false
	}

	public CustomerEmail(){
	}
	
    static constraints = {	
		email (email : true, unique : true)
    }
}
