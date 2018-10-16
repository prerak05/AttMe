package com.attme.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by prerak on 16/10/18.
 */

public class ShardPref {
    //ToDo this class helps for store temporary data
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    public static final String DMS_PREF = "app_pref";


    //Todo: Default constructor
    public ShardPref(Context context) {
        mSharedPreferences = context.getSharedPreferences(DMS_PREF, Context.MODE_PRIVATE);
    }

    //Todo: data save method
    public void saveValue(String key, String value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.commit();
    }

    //Todo: get value method
    public String getValue(String key, String value) {
        return mSharedPreferences.getString(key, value);
    }

    //Todo: clear data method
    public void clear() {
        mSharedPreferences.edit().clear().commit();
    }
}