/**
 * @author Anand Raju
 */
package com.openbid.projectspace.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openbid.projectspace.rest.resource.SellerResource;

/**
 * @author Anand Raju
 *
 */

public class SellerRepository {
	
	private static SellerRepository soleInstance = null;
	
	private Map<String,SellerResource> repository;
	
	protected SellerRepository() {
		initialize();
	}
	
	private void initialize() {
		repository = new HashMap<String,SellerResource>();
	}
	
	public SellerResource findById(String id){
		SellerResource seller = null;
		if(repository != null) {
			seller = repository.get(id);
		}
		return seller;
	}

	/**
	 * @return
	 */
	public static SellerRepository getSoleInstance() {
		if(soleInstance==null) {
			soleInstance= new SellerRepository();
		}
		return soleInstance;
	}
	
	public void add(SellerResource seller) {
		if(seller == null) {
			return;
		}
		String id = seller.getId();
		
		if(id == null) {
			return;
		}
		
		repository.put(id, seller);
	}

	/**
	 * @return
	 */
	public List<SellerResource> getAll() {
		List<SellerResource> sellers = null;
		if(repository != null) {
			sellers = new ArrayList<SellerResource>(repository.values());
		}
		return sellers;
	}
}
