package com.example.bacsafe;

import java.util.concurrent.ExecutionException;

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
	public boolean sendBuddyRequest(String curUserName){
		ServerAPI connection = new ServerAPI();
		
		String log = "";
		try {
			log = connection.sendBuddyRequest(curUserName, m_sBuddyUsername);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		if(log.compareTo("Success") == 0) {
			return true;
		}else return false;
	} // sendBuddyRequest()


	/**
	 * 	Retrieves the BAC for a Buddy
	 * @return m_dBuddyBAC - Estimated BAC value for the Buddy
	 */
	public double getBuddyBAC(){

		//TODO: Send request to receive Buddy's BAC
		ServerAPI connection = new ServerAPI();
		
		double curBAC = 0.0;
		try {
			curBAC = connection.getUserBAC(m_sBuddyUsername);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		m_dBuddyBAC = curBAC;
		return m_dBuddyBAC;
	} // getBuddyBAC()


	/**
	 * 	Retrieves the Total Drink Count for a Buddy
	 * @return m_nBuddyTotalDrinkCount - Number of total drinks consumed by the Buddy
	 */
	public int getBuddyTotalDrinkCount(){

		//TODO: Send request to receive Buddy's Total Drink Count
		ServerAPI connection = new ServerAPI();
		
		long curDrinkCount = 0;
		try {
			curDrinkCount = connection.getUserDrinkCount(m_sBuddyUsername);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		m_nBuddyTotalDrinkCount = (int) curDrinkCount;

		return m_nBuddyTotalDrinkCount;	
	} // getBuddyTotalDrinkCount()

} // class Buddy
