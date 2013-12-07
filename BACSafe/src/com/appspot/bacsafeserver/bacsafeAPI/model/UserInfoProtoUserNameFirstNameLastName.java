/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2013-11-22 19:59:01 UTC)
 * on 2013-12-04 at 04:09:19 UTC 
 * Modify at your own risk.
 */

//-------------------------------------------------------------------------------------------------------------------------------
//	Copyright 2013 by BAC Safe Creators: Zach Flies, Alec White, Josh Collins, Shannon Bisges, and David Menager. 
//  All Rights Reserved.
//-------------------------------------------------------------------------------------------------------------------------------

package com.appspot.bacsafeserver.bacsafeAPI.model;

/**
 * Model definition for UserInfoProtoUserNameFirstNameLastName.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the bacsafeAPI. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class UserInfoProtoUserNameFirstNameLastName extends com.google.api.client.json.GenericJson {

	/**
	 * The value may be {@code null}.
	 */
	@com.google.api.client.util.Key
	private java.lang.String firstName;

	/**
	 * The value may be {@code null}.
	 */
	@com.google.api.client.util.Key
	private java.lang.String lastName;

	/**
	 * The value may be {@code null}.
	 */
	@com.google.api.client.util.Key
	private java.lang.String userName;

	/**
	 * @return value or {@code null} for none
	 */
	public java.lang.String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName firstName or {@code null} for none
	 */
	public UserInfoProtoUserNameFirstNameLastName setFirstName(java.lang.String firstName) {
		this.firstName = firstName;
		return this;
	}

	/**
	 * @return value or {@code null} for none
	 */
	public java.lang.String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName lastName or {@code null} for none
	 */
	public UserInfoProtoUserNameFirstNameLastName setLastName(java.lang.String lastName) {
		this.lastName = lastName;
		return this;
	}

	/**
	 * @return value or {@code null} for none
	 */
	public java.lang.String getUserName() {
		return userName;
	}

	/**
	 * @param userName userName or {@code null} for none
	 */
	public UserInfoProtoUserNameFirstNameLastName setUserName(java.lang.String userName) {
		this.userName = userName;
		return this;
	}

	@Override
	public UserInfoProtoUserNameFirstNameLastName set(String fieldName, Object value) {
		return (UserInfoProtoUserNameFirstNameLastName) super.set(fieldName, value);
	}

	@Override
	public UserInfoProtoUserNameFirstNameLastName clone() {
		return (UserInfoProtoUserNameFirstNameLastName) super.clone();
	}

}
