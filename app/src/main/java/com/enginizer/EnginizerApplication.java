package com.enginizer;

import android.app.Application;

import com.enginizer.config.injection.component.ApplicationComponent;
import com.enginizer.config.injection.component.DaggerApplicationComponent;
import com.enginizer.config.injection.module.ServiceModule;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by xPku on 3/19/17.
 */

public class EnginizerApplication extends Application {

    private Tracker mTracker;
    private ApplicationComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerApplicationComponent.builder()
                .serviceModule(new ServiceModule())
                .build();
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
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


    public ApplicationComponent getApplicationComponent() {
        return mAppComponent;
    }

    public void setApplicationComponent(ApplicationComponent mAppComponent) {
        this.mAppComponent = mAppComponent;
    }

    public Tracker getmTracker() {
        return mTracker;
    }

    public void setmTracker(Tracker mTracker) {
        this.mTracker = mTracker;
    }
}
