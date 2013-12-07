//-------------------------------------------------------------------------------------------------------------------------------
//	Copyright 2013 by BAC Safe Creators: Zach Flies, Alec White, Josh Collins, Shannon Bisges, and David Menager. 
//  All Rights Reserved.
//-------------------------------------------------------------------------------------------------------------------------------

package com.example.bacsafe;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import android.content.Context;

/**
 * Group Object for storing Buddies belonging to a specific group.  
 * 
 * @author Team BAC Safe
 */
public class Group {

	private Context context;

	public String m_sGroupName;
	public LinkedList<Buddy> m_listGroupBuddies;


	/**
	 * Constructor.
	 * @param groupname
	 */
	public Group(String groupname) {

		context = Main.getContextOfApplication();

		m_sGroupName = groupname;

	} // Constructor

	/**
	 * Adds a new Buddy to the group
	 * @param newBuddy - Buddy Object to be added to group
	 */
	public void addGroupMember(Buddy newBuddy){

		//newBuddy.sendGroupRequest();

		m_listGroupBuddies.add(newBuddy);

		setGroupBuddies(m_listGroupBuddies);

	} // addGroupMember()

	/**
	 *  Loads the Buddies within the Group
	 * @return m_listGroupBuddies - LinkedList of Buddies in group
	 */
	public LinkedList<Buddy> getGroupBuddies(){

		//TODO: Load Buddies for Group from database
		ServerAPI connection = new ServerAPI();

		LinkedList<String> buddies = new LinkedList<String>();
		LinkedList<Buddy> groupList = new LinkedList<Buddy>();
		try {
			buddies = connection.getGroupDrinkers(m_sGroupName);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < buddies.size(); i++) {
			Buddy budd = new Buddy(buddies.get(i));
			groupList.add(budd);
		}
		m_listGroupBuddies = groupList;

		return m_listGroupBuddies;	

	} // getGroupBuddies()


	/**
	 * Saves the Group's Buddies
	 * @param listGroupBuddies - LinkedList of Buddies in group
	 */
	public void setGroupBuddies(LinkedList<Buddy> listGroupBuddies){

		m_listGroupBuddies = listGroupBuddies;

	} // saveGroupBuddies()

	/**
	 * Returns the name of the group
	 * @return m_sGroupName - Name of group
	 */
	public String getGroupName(){
		return m_sGroupName;
	} // getGroupName()

} // class Group
