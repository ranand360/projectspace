/**
 * @author Anand Raju
 */
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

public class ProjectRepository extends ResourceRepository{
	
	private static ProjectRepository soleInstance = null;
	
	protected Map<String,Resource> activeProjects;
	
	protected void initialize() {
		super.initialize();
		activeProjects = new ConcurrentHashMap<String,Resource>();
	}

	/**
	 * @return
	 */
	public static ProjectRepository getSoleInstance() {
		if(soleInstance==null) {
			soleInstance= new ProjectRepository();
		}
		return soleInstance;
	}

	/**
	 * @return
	 */
	public List<Resource> getAllActiveProjects() {
		return (List<Resource>) activeProjects.values();
	}

	/**
	 * 
	 */
	public void terminateExpiredProjects() {
		Date todayDate = new Date();
		activeProjects.clear();
		activeProjects.putAll(repository);
		for(Resource resource:activeProjects.values()) {
			ProjectResource project = (ProjectResource)resource;
			//Check project has gone past its endDate
			if(todayDate.compareTo(project.getEndTime()) > 0) {
				project.setExpired(true);
				if(project.getLowestBidderId() != null) {
					BuyerRepository.getSoleInstance().notifyWin(project.getLowestBidderId(), project.getId());
				}
				activeProjects.remove(project.getId());
			}
		}
	}
}
