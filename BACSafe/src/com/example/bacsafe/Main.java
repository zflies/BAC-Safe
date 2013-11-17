package com.example.bacsafe;

import java.util.LinkedList;
import java.text.DecimalFormat;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

/**
 * Main activity for user interaction in BAC Safe. Contains Tabs for the main features of this app
 * and is responsible for calling the other activities.
 * 
 * @author Team BAC Safe
 */
@SuppressWarnings("deprecation")
public class Main extends TabActivity {

	private static Context contextOfApplication; // Allows context of Main to be passed to User for SharedPreferences
	
	// User Information instance (Allows access to personal info for calculations)
	private User m_userProfile; 

	// Drink Counter 
	private String m_sDrinkTotal;
	private String m_sBACpercent;
	private String m_sBACtimerMinute;
	private String m_sBACtimerHour;
	private String m_sBeer;
	private String m_sWine;
	private String m_sShot;
	private int m_nShot;
	private int m_nWine;
	private int m_nBeer;
	private int m_nCurrentDrink;
	private int m_nDrinkTotal;
	private int m_nBACtimerMinute;
	private int m_nBACtimerHour;
	private double m_dBACpercent;

	/*
	 * TODO: change name to represent member variables
	 */
	//UI variables - Drink Counter tab
	private TextView m_tDrinkTotal;
	private TextView m_tBACpercent;
	private TextView m_tBeer;
	private TextView m_tWine;
	private TextView m_tShot;
	private TextView m_tBACtimer;
	
	//UI variables - Buddies tab
	private ListView m_listViewBuddies;
	
	//UI variables - Groups tab
	private ListView m_listViewGroups;
	

	//-------------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------------
	//--- IMPLEMENTATION ------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------------

	/**
	 * Create the main page for user interaction.  Called when application starts.
	 * 
	 * @Override onCreate()
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Load the home screen
		setContentView(R.layout.activity_main);
		setupHomeScreen();

		contextOfApplication = getApplicationContext(); // Allows User to access Internal Storage

		m_userProfile = new User(); // Initialization loads User Info/Prefs, Buddies and Groups
		
		// Show User Liability Agreement if needed, otherwise check if the user needs to create a profile
		if(m_userProfile.getShowUserAgreementAlert())
		{
			alertLiabilityAgreement();
		}
		else 
		{	// If the alert does not need to be shown, and a Username DNE, open Profile Page
			if(m_userProfile.getUserName().isEmpty())	
			{
				Intent profileActivityIntent = new Intent(this, ProfileActivity.class);
				startActivityForResult(profileActivityIntent, 1);
			}
		}

		// Setup each tab 
		drinkCounterTabSetup(); // Drink Counter
		buddiesTabSetup();		// Buddies
		groupsTabSetup();		// Groups

	} // onCreate()
	

	/**
	 *  Determines if either the User Information/Preferences, Buddies, and or Groups needs to be reloaded after another activity ends  
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {
			if(resultCode == RESULT_OK){      
				m_userProfile.loadUserInfoPrefs();    	     
			}
		}
		
		if (requestCode == 2) {
			if(resultCode == RESULT_OK){
				m_userProfile.loadBuddies();
				setBuddiesListView();
			}
		}
		
		if (requestCode == 3) {
			if(resultCode == RESULT_OK){
				m_userProfile.loadGroups();
			}
		}
	} // onActivityResult()

	/**
	 *  Returns the context of Main
	 *  
	 * @return contextOfApplication
	 */
	protected static Context getContextOfApplication(){
		return contextOfApplication;
	} // getContextOfApplication
	

	//----------------------------------------------------------------------------------------
	//-- BAC Safe SETUP ----------------------------------------------------------------------
	//----------------------------------------------------------------------------------------

	/**
	 * Setup the home screen and load navigation tabs (Drink Counter, Group, and Buddies).
	 */
	private void setupHomeScreen(){

		// Create the Home Screen navigation tabs
		TabHost tabHost=getTabHost();
		tabHost.setup();

		final TabSpec spec1=tabHost.newTabSpec(getString(R.string.DrinkCounter));	// Tab - Drink Counter
		spec1.setIndicator(getString(R.string.DrinkCounter));
		spec1.setContent(R.id.tabDrinkCounter);

		final TabSpec spec2=tabHost.newTabSpec(getString(R.string.Groups));       	// Tab - Groups List
		spec2.setIndicator(getString(R.string.Groups));
		spec2.setContent(R.id.tabGroups);

		final TabSpec spec3=tabHost.newTabSpec(getString(R.string.Buddies));     	// Tab - Buddies List
		spec3.setIndicator(getString(R.string.Buddies));
		spec3.setContent(R.id.tabBuddies);

		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
		tabHost.addTab(spec3);

		// Setup Tasks for when the tabs are changed.
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {

				// Drink Counter
				if(tabId.equals(spec1.getTag()))
				{
					// Refresh Drink Counter?
				}

				// Groups
				if(tabId.equals(spec2.getTag()))
				{
					// Load Groups?
				}

				// Buddies
				if(tabId.equals(spec3.getTag()))
				{
					setBuddiesListView();
				}
			}
		});

	} // setHomeScreen()
	
	/**
	 *  Loads the Buddies into the proper Text View in the Buddies Tab
	 */
	private void setBuddiesListView(){
		
		// Load Buddies from phone
		LinkedList<Buddy> listBuddies = new LinkedList<Buddy>();
		listBuddies = m_userProfile.getBuddies();
		
		// Place Usernames into String array for display in Buddies List
		String displayBuddies[] = new String[listBuddies.size()];
		for(int i=0; i < listBuddies.size(); i++){
			displayBuddies[i] = listBuddies.get(i).m_sBuddyUsername;
		}

		// Setup ListView
		m_listViewBuddies = (ListView) findViewById(R.id.listView_BuddyList);
		if(displayBuddies.length > 0){
			m_listViewBuddies.setAdapter(new ArrayAdapter<String>(Main.this,
					android.R.layout.simple_list_item_1,
					displayBuddies));	
		}
		
	} // setBuddiesListView()
	
	

	/**
	 * Load alert dialog Liability/User Agreement when BAC Safe is opened (depending on User Preference).
	 */
	private void alertLiabilityAgreement(){

		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setCancelable(false);

		// Set Title
		alert.setTitle(R.string.UserAgreement);	

		// Text View - User Agreement Message
		TextView textView_UserAgreement = new TextView(this);
		textView_UserAgreement.setText(R.string.UserAgreementMessage);
		textView_UserAgreement.setTextSize(16);
		textView_UserAgreement.setLineSpacing(5, 1);
		textView_UserAgreement.setPadding(30, 20, 30, 20);

		// Check Box - Do Not Show Again 
		final CheckBox checkBox_UserAgreement = new CheckBox (this);
		checkBox_UserAgreement.setText(R.string.UserAgreementDoNotShow);
		checkBox_UserAgreement.setTextSize(14);

		// Set Alert's View
		ScrollView scrollView = new ScrollView(this); // Need alert context to be scrollable on small screens
		LinearLayout alertLayout = new LinearLayout(this); // Need Linear Layout to contain more than one View
		alertLayout.setOrientation(1); // Set View to vertical
		alertLayout.addView(textView_UserAgreement);
		alertLayout.addView(checkBox_UserAgreement);
		scrollView.addView(alertLayout);
		alert.setView(scrollView);

		// Accept Button
		alert.setPositiveButton(R.string.Accept, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				// Set Preference 
				if(checkBox_UserAgreement.isChecked())
				{
					m_userProfile.setShowUserAgreementAlert(false);
				}

				// If a Username DNE, open the Profile Page
				if(m_userProfile.getUserName().isEmpty())	
				{
					Intent profileActivityIntent = new Intent(Main.this, ProfileActivity.class);
					profileActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					startActivity(profileActivityIntent);  
				}
			}
		});

		// Decline Button
		alert.setNegativeButton(R.string.Decline, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Close App
				android.os.Process.killProcess(android.os.Process.myPid());             
				System.exit(1);
			}
		});

		alert.show();

	} // alertLiabilityAgreement()


	/*
	 * ---------------------------------------------------------------------------------------
	 * -- DRINK COUNTER Tab ------------------------------------------------------------------
	 * ---------------------------------------------------------------------------------------
	 */	    

	/**
	 * Set up the UI for the Drink Counter Tab
	 */
	private void drinkCounterTabSetup(){

		// Edit Button - Allows user profile data to be changed
		Button editButton = (Button)findViewById(R.id.buttonEdit);
		editButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent profileActivityIntent = new Intent(Main.this, ProfileActivity.class);
				startActivityForResult(profileActivityIntent, 1);
			} 
		}); 

		// Shot Button
		Button shotButton =  (Button)findViewById(R.id.buttonShot);
		shotButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				m_nShot++;
				generateShotDrinks();
				generateNumberDrinks();
				m_nCurrentDrink = 3;
				generateBAC();
				generateBACTimer();
			}
		});

		// Wine Button
		Button wineButton = (Button)findViewById(R.id.buttonWine);
		wineButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				m_nWine++;
				generateWineDrinks();
				generateNumberDrinks();
				m_nCurrentDrink = 2;
				generateBAC();
				generateBACTimer();
			}
		});

		// Beer Button
		Button beerButton = (Button)findViewById(R.id.buttonBeer);
		beerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				m_nBeer++;
				generateBeerDrinks();
				generateNumberDrinks();
				m_nCurrentDrink = 1;
				generateBAC();
				generateBACTimer();
			}
		});

		// Refresh Button
		Button refreshButton = (Button)findViewById(R.id.buttonRefresh);
		refreshButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				generateBAC();
				generateBACTimer();
			}
		});
	} // drinkCounterTabSetup()


	/**
	 * Estimates the User's current BAC percentage
	 */
	private void generateBAC(){
		
		//Initialize Variables
    	double nLitersWater;
    	double nAlcoholInBodyWater;
    	double nGramsPercent;
    	
    	// Convert weight into approximate amount of mililiters of water in users body (1 & 2)	
    	if( m_userProfile.isMale()) {
    		nLitersWater = ( 1000 * ( .58 * ( m_userProfile.getWeight() / 2.2046 ) ) );
    	}
    	else {
    		nLitersWater = ( 1000 * ( .49 * ( m_userProfile.getWeight() / 2.2046 ) ) );
    	}
    	
    	// Calculate the grams of pure alcohol per mililiter of water in the body (3)
    	nAlcoholInBodyWater = ( 23.3603 / nLitersWater );
    	
    	// Calculate grams per 100 mililiters aka Grams Percent (4)
    	nGramsPercent = ( ( .806 * nAlcoholInBodyWater ) * 100 );
    	
    	//Take the grams percent multplied by the amount of pure alcohol recently consumed
    	m_dBACpercent += ( m_nCurrentDrink * .6 ) * nGramsPercent;
    	
    	DecimalFormat df = new DecimalFormat(".##");
    	m_sBACpercent = df.format(m_dBACpercent);
    	m_tBACpercent = (TextView)findViewById(R.id.labelBACPercent);
    	m_tBACpercent.setText(m_sBACpercent);
	} // generateBAC()


	/**
	 * TODO:
	 */
	private void generateNumberDrinks(){
    	m_nDrinkTotal = (m_nBeer) + (m_nWine*2) + (m_nShot*3);
    	m_sDrinkTotal = Integer.toString(m_nDrinkTotal);
    	m_tDrinkTotal = (TextView)findViewById(R.id.labelDrinkCountNumber);
    	m_tDrinkTotal.setText(m_sDrinkTotal);
	} // generateNumberDrinks()


	/**
	 * TODO:
	 */
	private void generateBeerDrinks(){
		m_sBeer = Integer.toString(m_nBeer);
		m_tBeer = (TextView)findViewById(R.id.labelBeerCount);
		m_tBeer.setText(m_sBeer);
	} // generateBeerDrinks


	/**
	 * TODO:
	 */
	private void generateWineDrinks(){
		m_sWine = Integer.toString(m_nWine);
		m_tWine = (TextView)findViewById(R.id.labelWineCount);
		m_tWine.setText(m_sWine);
	} // generateWineDrinks()


	/**
	 * TODO:
	 */
	private void generateShotDrinks(){
    	m_sShot = Integer.toString(m_nShot);
    	m_tShot = (TextView)findViewById(R.id.labelShotCount);
    	m_tShot.setText(m_sShot);
	} // generateShotDrinks()


	/**
	 * Estimates the User's current BAC Time until Sober
	 */
	private void generateBACTimer(){
    	m_tBACtimer = (TextView)findViewById(R.id.labelSoberTime);
    	
    	m_nBACtimerMinute = (m_nBeer) + (m_nWine*2) + (m_nShot*3);
    	m_sBACtimerMinute = Integer.toString(m_nBACtimerMinute);
	} // generageBACTimer()



	/*
	 * ---------------------------------------------------------------------------------------
	 * -- Buddies Tab ------------------------------------------------------------------------
	 * ---------------------------------------------------------------------------------------
	 */	

	/**
	 * Set up the UI for the Buddies Tab
	 */
	private void buddiesTabSetup(){

		// Add Buddy Button (Buddies tab) Allows user to search for / add buddies
		Button addBuddyButton = (Button)findViewById(R.id.buttonAddBuddy);
		addBuddyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Open Find Buddy Page
				final Intent findBuddyActivity = new Intent(Main.this, FindBuddyActivity.class);
				startActivityForResult(findBuddyActivity, 2);  
			}
		});


	} // buddiesTabSetup()



	/*
	 * ---------------------------------------------------------------------------------------
	 * -- GROUPS Tab -------------------------------------------------------------------------
	 * ---------------------------------------------------------------------------------------
	 */	

	/**
	 * Set up the UI for the Groups Tab
	 */
	private void groupsTabSetup(){
		
		setGroupsListView();

		// Create Group Button (Groups tab) Allows users to create groups of buddies from their buddies list
		Button createGroupButton = (Button)findViewById(R.id.buttonCreateGroup);
		createGroupButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				alertCreateGroupName();
			}
		});
	} // groupsTabSetup()
	
	/**
	 * Loads the Groups into the proper Text View in the Groups Tab
	 */
	private void setGroupsListView(){
		
		LinkedList<Group> listGroups = new LinkedList<Group>();
		listGroups = m_userProfile.getGroups();
		
		String[] sGroupNames = new String[listGroups.size()];
		
		for (int i=0; i < listGroups.size(); i++){
			sGroupNames[i] = listGroups.get(i).getGroupName();
		}

		m_listViewGroups = (ListView) findViewById(R.id.listView_Groups);
		m_listViewGroups.setAdapter(new ArrayAdapter<String>(Main.this,
				android.R.layout.simple_list_item_1,
				sGroupNames));
		
		m_listViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
			{
				//m_listViewGroups.getItemAtPosition(position).toString());
				/*
				 * TODO: Open ViewGroupActivity 
				 */
			}
		});
		
	} // setGroupsListView()

	/**
	 * Load alert dialog to get group name for a new group  
	 */
	private void alertCreateGroupName(){

		final AlertDialog.Builder alert = new AlertDialog.Builder(this);

		// Set dialog title and message
		alert.setTitle(R.string.CreateGroup);
		alert.setMessage(R.string.EnterGroupName);

		// Set an EditText view to get user input 
		final EditText newGroupName = new EditText(this);
		alert.setView(newGroupName);

		// Next Button
		alert.setPositiveButton(R.string.Next, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String sNewGroupName = newGroupName.getText().toString();

				// Verify the user inputs a group name
				if(!sNewGroupName.isEmpty())
				{					
					alertSelectGroupBuddies(sNewGroupName);
				}
				else 
				{
					alertCreateGroupName();
				}
			}
		});

		// Cancel Button
		alert.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});
		alert.show();

	} // alertCreateGroupName()


	/**
	 * Load alert dialog to select buddies for a new group 
	 */
	private void alertSelectGroupBuddies(final String sNewGroupName){

		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setCancelable(false);

		// Set dialog title
		alert.setTitle(getString(R.string.SelectBuddies) + " " + sNewGroupName);

		final int nNumBuddies = m_userProfile.getBuddies().size(); // Set Number of Buddies that user has

		final boolean itemsChecked[] = new boolean[nNumBuddies];

		// Set the username of each Buddy into a string array for reference
		final String sBuddies[] = new String[nNumBuddies];
		for(int i=0; i < nNumBuddies; i++)
		{
			sBuddies[i] = m_userProfile.getBuddies().get(i).m_sBuddyUsername;
		}

		// Create Group Button
		alert.setPositiveButton(R.string.CreateGroup, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				LinkedList<Buddy> listGroupBuddies = new LinkedList<Buddy>();
				
				for (int i = 0; i < nNumBuddies; i++) 
				{
					if (itemsChecked[i]) 
					{
						//Add Selected Buddy to Group's Buddy list
						listGroupBuddies.add(new Buddy(sBuddies[i]));
					}
				}
				
				//Create group based on selected Buddies
				Group newGroup = new Group(sNewGroupName);
				newGroup.setGroupBuddies(listGroupBuddies);
				
				//Add the group to User's list of groups
				LinkedList<Group> listGroups = new LinkedList<Group>();
				listGroups = m_userProfile.getGroups();
				listGroups.add(newGroup);
				m_userProfile.setGroups(listGroups);
				
				setGroupsListView(); // Updates the List View
			}
		});

		// Cancel Button
		alert.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});

		// Buddies List for selection
		alert.setMultiChoiceItems(sBuddies , null, new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				// Set which usernames in the list have been selected
				itemsChecked[which]=isChecked;
			}
		});

		alert.show();

	} // alertSelectGroupBuddies()

} // class Main
