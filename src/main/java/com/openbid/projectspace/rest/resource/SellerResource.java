package com.openbid.projectspace.rest.resource;

/**
 * SellerResource Bean for holding Seller's properties
 * @author Anand Raju
 */
public class SellerResource extends AbstractResource {
	/**
	 * 
	 */
	protected String idPrefix = "slr-";

	/**
	 * Default Constructor to create a SellerResource
	 */
	public SellerResource() {
		super();
		this.id = generateId();
	}

	@Override
	public String getPrefix() {
		return idPrefix;
	}

}
