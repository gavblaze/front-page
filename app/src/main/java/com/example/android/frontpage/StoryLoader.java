package com.example.android.frontpage;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

/**
 * Created by Gavin on 04-Mar-17.
 */

/**
 * Loads a list of stories by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class StoryLoader extends AsyncTaskLoader<List<Story>> {
    /** Tag for log messages */
    private static final String LOG_TAG = StoryLoader.class.getName();
    /** Query URL */
    String mUrl;

    /**
     * Constructs a new {@link StoryLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public StoryLoader(Context context, String url) {
    super(context);
        mUrl = url;
}

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "TEST: onStartLoading() called......");
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Story> loadInBackground() {
        Log.i(LOG_TAG, "TEST: loadInBackground() called......");
        if (mUrl.isEmpty()) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Story> stories = QueryUtils.fetchStoryData(mUrl);
        return stories;
    }
}

