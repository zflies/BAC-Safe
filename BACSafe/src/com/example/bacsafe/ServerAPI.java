//-------------------------------------------------------------------------------------------------------------------------------
//	Copyright 2013 by BAC Safe Creators: Zach Flies, Alec White, Josh Collins, Shannon Bisges, and David Menager. 
//  All Rights Reserved.
//-------------------------------------------------------------------------------------------------------------------------------

package com.example.bacsafe;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;

import com.appspot.bacsafeserver.bacsafeAPI.BacsafeAPI;
import com.appspot.bacsafeserver.bacsafeAPI.model.BacsafeAPIGroupUser;
import com.appspot.bacsafeserver.bacsafeAPI.model.BuddyRequestsProtoSenderUserNameRequestedUserName;
import com.appspot.bacsafeserver.bacsafeAPI.model.Groups;
import com.appspot.bacsafeserver.bacsafeAPI.model.GroupsProtoGroupName;
import com.appspot.bacsafeserver.bacsafeAPI.model.UserInfo;
import com.appspot.bacsafeserver.bacsafeAPI.model.UserInfoProtoUserName;
import com.appspot.bacsafeserver.bacsafeAPI.model.UserInfoProtoUserNameBuddies;
import com.appspot.bacsafeserver.bacsafeAPI.model.UserInfoProtoUserNameCurBAC;
import com.appspot.bacsafeserver.bacsafeAPI.model.UserInfoProtoUserNameDrinkCount;
import com.appspot.bacsafeserver.bacsafeAPI.model.UserInfoProtoUserNameFirstNameLastName;
import com.appspot.bacsafeserver.bacsafeAPI.model.UserInfoProtoUserNameGroups;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;


/**
 * ServerAPI Provides the necessary functions for UI/database integration.
 * @author Team BAC Safe
 *
 */
public class ServerAPI {
	private String logResult;
	private String[] buddies;
	private Groups groupInfo;
	
	/**
	 * Creates an account for a new user
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @return true if created successfully
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String userAccountSetup(String userName, String firstName, String lastName) throws InterruptedException, ExecutionException {
		UserAccountSetup request = new UserAccountSetup();
		return request.execute(userName, firstName, lastName).get();
	} // userAccountSetup()
	
	/**
	 * Updates a user's BAC to the database
	 * @param userName
	 * @param curBAC
	 * @return True if successful
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String updateUserBAC(String userName, Double curBAC) throws InterruptedException, ExecutionException {
		UpdateUserBAC request = new UpdateUserBAC();
		return request.execute(userName, curBAC.toString()).get();
	} // updateUserBAC()
	
	/**
	 * Updates a user's Total Drink Count
	 * @param userName
	 * @param drinkCount
	 * @return True if successful
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String updateUserDrinkCount(String userName, Integer drinkCount) throws InterruptedException, ExecutionException {
		UpdateUserDrinkCount request = new UpdateUserDrinkCount();
		return request.execute(userName, drinkCount.toString()).get();
	} // updateUserDrinkCount()
	
	/**
	 * Gets a user's BAC from the database
	 * @param userName
	 * @return True if successful
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public double getUserBAC(String userName) throws InterruptedException, ExecutionException {
		GetUserBuddiesInfo request = new GetUserBuddiesInfo();
		return request.execute(userName).get().getCurBAC();
	} // getUserBAC()
	
	/**
	 * Gets a user's First Name from the database
	 * @param userName
	 * @return True if successful
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String getUserFirstName(String userName) throws InterruptedException, ExecutionException {
		GetUserBuddiesInfo request = new GetUserBuddiesInfo();
		return request.execute(userName).get().getFirstName();
	} // getUserFirstName()
	
	/**
	 * Gets a user's Last Name from the database
	 * @param userName
	 * @return True if successful
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String getUserLastName(String userName) throws InterruptedException, ExecutionException {
		GetUserBuddiesInfo request = new GetUserBuddiesInfo();
		return request.execute(userName).get().getLastName();
	} // getUserLastName()
	
	/**
	 * Gets a user's list of Buddies from the database
	 * @param userName
	 * @return True if successful
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String[] getUserBuddies(String userName) throws InterruptedException, ExecutionException {
		GetUserBuddiesInfo request = new GetUserBuddiesInfo();
		List<String> buddies = request.execute(userName).get().getBuddies();
		String[] results = null;
		if(buddies != null) {
			results = new String[buddies.size()];
			
			for(int i = 0; i < buddies.size(); i++) {
				results[i] = buddies.get(i);
			}
		}
		return results;
	} // getUserBuddies()
	
	/**
	 * Gets a user's list of Groups from the databse
	 * @param userName
	 * @return True if successful
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String[] getUserGroups(String userName) throws InterruptedException, ExecutionException {
		GetUserBuddiesInfo request = new GetUserBuddiesInfo();
		List<String> groups = request.execute(userName).get().getGroups();
		String[] results = null;
		if(groups != null) {
			results = new String[groups.size()];
		
			for(int i = 0; i < groups.size(); i++) {
				results[i] = groups.get(i);
			}
		}
		return results;
	} // getUserGroups()
	
	/**
	 * Gets a user's Total Drink Count from the database
	 * @param userName
	 * @return True if successful
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public long getUserDrinkCount(String userName) throws InterruptedException, ExecutionException {
		GetUserBuddiesInfo request = new GetUserBuddiesInfo();
		return request.execute(userName).get().getDrinkCount();
	} // getUserDrinkCount()
	
	/**
	 * Sets a user's Buddies list to the database
	 * @param userName
	 * @param buddies
	 * @return True if successful
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String createBuddies(String userName, LinkedList<Buddy> buddies) throws InterruptedException, ExecutionException {
		CreateBuddies request = new CreateBuddies();	
		List<String> buddiesList = new LinkedList<String>();
		for(int i = 0; i < buddies.size(); i++) {
			buddiesList.add(buddies.get(i).m_sBuddyUsername);
		}
		UserInfoProtoUserNameBuddies buddyInfo = new UserInfoProtoUserNameBuddies();
		buddyInfo.setUserName(userName);
		buddyInfo.setBuddies(buddiesList);
		return request.execute(buddyInfo).get();
	} // createBuddies()
	
	/**
	 * Sets a user's Groups list to the database
	 * @param userName
	 * @param groups
	 * @return True if successful
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String setGroups(String userName, LinkedList<Group> groups) throws InterruptedException, ExecutionException {
		SetGroups request = new SetGroups();	
		List<String> groupsList = new LinkedList<String>();
		for(int i = 0; i < groups.size(); i++) {
			groupsList.add(groups.get(i).getGroupName());
		}
		UserInfoProtoUserNameGroups groupInfo = new UserInfoProtoUserNameGroups();
		groupInfo.setUserName(userName);
		groupInfo.setGroups(groupsList);
		return request.execute(groupInfo).get();
	} // setGroups()
	
	/**
	 * Gets a user's Buddy Information from the database
	 * @param userName
	 * @return True if successful
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String[] getUserBuddiesInfo(String userName) throws InterruptedException, ExecutionException {
		GetUserBuddiesInfo request = new GetUserBuddiesInfo();
		UserInfo user = request.execute(userName).get();
		String[] results = new String[4];
		results[0] = user.getFirstName();
		results[1] = user.getLastName();
		results[2] = user.getCurBAC().toString();
		results[3] = user.getDrinkCount().toString();
		
		return results;
	} // getUserBuddiesInfo()
	
	/**
	 * Sends a Buddy Request to the user's Buddy
	 * @param senderName
	 * @param requestedName
	 * @return True if successful
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String sendBuddyRequest(String senderName, String requestedName) throws InterruptedException, ExecutionException {
		SendBuddyRequest request = new SendBuddyRequest();
		return request.execute(senderName, requestedName).get();
	} // sendBuddyRequest()
	
	/**
	 * Gets a Buddy Request for the user
	 * @param userName
	 * @return True if successful
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String[] getBuddyRequests(String userName) throws InterruptedException, ExecutionException {
		GetBuddyRequests request = new GetBuddyRequests();
		return request.execute(userName).get();
	} // getBuddyRequests()
	
	/**
	 * Accepts a Buddy Requets for the user
	 * @param userName
	 * @param newBuddyUserName
	 * @return True if successful
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String acceptBuddyRequest(String userName, String newBuddyUserName) throws InterruptedException, ExecutionException {
		SendBuddyRequest request = new SendBuddyRequest();
		return request.execute(newBuddyUserName, userName).get();
	} // acceptBuddyRequets()
	
	/**
	 * Creates a group in the database for the user
	 * @param group
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void createGroup(Group group) throws InterruptedException, ExecutionException {
		CreateGroup request = new CreateGroup();
		request.execute(group.m_sGroupName);
		for(int i = 0; i < group.m_listGroupBuddies.size(); i++) {
			addDrinker(group.m_sGroupName, group.m_listGroupBuddies.get(i).m_sBuddyUsername);
		}
		for(int i = 0; i < group.m_listGroupBuddies.size(); i++) {
			LinkedList<Group> groups = new LinkedList<Group>();
			groups.add(group);
			setGroups(group.m_listGroupBuddies.get(i).m_sBuddyUsername, groups);
		}
	} // createGroup()
	
	/**
	 * Add's a DD to the Group
	 * @param groupName
	 * @param drinker
	 * @return True if successful
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String addDD(String groupName, String drinker) throws InterruptedException, ExecutionException {
		GroupAddDD request = new GroupAddDD();
		return request.execute(groupName, drinker).get();
	} // addDD()
	
	/**
	 * Adds a Drinker to a group
	 * @param groupName
	 * @param dd
	 * @return True if successful
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String addDrinker(String groupName, String dd) throws InterruptedException, ExecutionException {
		GroupAddDrinker request = new GroupAddDrinker();
		return request.execute(groupName, dd).get();
	} // addDrinker
	
	/**
	 * Gets the Drinkers in a Group
	 * @param groupName
	 * @return True if successful
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public LinkedList<String> getGroupDrinkers(String groupName) throws InterruptedException, ExecutionException {
		GetGroupInfo request = new GetGroupInfo();
		Groups group = request.execute(groupName).get();
		List<String> drinkers = group.getDrinkers();
		if(drinkers == null) return null; 
		String[] results = new String[drinkers.size()];
		for(int i = 0; i < drinkers.size(); i++) {
			results[i] = drinkers.get(i);
		}
		LinkedList<String> groupList = new LinkedList<String>(Arrays.asList(results));
		return groupList;
	} // getGroupDrinkers()
	
	/**
	 * Gets the DDs for a group
	 * @param groupName
	 * @return True if successful
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public String[] getGroupDDs(String groupName) throws InterruptedException, ExecutionException {
		GetGroupInfo request = new GetGroupInfo();
		Groups group = request.execute(groupName).get();
		List<String> dds = group.getDd();
		String[] results = new String[dds.size()];
		for(int i = 0; i < dds.size(); i++) {
			results[i] = dds.get(i);
		}
		return results;
	} // getGroupDDs()
	

	/**
	 * Sets up a new account for a user
	 */
	public class UserAccountSetup extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... info) {
			BacsafeAPI.Builder builder = new BacsafeAPI.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
			BacsafeAPI service = builder.build();
			String log = "";
			try {
				UserInfoProtoUserNameFirstNameLastName user = new UserInfoProtoUserNameFirstNameLastName();
				user.setUserName(info[0]);
				user.setFirstName(info[1]);
				user.setLastName(info[2]);
				log = service.userinfo().userAccountSetup(user).execute().getMessage();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			return log;
		}
		protected void onPostExecute(String result) {
			logResult = result;
		}
	} // UserAccountSetup
	
	/**
	 * Updates a User's BAC
	 */
	public class UpdateUserBAC extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... info) {
			BacsafeAPI.Builder builder = new BacsafeAPI.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
			BacsafeAPI service = builder.build();
			String log = "";
			try {
				UserInfoProtoUserNameCurBAC user = new UserInfoProtoUserNameCurBAC();
				user.setUserName(info[0]);
				user.setCurBAC(Double.parseDouble(info[1]));
				log = service.userinfo().updateUserBAC(user).execute().getMessage();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			return log;
		}
		protected void onPostExecute(String result) {
			logResult = result;
		}
	} // UpdateUserBAC()
	
	/**
	 * Updates a User's Total Drink Count
	 */
	public class UpdateUserDrinkCount extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... info) {
			BacsafeAPI.Builder builder = new BacsafeAPI.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
			BacsafeAPI service = builder.build();
			String log = "";
			try {
				UserInfoProtoUserNameDrinkCount user = new UserInfoProtoUserNameDrinkCount();
				user.setUserName(info[0]);
				user.setDrinkCount(Long.parseLong(info[1]));
				log = service.userinfo().updateUserDrinkCount(user).execute().getMessage();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			return log;
		}
		protected void onPostExecute(String result) {
			logResult = result;
		}
	} // UpdateUserDrinkCount()
	
	/**
	 * Creates Buddies list for a User
	 */
	public class CreateBuddies extends AsyncTask<UserInfoProtoUserNameBuddies, Void, String> {
		protected String doInBackground(UserInfoProtoUserNameBuddies... info) {
			BacsafeAPI.Builder builder = new BacsafeAPI.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
			BacsafeAPI service = builder.build();
			String log = "";
			try {
				log = service.userinfo().createBuddies(info[0]).execute().getMessage();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			return log;
		}
		protected void onPostExecute(String result) {
			logResult = result;
		}
	} // CreateBuddies()
	
	/**
	 * Sets the Groups List for a user
	 */
	public class SetGroups extends AsyncTask<UserInfoProtoUserNameGroups, Void, String> {
		protected String doInBackground(UserInfoProtoUserNameGroups... info) {
			BacsafeAPI.Builder builder = new BacsafeAPI.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
			BacsafeAPI service = builder.build();
			String log = "";
			try {
				log = service.userinfo().setGroups(info[0]).execute().getMessage();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			return log;
		}
		protected void onPostExecute(String result) {
			logResult = result;
		}
	} // SetGroups()
	
	/**
	 * Gets the info for a Buddy 
	 */
	public class GetUserBuddiesInfo extends AsyncTask<String, Void, UserInfo> {
		protected UserInfo doInBackground(String... info) {
			BacsafeAPI.Builder builder = new BacsafeAPI.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
			BacsafeAPI service = builder.build();
			UserInfo userInfo = new UserInfo();
			try {
				UserInfoProtoUserName user = new UserInfoProtoUserName();
				user.setUserName(info[0]);
				userInfo = service.userinfo().getUserBuddiesInfo(user).execute();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			return userInfo;
		}
	} // GetUserBuddiesInfo()
	
	/**
	 * Sends a Buddy Request 
	 */
	public class SendBuddyRequest extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... info) {
			BacsafeAPI.Builder builder = new BacsafeAPI.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
			BacsafeAPI service = builder.build();
			String log = "";
			try {
				BuddyRequestsProtoSenderUserNameRequestedUserName user = new BuddyRequestsProtoSenderUserNameRequestedUserName();
				user.setSenderUserName(info[0]);
				user.setRequestedUserName(info[1]);
				log = service.buddyrequest().sendBuddyRequest(user).execute().getMessage();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			return log;
		}
		protected void onPostExecute(String result) {
			logResult = result;
		}
	} // SendBuddyRequest()
	
	/**
	 * Gets Buddy Requests
	 */
	public class GetBuddyRequests extends AsyncTask<String, Void, String[]> {
		protected String[] doInBackground(String... info) {
			BacsafeAPI.Builder builder = new BacsafeAPI.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
			BacsafeAPI service = builder.build();
			Vector<String> names = new Vector<String>();
			String log = "";
			try {
				BuddyRequestsProtoSenderUserNameRequestedUserName user = new BuddyRequestsProtoSenderUserNameRequestedUserName();
				user.setRequestedUserName(info[0]);
				log = service.buddyrequest().sendBuddyRequest(user).execute().getMessage();
			} catch(IOException e) {
				e.printStackTrace();
			}
			int last = 0;
			for(int i = 0; i < log.length(); i++) {
				if (log.charAt(i) == ' ') {
					names.add(log.substring(last, i));
					last = i + 1;
				}
			}
			String[] result = new String[names.size()];
			for(int i = 0; i < result.length; i++) {
				result[i] = names.elementAt(i);
			}
			
			return result;
		}
		protected void onPostExecute(String[] result) {
			buddies = result;
		}
	} // GetBuddyRequests()
	
	/**
	 * Accepts a Buddy Request
	 */
	public class AcceptBuddyRequest extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... info) {
			BacsafeAPI.Builder builder = new BacsafeAPI.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
			BacsafeAPI service = builder.build();
			String log = "";
			try {
				BuddyRequestsProtoSenderUserNameRequestedUserName user = new BuddyRequestsProtoSenderUserNameRequestedUserName();
				user.setSenderUserName(info[0]);
				user.setRequestedUserName(info[1]);
				log = service.buddyrequest().acceptBuddyRequests(user).execute().getMessage();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			return log;
		}
		protected void onPostExecute(String result) {
			logResult = result;
		}
	} // Accept Buddy Request
	
	/**
	 * Creates a new Group for the user
	 */
	public class CreateGroup extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... info) {
			BacsafeAPI.Builder builder = new BacsafeAPI.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
			BacsafeAPI service = builder.build();
			String log = "";
			try {
				GroupsProtoGroupName group = new GroupsProtoGroupName();
				group.setGroupName(info[0]);
				log = service.groups().createGroup(group).execute().getMessage();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			return log;
		}
		protected void onPostExecute(String result) {
			logResult = result;
		}
	} // CreateGroup()
	
	/**
	 * Adds a DD for a Group
	 */
	public class GroupAddDD extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... info) {
			BacsafeAPI.Builder builder = new BacsafeAPI.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
			BacsafeAPI service = builder.build();
			String log = "";
			try {
				BacsafeAPIGroupUser group = new BacsafeAPIGroupUser();
				group.setUserName(info[0]);
				group.setGroupName(info[1]);
				log = service.groups().addDD(group).execute().getMessage();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			return log;
		}
		protected void onPostExecute(String result) {
			logResult = result;
		}
	} // GroupAddDD()
	
	/**
	 * Adds a Drinker for a Group
	 */
	public class GroupAddDrinker extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... info) {
			BacsafeAPI.Builder builder = new BacsafeAPI.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
			BacsafeAPI service = builder.build();
			String log = "";
			try {
				BacsafeAPIGroupUser group = new BacsafeAPIGroupUser();
				group.setUserName(info[1]);
				group.setGroupName(info[0]);
				log = service.groups().addDrinker(group).execute().getMessage();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			return log;
		}
		protected void onPostExecute(String result) {
			logResult = result;
		}
	} // GroupAddDrinker()
	
	/**
	 * Gets the Info for a Group
	 */
	public class GetGroupInfo extends AsyncTask<String, Void, Groups> {
		protected Groups doInBackground(String... info) {
			BacsafeAPI.Builder builder = new BacsafeAPI.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
			BacsafeAPI service = builder.build();
			Groups log = new Groups();
			log.setGroupName(info[0]);
			try {
				GroupsProtoGroupName group = new GroupsProtoGroupName();
				group.setGroupName(info[0]);
				log = service.groups().getGroupInfo(group).execute();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			return log;
		}
		protected void onPostExecute(Groups result) {
			groupInfo = result;
		}
	} // GetGroupInfo()
	
} // class ServerAPI
