package com.openbid.projectspace.repository;

import java.util.List;

import com.openbid.projectspace.rest.resource.Resource;

/**
 * Repository interface that all concrete repositories must implement
 * @author Anand Raju
 *
 */
public interface Repository {
	/**
	 * Add a resource to the repository 
	 * @param resource - resource to be added
	 * @param overwrite - true/false signifying update existing if resource already exists in repository
	 */
	public void add(Resource resource, boolean overwrite);

	/**
	 * Add a resource to the repository. Returns without adding if resource already exists in repository
	 * @param resource - resource to be added
	 */
	public void add(Resource resource);

	/**
	 * Find a resource in the repository by id
	 * @param id - id to use for lookup
	 * @return - resource found with id. Null if not found.
	 */
	public Resource findById(String id);

	/**
	 * @return - List of resources in the repository. Return empty if no resource are present.
	 */
	public List<Resource> getAll();
}
