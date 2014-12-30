package com.airgroup.domain
import org.codehaus.groovy.grails.validation.routines.InetAddressValidator
class Ipconfig {
	String ip
	String code
	Short status
    static constraints = {
		ip(blank:false, size:5..300)
		code(blank: false, size: 6..100, matches:/[a-zA-Z0-9\-_!@#$%^&*().]+/)
		status(blank: true)
    }
	static mapping={
		table 'tbl_ipconfig'
		id generator:'identity'
		version false
	}
}
