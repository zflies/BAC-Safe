package com.example.bacsafe;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Activity for viewing the user's group members. 
 * 
 * @author Team BAC Safe
 */
public class ViewGroupActivity extends Activity{

	private ListView m_listView; // Holds the list of Group Members
	private User userProfile; // Access to User Profile Info
	
	
	/**
	 * Create the View Group page for user interaction.  
	 * Called when a group is selected in the Groups Tab in Main Activity.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		//setContentView(R.layout.activity_find_buddy); //Show the Find Buddy Screen Activity		

		userProfile = new User();// Retrieve User Info and Prefs through userProfile initialization
		
		// Setup the Page to automatically show the keyboard 
		setupViewGroupPage();

	} // onCreate()
	
	
	/**
	 * Sets up UI for the View Group page 
	 */
	private void setupViewGroupPage(){
		
		
	}
	
	
} // class ViewGroupActivity
