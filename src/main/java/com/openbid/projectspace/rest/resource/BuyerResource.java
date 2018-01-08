package com.openbid.projectspace.rest.resource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anand Raju
 *
 */
public class BuyerResource extends AbstractResource{
	/**
	 * 
	 */
	protected String idPrefix= "byr-";
	
	private List<String> projectIdsWon;
	
	public BuyerResource() {
		super();
		this.id=generateId();
		initialize();
	}
	
	/**
	 * Initialize the list to hold all Projects the Buyer will won
	 */
	private void initialize() {
		projectIdsWon = new ArrayList<String>();
	}

	/**
	 * @return Get the list of projects the buyer has won
	 */
	public List<String> getProjectIdsWon() {
		return projectIdsWon;
	}

	/**
	 * @param project - Add the project to the list of projects the buyer has won
	 */
	public void projectWon(String project) {
		if(project != null && !projectIdsWon.contains(project)) {
			projectIdsWon.add(project);
		}
	}
	
	@Override
	public String getPrefix() {
		return idPrefix;
	}
}
