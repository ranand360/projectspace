/**
 * @author Anand Raju
 */
package com.openbid.projectspace.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.openbid.projectspace.rest.resource.ProjectResource;

/**
 * @author Anand Raju
 *
 */

public class ProjectRepository {
	
	private static ProjectRepository soleInstance = null;
	
	private Map<String,ProjectResource> repository;
	
	private Map<String,ProjectResource> activeProjects;
	
	protected ProjectRepository() {
		initialize();
	}
	
	private void initialize() {
		repository = new ConcurrentHashMap<String,ProjectResource>();
		activeProjects = new ConcurrentHashMap<String,ProjectResource>();
	}
	
	public ProjectResource findById(String id){
		ProjectResource project = null;
		if(repository != null) {
			project = repository.get(id);
		}
		return project;
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
	
	public void add(ProjectResource project, boolean overwrite) {
		if(project == null) {
			return;
		}
		String id = project.getId();
		
		if(id == null) {
			return;
		}
		
		if(!overwrite && findById(id) != null) {
			return;
		}
		
		repository.put(id, project);
	}

	/**
	 * @return
	 */
	public List<ProjectResource> getAll() {
		List<ProjectResource> projects = null;
		if(repository != null) {
			projects = new ArrayList<ProjectResource>(repository.values());
		}
		return projects;
	}

	/**
	 * @return
	 */
	public List<ProjectResource> getAllActiveProjects() {
		return (List<ProjectResource>) activeProjects.values();
	}

	/**
	 * 
	 */
	public void terminateExpiredProjects() {
		Date todayDate = new Date();
		activeProjects.clear();
		activeProjects.putAll(repository);
		for(ProjectResource project:activeProjects.values()) {
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
