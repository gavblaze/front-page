package com.example.android.frontpage;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Gavin on 25-Feb-17.
 */

public class StoryAdapter extends ArrayAdapter<Story> {
    public StoryAdapter(Context context, ArrayList<Story> story) {
        super(context, 0, story);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Story currentStory = getItem(position);

        TextView regionTextView = (TextView) listItemView.findViewById(R.id.region);
        regionTextView.setText(currentStory.getmRegion());

        TextView headlineTextView = (TextView) listItemView.findViewById(R.id.headline);
        headlineTextView.setText(currentStory.getmHeadline());

        ImageView thumbnailImageView = (ImageView) listItemView.findViewById(R.id.thumbnail);
        if (currentStory.getmThumbnail() == null) {
            thumbnailImageView.setImageDrawable(new ColorDrawable(Color.TRANSPARENT));
        } else {
            thumbnailImageView.setImageDrawable(currentStory.getmThumbnail());
        }

        //Get the String of the current story's date
        String dateString = currentStory.getmUpdatedDate();
        Date date = null;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); //Note the date format has to match the String
        try {
            /*Use SimpleDate format to parse the string to get a Date object which we can use */
            date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String dayOfStory = formatDay(date);
        String timeOfStory = formatTime(date);
        String timeAgo = timeAgoFormat(date);

//        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
//        dateTextView.setText(dayOfStory);
//
//        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
//        timeTextView.setText(timeOfStory);

        TextView timeAgoTextView = (TextView) listItemView.findViewById(R.id.timeago);
        timeAgoTextView.setText(timeAgo);

        return listItemView;
    }

    private String formatDay(Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }

    private String formatTime(Date date) {
        SimpleDateFormat tf = new SimpleDateFormat("h:mm a");
        return tf.format(date);
    }

    private String timeAgoFormat (Date date) {
        long timePublished = date.getTime();
        long timeNow = System.currentTimeMillis();
        String howLong = (String) DateUtils.getRelativeTimeSpanString(timePublished, timeNow, DateUtils.MINUTE_IN_MILLIS);
        return howLong;
    }
}
