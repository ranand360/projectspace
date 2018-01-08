package com.openbid.projectspace.rest.resource;

/*
 * AbstractResource that all resources must extend.  
 */
public abstract class AbstractResource {
	protected String id;
	
	public AbstractResource() {}
	
	public AbstractResource(final String id){
		this.id=id;
	}
	
	public String getId() {
		return id;
	}
}
