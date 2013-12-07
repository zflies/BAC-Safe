//-------------------------------------------------------------------------------------------------------------------------------
//	Copyright 2013 by BAC Safe Creators: Zach Flies, Alec White, Josh Collins, Shannon Bisges, and David Menager. 
//  All Rights Reserved.
//-------------------------------------------------------------------------------------------------------------------------------

package com.example.bacsafe;

import java.util.LinkedList;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Activity for viewing the user's group members. 
 * 
 * @author Team BAC Safe
 */
public class ViewGroup_Activity extends Activity{

	private Group m_userGroup; // Access User's Group

	private LinkedList<Buddy> m_listGroupBuddies = new LinkedList<Buddy>();
	private String m_sGroupName; 

	private ListView m_listView; // Holds the list of Group Members

	/**
	 * Create the View Group page for user interaction.  
	 * Called when a group is selected in the Groups Tab in Main Activity.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_view_group); //Show the Find Buddy Screen Activity		

		Bundle extras = getIntent().getExtras(); // Receive the group name from the calling Activity 
		m_sGroupName = extras.getString("groupname"); 

		m_userGroup = new Group(m_sGroupName); // Special Constructor
		m_listGroupBuddies = m_userGroup.getGroupBuddies(); // Get the list of buddies belonging to the group

		setupViewGroupPage(); // Setup the UI for the View Group Page

	} // onCreate()


	/**
	 * Sets up UI for the View Group page 
	 */
	private void setupViewGroupPage(){

		// Group Name Text Field
		TextView tvGroupName = (TextView)findViewById(R.id.label_groupName);
		tvGroupName.setText(m_sGroupName); 

		// Custom List View Setup
		m_listView = (ListView) findViewById(R.id.listView_ViewGroup); 
		m_listView.setAdapter(new ViewGroup_ListViewAdapt(m_listGroupBuddies , this)); 
		final Context context = this;

		// Done Button - Returns to Main screen (Buddies tab)
		Button doneButton = (Button)findViewById(R.id.button_ViewGroup_Done);
		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			} 
		}); // Done Button

		// Refresh Button - Returns to Main screen (Buddies tab)
		Button refreshButton = (Button)findViewById(R.id.button_ViewGroup_Refresh);
		refreshButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				m_listGroupBuddies = m_userGroup.getGroupBuddies();
				m_listView.setAdapter(new ViewGroup_ListViewAdapt(m_listGroupBuddies , context)); 
			} 
		}); // Done Button

	} // setupViewGroupPage()

} // class ViewGroupActivity
