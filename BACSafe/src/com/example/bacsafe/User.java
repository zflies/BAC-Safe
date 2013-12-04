package com.example.bacsafe;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * User Object for storing private User Information
 * 
 * @author Team BAC Safe
 */
public class User{

        // Create file for saving User App Preferences
        private final String userPrefFile = "BAC Safe User Preferences";                
        private SharedPreferences pref_userPrefs;
        // Create file for saving User Information
        private final String userInfoFile = "BAC Safe User Information";
        private SharedPreferences pref_userInfo;
        
        // Create file for saving User Buddies List
        /*
        private final String userBuddiesList = "BAC Safe User Buddies List";
        private SharedPreferences pref_buddiesList;
        
        //Create file for saving User Groups List
        private final String userGroupsList = "BAC Safe User Groups List";
        private SharedPreferences pref_groupsList;
        */
        
        // Create Preference Editor
        private SharedPreferences.Editor prefEditor;

        // User Information variables
        private String m_sUserName; 
        private String m_sFirstName; 
        private String m_sLastName;
        private int m_nWeight;
        private int m_nHeightFeet; 
        private int m_nHeightInches;
        private int m_nAge;
        private boolean m_bIsMale;
        
        // Drink Counter
        private long m_lSoberCounter;
        private double m_dBACpercent;
        
        // Buddies
        private LinkedList<Buddy> m_listBuddies;
        
        // Groups
        private LinkedList<Group> m_listGroups;

        // User Preferences variables
        private boolean m_bShowUserAgreementAlert;
        private Context context;
		// User Information variables
		//private String m_sUserName;


        /**
         * Constructor
         * @throws ExecutionException 
         * @throws InterruptedException 
         */
        public User() throws InterruptedException, ExecutionException 
        {
                context = Main.getContextOfApplication();

                
                loadUserInfoPrefs();
                
                if(m_sUserName!=""){
                        loadBuddies();
                        loadGroups();
                }

        } // Constructor


        /**
         * Loads the User's Information and Stored Preferences
         */
        protected void loadUserInfoPrefs(){
                
                //TODO: Load User Info and Prefs from database
                //                Delete code beneath

                // Set Access to internal storage files
                pref_userPrefs = context.getSharedPreferences(userPrefFile, 0);
                pref_userInfo = context.getSharedPreferences(userInfoFile, 0);                

                // Set UI Variables from saved User Data file
                m_sUserName = pref_userInfo.getString("username", "");
                m_sFirstName = pref_userInfo.getString("firstname", "");
                m_sLastName = pref_userInfo.getString("lastname", "");
                m_nWeight = pref_userInfo.getInt("weight", 0);
                m_nHeightFeet = pref_userInfo.getInt("height_feet", 0);
                m_nHeightInches = pref_userInfo.getInt("height_inches", 0);
                m_nAge = pref_userInfo.getInt("age", 21);
                m_bIsMale = pref_userInfo.getBoolean("male", true);

                // Set Preferences from saved User Preferences file
                m_bShowUserAgreementAlert = pref_userPrefs.getBoolean("useragreement", true);
                
                m_lSoberCounter = pref_userPrefs.getLong("sobercounter", 0); // Can 0 be used as a default value?
                
                Long tempBACpercent = pref_userPrefs.getLong("bacpercent", 0);
                m_dBACpercent = tempBACpercent.doubleValue();

        } // loadUserInfoPrefs()
        
        
        /**
         * Loads the Buddies list for the user
         */
        protected void loadBuddies() throws InterruptedException, ExecutionException{
    		
        	ServerAPI connection = new ServerAPI();
    		String[] buddies = null;
    		LinkedList<Buddy> buddyList = new LinkedList<Buddy>();

    		buddies = connection.getUserBuddies(m_sUserName);

    		if(buddies != null){
    			for(int i = 0; i < buddies.length; i++) {
	    			String[] buddyInfo = connection.getUserBuddiesInfo(buddies[i]);
	    			Buddy bud = new Buddy(buddies[i]);
	    			bud.m_sBuddyFirstName = buddyInfo[0];
	    			bud.m_sBuddyLastName = buddyInfo[1];
	    			bud.m_dBuddyBAC = Double.parseDouble(buddyInfo[2]); //set m_dBuddyBAC to not be private
	    			bud.m_nBuddyTotalDrinkCount = Integer.parseInt(buddyInfo[3]); //set m_nBuddyTotalDrinkCount to not be private
	    			buddyList.add(bud);
    			}
    		}
    		
    		m_listBuddies = buddyList;
    } // loadBuddies()
       
        
        
        
        /**
         * Loads the Groups list for the User
         */
        protected void loadGroups() throws InterruptedException, ExecutionException{
    		/*
        	ServerAPI connection = new ServerAPI();
            
            String[] groups = null;
            LinkedList<Group> groupBuddyList = new LinkedList<Group>();
            
            groups = connection.getUserGroups(m_sUserName);
            
            if(groups != null) {
            	for(int i = 0; i < groups.length; i++) {
            		Group g = new Group(groups[i]);
            		LinkedList<String> buddiesList = connection.getGroupDrinkers(groups[i]);
            		for(int j = 0; j < buddiesList.size(); j++) {
            			String[] buddies = new String[1];
            			LinkedList<Buddy> buddyList = new LinkedList<Buddy>();                        
            			buddies = connection.getUserBuddies(buddiesList.get(i));
            			for(int k = 0; k < buddies.length; k++) {
            				String[] buddyInfo = connection.getUserBuddiesInfo(buddies[i]);
            				Buddy bud = new Buddy(buddies[i]);
            				bud.m_sBuddyFirstName = buddyInfo[0];
            				bud.m_sBuddyLastName = buddyInfo[1];
            				bud.m_dBuddyBAC = Double.parseDouble(buddyInfo[2]); //set m_dBuddyBAC to not be private
            				bud.m_nBuddyTotalDrinkCount = Integer.parseInt(buddyInfo[3]); //set m_nBuddyTotalDrinkCount to not be private
            				buddyList.add(bud);
            			}
            			g.setGroupBuddies(buddyList);
            		} 
            		groupBuddyList.add(g);
            	}
            }

            m_listGroups = groupBuddyList;
        
        	
        	ServerAPI connection = new ServerAPI();
        	
        	String[] groups = null;
        	LinkedList<Group> groupList = new LinkedList<Group>();
        	
        	groups = connection.getUserGroups(m_sUserName);
        	
        	if(groups != null) {
        		for(int i = 0; i < groups.length; i++) {
        			Group g = new Group(groups[i]);
        			LinkedList<String> buddiesList = connection.getGroupDrinkers(groups[i]);
        			LinkedList<Buddy> buddyList = new LinkedList<Buddy>();
        			for(int k = 0; k < buddiesList.size(); k++) {
        				String[] buddyInfo = connection.getUserBuddiesInfo(buddiesList.get(k));
        				Buddy bud = new Buddy(buddiesList.get(k));
        				bud.m_sBuddyFirstName = buddyInfo[0];
        				bud.m_sBuddyLastName = buddyInfo[1];
        				bud.m_dBuddyBAC = Double.parseDouble(buddyInfo[2]); //set m_dBuddyBAC to not be private
        				bud.m_nBuddyTotalDrinkCount = Integer.parseInt(buddyInfo[3]); //set m_nBuddyTotalDrinkCount to not be private
        				buddyList.add(bud);
        			}
        			g.m_listGroupBuddies = buddyList;
        			groupList.add(g);
        		} 		
        	} */
        	ServerAPI connection = new ServerAPI();
        	
        	String[] groups = null;
        	LinkedList<Group> groupList = new LinkedList<Group>(); 	
        	groups = connection.getUserGroups(m_sUserName);
        	if(groups != null) {
        		for(int i = 0; i < groups.length; i++) {
        			Group g = new Group(groups[i]);
        			LinkedList<String> buddiesGroupList = connection.getGroupDrinkers(groups[i]);
        			LinkedList<Buddy> buddyList = new LinkedList<Buddy>();
        			for(int j = 0; j < buddiesGroupList.size(); j++) {
        				String[] buddyInfo = connection.getUserBuddiesInfo(buddiesGroupList.get(j));
        				Buddy bud = new Buddy(buddiesGroupList.get(j));
        				bud.m_sBuddyFirstName = buddyInfo[0];
        				bud.m_sBuddyLastName = buddyInfo[1];
        				bud.m_dBuddyBAC = Double.parseDouble(buddyInfo[2]); //set m_dBuddyBAC to not be private
        				bud.m_nBuddyTotalDrinkCount = Integer.parseInt(buddyInfo[3]); //set m_nBuddyTotalDrinkCount to not be private
        				buddyList.add(bud);
        			}
        			g.m_listGroupBuddies = buddyList;
        			groupList.add(g);
        		}
        		m_listGroups = groupList;
        	}
        } /*
        	String[] groups = null;
        	LinkedList<Group> groupList = new LinkedList<Group>();
        	LinkedList<Buddy> groupBuddyList = new LinkedList<Buddy>();
        	groups = connection.getUserGroups(m_sUserName);
        	if(groups != null) {
        		for(int i = 0; i < groups.length; i++) {
        			Group g = new Group(groups[i]);
        			groupBuddyList. = connection.getGroupDrinkers(groups[i]);
        		}
        	}
        	m_listGroups
    }  // loadGroups() */



        /**
         * Deletes the user's profile from the database and phone
         */
        protected void deleteUserProfile(){
                
                // TODO: Delete User from Database

                pref_userInfo = context.getSharedPreferences(userInfoFile, 0);
                pref_userPrefs = context.getSharedPreferences(userPrefFile, 0);

                // Delete User Information (i.e. Username, Height, Weight, Gender, etc.)
                prefEditor = pref_userInfo.edit().clear();
                prefEditor.apply();

                // Delete User Preferences
                prefEditor = pref_userPrefs.edit().clear();
                prefEditor.apply();

        } // deleteUserProfile()


        /**
         * Returns the User's username
         * @return m_sUserName
         */
        protected String getUserName() {
                return m_sUserName;
        } // setUserName()


        /**
         * Sets the User's username
         * @param m_sUserName
         */
        protected void setUserName(String sUserName) {
                
                //TODO: Save user's username to database

                m_sUserName = sUserName;

                pref_userInfo = context.getSharedPreferences(userInfoFile, 0);
                prefEditor = pref_userInfo.edit();
                prefEditor.putString("username", sUserName);
                prefEditor.apply();

        } // setUserName()


        /**
         * Returns the User's first name
         * @return m_sFirstName
         */
        protected String getFirstName() {
                return m_sFirstName;
        } // getFirsttName()


        /**
         * Sets the User's first name
         * @param m_sFirstName
         */
        protected void setFirstName(String sFirstName) {
                
                //TODO: Save user's first name to database

                m_sFirstName = sFirstName;

                pref_userInfo = context.getSharedPreferences(userInfoFile, 0);
                prefEditor = pref_userInfo.edit();
                prefEditor.putString("firstname", sFirstName);
                prefEditor.apply();
        } // setFirstName()


        /**
         * Returns the User's last name
         * @return m_sLastName
         */
        protected String getLastName() {
                return m_sLastName;
        } // getLastName()


        /**
         * Sets the User's last name
         * @param m_sLastName
         */
        protected void setLastName(String sLastName) {
                
                //TODO: Save user's last name to database

                m_sLastName = sLastName;

                pref_userInfo = context.getSharedPreferences(userInfoFile, 0);
                prefEditor = pref_userInfo.edit();
                prefEditor.putString("lastname", sLastName);
                prefEditor.apply();
        } // setLastName()


        /**
         * Returns the User's weight
         * @return m_nWeight
         */
        protected int getWeight() {
                return m_nWeight;
        } // getWeight()


        /**
         * Sets the User's weight
         * @param m_nWeight
         */
        protected void setWeight(int nWeight) {
                
                //TODO: Save user's weight to database


                m_nWeight = nWeight;

                pref_userInfo = context.getSharedPreferences(userInfoFile, 0);
                prefEditor = pref_userInfo.edit();
                prefEditor.putInt("weight", nWeight);
                prefEditor.apply();

        } // setWeight()


        /**
         * Returns the User's Height in unit feet
         * @return m_nHeightFeet
         */
        protected int getHeightFeet() {
                return m_nHeightFeet;
        } // getHeightFeet()


        /**
         * Sets the User's Height in unit feet
         * @param m_nHeightFeet
         */
        protected void setHeightFeet(int nHeightFeet) {
                
                //TODO: Save user's Height to database

                m_nHeightFeet = nHeightFeet;

                pref_userInfo = context.getSharedPreferences(userInfoFile, 0);
                prefEditor = pref_userInfo.edit();
                prefEditor.putInt("height_feet", nHeightFeet);
                prefEditor.apply();

        } // setHeightFeet()


        /**
         * Returns the User's Height in unit inches
         * @return m_nHeightInches
         */
        protected int getHeightInches() {
                return m_nHeightInches;
        } // getHeightInches()


        /**
         * Returns the User's Height in unit inches
         * @param m_nHeightInches 
         */
        protected void setHeightInches(int nHeightInches) {
                
                //TODO: Save user's Height to database

                m_nHeightInches = nHeightInches;

                pref_userInfo = context.getSharedPreferences(userInfoFile, 0);
                prefEditor = pref_userInfo.edit();
                prefEditor.putInt("height_inches", nHeightInches);
                prefEditor.apply();
        } // setHeightInches()


        /**
         * Returns the User's Age
         * @return m_nAge
         */
        protected int getAge() {
                return m_nAge;
        } // getAge()


        /**
         * Sets the User's Age
         * @param m_nAge
         */
        protected void setAge(int nAge) {
                
                //TODO: Save user's age to database


                m_nAge = nAge;

                pref_userInfo = context.getSharedPreferences(userInfoFile, 0);
                prefEditor = pref_userInfo.edit();
                prefEditor.putInt("age", nAge);
                prefEditor.apply();

        } // setAge()


        /**
         * Returns true if the user is a male
         * @return m_bIsMale
         */
        protected boolean isMale() {
                return m_bIsMale;
        } // isMale()


        /**
         * Sets true if the User is a male
         * @param m_bIsMale
         */
        protected void setMale(boolean bIsMale) {
                
                //TODO: Save user's gender to database

                m_bIsMale = bIsMale;

                pref_userInfo = context.getSharedPreferences(userInfoFile, 0);
                prefEditor = pref_userInfo.edit();
                prefEditor.putBoolean("male", bIsMale);
                prefEditor.apply();

        } // setMale()


        /**
         * Returns true if the User Liability Agreement Alert needs to be shown
         * @return m_bShowUserAgreementAlert
         */
        protected boolean getShowUserAgreementAlert() {
                return m_bShowUserAgreementAlert;
        } // getShowUserAgreementAlert()


        /**
         * Sets true if the User Liability Agreement Alert needs to be shown
         * @param m_bShowUserAgreementAlert the m_bShowUserAgreementAlert to set
         */
        protected void setShowUserAgreementAlert(boolean bShowUserAgreementAlert) {
                
                //TODO: Save user's preference to database

                m_bShowUserAgreementAlert = bShowUserAgreementAlert;

                pref_userPrefs = context.getSharedPreferences(userPrefFile, 0);
                prefEditor = pref_userPrefs.edit();
                prefEditor.putBoolean("useragreement", bShowUserAgreementAlert);
                prefEditor.apply();
        } // setShowUserAgreementAlert()
        
        /**
         * Returns the Sober Counter time stamp for the Drink Counter
         */
        protected long getSoberCounter(){
                return m_lSoberCounter;
        } // getSoberCounter()
        
        /**
         * Sets the Sober Counter time stamp value from the Drink Counter
         * @param lSoberCounter - time stamp value
         */
        protected void setSoberCounter(long lSoberCounter){
                
                m_lSoberCounter = lSoberCounter;
                
                pref_userPrefs = context.getSharedPreferences(userPrefFile, 0);
                prefEditor = pref_userPrefs.edit();
                prefEditor.putLong("sobercounter", lSoberCounter);
                prefEditor.apply();
        } // setSoberCounter()
        

        /**
         * Saves the BAC Percent value from the Drink Counter to database
         * @param dBACpercent - BAC percent value
         */
        public void setBACpercent(double dBACpercent) throws InterruptedException, ExecutionException{
                               
                /*
                 * TODO: Save BAC to Server
                 */
                
        	ServerAPI connection = new ServerAPI();		
        	String log = "";

        	log = connection.updateUserBAC(m_sUserName, dBACpercent);		
                   
        } // setBACpercent()
        
        /**
         * Save the Total Drink Count for the user to the database
         */
        public void setTotalDrinkCount(int nDrinkCount)throws InterruptedException, ExecutionException{
        	
        	/*
        	 * TODO: Save total drink count to database
        	 */
        	ServerAPI connection = new ServerAPI();		
        	String log = "";

        	log = connection.updateUserDrinkCount(m_sUserName, nDrinkCount);
        	
        } // setTotalDrinkCount()

        /**
         * Returns a List of Buddies for the User
         * @return m_listBuddies
         */
        protected LinkedList<Buddy> getBuddies() {
                
                if(m_listBuddies==null)
                {
                        m_listBuddies = new LinkedList<Buddy>();
                }
                
                return m_listBuddies;
        } // getBuddies()

        /**
         * Sets the Buddies list for the User
         * @param listBuddies
         */
        
        protected void setBuddies(LinkedList<Buddy> listBuddies) throws InterruptedException, ExecutionException{
    		
        	ServerAPI connection = new ServerAPI();		
        	String log = "";

        	log = connection.createBuddies(m_sUserName, listBuddies);		
        	
        	m_listBuddies = listBuddies;
        	
        } // setBuddies()*/



        /**
         * Gets the list of Groups for the User
         * @return m_listGroups
         */
        protected LinkedList<Group> getGroups() {
                
                if(m_listGroups==null)
                {
                        m_listGroups = new LinkedList<Group>();
                }
                return m_listGroups;
        } // getGroups()


        /**
         * Sets the list of Groups for the User
         * @param listGroups
         */
        protected void setGroups(LinkedList<Group> listGroups) throws InterruptedException, ExecutionException{
    		
    		ServerAPI connection = new ServerAPI();		
    		String log = "";

    		log = connection.setGroups(m_sUserName, listGroups);
    				
    		m_listGroups = listGroups;

    }/*
        protected void setGroups(LinkedList<Group> listGroups) {
                
                //TODO: Save user's groups to database
                
                m_listGroups = listGroups;
                
                // Set Access to internal storage file with Buddies List                
                pref_groupsList = context.getSharedPreferences(userGroupsList, 0);
                                
                prefEditor = pref_groupsList.edit().clear();

                prefEditor.putInt("Groups_Size", listGroups.size()); //Store the number of Buddies the user has.

                // Store the usernames
                for(int i=0; i < listGroups.size(); i++)  
                {
                        prefEditor.putString("Group_" + i, listGroups.get(i).getGroupName());
                }

                prefEditor.commit();
                
        } // getGroups() */
        protected void createGroup(Group group) throws InterruptedException, ExecutionException {
        	ServerAPI connection = new ServerAPI();

    		connection.createGroup(group);
        }

} //class User
