package com.openbid.projectspace.rest.resource;

import java.util.ArrayList;
import java.util.List;

/**
 * BuyerResource Bean for holding Buyer's properties
 * @author Anand Raju
 */
public class BuyerResource extends AbstractResource {

	protected String idPrefix = "byr-";

	private List<String> projectIdsWon;
	
	/**
	 * Default Constructor to create a BuyerResource
	 */
	public BuyerResource() {
		super();
		this.id = generateId();
		initialize();
	}

	/**
	 * Initialize the list to hold all Projects the Buyer will win
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
	 * Add the project to the list of projects the buyer has won
	 * @param project
	 * 
	 */
	public void projectWon(String project) {
		if (project != null && !projectIdsWon.contains(project)) {
			projectIdsWon.add(project);
		}
	}

	@Override
	public String getPrefix() {
		return idPrefix;
	}
}
