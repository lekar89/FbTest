package com.example.macpro.fbtest.tools;

import android.content.Context;
import android.content.SharedPreferences;

import static com.example.macpro.fbtest.tools.Config.PREFS_CONFIG;


public class SharedPreferencesHelper {

    private static final String TAG = "SharedPreferencesHelper";

    private static final SharedPreferencesHelper ourInstance = new SharedPreferencesHelper();
    private SharedPreferences mSharedPreferences;


    public static SharedPreferencesHelper getInstance() {
        return ourInstance;
    }

    private SharedPreferencesHelper() {
    }

    public void initialize(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFS_CONFIG, 0);
    }

    public void putStringValue(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    public String getStringValue(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public void putIntValue(String key, int value) {
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    public int getIntValue(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

}