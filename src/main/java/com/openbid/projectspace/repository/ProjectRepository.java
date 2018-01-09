package com.openbid.projectspace.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.openbid.projectspace.rest.resource.Resource;
import com.openbid.projectspace.rest.resource.ProjectResource;

/**
 * @author Anand Raju
 *
 */

public class ProjectRepository extends ResourceRepository {

	private static ProjectRepository soleInstance = null;

	protected Map<String, Resource> activeProjects;
	protected Map<String, Resource> inActiveProjects;

	protected void initialize() {
		super.initialize();
		activeProjects = new ConcurrentHashMap<String, Resource>();
		inActiveProjects = new ConcurrentHashMap<String, Resource>();
	}

	/**
	 * @return
	 */
	public static ProjectRepository getSoleInstance() {
		if (soleInstance == null) {
			soleInstance = new ProjectRepository();
		}
		return soleInstance;
	}

	/**
	 * @return
	 */
	public List<Resource> getAllActiveProjects() {
		List<Resource> projects = (List<Resource>) getAll();
		projects.removeAll(inActiveProjects.values());
		return projects;
	}

	/**
	 * 
	 */
	public void terminateExpiredProjects() {
		Date todayDate = new Date();
		for (Resource resource : repository.values()) {
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
