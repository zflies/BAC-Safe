package com.example.bacsafe.Test;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;

import org.junit.*;

import com.example.bacsafe.Main;
import com.example.bacsafe.User;

public class Test0 {
	/*@Before
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
	private double m_dBACpercent;*/
	/*	public void TestgenerateBAC(int beer, int wine, int shot){
	m_nShot = shot;
	m_nWine = wine;
	m_nBeer = beer;
	generateNumberDrinks();
	m_userProfile.isMale = true;
	m_userProfile.setWeight(130);
	
	generateBAC();
	
	}
	public void TestgenerateBACTimer(double bac){
		m_dBACpercent = bac;
		
		generateBACTimer();
		return(m_nBACtimerMinute + (m_nBACtimerHour*60));
	
		
	}
	
	public void TestgenerateBeerDrinks(){
		
	}
	public void TestgenerateNumberDrinks(){
		
	}
	
	public void TestgenerateShotDrinks(){
		
	}
	
	public void TestgenerateWineDrinks(){
		
	}
	
	public void TestresetAllDrinkValues(){
		
	}*/
	//main tests
		@Test
		public void BAC() {
			Main tester = new Main();
			assertEquals(.10, tester.TestgenerateBAC(1, 1, 0, 130), .01);
		}
		
		@Test
		public void timer(){
			Main tester = new Main();
			assertEquals(496, tester.TestgenerateBACTimer(.1), .1);
		}
		
		
		@Test
		public void number(){
			Main tester = new Main();
			assertEquals(3, tester.TestgenerateNumberDrinks(1, 2, 0), 0);
			
		}
		
		/*
		@Test
		public void reset(){
			Main tester = new Main();
			assertEquals(0, tester.TestresetAllDrinkValues(), .1);
		}
		*/
		
		//user tests	
		@Test
		public void Age(){
			User tester = new User();
			assertEquals(21, tester.TestAge(21), 0);
		}
		
		@Test
		public void FirstName(){
			User tester = new User();
			assertEquals("Name", tester.TestFName("Name"));
		}
		
		@Test
		public void HeightFeet(){
			User tester = new User();
			assertEquals(6, tester.TestHeight(6), 0);
		}
		
		@Test
		public void HeighInches(){
			User tester = new User();
			assertEquals(0, tester.TestInches(0));
		}
		
		@Test
		public void LastName(){
			User tester = new User();
			assertEquals("Name", tester.TestLName("Name"));
		}
		
		@Test
		public void UserName(){
			User tester = new User();
			assertEquals("Name", tester.Testusername("Name"));
		}
		
		@Test
		public void Weight(){
			User tester = new User();
			assertEquals(130, tester.TestWeight(130), 0);
		}
		
		@Test
		public void isMale(){
			User tester = new User();
			assertEquals(true, tester.TestisMale(true));
		}

	}
