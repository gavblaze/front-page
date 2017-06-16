package com.example.android.frontpage;



import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorldFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Story>> {

    public static final String LOG_TAG = WorldFragment.class.getName();
    private static final String NY_TIMES_REQUEST_URL = "https://api.nytimes.com/svc/topstories/v2/world.json?api-key=d8e5bf1a558b466cadeb2e0aa9cc5cec";
    private StoryAdapter mAdapter;
    private TextView mEmptyTextView;
    private View rootView;


    public WorldFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(LOG_TAG, "TEST: WorldFragment onCreate() called");

        rootView = inflater.inflate(R.layout.story_list, container, false);

        mAdapter = new StoryAdapter(getActivity(), new ArrayList<Story>());

        ListView listView = (ListView) rootView.findViewById(R.id.story_list);
        mEmptyTextView = (TextView) rootView.findViewById(R.id.empty_textview);

        listView.setEmptyView(mEmptyTextView);

        listView.setAdapter(mAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Story currentStory = mAdapter.getItem(position);
                String url = currentStory.getmURL();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });


        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {

        LoaderManager loaderManager = getLoaderManager();
        Log.i(LOG_TAG, "TEST: Create loader manager.....");
        loaderManager.initLoader(0, null, this);

        } else {

            View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyTextView = (TextView) rootView.findViewById(R.id.empty_textview);
            mEmptyTextView.setText(R.string.no_internet_connection);
        }
        return rootView;

    }

    @Override
    public Loader<List<Story>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "TEST: onCreateLoader() called......");

        return new StoryLoader(getActivity(), NY_TIMES_REQUEST_URL);
        //        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//
//        String section = sharedPrefs.getString(getString(R.string.section_key), getString(R.string.section_default));
//
//        Uri baseUri = Uri.parse(NY_TIMES_REQUEST_URL);
//
//        Uri.Builder uriBuilder = baseUri.buildUpon();
//
//        uriBuilder.appendQueryParameter(getString(R.string.section_default), section);
//
//
//        return new StoryLoader(getActivity(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Story>> loader, List<Story> data) {
        Log.i(LOG_TAG, "TEST: onLoadFinished() called......");

        mAdapter.clear();

        View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyTextView.setText(R.string.no_new_stories);

        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);

        }

    }

    @Override
    public void onLoaderReset(Loader<List<Story>> loader) {
        Log.i(LOG_TAG, "TEST: onLoaderReset() called......");

        mAdapter.clear();

    }
}


