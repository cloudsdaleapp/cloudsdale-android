package org.cloudsdale.android.authentication;

import com.google.gson.annotations.SerializedName;

public class OAuthBundle {
	private static final String	CLIENT_TYPE	= "android";

	private String				provider;
	private String				uid;
	private String				token;
	@SerializedName("client_type")
	private String				clientType;

	/**
	 * Constuctor for the oAuth bundle class
	 * 
	 * @param provider
	 *            Provider for the oAuth
	 * @param uid
	 *            User's ID from provider
	 * @param token
	 *            The security token for the app
	 */
	public OAuthBundle(Provider provider, String uid, String token) {
		this.provider = provider.toString();
		this.uid = uid;
		this.token = token;
		clientType = CLIENT_TYPE;
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return "{ \"oauth\": { \"provider\": \"" + provider + "\", \"uid\": \""
				+ uid + "\", \"token\": \"" + token + "\", \"client_type\": \""
				+ clientType + "\" }}";
	}

	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * @param provider
	 *            the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the clientType
	 */
	public String getClientType() {
		return clientType;
	}

	/**
	 * @param clientType
	 *            the clientType to set
	 */
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
}
