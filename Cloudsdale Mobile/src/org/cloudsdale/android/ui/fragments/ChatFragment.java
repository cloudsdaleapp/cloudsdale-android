package org.cloudsdale.android.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import org.cloudsdale.android.R;

public class ChatFragment extends SherlockFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO I just don't know what went wrong!
        TextView tv = new TextView(getActivity());
        tv.setText("I'm a drop fragment!");

        return tv;
    }

}
