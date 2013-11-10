package com.example.bacsafe;

import java.util.LinkedList;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class Main extends TabActivity {
	
	//Create files for saving User App Preferences and User Information to internal storage 
	private static final String userPrefFile = "BAC Safe User Preferences";
	private static final String userInfoFile = "BAC Safe User Information";
	SharedPreferences userPrefs, userInfo;
	SharedPreferences.Editor editor;
	
	//User Information variables
	private String m_sUserName; 
	private String m_sFirstName; 
	private String m_sLastName;
	private int m_nWeight;
	private int m_nHeightFeet; 
	private int m_nHeightInches;
	private int m_nAge;
	private boolean m_bIsMale;
	
    protected String tempBuddyList[] = {"Buddy 1", "Buddy 2", "Buddy 3", "Buddy 4", "Buddy 5"};
	protected LinkedList buddiesList = new LinkedList();
		
	//User Preferences variables
	private boolean m_bShowUserAgreementAlert;
		
	//Drink Counter Variables
	private String sDrinkTotal;
	private String sBACpercent;
	private String sBACtimerMinute;
	private String sBACtimerHour;
	private String sBeer;
	private String sWine;
	private String sShot;
	private int nShot;
	private int nWine;
	private int nBeer;
	private int nDrinkTotal;
	private int nBACtimerMinute;
	private int nBACtimerHour;
	private double dBACpercent;

	//UI variables
	TextView tDrinkTotal;
	TextView tBACpercent;
	TextView tBeer;
	TextView tWine;
	TextView tShot;
	TextView tBACtimer;
	
	//-------------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------------
	//----- IMPLEMENTATION ----------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------------
	
	/*
	 * @Override onCreate()
	 * 
	 * @Defn - Set up Activity when called
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /* 
         * -- BAC Safe SETUP ---------------------------------------------------------------------
         */
        
	        //Remove title bar
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        
	        //Load the home screen
	        setContentView(R.layout.activity_main);
	        setupHomeScreen();

	        //Get user information and preferences
	        getUserInfoAndPrefs();
	        	        
	     	//Show User Liability Agreement if needed, otherwise check if the user needs to create a profile
	        if(m_bShowUserAgreementAlert)
	        {
	        	alertLiabilityAgreement();
	        }
	        else 
	        {	//If the alert does not need to be shown, and a Username DNE, open Profile Page
	        	if(m_sUserName.isEmpty())	
	        	{
	                Intent profileActivityIntent = new Intent(this, ProfileActivity.class);
	        		startActivity(profileActivityIntent);
	        	}
    		}
	        
	        //Setup each tab 
	        drinkCounterTabSetup(); //Drink Counter
	        buddiesTabSetup();		//Buddies
	        groupsTabSetup();		//Groups
	        
    }//onCreate()
   
    
    
    
    /* ------------------------------------------------------------------------------------------/*
     * ------------------------------------------------------------------------------------------
     * ----- SUPPORTING METHODS	-----------------------------------------------------------------	
     * ------------------------------------------------------------------------------------------
     * ------------------------------------------------------------------------------------------*/
    
    
    	//----------------------------------------------------------------------------------------
     	//-- BAC Safe SETUP ----------------------------------------------------------------------
    	//----------------------------------------------------------------------------------------
    
	    /* 
	     * setHomeScreen
	     * 
	     * @Defn - Load navigation tabs (i.e. Drink Counter, Groups, Friends)
	     */
	    private void setupHomeScreen(){
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
	    private void getUserInfoAndPrefs(){
	    	
	    	//Set Access to internal storage files
	    	userPrefs = getSharedPreferences(userPrefFile, 0);
			userInfo = getSharedPreferences(userInfoFile, 0);		

			//Set UI Variables from saved User Data file
			m_sUserName = userInfo.getString("username", "");
			m_sFirstName = userInfo.getString("firstname", "");
			m_sLastName = userInfo.getString("lastname", "");
			m_nWeight = userInfo.getInt("weight", 0);
			m_nHeightFeet = userInfo.getInt("height_feet", 0);
			m_nHeightInches = userInfo.getInt("height_inches", 0);
			m_nAge = userInfo.getInt("age", 21);
			m_bIsMale = userInfo.getBoolean("male", true);
			
			//Set Preferences from saved User Preferences file
			m_bShowUserAgreementAlert = userPrefs.getBoolean("useragreement", true);
		
	    }//loadProfileData()
	    
	    
	    
	    /*
	     * alertLiabilityAgreement
	     * 
	     * @Defn - Load alert dialog Liability/User Agreement (When BAC Safe is opened) 
	     */
	    public void alertLiabilityAgreement(){
	    	
	        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

	        alert.setTitle(R.string.UserAgreement);	//Set Title
	        
	        //Text View - User Agreement Message
	        TextView textView_UserAgreement = new TextView(this);
	        textView_UserAgreement.setText(R.string.UserAgreementMessage);
	        textView_UserAgreement.setTextSize(16);
	        textView_UserAgreement.setLineSpacing(5, 1);
	        textView_UserAgreement.setPadding(30, 20, 30, 20);
	        
	        //Check Box - Do Not Show Again 
	        final CheckBox checkBox_UserAgreement = new CheckBox (this);
	        checkBox_UserAgreement.setText(R.string.UserAgreementDoNotShow);
	        checkBox_UserAgreement.setTextSize(14);
	        
	        //Set Alert's View
	        ScrollView scrollView = new ScrollView(this); //Need alert context to be scrollable on small screens
	        LinearLayout alertLayout = new LinearLayout(this); //Need Linear Layout to contain more than one View
	        alertLayout.setOrientation(1); //Set View to vertical
	        alertLayout.addView(textView_UserAgreement);
	        alertLayout.addView(checkBox_UserAgreement);
	        scrollView.addView(alertLayout);
	        alert.setView(scrollView);
	        
	        //Accept Button
	        alert.setPositiveButton(R.string.Accept, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	        	
	        	//Save Preference for "Do not show warning again" check box if checked
	        	if(checkBox_UserAgreement.isChecked())
	        	{
	        		userPrefs = getSharedPreferences(userPrefFile, 0);
	        		editor = userPrefs.edit();
	        		editor.putBoolean("useragreement", false);
	        		editor.apply();	
	        	}
	        	
	        	// If a Username DNE, open the Profile Page
	        	if(m_sUserName.isEmpty())	
		        {
	        		Intent profileActivityIntent = new Intent(Main.this, ProfileActivity.class);
	        		profileActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    
	        		Main.this.startActivity(profileActivityIntent);
		        }
	          }
	        });

	        //Decline Button
	        alert.setNegativeButton(R.string.Decline, new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog, int whichButton) {
	            // Close App
	        	  android.os.Process.killProcess(android.os.Process.myPid());             
	        	  System.exit(1);
	          }
	        });
	        
	        alert.show();
	       
	    }//alertLiabilityAgreement()
	    
	    
	/*
	 * ---------------------------------------------------------------------------------------
     * -- DRINK COUNTER Tab ------------------------------------------------------------------
     * ---------------------------------------------------------------------------------------
     */	    
	    
	    /*
	     * drinkCounterTabSetup()
	     * 
	     * @Defn - Set up the UI for the Drink Counter Tab
	     */
	    private void drinkCounterTabSetup(){
	    	
		      //Edit Button (Drink Counter tab) Allows user profile data to be changed
	        Button editButton = (Button)findViewById(R.id.buttonEdit);
	        editButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
		            Intent profileActivityIntent = new Intent(Main.this, ProfileActivity.class);
					startActivity(profileActivityIntent);
				}//onClick()
			}); 
	        
	        //Shot Button
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
	        
	        //Wine Button
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
	        
	        //Beer Button
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
	    
	        //Refresh Button
	        Button refreshButton = (Button)findViewById(R.id.buttonRefresh);
	        refreshButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					generateBAC();
					generateBACTimer();
				}
			});
	    }//drinkCounterTabSetup()
	    
    
	    /*
	     * generateBAC
	     * 
	     * @Defn - 
	     */
	    private void generateBAC(){
	    	dBACpercent = (nBeer) + (nWine*2) + (nShot*3);
	    	sBACpercent = Double.toString(dBACpercent);
	    	tBACpercent = (TextView)findViewById(R.id.labelBACPercent);
	    	tBACpercent.setText(sBACpercent);
	    }//generateBAC()
	    
	    
	    /*
	     * generateNumberDrinks
	     * 
	     * @Defn - 
	     */
	    private void generateNumberDrinks(){
	    	nDrinkTotal = (nBeer) + (nWine*2) + (nShot*3);
	    	sDrinkTotal = Integer.toString(nDrinkTotal);
	    	tDrinkTotal = (TextView)findViewById(R.id.labelDrinkCountNumber);
	    	tDrinkTotal.setText(sDrinkTotal);
	    }//generateNumberDrinks()
	    
	    
	    /*
	     * generateBeerDrinks
	     * 
	     * @Defn - 
	     */
	    private void generateBeerDrinks(){
	    	sBeer = Integer.toString(nBeer);
	    	tBeer = (TextView)findViewById(R.id.labelBeerCount);
	    	tBeer.setText(sBeer);
	    }//generateBeerDrinks
	    
	    
	    /*
	     * generateWineDrinks
	     * 
	     * @Defn - 
	     */
	    private void generateWineDrinks(){
	    	sWine = Integer.toString(nWine);
	    	tWine = (TextView)findViewById(R.id.labelWineCount);
	    	tWine.setText(sWine);
	    }//generateWineDrinks()
	    
	    
	    /*
	     * generateShotDrinks
	     * 
	     * @Defn - 
	     */
	    private void generateShotDrinks(){
	    	sShot = Integer.toString(nShot);
	    	tShot = (TextView)findViewById(R.id.labelShotCount);
	    	tShot.setText(sShot);
	    }//generateShotDrinks()
	    
	    
	    /*
	     * generateBACTimer
	     * 
	     * @Defn - 
	     */
	    private void generateBACTimer(){
	    	tBACtimer = (TextView)findViewById(R.id.labelSoberTime);
	    	
	    	nBACtimerMinute = (nBeer) + (nWine*2) + (nShot*3);
	    	sBACtimerMinute = Integer.toString(nBACtimerMinute);
	
	    	nBACtimerHour = (nBeer) + (nWine*2) + (nShot*3);
	    	sBACtimerHour = Integer.toString(nBACtimerHour);
	    	
	    	tBACtimer.setText(sBACtimerMinute + ":" + sBACtimerHour);
	    }//generageBACTimer()
    
	    
	    
      	//---------------------------------------------------------------------------------------
	    //-- BUDDIES Tab ------------------------------------------------------------------------
	    //---------------------------------------------------------------------------------------	    
	    
	    /*
	     * buddiesTabSetup()
	     * 
	     * @Defn - Set up the UI for the Buddies Tab
	     */
	    public void buddiesTabSetup(){
		   	        
	        //Add Buddy Button (Buddies tab) Allows user to search for / add buddies
	        Button addBuddyButton = (Button)findViewById(R.id.buttonAddBuddy);
	        addBuddyButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//Open Find Buddy Page
					final Intent findBuddyActivity = new Intent(Main.this, FindBuddyActivity.class);
					startActivity(findBuddyActivity); 
				}
			});
	        
	    }//buddiesTabSetup()
	    
	    
      	//---------------------------------------------------------------------------------------
	    //-- GROUPS Tab -------------------------------------------------------------------------
      	//---------------------------------------------------------------------------------------
    
	    /*
	     * groupsTabSetup()
	     * 
	     * @Defn - Set up the UI for the Groups Tab
	     */
	    public void groupsTabSetup(){
	    	
	        //Create Group Button (Groups tab) Allows users to create groups of buddies from their buddies list
	        Button createGroupButton = (Button)findViewById(R.id.buttonCreateGroup);
	        createGroupButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					alertCreateGroupName();
				}//onClick()
			});
	    }//groupsTabSetup()
	    
	    /*
	     * alertCreateGroupName
	     * 
	     * @Defn - Load alert dialog to get group name for a new group  
	     */
	    private void alertCreateGroupName(){
	    	
	        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
	
	        alert.setTitle(R.string.CreateGroup);
	        alert.setMessage(R.string.EnterGroupName);
	
	        // Set an EditText view to get user input 
	        final EditText newGroupName = new EditText(this);
	        alert.setView(newGroupName);
	
	        alert.setPositiveButton(R.string.Next, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	          String sNewGroupName = newGroupName.getText().toString();
	          // Do something with value? or wait till alertSelectGroupBuddies() -> Create Group button?
	          if(!sNewGroupName.isEmpty())
				{
	        	  alertSelectGroupBuddies(sNewGroupName);
				}
	          else alertCreateGroupName();
	          }
	        });
	
	        alert.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog, int whichButton) {
	            // Canceled.
	          }
	        });
	        alert.show();
	       
	    }//alertCreateGroupName()
	    
	    
	    /*
	     * alertSelectGroupBuddies
	     * 
	     * @Defn - Load alert dialog to select buddies for a new group 
	     */
	    private void alertSelectGroupBuddies(String sNewGroupName){
	    	
	        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
	
	        alert.setTitle(getString(R.string.SelectBuddies) + " " + sNewGroupName);
	        	        
	        alert.setMultiChoiceItems(tempBuddyList, null, new DialogInterface.OnMultiChoiceClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					//TODO: Save Selected Buddies to new Group
					
				}
			});
	        
	        alert.setPositiveButton(R.string.CreateGroup, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	        	//TODO: Save Selected Buddies to new Group
	          }
	        });
	
	        alert.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog, int whichButton) {
	            //Cancel
	          }
	        });
	        alert.show();
	       
	    }//alertSelectGroupBuddies()
	    
	    
    
}//class Main
