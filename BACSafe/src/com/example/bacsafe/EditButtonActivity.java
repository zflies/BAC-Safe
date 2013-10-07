package com.example.bacsafe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;


/*	EditButtonActivity Class
 * 	
 * 	@Defn - Controls the Edit Button within the Drink Counter of class Main.
 */
public class EditButtonActivity extends Activity {

	//Creates file for saving User Information to internal storage
	public static String userDataFile = "UserData";
	SharedPreferences userData;
	
	//Declared UI Objects
	Spinner ageSpinner;
	EditText usernameTextField, nameTextField, weightTextField, height_feet_TextField, height_inches_TextField;
	RadioButton maleRadioButton, femaleRadioButton;
	
	//Declared UI Variables
	String sUserName, sName;
	int nWeight, nHeightFeet, nHeightInches, nAge;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_drinkcounter_edit); //Show the Edit Button Screen Activity
		
		//Setup UI Objects
		setupObjectVariables();

		//Setup Saved Data
		userData = getSharedPreferences(userDataFile, 0);
		
		//Load User Data
		loadUserData();
		
	}//onCreate()
	
	private void setupObjectVariables(){
		
		//Setup Text Field objects
		usernameTextField = (EditText)findViewById(R.id.textBox_Username);
		nameTextField = (EditText)findViewById(R.id.textBox_Name);
		weightTextField = (EditText)findViewById(R.id.textBox_Weight);
		height_feet_TextField = (EditText)findViewById(R.id.textBox_Height_Feet);
		height_inches_TextField = (EditText)findViewById(R.id.textBox_Height_Inches);
		
		//Setup Spinner object
		ageSpinner = (Spinner) findViewById(R.id.spinner_Edit_Age);
		addItemsOnAgeSpinner();
		
		//Setup Button objects
		Button backButton = (Button)findViewById(R.id.button_Edit_Back);
		Button saveButton = (Button)findViewById(R.id.button_Edit_Save);

		//Back Button - Returns to Main screen (Drink Counter)
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EditButtonActivity.this, Main.class);
				startActivity(intent);
			}//onClick()
		}); 

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
				//nAge = Integer.parseInt(ageSpinner.toString());
				
				editor.putString("username", sUserName);
				editor.putString("name", sName);
				editor.putInt("weight", nWeight);
				editor.putInt("height_feet", nHeightFeet);
				editor.putInt("height_inches", nHeightInches);
				//editor.putInt("age", nAge);
				
				editor.commit();
				
				//Return to Main screen
				Intent intent = new Intent(EditButtonActivity.this, Main.class); 
				startActivity(intent);	
			}//onClick()
		}); 
		
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
		//nAge = userData.getInt("age", 21);
	
		
		//Set UI Objects from UI Variables
		usernameTextField.setText(sUserName);
		nameTextField.setText(sName);
		weightTextField.setText(Integer.toString(nWeight));
		height_feet_TextField.setText(Integer.toString(nHeightFeet));
		height_inches_TextField.setText(Integer.toString(nHeightInches));
		//ageSpinner.setPromptId(nAge);
		
		
		if(!sUserName.isEmpty()){ //If a Username has been created, do not allow the user to change it
			usernameTextField.setCursorVisible(false);
			usernameTextField.setFocusable(false);
			usernameTextField.setClickable(false);
			nameTextField.requestFocus(); //Set tab focus to the Name Text Box on startup
		}
		else usernameTextField.requestFocus(); //Else set the focus to the username text box on startup
		
	} //loadUserData()
	
}//class EditButtonActivity

