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

	double m_dBuddyBAC;
	double m_dBuddyTimeUntilSober;
	int m_nBuddyTotalDrinkCount;



	/**
	 * Constructor. Creates new Buddy object
	 * @param username
	 * @param firstname
	 * @param lastname
	 */
	public Buddy(String username)
	{
		m_sBuddyUsername = username;
		
		/*
		 * Get rest of information from server
		 */
		
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
	 * 	TODO:
	 * @return
	 */
	public double getBuddyBAC(){

		//TODO: Send request to receive Buddy's BAC

		return m_dBuddyBAC;
	} // getBuddyBAC()


	/**
	 * TODO:
	 * @return
	 */
	public double getBuddyTimeUntilSober(){

		//TODO: Send request to receive Buddy's Time Until Sober

		return m_dBuddyTimeUntilSober;
	} // getBuddyTimeUntilSober()


	/**
	 * TODO: 
	 * @return
	 */
	public double getBuddyTotalDrinkCount(){

		//TODO: Send request to receive Buddy's Total Drink Count

		return m_nBuddyTotalDrinkCount;	
	} // getBuddyTotalDrinkCount()

} // class Buddy
