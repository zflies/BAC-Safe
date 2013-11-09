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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class ProfileActivity extends Activity {
	
	//Create file for saving User Information to internal storage 
	private static final String userInfoFile = "BAC Safe User Information";
	SharedPreferences userInfo;
	
	//Declared UI Objects
	TextView titleTextView;
	Spinner ageSpinner;
	EditText usernameTextField;
	EditText firstNameTextField;
	EditText lastNameTextField;
	EditText weightTextField;
	EditText height_feet_TextField;
	EditText height_inches_TextField;
	RadioButton maleRadioButton;
	RadioButton femaleRadioButton;
	Button backButton;
	Button saveButton;
	Button profileDeleteButton;
	
	//User Information variables
	private String m_sUserName;
	private String m_sFirstName;
	private String m_sLastName;
	private int m_nWeight;
	private int m_nHeightFeet;
	private int m_nHeightInches;
	private int m_nAge;
	private boolean m_bIsMale;
	
	//-------------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------------

	/*
	 * @Override onCreate()
	 * 
	 * @Defn - Set up Activity when called
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_profile); //Show the Edit Button Screen Activity
		
		setupObjectVariables();//Setup UI Objects
		loadUserData();//Load User Data
		setUserInfo();//Put User Info into UI Objects
		
		if(!m_sUserName.isEmpty()){ //If a Username has already been created, do not allow the user to change it
			usernameTextField.setCursorVisible(false);
			usernameTextField.setFocusable(false);
			usernameTextField.setClickable(false);
			usernameTextField.setGravity(0x01);
			firstNameTextField.requestFocus(); //Set tab focus to the Name Text Box on startup
		}
		else //If the Username needs to be created
		{
			titleTextView.setText("Create Your Profile");
			backButton.setVisibility(8); //Hide the Back button
			profileDeleteButton.setVisibility(8);
			usernameTextField.requestFocus(); //Set the focus to the Username text box on startup
		}
		
	}//onCreate()
	
	
	/* @Override onBackPressed()
	 * 
	 * Disable the back button if a user profile DNE.
	 */
    @Override
    public void onBackPressed() {
       	if(!m_sUserName.isEmpty())
       	{
    		finish();
    	}
    }//onBackPressed()
	
	
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
		addItemsOm_nAgeSpinner();
		
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
				
				//Form Validation - Check for unique Username
				if(uniqueUsernameValidation())
				{
					//Form Validation - Required fields (Height and Weight)
					if(requiredFieldValidation())
					{	
						saveUserInfo();
					}
				}
				
			}//onClick()
			
		});//saveButton
		
	}//setupObjectVariables()
	
	
	
	
	/*	
	 * addItemsOm_nAgeSpinner
	 * 
	 * @Defn - Add ages 21-100 to Spinner menu for age selection
	 */
	private void addItemsOm_nAgeSpinner() {
		List<Integer> list = new ArrayList<Integer>();
	    for(int x=21; x<=100; x++){
	    	list.add(x);
	    }
	    
	    ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, list);
	    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ageSpinner.setAdapter(dataAdapter);
	    
	}//addItemsOm_nAgeSpinner()
	
	
	
	/*	
	 * loadUserData
	 * 
	 * @Defn - Loads User Profile information (i.e. weight, height, age, gender, etc.)
	 */
	private void loadUserData(){
		
		//Set Access to internal storage files
		userInfo = getSharedPreferences(userInfoFile, 0);
		
		m_sUserName = userInfo.getString("username", "");
		m_sFirstName = userInfo.getString("firstname", "");
		m_sLastName = userInfo.getString("lastname", "");
		m_nWeight = userInfo.getInt("weight", 0);
		m_nHeightFeet = userInfo.getInt("height_feet", 0);
		m_nHeightInches = userInfo.getInt("height_inches", 0);
		m_nAge = userInfo.getInt("age", 21);
		m_bIsMale = userInfo.getBoolean("male", true);
		
	} //loadUserData()
	
	/*
	 * setUserInfo()
	 * 
	 * @Defn - Sets up the UI for the Profile Activity
	 */
	private void setUserInfo(){
		
		//Set UI Objects from UI Variables
		usernameTextField.setText(m_sUserName);
		firstNameTextField.setText(m_sFirstName);
		lastNameTextField.setText(m_sLastName);
		maleRadioButton.setChecked(m_bIsMale);
		femaleRadioButton.setChecked(!m_bIsMale);
		ageSpinner.setSelection(m_nAge-21);
		weightTextField.setText(Integer.toString(m_nWeight));
		height_feet_TextField.setText(Integer.toString(m_nHeightFeet));
		height_inches_TextField.setText(Integer.toString(m_nHeightInches));
		
	}//setUserInfo()
	
	/*
	 * saveUserInfo()
	 * 
	 * @Defn - Saves user information to internal storage
	 */
	private void saveUserInfo(){
		
		userInfo = getSharedPreferences(userInfoFile, 0);
		SharedPreferences.Editor editor = userInfo.edit();
			
		editor.putString("username", usernameTextField.getText().toString());
		editor.putString("firstname", firstNameTextField.getText().toString());
		editor.putString("lastname", lastNameTextField.getText().toString());
		editor.putBoolean("male", maleRadioButton.isChecked());
		editor.putInt("age", Integer.parseInt(ageSpinner.getSelectedItem().toString()));
		editor.putInt("weight", Integer.parseInt(weightTextField.getText().toString()));
		
		if(!height_feet_TextField.getText().toString().isEmpty())
		{
			editor.putInt("height_feet", Integer.parseInt(height_feet_TextField.getText().toString()));
		}
		else 
		{
			editor.putInt("height_feet", 0);
		}
		
		
		if(!height_inches_TextField.getText().toString().isEmpty())
		{
			editor.putInt("height_inches", Integer.parseInt(height_inches_TextField.getText().toString()));
		}
		else 
		{
			editor.putInt("height_inches", 0);
		}
		
		editor.apply(); //Saves changes to file
		  
		finish(); //Return to Main screen		
	}
	
	
	//----------------------------------------------------------------------------------------
 	//-- PROFILE DATA VALIDATION -------------------------------------------------------------
	//----------------------------------------------------------------------------------------
	
	/*
	 * uniqueUsernameValidation()
	 * 
	 * @Defn - Check database for unique Username selected by user
	 * @Return - True if Username is unique
	 */
	private boolean uniqueUsernameValidation(){
		
		//If any validation fails, open keyboard to allow user to re-enter info quickly 
		InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		
		if(usernameTextField.getText().toString().isEmpty())
		{
			alertRequiredFieldsValidation();
			usernameTextField.requestFocus();
			keyboard.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
			return false;
		}
		
		//TODO:  Search DATABASE for unique username
		/*
		 * if( < Username Already Exists > )
		 * {
		 * 		alertUsernameAlreadyExists();
		 * 		return false;
		 * }
		 */
	
		return true; //Username is unique 
		
	}//uniqueUsernameValidation()
	
	
	
	/*
	 * requiredFieldValidation()
	 * 
	 * @Defn - Verify all required user information is entered, with a minimum total height of 3ft (36in.). and minimum weight of 50lbs.
	 * @Return - True if all required fields have been entered and valid height and weight
	 */
	private boolean requiredFieldValidation(){
		
		//If any validation fails, open keyboard to allow user to re-enter info quickly 
		InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		
		String sHeight_Feet = height_feet_TextField.getText().toString();
		String sHeight_Inches = height_inches_TextField.getText().toString();
		String sWeight = weightTextField.getText().toString();
		
		//Form Validation - Required fields (Height)
		if(sHeight_Feet.isEmpty() && sHeight_Inches.isEmpty())
		{	
			alertRequiredFieldsValidation(); //Missing Fields alert
			height_feet_TextField.requestFocus();
			keyboard.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
			return false;
		}
		else
		{
			int heightFeet = 0;
			int heightInches = 0;
			
			if(!sHeight_Feet.isEmpty()){
				heightFeet = Integer.parseInt(sHeight_Feet);
			}
			if(!sHeight_Inches.isEmpty()){
				heightInches = Integer.parseInt(sHeight_Inches);
			}
			
			int totalHeightInches = heightInches + (heightFeet * 12);
			
			if(totalHeightInches < 36)
			{
				alertHeightValidation(); //Invalid Height alert
				height_feet_TextField.requestFocus();
				keyboard.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
				return false;
			}
			else
			{	
				//Form Validation - Required fields (Weight)
				if(sWeight.isEmpty())
				{
					alertRequiredFieldsValidation(); //Missing Fields alert
					weightTextField.requestFocus();
					keyboard.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
					return false;
				}
				else
				{
					int weight = Integer.parseInt(sWeight);

					if(weight < 50)
					{
						alertWeightValidation(); //Invalid Weight alert
						weightTextField.requestFocus();
						keyboard.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
						return false;
					}
				}
			}
		}

		return true; //All checks pass
		
	}//requiredFieldValidation
	
	
	
    /*
     * alertUsernameAlreadyExists()
     * 
     * @Defn - Load alert dialog for missing required fields
     */
    private void alertUsernameAlreadyExists(){
    	
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(R.string.UsernameAlreadyExists);
        alert.setMessage(R.string.PleaseTryAgain);
        
        alert.setPositiveButton(R.string.Okay, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
          }
        });
        alert.show();
       
    }//alertUsernameAlreadyExists()
	
	
    /*
     * alertRequiredFieldsValidation()
     * 
     * @Defn - Load alert dialog for missing required fields
     */
    private void alertRequiredFieldsValidation(){
    	
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(R.string.InvalidEntry);
        alert.setMessage(R.string.MissingRequiredField);
        
        alert.setPositiveButton(R.string.Okay, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
          }
        });
        alert.show();
       
    }//alertRequiredFieldsValidation()
    
    /*
     * alertHeightValidation()
     * 
     * @Defn - Requests total height greater than 3 feet
     */
    private void alertHeightValidation(){
    	
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(R.string.InvalidHeight);
        alert.setMessage(R.string.MinimumHeight);
        
        alert.setPositiveButton(R.string.Okay, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
          }
        });
        alert.show();
    } //alertHeightValidation()
    
    
    /*
     * alertWeightValidation()
     * 
     * @Defn - Requests total Weight greater than 50 lbs.
     */
    private void alertWeightValidation(){
    	
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(R.string.InvalidWeight);
        alert.setMessage(R.string.MinimumWeight);
        
        alert.setPositiveButton(R.string.Okay, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
          }
        });
        alert.show();
    } //alertWeightValidation()

}//class ProfileActivity

