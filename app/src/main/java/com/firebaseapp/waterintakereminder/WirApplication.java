package com.firebaseapp.waterintakereminder;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Application entry point. It holds configuration of Firebase
 * Realtime Database and SharedPreferences management.
 */
public class WirApplication extends Application {

    private static Application _instance;
    private static SharedPreferences _mSharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        _instance = this;

        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

    }

    /**
     * Gets shared preferences.
     *
     * @return Shared preferences
     */
    public static SharedPreferences getSharedPreferences() {
        if (_mSharedPreferences == null)
            _mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(_instance);
        return _mSharedPreferences;
    }

    public static void setPreferencesString(String key, String value) {
        getSharedPreferences().edit().putString(key, value).apply();
    }

    public static String getPreferenceDataString(String key) {
        return getSharedPreferences().getString(key, "");
    }

    public static void setPreferencesBoolean(String key, boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).apply();
    }

    public static boolean getPreferenceDataBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }


}
