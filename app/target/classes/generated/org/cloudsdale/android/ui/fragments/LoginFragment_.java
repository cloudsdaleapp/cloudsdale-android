//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package org.cloudsdale.android.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import org.cloudsdale.android.Cloudsdale;
import org.cloudsdale.android.R.layout;

public final class LoginFragment_
    extends LoginFragment
{

    private View contentView_;

    private void init_(Bundle savedInstanceState) {
        cloudsdale = ((Cloudsdale) getActivity().getApplication());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void afterSetContentView_() {
        loginFormView = ((View) findViewById(org.cloudsdale.android.R.id.login_form));
        passwordView = ((EditText) findViewById(org.cloudsdale.android.R.id.password));
        emailView = ((EditText) findViewById(org.cloudsdale.android.R.id.email));
        loginStatusMessageView = ((TextView) findViewById(org.cloudsdale.android.R.id.login_status_message));
        loginStatusView = ((View) findViewById(org.cloudsdale.android.R.id.login_status));
        {
            View view = findViewById(org.cloudsdale.android.R.id.sign_in_button);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        LoginFragment_.this.signInClick();
                    }

                }
                );
            }
        }
        addViewBehaviour();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.fragment_login, container, false);
        }
        return contentView_;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterSetContentView_();
    }

    public View findViewById(int id) {
        if (contentView_ == null) {
            return null;
        }
        return contentView_.findViewById(id);
    }

    public static LoginFragment_.FragmentBuilder_ builder() {
        return new LoginFragment_.FragmentBuilder_();
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public LoginFragment build() {
            LoginFragment_ fragment_ = new LoginFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}
