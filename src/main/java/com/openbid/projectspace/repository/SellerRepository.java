/**
 * @author Anand Raju
 */
package com.openbid.projectspace.repository;

/**
 * @author Anand Raju
 *
 */

public class SellerRepository extends ResourceRepository {

	private static SellerRepository soleInstance = null;

	/**
	 * @return
	 */
	public static SellerRepository getSoleInstance() {
		if (soleInstance == null) {
			soleInstance = new SellerRepository();
		}
		return soleInstance;
	}

}
