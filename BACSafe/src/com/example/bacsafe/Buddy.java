//-------------------------------------------------------------------------------------------------------------------------------
//	Copyright 2013 by BAC Safe Creators: Zach Flies, Alec White, Josh Collins, Shannon Bisges, and David Menager. 
//  All Rights Reserved.
//-------------------------------------------------------------------------------------------------------------------------------

package com.example.bacsafe;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

/**
 * Buddy Object for getting specific buddy information, such as username, firstname, lastname, etc.
 * 
 * @author Team BAC Safe
 */
public class Buddy {

        public String m_sBuddyUsername;
        String m_sBuddyFirstName;
        String m_sBuddyLastName;

        double m_dBuddyBAC;
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
                
        } // Constructor


        /**
         *  TODO:
         * @return
         */
        public boolean sendGroupRequest(String groupName) throws InterruptedException, ExecutionException{
    		ServerAPI connection = new ServerAPI();	
    		LinkedList<Group> groups = new LinkedList<Group>();
    		Group g = new Group(groupName);
    		groups.add(g);
    		String log = "";

    		log = connection.setGroups(m_sBuddyUsername, groups);		
    		if(log.compareTo("Success") == 0) {
    			return true;
    		}else return false;
    } /*
        public boolean sendGroupRequest(){

                return true;
        } // sendGroupRequest() */


        /**
         *  TODO:
         * @return
         */
        public boolean sendBuddyRequest(String curUserName) throws InterruptedException, ExecutionException{
    		ServerAPI connection = new ServerAPI();		
    		LinkedList<Buddy> buddies = new LinkedList<Buddy>();
    		buddies.add(this);
    		String log = "";

    		log = connection.createBuddies(curUserName, buddies);		
    		if(log.compareTo("Success") == 0) {
    			return true;
    		}else return false;
        } // sendBuddyRequest()
        
        /**
         * Gets the Buddy's First name from database
         * @return
         * @throws InterruptedException
         * @throws ExecutionException
         */
        public String getBuddyFirstName() throws InterruptedException, ExecutionException{
        	        	
        	ServerAPI connection = new ServerAPI();	
        	
        	String sFirstName = "";

    		sFirstName = connection.getUserFirstName(m_sBuddyUsername);
        	
        	
        	return sFirstName;
        } // getBuddyFirstName()
        
        /**
         * Gets the Buddy's Last name from database
         * @return
         * @throws InterruptedException
         * @throws ExecutionException
         */
        public String getBuddyLastName() throws InterruptedException, ExecutionException{
        	        	
        	ServerAPI connection = new ServerAPI();	
        	
        	String sLastName = "";

        	sLastName = connection.getUserLastName(m_sBuddyUsername);
        	
        	return sLastName;
        } // getBuddyLastName()


        /**
         * Retrieves the BAC for a Buddy
         * @return m_dBuddyBAC - Estimated BAC value for the Buddy
         */
        public double getBuddyBAC() throws InterruptedException, ExecutionException{

    		ServerAPI connection = new ServerAPI();	
    		double curBAC = 0.0;

    		curBAC = connection.getUserBAC(m_sBuddyUsername);
    		
    		m_dBuddyBAC = curBAC;
    		return m_dBuddyBAC;
        } // getBuddyBAC()


        /**
         *         Retrieves the Total Drink Count for a Buddy
         * @return m_nBuddyTotalDrinkCount - Number of total drinks consumed by the Buddy
         */
        public int getBuddyTotalDrinkCount() throws InterruptedException, ExecutionException{

    		ServerAPI connection = new ServerAPI();
    		long curDrinkCount = 0;

    		curDrinkCount = connection.getUserDrinkCount(m_sBuddyUsername);
    		
    		m_nBuddyTotalDrinkCount = (int) curDrinkCount;

    		return m_nBuddyTotalDrinkCount;
        } // getBuddyTotalDrinkCount()

} // class Buddy