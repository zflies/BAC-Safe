package com.example.bacsafe;

import java.util.LinkedList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

/**
 * Activity for finding new Buddies to add to Buddies tab in Main Activity.
 * 
 * @author Team BAC Safe
 */
public class FindBuddyActivity extends Activity implements SearchView.OnQueryTextListener{

	private SearchView m_searchView; // Will Search for Buddies
	private ListView m_listView; // Will return results of searched Buddies
	private InputMethodManager keyboard; // Keyboard Access
	private User userProfile; // Access to User Profile Info

	//Temporary String until Database returns usernames
	private final String[] m_TempBuddies = Temp_BuddyDatabase.sCheeseStrings;


	/**
	 * Create the Find Buddy page for user interaction.  
	 * Called when Add Buddy Button is pressed in Main Activity.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_find_buddy); //Show the Find Buddy Screen Activity		

		userProfile = new User();// Retrieve User Info and Prefs through userProfile initialization
		
		// Setup the Page to automatically show the keyboard 
		setupFindBuddyPage();

	} // onCreate()


	/**
	 * Sets up UI for the search page 
	 */
	public void setupFindBuddyPage(){

		// Initialize Keyboard
		keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		// Setup and give focus to SearchView. 
		keyboard.toggleSoftInput(InputMethodManager.SHOW_FORCED,0); //Hide Keyboard
		m_searchView = (SearchView) findViewById(R.id.searchView_Find_Buddy);
		m_searchView.setIconifiedByDefault(false);
		m_searchView.setOnQueryTextListener(this);
		m_searchView.setSubmitButtonEnabled(true); 
		m_searchView.setQueryHint(getText(R.string.SearchUsername));

		// Keyboard Search Button
		m_searchView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				keyboard.toggleSoftInput(InputMethodManager.RESULT_HIDDEN,0); //Hide Keyboard
			}
		});

		// Setup ListView
		m_listView = (ListView) findViewById(R.id.listView_BuddySearchList);
		m_listView.setTextFilterEnabled(true);
		m_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
			{
				alertConfirmAddBuddy(m_listView.getItemAtPosition(position).toString());
			}
		});


		// Done Button - Returns to Main screen (Buddies tab)
		Button doneButton = (Button)findViewById(R.id.button_AddBuddy_Back);
		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			} 
		}); // Done Button

	} // setupFindBuddyPage



	/*
	 * (non-Javadoc)
	 * @see android.widget.SearchView.OnQueryTextListener#onQueryTextChange(java.lang.String)
	 */
	public boolean onQueryTextChange(String newText) {

		if (TextUtils.isEmpty(newText)) {
			// Hide entire list
			m_listView.setVisibility(View.INVISIBLE);
			m_listView.setAdapter(null);
			m_listView.clearTextFilter();
		} 
		else 
		{
			// Show search results after input is 3 characters long
			if(newText.length() > 2){

				m_listView.setAdapter(new ArrayAdapter<String>(FindBuddyActivity.this,
						android.R.layout.simple_list_item_1,
						m_TempBuddies));
				m_listView.setFilterText(newText.toString());
				m_listView.setVisibility(View.VISIBLE);
			}
			else
			{	// hide entire list
				m_listView.setVisibility(View.INVISIBLE);
				m_listView.setAdapter(null); 
			}
		}
		return true;
	} // onQueryTextChange



	/*
	 * onQueryTextSubmit
	 * 
	 * (non-Javadoc)
	 * @see android.widget.SearchView.OnQueryTextListener#onQueryTextSubmit(java.lang.String)
	 */
	public boolean onQueryTextSubmit(String query) {
		keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		keyboard.toggleSoftInput(InputMethodManager.RESULT_HIDDEN,0); //Hide Keyboard

		// TODO: Search database for exact math?
		//
		//		Display alert dialog if no matches are found

		return true;
	} // onQueryTextSubmit()

	/**
	 * Load alert dialog to select buddies for a new group.
	 * @param sSelectedUsername - username of Buddy selected by user from Buddies List in Main Activity
	 */
	private void alertConfirmAddBuddy(final String sSelectedUsername){

		final AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle(getString(R.string.Add) +" " + sSelectedUsername + "?");

		// Text View - User Agreement Message
		TextView tvAddBuddyMessage_1 = new TextView(this);
		TextView tvSelectedUsername = new TextView(this);
		TextView tvAddBuddyMessage_2 = new TextView(this);

		tvAddBuddyMessage_1.setText(R.string.DoYouWishToAdd);
		tvAddBuddyMessage_1.setTextSize(18);
		tvAddBuddyMessage_1.setPadding(30, 20, 30, 10);
		tvAddBuddyMessage_1.setGravity(0x01);

		tvSelectedUsername.setText(sSelectedUsername);
		tvSelectedUsername.setTextSize(18);
		tvSelectedUsername.setPadding(30, 0, 30, 10);
		tvSelectedUsername.setTypeface(null, Typeface.BOLD_ITALIC);
		tvSelectedUsername.setGravity(0x01);

		tvAddBuddyMessage_2.setText(R.string.ToYourBuddies);
		tvAddBuddyMessage_2.setTextSize(18);
		tvAddBuddyMessage_2.setPadding(30, 0, 30, 20);
		tvAddBuddyMessage_2.setGravity(0x01);

		// Set Alert's View
		ScrollView scrollView = new ScrollView(this); // Need alert context to be scrollable on small screens
		LinearLayout alertLayout = new LinearLayout(this); // Need Linear Layout to contain more than one View
		alertLayout.setOrientation(1); // Set View to vertical
		alertLayout.addView(tvAddBuddyMessage_1);
		alertLayout.addView(tvSelectedUsername);
		alertLayout.addView(tvAddBuddyMessage_2);
		scrollView.addView(alertLayout);
		alert.setView(scrollView);

		// Yes Button
		alert.setPositiveButton(R.string.YES, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				// TODO: Request object for selected Buddy from database
				//			Save Selected Buddy object to Buddies List
				Buddy newBuddy = new Buddy(sSelectedUsername);

				// Get User's current Buddies List
				LinkedList<Buddy> listBuddies = new LinkedList<Buddy>();
				listBuddies = userProfile.getBuddies();

				// Add new Buddy to the User's Buddies List
				listBuddies.add(newBuddy);
				userProfile.setBuddies(listBuddies);

				Intent returnIntent = new Intent();
				setResult(RESULT_OK, returnIntent); // Send activity result of OK so Main reloads Buddies List
				finish();
			}
		});

		// NO Button
		alert.setNegativeButton(R.string.NO, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Cancel
			}
		});
		alert.show();

	} // alertConfirmAddBuddy()

} // class FindBuddyActivity
