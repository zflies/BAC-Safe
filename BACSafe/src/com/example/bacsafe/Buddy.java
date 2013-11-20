package com.example.bacsafe;

/**
 * Buddy Object for getting specific buddy information, such as username, firstname, lastname, etc.
 * 
 * @author Team BAC Safe
 */
public class Buddy {

	String m_sBuddyUsername;
	String m_sBuddyFirstName;
	String m_sBuddyLastName;

	private double m_dBuddyBAC;
	private int m_nBuddyTotalDrinkCount;


	/**
	 * Constructor. Creates new Buddy object
	 * @param username
	 * @param firstname
	 * @param lastname
	 */
	public Buddy(String username)
	{
		m_sBuddyUsername = username;
		
	} // Constructor


	/**
	 *  TODO:
	 * @return
	 */
	public boolean sendGroupRequest(){

		return true;
	} // sendGroupRequest()


	/**
	 *  TODO:
	 * @return
	 */
	public boolean sendBuddyRequest(){

		return true;
	} // sendBuddyRequest()


	/**
	 * 	Retrieves the BAC for a Buddy
	 * @return m_dBuddyBAC - Estimated BAC value for the Buddy
	 */
	public double getBuddyBAC(){

		//TODO: Send request to receive Buddy's BAC

		return m_dBuddyBAC;
	} // getBuddyBAC()


	/**
	 * 	Retrieves the Total Drink Count for a Buddy
	 * @return m_nBuddyTotalDrinkCount - Number of total drinks consumed by the Buddy
	 */
	public int getBuddyTotalDrinkCount(){

		//TODO: Send request to receive Buddy's Total Drink Count

		return m_nBuddyTotalDrinkCount;	
	} // getBuddyTotalDrinkCount()

} // class Buddy
