package org.cloudsdale.android.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

import org.cloudsdale.android.Cloudsdale;
import org.cloudsdale.android.R;
import org.cloudsdale.android.models.QueryData;
import org.cloudsdale.android.models.api_models.Drop;
import org.cloudsdale.android.models.queries.DropGetQuery;
import org.cloudsdale.android.ui.adapters.CloudDropAdapter;

public class DropFragment extends SherlockFragment {

    private static final String     TAG = "Drop Fragment";

    private static View             sDropView;
    private static ListView         sDropList;
    private static CloudDropAdapter sDropAdapter;
    private static String           sCloudUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        sDropView = inflater.inflate(R.layout.drop_list_layout, null);
        return sDropView;
    }

    @Override
    public void onStart() {
        attachViews();
        super.onStart();
    }

    @Override
    public void onResume() {
        attachViews();
        super.onResume();
    }

    private void attachViews() {
        sDropList = (ListView) sDropView.findViewById(R.id.drop_list);
        sDropAdapter = new CloudDropAdapter(getActivity(), null);
        sDropList.setAdapter(sDropAdapter);
        populateDrops();
    }

    private void populateDrops() {
        sCloudUrl = getString(R.string.cloudsdale_api_base)
                + getString(R.string.cloudsdale_cloud_drop_endpoint,
                        Cloudsdale.getShowingCloud());
        DropAsyncQuery query = new DropAsyncQuery();
        query.execute();
    }

    class DropAsyncQuery extends AsyncTask<Void, Void, Drop[]> {

        @Override
        protected Drop[] doInBackground(Void... params) {
            QueryData data = new QueryData();
            data.setUrl(sCloudUrl);

            DropGetQuery query = new DropGetQuery(sCloudUrl);
            return query.executeForCollection(data, getActivity());
        }

        @Override
        protected void onPostExecute(Drop[] result) {
            super.onPostExecute(result);

            if (result != null) {
                for (Drop d : result) {
                    sDropAdapter.addDrop(d);
                }
            }
        }

    }

}
