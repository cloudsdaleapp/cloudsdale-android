package org.cloudsdale.android.models.queries;

import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.cloudsdale.android.Cloudsdale;
import org.cloudsdale.android.models.Model;
import org.cloudsdale.android.models.QueryData;
import org.cloudsdale.android.models.api.User;
import org.cloudsdale.android.models.exceptions.QueryException;
import org.cloudsdale.android.models.network.ApiUserResponse;

import java.io.IOException;

public class UserGetQuery extends GetQuery {

	public UserGetQuery(String url) {
		super(url);
	}

	private static final String	TAG	= "UserGet Query";

	private String				json;
	private User				u;
	private ApiUserResponse		response;

	/**
	 * Executes the query using data provided to return a User from the
	 * Cloudsdale API
	 * 
	 * @param data
	 *            The object containing all the header data required by the
	 *            query
	 * @param context
	 *            Not used in this query implementation
	 */
	@Override
	public User execute(final QueryData data, final Context context)
			throws QueryException {
		AccountManager am = AccountManager.get(Cloudsdale.getContext());
		addHeader("X-AUTH-TOKEN",
				am.getPassword(Cloudsdale.getUserAccountManager().getAccount()));

		// Query the API
		try {
			// Get the response
			mHttpResponse = mhttpClient.execute(httpGet);

			// Build the json
			json = EntityUtils.toString(mHttpResponse.getEntity());
			json = stripHtml(json);

			// Deserialize
			Gson gson = Cloudsdale.getJsonDeserializer();
			if (json != null) {
				Log.d(TAG, json);
				response = gson.fromJson(json, ApiUserResponse.class);
				if (response.getStatus() == 200) {
					u = response.getResult();
				} else {
					throw new QueryException(
							response.getErrors()[0].getMessage(),
							response.getStatus());
				}
			}
		} catch (ClientProtocolException e) {
			throw new QueryException("Couldn't connect to Cloudsdale", 404);
		} catch (IOException e) {
			throw new QueryException("Couldn't connect to Cloudsdale", 404);
		}

		return u;
	}

	@Override
	public Model[] executeForCollection(QueryData data, Context context) {
		throw new UnsupportedOperationException(
				"Getting multiple users is not supported at this point in time");
	}
}
