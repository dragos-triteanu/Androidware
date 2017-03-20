package com.enginizer.application;

import android.app.Application;

import android.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by xPku on 3/19/17.
 */

public class AnalythicsApplication extends Application {

    public Tracker getmTracker() {
        return mTracker;
    }

    public void setmTracker(Tracker mTracker) {
        this.mTracker = mTracker;
    }

    private Tracker mTracker;

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(ACCESSIBILITY_SERVICE);
        }
        return mTracker;
    }





}
