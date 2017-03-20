package com.enginizer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

public class PreferencesHelper {

    private static final String TAG = "PreferencesHelper:";
    private static final String PREFERENCES_LOGIN_TOKEN = "loginToken";
    private static final String PREFERENCES_DATABASE_RETRY_SCHEDULED = "databaseRetryScheduled";
    private static final String PREFERENCES_DATABASE_RETRY_INTERVAL = "databaseRetryInterval";
    private static final String PREFERENCES_TERMS_OF_SERVICE_SIGNED = "termsOfServiceSigned";
    private static final String PREFERENCES_ANALYTICS_ENABLED = "analyticsEnabled";
    private static final String IGNORE_LIST_FILE = "ignoreList";

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    
    private static SharedPreferences.Editor getSharedPreferencesEditor(Context context) {
        return getSharedPreferences(context).edit();
    }
    
    public static void setLoginToken(Context context, String loginToken) {
        SharedPreferences.Editor edit = getSharedPreferencesEditor(context);
        edit.putString(PREFERENCES_LOGIN_TOKEN, loginToken);
        edit.commit();
    }

    public static String getLoginToken(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);
        String loginToken = prefs.getString(PREFERENCES_LOGIN_TOKEN, null);
        
        return loginToken;
    }

    public static void ignorePhoneNumber(Context context, String phoneNumber) {
        FileInputStream fis;
        try {
            fis = context.openFileInput(IGNORE_LIST_FILE);
        } catch (FileNotFoundException e) {
            HashSet<String> ignoreList = new HashSet<String>();
            ignoreList.add(phoneNumber);
            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(context.openFileOutput(
                        IGNORE_LIST_FILE, Context.MODE_PRIVATE));
                oos.writeObject(ignoreList);
                oos.close();
            } catch (Exception e1) {
                Log.e(TAG, "Cannot create igore list file:" + e.getMessage());
            }
            return;
        }
        try {
            ObjectInputStream ois = new ObjectInputStream(fis);
            @SuppressWarnings("unchecked")
            HashSet<String> ignoreList = new HashSet<String>(
                    (HashSet<String>) ois.readObject());
            ignoreList.add(phoneNumber);
            ObjectOutputStream oos = new ObjectOutputStream(
                    context.openFileOutput(IGNORE_LIST_FILE,
                            Context.MODE_PRIVATE));
            oos.writeObject(ignoreList);
            oos.close();
        } catch (Exception e) {
            Log.e(TAG, "Cannot write ignore list to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static boolean isPhoneNumberIgnored(Context context,
            String phoneNumber) {
        HashSet<String> ignoreList = new HashSet<String>();
        try {
            FileInputStream fis = context.openFileInput(IGNORE_LIST_FILE);
            ObjectInputStream oin = new ObjectInputStream(fis);
            ignoreList = new HashSet<String>((HashSet<String>) oin.readObject());
            oin.close();
        } catch (Exception e) {
            Log.d(TAG, "Cannot read ignore list file: " + e.getMessage());
        }
        return ignoreList.contains(phoneNumber);
    }

    public static boolean isRetryScheduled(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);
        boolean retryScheduled = prefs.getBoolean(PREFERENCES_DATABASE_RETRY_SCHEDULED, false);
        
        return retryScheduled;
    }
    
    public static void setRetryScheduled(Context context, boolean retryScheduled) {
        SharedPreferences.Editor edit = getSharedPreferencesEditor(context);
        edit.putBoolean(PREFERENCES_DATABASE_RETRY_SCHEDULED, retryScheduled);
        edit.commit();
    }
    
    public static long getRetryInterval(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);
        long retryInterval = prefs.getLong(PREFERENCES_DATABASE_RETRY_INTERVAL, 0);
        
        return retryInterval;
    }
    
    public static void setRetryInterval(Context context, long retryInterval) {
        SharedPreferences.Editor edit = getSharedPreferencesEditor(context);
        edit.putLong(PREFERENCES_DATABASE_RETRY_INTERVAL, retryInterval);
        edit.commit();
    }

    public static Boolean getTermsOfServiceAgreed(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);
        Boolean has_agreed = prefs.getBoolean(PREFERENCES_TERMS_OF_SERVICE_SIGNED, false);

        return has_agreed;
    }
    
    public static void setTermsOfServiceSigned(Context context, Boolean has_agreed) {
        SharedPreferences.Editor edit = getSharedPreferencesEditor(context);
        edit.putBoolean(PREFERENCES_TERMS_OF_SERVICE_SIGNED, has_agreed);
        edit.commit();
    }
    
    public static Boolean getAnalyiticsEnabled(Context context) {
        SharedPreferences prefs = getSharedPreferences(context);
        Boolean has_agreed = prefs.getBoolean(PREFERENCES_ANALYTICS_ENABLED, false);

        return has_agreed;
    }
    
    public static void setAnalyiticsEnabled(Context context, Boolean enabled) {
        SharedPreferences.Editor edit = getSharedPreferencesEditor(context);
        edit.putBoolean(PREFERENCES_ANALYTICS_ENABLED, enabled);
        edit.commit();
    }
    
}
