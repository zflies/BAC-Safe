package com.example.bacsafe;

public class Buddy {
	
	String buddyUsername;
	String buddyFirstName;
	String buddyLastName;
	
	double buddyBAC;
	double buddyTimeUntilSober;
	int buddyTotalDrinkCount;
	
																	
	public Buddy(String username, String firstname, String lastname) //Need additional parameters for accessing Buddy's phone. 
	{
		buddyUsername = username;
		buddyFirstName = firstname;
		buddyLastName = lastname;
		
		//TODO: When created, need to sendBuddyRequest()
	}

	
	public boolean sendGroupRequest(){
		
		return true;
	}
	
	public boolean sendBuddyRequest(){
		
		return true;
	}
	
	public double getBuddyBAC(){
		
		//TODO: Send request to receive Buddy's BAC
		
		return buddyBAC;
	}
	
	public double getBuddyTimeUntilSober(){
		
		//TODO: Send request to receive Buddy's Time Until Sober
		
		return buddyTimeUntilSober;
	}
	
	public double getBuddyTotalDrinkCount(){
		
		//TODO: Send request to receive Buddy's Total Drink Count

		return buddyTotalDrinkCount;	
	}

}//class Buddy
