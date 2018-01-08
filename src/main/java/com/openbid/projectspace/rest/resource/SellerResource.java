/**
 * @author Anand Raju
 */
package com.openbid.projectspace.rest.resource;

/**
 * @author Anand Raju
 *
 */
public class SellerResource extends AbstractResource{
	/**
	 * 
	 */
	protected String idPrefix= "slr-";
	
	public SellerResource() {
		super();
		this.id=generateId();
	}
	
	public String getId() {
		return id;
	}
	
	@Override
	public String getPrefix() {
		return idPrefix;
	}
	
}
