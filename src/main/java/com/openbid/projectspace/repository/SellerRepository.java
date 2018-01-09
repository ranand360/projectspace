/**
 * @author Anand Raju
 */
package com.openbid.projectspace.repository;

/**
 * Repository to store/retrieve all SellerResources
 * @author Anand Raju
 */

public class SellerRepository extends ResourceRepository {

	private static SellerRepository soleInstance = null;

	/**
	 * @return - Handle to the singleton instance
	 */
	public static SellerRepository getSoleInstance() {
		if (soleInstance == null) {
			soleInstance = new SellerRepository();
		}
		return soleInstance;
	}

}
