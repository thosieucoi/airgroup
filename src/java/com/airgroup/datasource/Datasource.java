/**
 * 
 */
package com.airgroup.datasource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author linhnd1
 *
 */
public class Datasource {
	protected Logger logger;
	protected ObjectMapper objectMapper;

	public Datasource() {
		this.logger = LoggerFactory.getLogger(super.getClass());

		this.objectMapper = new ObjectMapper();
	}
}
