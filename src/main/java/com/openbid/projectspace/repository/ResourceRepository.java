package com.openbid.projectspace.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openbid.projectspace.rest.resource.Resource;

/**
 * @author Anand Raju 
 * Resource Repository - Provides a basic API to
 * store and retrieve resources that all resource repositories must
 * extend
 *
 */
public abstract class ResourceRepository implements Repository {

	protected Map<String, Resource> repository;

	protected ResourceRepository() {
		initialize();
	}

	/**
	 * Initializes the repository
	 */
	protected void initialize() {
		repository = new HashMap<String, Resource>();
	}

	/**
	 * Add a resource to the repository 
	 * @param resource - resource to be added
	 * @param overwrite - true/false signifying update existing if resource already exists in repository
	 */
	public void add(Resource resource, boolean overwrite) {
		if (resource == null) {
			return;
		}
		String id = resource.getId();

		if (id == null) {
			return;
		}

		if (!overwrite && findById(id) != null) {
			return;
		}

		repository.put(id, resource);
	}
	
	/**
	 * Add a resource to the repository. Returns without adding if resource already exists in repository
	 * @param resource - resource to be added
	 */
	public void add(Resource project) {
		add(project, Boolean.FALSE);
	}

	/**
	 * Find a resource in the repository by id
	 * @param id - id to use for lookup
	 * @return - resource found with id. Null if not found.
	 */
	public Resource findById(String id) {
		Resource resource = null;
		if (repository != null) {
			resource = repository.get(id);
		}
		return resource;
	}

	/**
	 * @return - List of resources in the repository. Return empty if no resource are present.
	 */
	public List<Resource> getAll() {
		List<Resource> recources = null;
		if (repository != null) {
			recources = new ArrayList<Resource>(repository.values());
		}
		return recources;
	}

}
