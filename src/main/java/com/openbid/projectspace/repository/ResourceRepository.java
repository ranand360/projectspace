/**
 * @author Anand Raju
 */
package com.openbid.projectspace.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openbid.projectspace.rest.resource.AbstractResource;

/**
 * @author Anand Raju
 * Abstract Resource Repository - Provides a basic API to store and retrieve resources that all resource repositories must extend
 *
 */
public abstract class ResourceRepository {

	protected Map<String,AbstractResource> repository;
	
	protected ResourceRepository() {
		initialize();
	}
	
	/**
	 * Initializes the repository
	 */
	protected void initialize() {
		repository = new HashMap<String,AbstractResource>();
	}
	

	/**
	 * @param resource Adds the resource to the repository
	 */
	public void add(AbstractResource resource, boolean overwrite) {
		if(resource == null) {
			return;
		}
		String id = resource.getId();
		
		if(id == null) {
			return;
		}
		
		if(!overwrite && findById(id) != null) {
			return;
		}
		
		repository.put(id, resource);
	}
	
	public void add(AbstractResource project) {
		add(project,Boolean.FALSE);
	}
	
	
	/**
	 * @param id
	 * @return Returns the resource found by id. Returns null otherwise.
	 */
	public AbstractResource findById(String id){
		AbstractResource resource = null;
		if(repository != null) {
			resource = repository.get(id);
		}
		return resource;
	}

	/**
	 * @return Returns a list of all the resource. Returns null if no resources are found.
	 */
	public List<AbstractResource> getAll() {
		List<AbstractResource> recources = null;
		if(repository != null) {
			recources = new ArrayList<AbstractResource>(repository.values());
		}
		return recources;
	}

}
