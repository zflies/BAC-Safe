package com.example.bacsafe;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;


public class Main extends TabActivity {
	
	//Profile Data Instance 
	private ProfileActivity profileData = new ProfileActivity();
	private SharedPreferences userData = profileData.userData;
	//User Profile variables
	private String sUserName, sFirstName, sLastName, sDrinkTotal, sBACpercent, sBACtimerMinute, sBACtimerHour,sBeer, sWine, sShot;
	private int nWeight, nHeightFeet, nHeightInches, nAge, nShot, nWine, nBeer, nDrinkTotal, nBACtimerMinute, nBACtimerHour;
	private double dBACpercent;
	private boolean bIsMale;
	
	//Other variables
	TextView tDrinkTotal, tBACpercent, tBeer, tWine, tShot, tBACtimer;
	
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
		final Intent findBuddyActivity = new Intent(Main.this, FindBuddyActivity.class);
		 
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
        
        
        Button shotButton =  (Button)findViewById(R.id.buttonShot);
        shotButton.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
        		nShot++;
        		generateShotDrinks();
        		generateNumberDrinks();
        		generateBAC();
        		generateBACTimer();
			}//onClick
		});
        
        Button wineButton = (Button)findViewById(R.id.buttonWine);
        wineButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				nWine++;
				generateWineDrinks();
				generateNumberDrinks();
				generateBAC();
				generateBACTimer();
			}
		});
        
        Button beerButton = (Button)findViewById(R.id.buttonBeer);
        beerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				nBeer++;
				generateBeerDrinks();
				generateNumberDrinks();
				generateBAC();
				generateBACTimer();
			}
		});
        
        
        
        
        
        
        
        
        //Add Buddy Button (Buddies tab) Allows user to search for / add buddies
        Button addBuddyButton = (Button)findViewById(R.id.buttonAddBuddy);
        addBuddyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(findBuddyActivity); 
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
        spec2.setContent(R.id.tabGroups);

        TabSpec spec3=tabHost.newTabSpec("Buddies");        //Tab - Friends List
        spec3.setIndicator("Buddies");
        spec3.setContent(R.id.tabBuddies);

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
		sFirstName = userData.getString("firstname", "");
		sLastName = userData.getString("lastname", "");
		nWeight = userData.getInt("weight", 0);
		nHeightFeet = userData.getInt("height_feet", 0);
		nHeightInches = userData.getInt("height_inches", 0);
		nAge = userData.getInt("age", 21);
		bIsMale = userData.getBoolean("male", true);
    	
    }
    
    private void generateBAC(){
    	dBACpercent = (nBeer) + (nWine*2) + (nShot*3);
    	sBACpercent = Double.toString(dBACpercent);
    	tBACpercent = (TextView)findViewById(R.id.labelBACPercent);
    	tBACpercent.setText(sBACpercent);
    }
    
    private void generateNumberDrinks(){
    	nDrinkTotal = (nBeer) + (nWine*2) + (nShot*3);
    	sDrinkTotal = Integer.toString(nDrinkTotal);
    	tDrinkTotal = (TextView)findViewById(R.id.labelDrinkCountNumber);
    	tDrinkTotal.setText(sDrinkTotal);
    }
    
    private void generateBeerDrinks(){
    	sBeer = Integer.toString(nBeer);
    	tBeer = (TextView)findViewById(R.id.labelBeerCount);
    	tBeer.setText(sBeer);
    }
    
    private void generateWineDrinks(){
    	sWine = Integer.toString(nWine);
    	tWine = (TextView)findViewById(R.id.labelWineCount);
    	tWine.setText(sWine);
    }
    
    private void generateShotDrinks(){
    	sShot = Integer.toString(nShot);
    	tShot = (TextView)findViewById(R.id.labelShotCount);
    	tShot.setText(sShot);
    }
    
    private void generateBACTimer(){
    	tBACtimer = (TextView)findViewById(R.id.labelSoberTime);
    	
    	nBACtimerMinute = (nBeer) + (nWine*2) + (nShot*3);
    	sBACtimerMinute = Integer.toString(nBACtimerMinute);

    	nBACtimerHour = (nBeer) + (nWine*2) + (nShot*3);
    	sBACtimerHour = Integer.toString(nBACtimerHour);
    	
    	tBACtimer.setText(sBACtimerMinute + ":" + sBACtimerHour);
    }
}//class Main
