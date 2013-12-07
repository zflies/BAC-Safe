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

package com.appspot.bacsafeserver.bacsafeAPI;

/**
 * BacsafeAPI request initializer for setting properties like key and userIp.
 *
 * <p>
 * The simplest usage is to use it to set the key parameter:
 * </p>
 *
 * <pre>
  public static final GoogleClientRequestInitializer KEY_INITIALIZER =
      new BacsafeAPIRequestInitializer(KEY);
 * </pre>
 *
 * <p>
 * There is also a constructor to set both the key and userIp parameters:
 * </p>
 *
 * <pre>
  public static final GoogleClientRequestInitializer INITIALIZER =
      new BacsafeAPIRequestInitializer(KEY, USER_IP);
 * </pre>
 *
 * <p>
 * If you want to implement custom logic, extend it like this:
 * </p>
 *
 * <pre>
  public static class MyRequestInitializer extends BacsafeAPIRequestInitializer {

    {@literal @}Override
    public void initializeBacsafeAPIRequest(BacsafeAPIRequest{@literal <}?{@literal >} request)
        throws IOException {
      // custom logic
    }
  }
 * </pre>
 *
 * <p>
 * Finally, to set the key and userIp parameters and insert custom logic, extend it like this:
 * </p>
 *
 * <pre>
  public static class MyRequestInitializer2 extends BacsafeAPIRequestInitializer {

    public MyKeyRequestInitializer() {
      super(KEY, USER_IP);
    }

    {@literal @}Override
    public void initializeBacsafeAPIRequest(BacsafeAPIRequest{@literal <}?{@literal >} request)
        throws IOException {
      // custom logic
    }
  }
 * </pre>
 *
 * <p>
 * Subclasses should be thread-safe.
 * </p>
 *
 * @since 1.12
 */
public class BacsafeAPIRequestInitializer extends com.google.api.client.googleapis.services.json.CommonGoogleJsonClientRequestInitializer {

	public BacsafeAPIRequestInitializer() {
		super();
	}

	/**
	 * @param key API key or {@code null} to leave it unchanged
	 */
	public BacsafeAPIRequestInitializer(String key) {
		super(key);
	}

	/**
	 * @param key API key or {@code null} to leave it unchanged
	 * @param userIp user IP or {@code null} to leave it unchanged
	 */
	public BacsafeAPIRequestInitializer(String key, String userIp) {
		super(key, userIp);
	}

	@Override
	public final void initializeJsonRequest(com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest<?> request) throws java.io.IOException {
		super.initializeJsonRequest(request);
		initializeBacsafeAPIRequest((BacsafeAPIRequest<?>) request);
	}

	/**
	 * Initializes BacsafeAPI request.
	 *
	 * <p>
	 * Default implementation does nothing. Called from
	 * {@link #initializeJsonRequest(com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest)}.
	 * </p>
	 *
	 * @throws java.io.IOException I/O exception
	 */
	protected void initializeBacsafeAPIRequest(BacsafeAPIRequest<?> request) throws java.io.IOException {
	}
}
