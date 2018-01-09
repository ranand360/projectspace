/**
 * @author Anand Raju
 */
package com.openbid.projectspace.repository;

import java.util.List;

import com.openbid.projectspace.rest.resource.Resource;

/**
 * @author Anand Raju
 *
 */
public interface Repository {
	public void add(Resource resource, boolean overwrite);
	public void add(Resource resource);
	public Resource findById(String id);
	public List<Resource> getAll();
}
