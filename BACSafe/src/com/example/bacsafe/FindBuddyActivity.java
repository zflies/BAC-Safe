package com.example.bacsafe;

import android.R.menu;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

public class FindBuddyActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_find_buddy); //Show the Find Buddy Screen Activity
		
		
		
		Button doneButton = (Button)findViewById(R.id.button_AddBuddy_Done);
		
		//This will automatically give focus to and expand the search box.  
	    SearchView searchView = (SearchView)findViewById(R.id.searchView_Find_Buddy);
	    searchView.setFocusable(true);
	    searchView.setIconified(false);
	    searchView.requestFocusFromTouch();
		
		
		
		
		
		final Intent mainActivity = new Intent(FindBuddyActivity.this, Main.class);
		
		//Done Button - Returns to Main screen (Buddies tab)
		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}//onClick()
		});//Done Button
		
	} //onCreate()
	
} //FindBuddyActivity
