package com.example.bacsafe;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


public class Main extends TabActivity {
	
	//Profile Data Instance 
	private ProfileActivity profileData = new ProfileActivity();
	private SharedPreferences userData = profileData.userData;
	//User Profile variables
	private String sUserName, sName;
	private int nWeight, nHeightFeet, nHeightInches, nAge;
	private boolean bIsMale;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //Load the home screen
        setContentView(R.layout.activity_main);
        setHomeScreen();
       
        //Load profile data
        loadProfileData();
        
		final Intent profileActivity = new Intent(Main.this, ProfileActivity.class);
		 
    	//If the profile has not been created, open the Create Profile page.
        if(sUserName.isEmpty()){
        	startActivity(profileActivity);
        }
        
        //Edit Button (Drink Counter tab) Allows user profile data to be changed
        Button editButton = (Button)findViewById(R.id.buttonEdit);
        editButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(profileActivity);
			}//onClick()
		}); 
    }//onCreate()
    
    
    
    /* 
     * setHomeScreen
     * 
     * @Defn - Load navigation tabs (i.e. Drink Counter, Groups, Friends)
     */
    private void setHomeScreen(){
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
    }// setHomeScreen()
    
    
    /*
     * loadProfileData()
     * 
     * @Defn - Loads User Information for BAC calculations
     */
    private void loadProfileData(){
    	
    	userData = getSharedPreferences(profileData.userDataFile, 0);
		
		//Set UI Variables from saved file
		sUserName = userData.getString("username", "");
		sName = userData.getString("name", "");
		nWeight = userData.getInt("weight", 0);
		nHeightFeet = userData.getInt("height_feet", 0);
		nHeightInches = userData.getInt("height_inches", 0);
		nAge = userData.getInt("age", 21);
		bIsMale = userData.getBoolean("male", true);
    	
    }
    
    
}//class Main
