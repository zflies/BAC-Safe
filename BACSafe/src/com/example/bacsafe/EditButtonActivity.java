package com.example.bacsafe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class EditButtonActivity extends Activity {

	Spinner ageSpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drinkcounter_edit);
		
		EditText textBox_Name = (EditText)findViewById(R.id.textBox_Name);
		textBox_Name.requestFocus();
		
		ageSpinner = (Spinner) findViewById(R.id.spinner_Edit_Age);
		addItemsOnAgeSpinner();
		    
		
		
		//Back Button - Returns to Main screen (Drink Counter)
		Button backButton = (Button)findViewById(R.id.button_Edit_Back);
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EditButtonActivity.this, Main.class);
				startActivity(intent);
			}
		}); 
		
		//Save Button - Saves new information and recalculates BAC
		Button saveButton = (Button)findViewById(R.id.button_Edit_Save);
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EditButtonActivity.this, Main.class);
				startActivity(intent);
				
				/*	TODO:
				 * 		Save information
				 * 		Recalculate BAC
				 */
			}
		}); 
	}
	
	//Age drop down menu
	public void addItemsOnAgeSpinner() {
		//Add ages 21-100 to menu
		List list = new ArrayList();
	    for(int x=21; x<=100; x++){
	    	list.add(x);
	    }
	    ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, list);
	    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ageSpinner.setAdapter(dataAdapter);
	}
	
	
}
