package com.openbid.projectspace.repository;

import com.openbid.projectspace.rest.resource.BuyerResource;

/**
 * @author Anand Raju
 *
 */

public class BuyerRepository extends ResourceRepository {

	private static BuyerRepository soleInstance = null;

	/**
	 * Method to get access to sole instance
	 * 
	 * @return soleInstance
	 */
	public static BuyerRepository getSoleInstance() {
		if (soleInstance == null) {
			soleInstance = new BuyerRepository();
		}
		return soleInstance;
	}

	/**
	 * @param projectId
	 * @param lowestBidderId
	 */
	public void notifyWin(String lowestBidderId, String projectId) {
		BuyerResource buyer = (BuyerResource) findById(lowestBidderId);
		buyer.projectWon(projectId);
	}
}
