package org.cloudsdale.android.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.cloudsdale.android.ui.LauncherActivity;

class AccountAuthenticator extends
        AbstractAccountAuthenticator {

    private Context	mContext;

    public AccountAuthenticator(Context context) {
        super(context);
        mContext = context;
    }

    /**
     * The user has requested to add a new account to the system. We return
     * an intent that will launch our login screen if the user has not
     * logged in yet, otherwise our activity will just pass the user's
     * credentials on to the account manager.
     */
    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response,
                             String accountType, String authTokenType,
                             String[] requiredFeatures, Bundle options)
            throws NetworkErrorException {
        Bundle reply = new Bundle();
        Intent i = new Intent(mContext, LauncherActivity.class);
        i.setAction("org.cloudsdale.android.account.LOGIN");
        i.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        reply.putParcelable(AccountManager.KEY_INTENT, i);
        return reply;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response,
                                     Account account, Bundle options) throws NetworkErrorException {
        // Fuck other apps
        return null;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response,
                                 String accountType) {
        // No fucking editing
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response,
                               Account account, String authTokenType, Bundle options)
            throws NetworkErrorException {
        // No one will be using the biult-in auth tokens
        return null;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        // See above
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response,
                              Account account, String[] features)
            throws NetworkErrorException {
        // We don't do features in this house, bitch!
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response,
                                    Account account, String authTokenType, Bundle options)
            throws NetworkErrorException {
        // TODO, this we may want to do, however
        return null;
    }
}