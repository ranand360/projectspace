/**
 * @author Anand Raju
 */
package com.openbid.projectspace.rest.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Anand Raju
 *
 */
public class BuyerResource {
	/**
	 * 
	 */
	private static final String BYR = "byr-";
	
	private String id;
	
	private List<String> projectIdsWon;
	
	public BuyerResource() {
		this.id= BYR + UUID.randomUUID().toString();
		projectIdsWon = new ArrayList<String>();
	}
	
	public String getId() {
		return id;
	}

	public List<String> getProjectIdsWon() {
		return projectIdsWon;
	}

	public void projectWon(String project) {
		projectIdsWon.add(project);
	}
}
