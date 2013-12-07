package com.example.bacsafe.Test;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * UnitTesting provides the JUnit Tests for BAC Safe.
 * Debugged and Tested by Team BAC Safe
 * @author Team BAC Safe
 */
public class UnitTesting {

	//main tests
	//BAC
	@Test
	public void BAC() {
		assertEquals(.10, TestgenerateBAC(1, 2, 0, true, 130, 1), .01);
	}
	
	//BAC Timer
	@Test
	public void timer(){
		assertEquals(500, TestgenerateBACTimer(.1), 1);
	}

	//Number of Drinks
	@Test
	public void number(){
		//Main tester = new Main();
		assertEquals(3, TestgenerateNumberDrinks(1, 2, 0), 0);

	}
	
	//Reset values
	@Test
	public void reset(){
		assertEquals(0, resetAllDrinkValues(2, 1, 0, 1, .1), .1);
	}
	
	//Beer
	@Test
	public void beer(){
		assertEquals(4, generateBeerDrinks(4), 0);
	}
	//Wine
	@Test
	public void wine(){
		assertEquals(2, generateWineDrinks(2), 0);
	}
	//Shot
	@Test
	public void shot(){
		assertEquals(3, generateBeerDrinks(3),0);
			
	}

	//user tests -- all the information in the profile user cases, (both initial and editing)	
	@Test
	public void Age(){
		setAge(30);
		assertEquals(30, getAge(), 0);
	}

	@Test
	public void FirstName(){
		setFirstName("Name");
		assertEquals("Name", getFirstName());
	}

	@Test
	public void HeightFeet(){
		setHeightFeet(6);
		assertEquals(6, getHeightFeet(), 0);
	}

	@Test
	public void HeighInches(){
		setHeightInches(4);
		assertEquals(4, getHeightInches(),0);
	}

	@Test
	public void LastName(){
		setLastName("Name");
		assertEquals("Name", getLastName());
	}

	@Test
	public void Weight(){
		setWeight(150);
		assertEquals(150, getWeight(),0);
	}

	@Test
	public void yesMale(){
		setMale(true);
		assertEquals(true, isMale());
	}
	
	//backend/integration
	@Test
	public void UserName(){
		setUserName("Name");
		assertEquals("Name", getUserName());
	}
	
	@Test
	public void buddy(){
		setBuddy("buddy");
		assertEquals(true, getBuddy("buddy"));
		
	}
	
	@Test
	public void group(){
		setGroup("group");
		assertEquals(true, getGroup("group"));
	}
 
	
	
	

	//main tests
	public int TestgenerateNumberDrinks(int beer, int wine, int shot){

		int nDrinkTotal = (beer) + (wine) + (shot);
		return nDrinkTotal;
	}

	public double TestgenerateBAC(int beer, int wine, int shot, boolean isMale, int weight, int nCurrentDrink){

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

	private double resetAllDrinkValues(int beer, int wine, int shot, int current, double BACpercent){
		beer = 0;
		wine = 0; 
		shot = 0; 
		current = 0;
		BACpercent = 0;

		return (beer+wine+shot+current+BACpercent);
	}
	
	private int generateBeerDrinks(int num){
		return num;
	} // generateBeerDrinks
	
	private int generateWineDrinks(int num){
		return num;
	} // generateBeerDrinks
	
	private int generateShotDrinks(int num){
		return num;
	} // generateBeerDrinks
	
	int nage;
	protected int getAge() {
		return nage;
	} // getAge()

	protected void setAge(int nAge) {
		nage = nAge;
	} // setAge()
	
	String fname;
	protected String getFirstName() {
		return fname;
	} // getFirsttName()

	protected void setFirstName(String sFirstName) {

		//TODO: Save user's first name to database

		fname = sFirstName;
	} // setFirstName()
	
	int feet;
	protected int getHeightFeet() {
		return feet;
	} // getHeightFeet()

	protected void setHeightFeet(int nHeightFeet) {
		feet = nHeightFeet;
	} // setHeightFeet()
	
	int inch;
	protected int getHeightInches() {
		return inch;
	} // getHeightInches()

	protected void setHeightInches(int nHeightInches) {
		inch = nHeightInches;
	} // setHeightInches()
	
	String lname;
	protected String getLastName() {
		return lname;
	} // getLastName()

	protected void setLastName(String sLastName) {
		lname = sLastName;
	} // setLastName()

	String uname;
	protected String getUserName() {
		return uname;
	} // setUserName()

	protected void setUserName(String sUserName) {
		uname = sUserName;
	} // setUserName()
	
	int weight;
	protected int getWeight() {
		return weight;
	} // getWeight()

	protected void setWeight(int nWeight) {
		weight = nWeight;
	} // setWeight()
	
	boolean male;
	protected boolean isMale() {
		return male;
	} // isMale()

	protected void setMale(boolean bIsMale) {
		male = bIsMale;
	} // setMale()

	boolean bud;
	public void setBuddy(String str){
		bud = true;
	}
	
	public boolean getBuddy(String str){
		return bud;
	}
	
	boolean grou;
	public void setGroup(String str){
		grou = true;
	}
	
	public boolean getGroup(String str){
		return grou;
	}
}
