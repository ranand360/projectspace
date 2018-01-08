/**
 * @author Anand Raju
 */
package com.openbid.projectspace.rest.resource;

import java.util.UUID;

/**
 * @author Anand Raju
 *
 */
public class SellerResource {
	/**
	 * 
	 */
	private static final String SLR = "slr-";
	
	private String id;
	
	public SellerResource() {
		this.id= SLR + UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
	}
	
}
