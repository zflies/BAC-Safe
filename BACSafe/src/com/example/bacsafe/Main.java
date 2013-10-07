package com.example.bacsafe;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/*
 * Main class
 * 
 * @Defn - Main home screen with tabHost for Drink Counter, Friends, and Groups
 */
public class Main extends TabActivity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        
        /*	TODO:	if (profile exists)
         * 				load Home screen (activity_main)
         * 			else
         * 				load CreateProfileScreen
         */
        
        //Load the home screen
        setContentView(R.layout.activity_main);

        //Create the Home Screen navigation tabs
        TabHost tabHost=getTabHost();
        tabHost.setup();

        TabSpec spec1=tabHost.newTabSpec("Drink Counter");	//Tab - Drink Counter
        spec1.setIndicator("Drink Counter");
        spec1.setContent(R.id.tabDrinkCounter);
     
        TabSpec spec2=tabHost.newTabSpec("Groups");         //Tab - Night Group
        spec2.setIndicator("Groups");
        spec2.setContent(R.id.tabNightGroup);

        TabSpec spec3=tabHost.newTabSpec("Friends");        //Tab - Friends List
        spec3.setIndicator("Friends");
        spec3.setContent(R.id.tabFriendsList);

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);
       
        //Edit Button (Drink Counter tab) Allows user profile data to be changed
        Button editButton = (Button)findViewById(R.id.buttonEdit);
        editButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Main.this, EditButtonActivity.class);
				startActivity(intent);
			}//onClick()
		}); 
        

    }//onCreate()
    
}//class Main
