package com.igzafer.viking.amaleler;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPr {
    static SharedPreferences preferences;
    static SharedPreferences.Editor editor;

    
    public static Boolean Login(Context ctx){
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return preferences.getBoolean("login",false);
    }
    public static String getToken(Context ctx){
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return preferences.getString("key","");
    }
    public static String getPass(Context ctx){
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return preferences.getString("pass","");
    }
    public static void setLoginFalse(Context ctx){
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = preferences.edit();
        editor.putBoolean("login",false);
        editor.apply();
    }


}
