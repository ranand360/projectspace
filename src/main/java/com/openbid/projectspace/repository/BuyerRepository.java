/**
 * @author Anand Raju
 */
package com.openbid.projectspace.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openbid.projectspace.rest.resource.BuyerResource;

/**
 * @author Anand Raju
 *
 */

public class BuyerRepository {
	
	private static BuyerRepository soleInstance = null;
	
	private Map<String,BuyerResource> repository;
	
	protected BuyerRepository() {
		initialize();
	}
	
	private void initialize() {
		repository = new HashMap<String,BuyerResource>();
	}
	
	public BuyerResource findById(String id){
		BuyerResource buyer = null;
		if(repository != null) {
			buyer = repository.get(id);
		}
		return buyer;
	}

	/**
	 * @return
	 */
	public static BuyerRepository getSoleInstance() {
		if(soleInstance==null) {
			soleInstance= new BuyerRepository();
		}
		return soleInstance;
	}
	
	public void add(BuyerResource buyer) {
		if(buyer == null) {
			return;
		}
		String id = buyer.getId();
		
		if(id == null) {
			return;
		}
		
		repository.put(id, buyer);
	}

	/**
	 * @return
	 */
	public List<BuyerResource> getAll() {
		List<BuyerResource> buyers = null;
		if(repository != null) {
			buyers = new ArrayList<BuyerResource>(repository.values());
		}
		return buyers;
	}

	/**
	 * @param projectId
	 * @param lowestBidderId
	 */
	public void notifyWin(String lowestBidderId, String projectId) {
		BuyerResource buyer = findById(lowestBidderId);
		buyer.projectWon(projectId);
	}
}
