package com.example.bacsafe;

import java.util.LinkedList;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Group Object for storing Buddies belonging to a specific group.  
 * 
 * @author Team BAC Safe
 */
public class Group {
	
	private Context context;
	
	// Create file for saving Group's Buddies List
	private final String userGroupsBuddiesList = "BAC Safe User Groups Buddies List";
	private SharedPreferences pref_groupsBuddiesList;
	
	// Create Preference Editor
	private SharedPreferences.Editor prefEditor;

	private String m_sGroupName;
	private LinkedList<Buddy> m_listGroupBuddies;


	/**
	 * Constructor.
	 * @param groupname
	 */
	public Group(String groupname) {
		
		context = Main.getContextOfApplication();
		
		m_sGroupName = groupname;
		
	} // Constructor

	/**
	 * //TODO:
	 * @param newBuddy
	 */
	public void addGroupMember(Buddy newBuddy){

		//newBuddy.sendGroupRequest();

		m_listGroupBuddies.add(newBuddy);
		
		setGroupBuddies(m_listGroupBuddies);

	} // addGroupMember()

	/**
	 *  Loads the Buddies within the Group
	 * @return m_listGroupBuddies
	 */
	public LinkedList<Buddy> getGroupBuddies(){
		
		// Set Access to internal storage file with Groups List
		pref_groupsBuddiesList = context.getSharedPreferences(userGroupsBuddiesList, 0);
		
		LinkedList<Buddy> listGroupBuddies = new LinkedList<Buddy>();

		String sUsername;

		int nNumberOfGroupBuddies = pref_groupsBuddiesList.getInt(m_sGroupName + "_Size", 0); // Number of Buddies in group i

		// Load the Buddies for Each Group
		for(int i=0; i < nNumberOfGroupBuddies; i++){

			sUsername = pref_groupsBuddiesList.getString(m_sGroupName + "_" + i, null);

			/* 
			 * TODO:
			 * Verify username still exists in database
			 */

			Buddy newBuddy = new Buddy(sUsername); // Create Buddy object with saved Buddy info

			listGroupBuddies.add(newBuddy); // Add new Buddies to Buddy List
		}
		
		m_listGroupBuddies = listGroupBuddies;
		
		return m_listGroupBuddies;	
		
	} // getGroupBuddies()


	/**
	 * Saves the Group's Buddies
	 * @param listGroupBuddies
	 */
	public void setGroupBuddies(LinkedList<Buddy> listGroupBuddies){
		
		m_listGroupBuddies = listGroupBuddies;
		
		// Set Access to internal storage file with Groups List
		pref_groupsBuddiesList = context.getSharedPreferences(userGroupsBuddiesList, 0);
		
		prefEditor = pref_groupsBuddiesList.edit().clear(); // Open the editor and empty the file
		
		prefEditor.putInt(m_sGroupName + "_Size", listGroupBuddies.size()); // Number of Buddies in group
		
		// Load the Buddies for Each Group
		for(int i=0; i < listGroupBuddies.size(); i++){
			prefEditor.putString(m_sGroupName + "_" + i, listGroupBuddies.get(i).m_sBuddyUsername);
		}
		
		prefEditor.apply();

	} // saveGroupBuddies()
	
	/**
	 * Returns the name of the group
	 */
	public String getGroupName(){
		return m_sGroupName;
	} // getGroupName()

} // class Group
