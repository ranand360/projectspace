package com.openbid.projectspace.rest.resource;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * AbstractResource that all resources must extend.  
 */
public abstract class AbstractResource {
	//id property for the resource
	protected String id;
	
	protected String idPrefix;
	
	/**
	 *No argument constructor 
	 */
	public AbstractResource() {}
	

	/**
	 * @return id - id property of the resource
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return idPrefix - id property of the resource
	 */
	@JsonIgnore
	public abstract String getPrefix();
	
	/**
	 * @return Generates an unique ID
	 */
	protected String generateId() {
		return new String(getPrefix()+UUID.randomUUID().toString());
	}
}
