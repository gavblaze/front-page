package com.example.android.frontpage;

import android.graphics.drawable.Drawable;

/**
 * Created by Gavin on 25-Feb-17.
 */

public class Story {
    String mRegion;
    String mHeadline;
    Drawable mThumbnail;
    String mUpdatedDate;
    String mURL;

    public Story(Drawable thubmnail, String headline, String region, String publishDate, String url) {
        this.mRegion = region;
        this.mHeadline = headline;
        this.mThumbnail = thubmnail;
        this.mUpdatedDate = publishDate;
        this.mURL = url;
    }

    public String getmRegion() {
        return mRegion;
    }

    public String getmHeadline() {
        return mHeadline;
    }

    public Drawable getmThumbnail() {
        return mThumbnail;
    }

    public String getmUpdatedDate() {
        return mUpdatedDate;
    }

    public String getmURL() { return mURL; }
}
