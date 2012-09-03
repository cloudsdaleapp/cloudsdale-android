package org.cloudsdale.android.ui.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.gson.Gson;

import org.cloudsdale.android.Cloudsdale;
import org.cloudsdale.android.PersistentData;
import org.cloudsdale.android.R;
import org.cloudsdale.android.models.LoggedUser;
import org.cloudsdale.android.models.QueryData;
import org.cloudsdale.android.models.api_models.Message;
import org.cloudsdale.android.models.queries.ChatMessageGetQuery;
import org.cloudsdale.android.models.queries.MessagePostQuery;
import org.cloudsdale.android.ui.adapters.CloudMessageAdapter;

import java.util.ArrayList;

public class ChatFragment extends SherlockFragment {

    private static final String        TAG = "Chat Fragment";

    private static View                sChatView;
    private static ListView            sMessageList;
    private static CloudMessageAdapter sMessageAdapter;
    private static String              sCloudUrl;
    private static EditText            sInputField;
    private static ImageButton         sSendButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        sChatView = inflater.inflate(R.layout.chat_view, null);

        sMessageList = (ListView) sChatView
                .findViewById(R.id.chat_message_list);
        sMessageAdapter = new CloudMessageAdapter(getActivity(),
                new ArrayList<Message>());
        sMessageList.setAdapter(sMessageAdapter);

        sInputField = (EditText) sChatView.findViewById(R.id.chat_input_field);
        sSendButton = (ImageButton) sChatView
                .findViewById(R.id.chat_send_button);

        sSendButton.setClickable(true);
        sSendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendChatMessage();
            }
        });

        populateChat();
        return sChatView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void populateChat() {
        sCloudUrl = getString(R.string.cloudsdale_api_base)
                + getString(R.string.cloudsdale_cloud_chat_messages_endpoint,
                        Cloudsdale.getShowingCloud());
        MessageAsyncQuery query = new MessageAsyncQuery();
        query.execute();
    }

    public void addMessage(Message message) {
        sMessageAdapter.addMessage(message);
    }

    private void sendChatMessage() {
        String content = sInputField.getText().toString();
        if (content.length() > 0) {
            new MessageAsyncSend().execute(content);
        } else {
            return;
        }
    }

    class MessageAsyncQuery extends AsyncTask<Void, Void, Message[]> {

        @Override
        protected Message[] doInBackground(Void... params) {
            QueryData data = new QueryData();
            String url = sCloudUrl;
            if (Cloudsdale.DEBUG) {
                Log.d("Cloudsdale Chat Fragment", "Message url: " + url);
            }
            data.setUrl(url);

            ChatMessageGetQuery query = new ChatMessageGetQuery(url);
            return query.executeForCollection(data, getActivity());
        }

        @Override
        protected void onPostExecute(Message[] result) {
            super.onPostExecute(result);
            if (Cloudsdale.DEBUG) {
                Log.d(TAG, "There are " + result.length + " messages");
            }
            if (result != null) {
                for (Message m : result) {
                    // Message previous = getPreviousMessage();
                    // if (previous != null
                    // && m.getAuthor().getName()
                    // .equals(previous.getAuthor().getName())) {
                    // LinearLayout layout = (LinearLayout) mMessageList
                    // .getChildAt(mMessageAdapter.getCount() - 1)
                    // .findViewById(R.id.message_root);
                    // TextView tv = new TextView(getActivity());
                    // tv.setText(m.getContent());
                    // layout.addView(tv);
                    // } else {
                    ((CloudMessageAdapter) sMessageList.getAdapter())
                            .addMessage(m);
                    // }
                }
            }
        }

        private Message getPreviousMessage() {
            if (sMessageAdapter.getCount() > 0) {
                return (Message) sMessageAdapter.getItem(sMessageAdapter
                        .getCount() - 1);
            } else {
                return null;
            }
        }
    }

    class MessageAsyncSend extends AsyncTask<String, Void, Message> {

        @Override
        protected Message doInBackground(String... params) {
            if (Cloudsdale.DEBUG) {
                Log.d("Chat Send Message", "Attempting to send message");
            }
            LoggedUser me = PersistentData.getMe();
            String json = "{\"content\":\"" + params[0] + "\",\"client_id\":\""
                    + me.getId() + "\"}";
            QueryData qd = new QueryData();
            qd.setJson(json);
            MessagePostQuery q = new MessagePostQuery(sCloudUrl);
            return q.execute(qd, getActivity());
        }

        @Override
        protected void onPostExecute(Message result) {
            if (Cloudsdale.DEBUG) {
                Log.d("Chat Send Message", "Results received");
            }
            if (result != null) {
                ((CloudMessageAdapter) sMessageList.getAdapter())
                        .addMessage(result);
            }
            super.onPostExecute(result);
        }
    }

    class MessageAsyncAdd extends AsyncTask<String, Void, Message> {

        @Override
        protected Message doInBackground(String... params) {
            Gson gson = new Gson();
            Message message = gson.fromJson(params[0], Message.class);
            return message;
        }

        @Override
        protected void onPostExecute(Message result) {
            sMessageAdapter.addMessage(result);
            super.onPostExecute(result);
        }

    }
}
