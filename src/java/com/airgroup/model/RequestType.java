/**
 * 
 */
package com.airgroup.model;

/**
 * @author linhnd1
 *
 */
public enum RequestType {
	POST ("POST"),
	GET ("GET");
	
	String type;
	
	private RequestType(String newType) {
		this.type = newType;
	}
	
	public String getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return type;
	}
}
