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
 * (build: 2013-10-30 15:57:41 UTC)
 * on 2013-11-20 at 00:25:42 UTC 
 * Modify at your own risk.
 */

package com.appspot.bacsafeserver.bacsafeAPI.model;

/**
 * Model definition for UserInfoProtoUserNameDrinkCount.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the bacsafeAPI. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class UserInfoProtoUserNameDrinkCount extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long drinkCount;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String userName;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getDrinkCount() {
    return drinkCount;
  }

  /**
   * @param drinkCount drinkCount or {@code null} for none
   */
  public UserInfoProtoUserNameDrinkCount setDrinkCount(java.lang.Long drinkCount) {
    this.drinkCount = drinkCount;
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
  public UserInfoProtoUserNameDrinkCount setUserName(java.lang.String userName) {
    this.userName = userName;
    return this;
  }

  @Override
  public UserInfoProtoUserNameDrinkCount set(String fieldName, Object value) {
    return (UserInfoProtoUserNameDrinkCount) super.set(fieldName, value);
  }

  @Override
  public UserInfoProtoUserNameDrinkCount clone() {
    return (UserInfoProtoUserNameDrinkCount) super.clone();
  }

}
