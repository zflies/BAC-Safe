package com.example.bacsafe.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import android.os.AsyncTask;
import android.widget.TextView;


import com.example.bacsafe.*;

public class Test0 {

	//backend tests
	//adding user
	/*@Test
	public void backuser() {
		assertEquals(true, TestAddUser());
	}*/

	//adding buddy
	/*@Test
	public void backbuddy() throws InterruptedException, ExecutionException{
		BacsafeAPI.Builder builder = new BacsafeAPI.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
		BacsafeAPI service = builder.build();
		String log = "";
		try {
			log = service.userinfo().createBuddies(info[0]).execute().getMessage();
		} catch(IOException e) {
			e.printStackTrace();
		}

		assertEquals(true, log.compareTo("Success") == 0);
	}

	//adding group
	@Test
	public void backgroup() throws InterruptedException, ExecutionException{
		Group group = new Group("TestGroup");
		group.addGroupMember(new Buddy("TestUser"));
		group.addGroupMember(new Buddy("TestUserBuddy"));
		createGroup(group);
		LinkedList<String> members = new LinkedList<String>();
		members.add("TestUser");
		members.add("TestUserBuddy");
		
		assertEquals(members, getGroupDrinkers("TestGroup"));
	}*/



	//main tests
	@Test
	public void BAC() {
		assertEquals(.10, TestgenerateBAC(1, 2, 0, true, 130, 1), .01);
	}
	
	@Test
	public void timer(){
		assertEquals(496, TestgenerateBACTimer(.1), .1);
	}

	@Test
	public void number(){
		//Main tester = new Main();
		assertEquals(3, TestgenerateNumberDrinks(1, 2, 0), 0);

	}
	

	
	
	

	//main tests
	public int TestgenerateNumberDrinks(int beer, int wine, int shot){

		int nDrinkTotal = (beer) + (wine) + (shot);
		return nDrinkTotal;
	}

	//Testing classes
	public double TestgenerateBAC(int beer, int wine, int shot, boolean isMale, int weight, int nCurrentDrink){

		//TestgenerateNumberDrinks(beer, wine, shot);

		return generateBAC(isMale, weight, nCurrentDrink,TestgenerateNumberDrinks(beer, wine, shot) );

	}
	
	public double TestgenerateBACTimer(double bac){

		int m_nBACtimerMinute = 0;

		double i = bac;
		while( i > 0){
			//On average the body can process .012 per hour or .0002 a minute
			i = i - .0002;
			m_nBACtimerMinute++;
		}
		return m_nBACtimerMinute++;
		}

	/**
	 * Estimates the User's current BAC percentage
	 */
	private double generateBAC(boolean isMale, int weight, int nCurrentDrink, int nNumDrinks){

		//Initialize Variables
		double nLitersWater;
		double nAlcoholInBodyWater;
		double nGramsPercent;

		double m_dBACpercent = 0;

		for(int i=0; i< nNumDrinks; i++){
			// Convert weight into approximate amount of mililiters of water in users body (1 & 2)        
			if( isMale) {
				nLitersWater = ( 1000 * ( .58 * ( weight / 2.2046 ) ) );
			}
			else {
				nLitersWater = ( 1000 * ( .49 * ( weight / 2.2046 ) ) );
			}

			// Calculate the grams of pure alcohol per mililiter of water in the body (3)
			nAlcoholInBodyWater = ( 23.3603 / nLitersWater );

			// Calculate grams per 100 mililiters aka Grams Percent (4)
			nGramsPercent = ( ( .806 * nAlcoholInBodyWater ) * 100 );

			//Take the grams percent multplied by the amount of pure alcohol recently consumed
			m_dBACpercent += ( nCurrentDrink * .6 ) * nGramsPercent;
		}
		return m_dBACpercent;
	} // generateBAC()



	/*
		@Test
		public void reset(){
			Main tester = new Main();
			assertEquals(0, tester.TestresetAllDrinkValues(), .1);
		}
	 */
	/*
		//user tests	
		@Test
		public void Age() throws InterruptedException, ExecutionException{
			User tester = new User();
			try {
				assertEquals(21, tester.TestAge(21), 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Test
		public void FirstName() throws InterruptedException, ExecutionException{
			User tester = new User();
			try {
				assertEquals("Name", tester.TestFName("Name"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Test
		public void HeightFeet() throws InterruptedException, ExecutionException{
			User tester = new User();
			try {
				assertEquals(6, tester.TestHeight(6), 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Test
		public void HeighInches() throws InterruptedException, ExecutionException{
			User tester = new User();
			try {
				assertEquals(0, tester.TestInches(0));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Test
		public void LastName() throws InterruptedException, ExecutionException{
			User tester = new User();
			try {
				assertEquals("Name", tester.TestLName("Name"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Test
		public void UserName() throws InterruptedException, ExecutionException{
			User tester = new User();
			try {
				assertEquals("Name", tester.Testusername("Name"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Test
		public void Weight() throws InterruptedException, ExecutionException{
			User tester = new User();
			try {
				assertEquals(130, tester.TestWeight(130), 0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Test
		public void isMale() throws InterruptedException, ExecutionException{
			User tester = new User();
			try {
				assertEquals(true, tester.TestisMale(true));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 */
	
	//Tests Backend
	/*public boolean TestAddUser(){
		String log = "";
		try {
			log = userAccountSetup("testUserName", "Tester", "McTester");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		if(log.compareTo("Success") == 0) {
			return true;
		}else return false;
	}*/
}
