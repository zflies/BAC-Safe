package com.example.bacsafe;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class ProfileActivity extends Activity {

	//Creates file for saving User Information to internal storage
	public static String userDataFile = "UserData";
	public static String userPreferencesFile = "UserPreferences";
	SharedPreferences userData, userPreferences;
	
	//Declared UI Objects
	TextView titleTextView;
	Spinner ageSpinner;
	EditText usernameTextField, firstNameTextField, lastNameTextField, weightTextField, height_feet_TextField, height_inches_TextField;
	RadioButton maleRadioButton, femaleRadioButton;
	Button backButton, saveButton, profileDeleteButton;
	
	//Declared UI Variables
	private String sUserName, sFirstName, sLastName;
	private int nWeight, nHeightFeet, nHeightInches, nAge;
	private boolean bIsMale, bShowUserAgreementAlert;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_profile); //Show the Edit Button Screen Activity
		setupObjectVariables();//Setup UI Objects
		loadUserData();//Load User Data

			
		if(!sUserName.isEmpty()){ //If a Username has already been created, do not allow the user to change it
			usernameTextField.setCursorVisible(false);
			usernameTextField.setFocusable(false);
			usernameTextField.setClickable(false);
			usernameTextField.setGravity(0x01);
			firstNameTextField.requestFocus(); //Set tab focus to the Name Text Box on startup
		}
		else //If the Username needs to be created
		{
			//Display User Agreement if useragreement preference is not set
			if(bShowUserAgreementAlert){
				alertLiabilityAgreement(this, userPreferences);
			}
			
			titleTextView.setText("Create Your Profile");
			backButton.setVisibility(8); //Hide the Back button
			profileDeleteButton.setVisibility(8);
			usernameTextField.requestFocus(); //Else set the focus to the username text box on startup
		}
		
	}//onCreate()
	
	
	
	
	/*
	 * setupObjectVariables()
	 * 
	 * @Defn - Sets up the UI and contains the actions for each (i.e. Save Button)
	 */
	private void setupObjectVariables(){
		
		//Setup Text Field objects
		titleTextView = (TextView)findViewById(R.id.label_Title);
		usernameTextField = (EditText)findViewById(R.id.textBox_Username);
		firstNameTextField = (EditText)findViewById(R.id.textBox_FirstName);
		lastNameTextField = (EditText)findViewById(R.id.textBox_LastName);
		weightTextField = (EditText)findViewById(R.id.textBox_Weight);
		height_feet_TextField = (EditText)findViewById(R.id.textBox_Height_Feet);
		height_inches_TextField = (EditText)findViewById(R.id.textBox_Height_Inches);
		maleRadioButton = (RadioButton)findViewById(R.id.radioGroup_Sex_Male);
		femaleRadioButton = (RadioButton)findViewById(R.id.radioGroup_Sex_Female);
		
		//Setup Spinner object
		ageSpinner = (Spinner) findViewById(R.id.spinner_Edit_Age);
		addItemsOnAgeSpinner();
		
		//Setup Button objects
		backButton = (Button)findViewById(R.id.button_Edit_Back);
		saveButton = (Button)findViewById(R.id.button_Edit_Save);
		profileDeleteButton = (Button)findViewById(R.id.button_Profile_Delete);
		
		//Back Button - Returns to Main screen (Drink Counter)
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}//onClick()
		});//backButton

		//Save Button - Saves new information and recalculates BAC
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!usernameTextField.getText().toString().isEmpty())
				{
				
					SharedPreferences.Editor editor = userData.edit(); //Used to edit the user data file
					
					sUserName = usernameTextField.getText().toString();
					sFirstName = firstNameTextField.getText().toString();
					sLastName = lastNameTextField.getText().toString();
					nWeight = Integer.parseInt(weightTextField.getText().toString());
					nHeightFeet = Integer.parseInt(height_feet_TextField.getText().toString());
					nHeightInches = Integer.parseInt(height_inches_TextField.getText().toString());
					nAge = Integer.parseInt(ageSpinner.getSelectedItem().toString());
					
					editor.putString("username", sUserName);
					editor.putString("firstname", sFirstName);
					editor.putString("lastname", sLastName);
					editor.putBoolean("male", maleRadioButton.isChecked());
					editor.putInt("age", nAge);
					editor.putInt("weight", nWeight);
					editor.putInt("height_feet", nHeightFeet);
					editor.putInt("height_inches", nHeightInches);
						
					editor.apply(); //Saves changes to file
					
					//Return to Main screen
					finish();
				}
				
			}//onClick()
		});//saveButton
		
	}//setupObjectVariables()
	
	
	
	
	/*	
	 * addItemsOnAgeSpinner
	 * 
	 * @Defn - Add ages 21-100 to Spinner menu for age selection
	 */
	private void addItemsOnAgeSpinner() {
		List<Integer> list = new ArrayList<Integer>();
	    for(int x=21; x<=100; x++){
	    	list.add(x);
	    }
	    ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, list);
	    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ageSpinner.setAdapter(dataAdapter);
	}//addItemsOnAgeSpinner()
	
	
	
	
	/*	
	 * loadUserData
	 * 
	 * @Defn - Loads User Profile information (i.e. weight, height, age, gender, etc.)
	 */
	private void loadUserData(){
		
		//Set UI Variables from saved User Data file
		userData = getSharedPreferences(userDataFile, 0);
		sUserName = userData.getString("username", "");
		sFirstName = userData.getString("firstname", "");
		sLastName = userData.getString("lastname", "");
		nWeight = userData.getInt("weight", 0);
		nHeightFeet = userData.getInt("height_feet", 0);
		nHeightInches = userData.getInt("height_inches", 0);
		nAge = userData.getInt("age", 21);
		bIsMale = userData.getBoolean("male", true);
		
		//Set Preferences from saved User Preferences file
		userPreferences = getSharedPreferences(userPreferencesFile, 0);
		bShowUserAgreementAlert = userPreferences.getBoolean("useragreement", true);
		
		//Set UI Objects from UI Variables
		usernameTextField.setText(sUserName);
		firstNameTextField.setText(sFirstName);
		lastNameTextField.setText(sLastName);
		maleRadioButton.setChecked(bIsMale);
		femaleRadioButton.setChecked(!bIsMale);
		weightTextField.setText(Integer.toString(nWeight));
		height_feet_TextField.setText(Integer.toString(nHeightFeet));
		height_inches_TextField.setText(Integer.toString(nHeightInches));
		ageSpinner.setSelection(nAge-21);
		

	} //loadUserData()
	
    /*
     * alertLiabilityAgreement
     * 
     * @Defn - Load alert dialog Liability/User Agreement (When BAC Safe is opened) 
     */
    public void alertLiabilityAgreement(Context context, final SharedPreferences sharedUserPreferences){
    	
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle(R.string.UserAgreement);
        alert.setMessage(R.string.UserAgreementMessage);
        	
        final CheckBox checkBox_UserAgreement = new CheckBox (context);
        checkBox_UserAgreement.setText("Do not show this warning again.");
        checkBox_UserAgreement.setTextSize(14);
        alert.setView(checkBox_UserAgreement);

        //Accept Button
        alert.setPositiveButton(R.string.Accept, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
        	
        	//Save Preference for "Do not show warning again" check box if checked
        	if(checkBox_UserAgreement.isChecked())
        	{
	        	SharedPreferences.Editor preferences = sharedUserPreferences.edit(); //Used to edit the user data file
	        	preferences.putBoolean("useragreement", false);
	        	preferences.apply();
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
    
	
}//class ProfileActivity

