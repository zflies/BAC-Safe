package com.example.bacsafe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

public class FindBuddyActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_find_buddy); //Show the Find Buddy Screen Activity		
		
		//Setup the Page to automatically show the keyboard 
		setupFindBuddyPage();
		
	} //onCreate()
	
	/*
	 * setupFindBuddyPage()
	 * 
	 * @Defn - Sets up UI for the page 
	 */
	public void setupFindBuddyPage(){
		
		Button doneButton = (Button)findViewById(R.id.button_AddBuddy_Done);
		
		//This will automatically give focus to and expand the search box.  
	    SearchView searchView = (SearchView)findViewById(R.id.searchView_Find_Buddy);
	    searchView.setFocusable(true);
	    searchView.setIconified(false);
	    searchView.requestFocusFromTouch();
	    
	    //Done Button - Returns to Main screen (Buddies tab)
	    doneButton.setOnClickListener(new View.OnClickListener() {
	  	@Override
	  	public void onClick(View v) {
	  		finish();
	  	}//onClick()
	    });//Done Button
	    
	}// setupFindBuddyPage
	
} //FindBuddyActivity
