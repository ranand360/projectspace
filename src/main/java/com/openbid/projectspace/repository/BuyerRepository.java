package com.openbid.projectspace.repository;

import com.openbid.projectspace.rest.resource.BuyerResource;

/**
 * Repository to store/retrieve all BuyerResources
 * @author Anand Raju
 */

public class BuyerRepository extends ResourceRepository {

	private static BuyerRepository soleInstance = null;

	/**
	 * @return - Handle to the singleton instance
	 */
	public static BuyerRepository getSoleInstance() {
		if (soleInstance == null) {
			soleInstance = new BuyerRepository();
		}
		return soleInstance;
	}

	/**
	 * @param lowestBidderId - The buyer id who won 
	 * @param projectId - The project won by the buyer
	 */
	public void notifyWin(String lowestBidderId, String projectId) {
		BuyerResource buyer = (BuyerResource) findById(lowestBidderId);
		buyer.projectWon(projectId);
	}
}
