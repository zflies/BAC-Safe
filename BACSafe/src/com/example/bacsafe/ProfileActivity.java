package com.example.bacsafe;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class ProfileActivity extends Activity {

	//Creates file for saving User Information to internal storage
	public static String userDataFile = "UserData";
	SharedPreferences userData;
	
	//Declared UI Objects
	TextView titleTextView;
	Spinner ageSpinner;
	EditText usernameTextField, nameTextField, weightTextField, height_feet_TextField, height_inches_TextField;
	RadioButton maleRadioButton, femaleRadioButton;
	Button backButton, saveButton;
	
	//Declared UI Variables
	private String sUserName, sName;
	private int nWeight, nHeightFeet, nHeightInches, nAge;
	private boolean bIsMale;
	
	
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
			nameTextField.requestFocus(); //Set tab focus to the Name Text Box on startup
		}
		else //If the Username needs to be created
		{
			titleTextView.setText("Create Your Profile");
			backButton.setVisibility(8); //Hide the Back button
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
		nameTextField = (EditText)findViewById(R.id.textBox_Name);
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

		
		final Intent mainActivity = new Intent(ProfileActivity.this, Main.class);
		
		//Back Button - Returns to Main screen (Drink Counter)
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				//Return to Main screen
				startActivity(mainActivity);
				
			}//onClick()
		});//backButton

		//Save Button - Saves new information and recalculates BAC
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				SharedPreferences.Editor editor = userData.edit(); //Used to edit the user data file
				
				sUserName = usernameTextField.getText().toString();
				sName = nameTextField.getText().toString();
				nWeight = Integer.parseInt(weightTextField.getText().toString());
				nHeightFeet = Integer.parseInt(height_feet_TextField.getText().toString());
				nHeightInches = Integer.parseInt(height_inches_TextField.getText().toString());
				nAge = Integer.parseInt(ageSpinner.getSelectedItem().toString());
				
				editor.putString("username", sUserName);
				editor.putString("name", sName);
				editor.putBoolean("male", maleRadioButton.isChecked());
				editor.putInt("age", nAge);
				editor.putInt("weight", nWeight);
				editor.putInt("height_feet", nHeightFeet);
				editor.putInt("height_inches", nHeightInches);
					
				editor.commit(); //Saves changes to file
				
				//Return to Main screen
				startActivity(mainActivity);
				
			}//onClick()
		});//saveButton
		
	}//setupObjectVariables()
	
	
	
	
	/*	
	 * addItemsOnAgeSpinner
	 * 
	 * @Defn - Add ages 21-100 to Spinner menu for age selection
	 */
	private void addItemsOnAgeSpinner() {
		List list = new ArrayList();
	    for(int x=21; x<=100; x++){
	    	list.add(x);
	    }
	    ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, list);
	    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ageSpinner.setAdapter(dataAdapter);
	}//addItemsOnAgeSpinner()
	
	
	
	
	/*	
	 * loadUserData
	 * 
	 * @Defn - Loads User Profile information (i.e. weight, height, age, gender, etc.)
	 */
	private void loadUserData(){
		
		userData = getSharedPreferences(userDataFile, 0);
		
		//Set UI Variables from saved file
		sUserName = userData.getString("username", "");
		sName = userData.getString("name", "");
		nWeight = userData.getInt("weight", 0);
		nHeightFeet = userData.getInt("height_feet", 0);
		nHeightInches = userData.getInt("height_inches", 0);
		nAge = userData.getInt("age", 21);
		bIsMale = userData.getBoolean("male", true);
		
		//Set UI Objects from UI Variables
		usernameTextField.setText(sUserName);
		nameTextField.setText(sName);
		maleRadioButton.setChecked(bIsMale);
		femaleRadioButton.setChecked(!bIsMale);
		weightTextField.setText(Integer.toString(nWeight));
		height_feet_TextField.setText(Integer.toString(nHeightFeet));
		height_inches_TextField.setText(Integer.toString(nHeightInches));
		ageSpinner.setSelection(nAge-21);
		

	} //loadUserData()
	
}//class ProfileActivity

