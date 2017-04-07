package com.enginizer;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.enginizer.config.injection.component.ApplicationComponent;
import com.enginizer.config.injection.component.DaggerApplicationComponent;
import com.enginizer.config.injection.module.ServiceModule;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * Created by xPku on 3/19/17.
 */

public class EnginizerApplication extends Application {
    private static final String TAG = "EnginizerApplication: ";

    private Tracker mTracker;
    public static final String ASSETS_APP_PROPERTIES = "assets/app.properties";
    public static ApplicationComponent APP;
    public static PropertiesConfiguration PROPERTIES;

    @Override
    public void onCreate() {
        super.onCreate();

        APP = DaggerApplicationComponent.builder()
                .serviceModule(new ServiceModule())
                .build();

        loadAssetProperties();
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

    /**
     * Loads the app.properties property file from the assets folder.
     */
    private void loadAssetProperties() {
        Configurations configs = new Configurations();
        try {
            PROPERTIES = configs.properties(ASSETS_APP_PROPERTIES);
        } catch (ConfigurationException e) {
            Log.e(TAG,e.getStackTrace().toString());
        }
    }

    public static EnginizerApplication get(Context context) {
        return (EnginizerApplication) context.getApplicationContext();
    }

    public Tracker getmTracker() {
        return mTracker;
    }

    public void setmTracker(Tracker mTracker) {
        this.mTracker = mTracker;
    }
}
