package com.openbid.projectspace.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.openbid.projectspace.rest.resource.Resource;
import com.openbid.projectspace.rest.resource.ProjectResource;

/**
 * Repository to store/retrieve all ProjectResources
 * @author Anand Raju
 */

public class ProjectRepository extends ResourceRepository {
	
	private static ProjectRepository soleInstance = null;
	
	//Map of all inActiveProjects. Helps to retrieve ActiveProjects quickly.
	protected Map<String, Resource> inActiveProjects;
	
	protected void initialize() {
		super.initialize();
		inActiveProjects = new ConcurrentHashMap<String, Resource>();
	}

	/**
	 * @return - Handle to the singleton instance
	 */
	public static ProjectRepository getSoleInstance() {
		if (soleInstance == null) {
			soleInstance = new ProjectRepository();
		}
		return soleInstance;
	}

	/**
	 * @return - Returns all projects that are active
	 */
	public List<Resource> getAllActiveProjects() {
		List<Resource> projects = (List<Resource>) getAll();
		projects.removeAll(inActiveProjects.values());
		return projects;
	}

	/**
	 * Method to close all projects that have expired.
	 * Modifies project's expiry to true
	 * Notifies Lowest Bidder that he won the project
	 * Appends to inActiveProjects Map for subsequent optimized lookup 
	 */
	public void terminateExpiredProjects() {
		Date todayDate = new Date();
		for (Resource resource : getAllActiveProjects()) {
			ProjectResource project = (ProjectResource) resource;
			// Check project has gone past its endDate
			if (todayDate.compareTo(project.getEndTime()) > 0) {
				project.setExpired(true);
				if (project.getLowestBidderId() != null) {
					BuyerRepository.getSoleInstance().notifyWin(project.getLowestBidderId(), project.getId());
				}
				inActiveProjects.put(project.getId(),project);
			}
		}
	}
}
