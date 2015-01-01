package com.airgroup.securitycfg

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class IGUserDetails implements UserDetails {
	final Collection<GrantedAuthority> authorities
	final String password
	final String username
	final Short status

	final boolean accountNonExpired = true
	final boolean accountNonLocked = true
	final boolean credentialsNonExpired = true

	final def extraProperties

	IGUserDetails(username, password, status, authorities, otherProps) {
		this.username = username
		this.password = password
		this.authorities = authorities
		this.status = status
		this.extraProperties = otherProps
	}

	def propertyMissing(String name) {
		return extraProperties[name]
	}

	public boolean isEnabled() {
		return status==1?true:false
	}
}
