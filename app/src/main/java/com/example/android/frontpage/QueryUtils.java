package com.example.android.frontpage;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gavin on 26-Feb-17.
 */

public final class QueryUtils {
    private static final int IMAGE_ARRAY_LOCATION = 3;
    private static final String LOG_TAG = MainActivity.class.getName();


    private QueryUtils() {

    }

    public static List<Story> fetchStoryData(String requestURL) {
        Log.i(LOG_TAG, "TEST: fetchStoryData() called....");

        //Create URL object
        URL url = createURL(requestURL);

        //Perform HTTP request to the URL & receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        List<Story> stories = extractStories(jsonResponse);

        return stories;
    }


    public static List<Story> extractStories(String storyJSON) {

        String region;
        String thumbnailUrlString;
        Drawable drawable = null;

        //If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(storyJSON)) {
            return null;
        }

        List<Story> stories = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(storyJSON);
            JSONArray resultsArray = root.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject story = resultsArray.getJSONObject(i);

                String headline = story.getString("title");
                if (story.getString("subsection").isEmpty()) {
                    region = "General";
                } else {
                    region = story.getString("subsection");
                }
                String date = story.getString("updated_date");
                String storyUrl = story.getString("url");

                JSONArray multimediaArray = story.getJSONArray("multimedia");

                if (multimediaArray.length() == 0) {
                    drawable = null;
                } else {
                    JSONObject imageObject = multimediaArray.getJSONObject(IMAGE_ARRAY_LOCATION);
                    thumbnailUrlString = imageObject.getString("url");
                    URL url = createURL(thumbnailUrlString);
                    try {
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.connect();
                        InputStream inputStream = urlConnection.getInputStream();
                        Bitmap bitmap = inputstreamToImageBitmap(inputStream);
                        drawable = bitmapToDrawable(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (drawable != null) {
                    stories.add(new Story(drawable, headline, region, date, storyUrl));
                }
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the stories JSON results", e);
        }

        return stories;
    }

    /*
    * Returns new URL object from the given URL.
    * */
    private static URL createURL(String stringUrl) {
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating the URL", exception);
            return null;
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        InputStream inputStream = null;
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.connect();

        /*
        * If the request was successful (response code 200),
        * then read the input stream & parse the response.
        * */
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + connection.getResponseCode());
            }
        } catch (IOException exception) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", exception);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Converts the contents of an InputStream to a String.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            //Ask the BufferedReader for a line of text
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static Bitmap inputstreamToImageBitmap(InputStream inputStream) {
        Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
        return myBitmap;
    }

    private static Drawable bitmapToDrawable(Bitmap bitmap) {
        Drawable drawable = new BitmapDrawable(Resources.getSystem(), bitmap);
        return drawable;
    }
}
