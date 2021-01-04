package com.igzafer.viking.LocalDatabase;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.igzafer.viking.Model.LoginRegisterModels.AuthModel;
import com.igzafer.viking.Model.LoginRegisterModels.GetTokenModel;
import com.igzafer.viking.Model.UserDetailModels.myDetailsModel;

public class LocalDatabase {
    static SharedPreferences preferences;
    static SharedPreferences.Editor editor;

    public static Boolean isLogin(Context ctx){
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return preferences.getBoolean("isLogin",false);
    }

    public static String getToken(Context ctx){
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return preferences.getString("token","");
    }

    static String getEmail(Context ctx){
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return preferences.getString("email","");
    }
    static String getPassword(Context ctx){
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return preferences.getString("password","");
    }
    public static AuthModel getLoginDetails(Context ctx){
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        AuthModel authModel=new AuthModel(getEmail(ctx),getPassword(ctx));
        return authModel;
    }
    public static void setEmailandPassword(Context ctx, AuthModel authModel){
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = preferences.edit();
        editor.putString("email",authModel.getEmailornickname());
        editor.putString("password",authModel.getSifre());
        editor.apply();
    }
    public static void setUserDetails(Context ctx, myDetailsModel myDetailsModel){
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = preferences.edit();
        editor.putInt("myId",myDetailsModel.getUser_id());
        editor.putString("myNickName",myDetailsModel.getNick());
        editor.putString("myFirstName",myDetailsModel.getAd());
        editor.putString("myLastName",myDetailsModel.getSoyad());
        editor.putString("myEmail",myDetailsModel.getEmail());
        editor.putString("myBiyografi",myDetailsModel.getBiyografi());
        editor.putString("myAvatarUrl",myDetailsModel.getAvatar());
        editor.apply();
    }
    public static int getmyId(Context ctx){
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return preferences.getInt("myId",0);
    }
    public static myDetailsModel getUserDetails(Context ctx){
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        myDetailsModel model=new myDetailsModel(preferences.getString("myNickName",""),preferences.getString("myFirstName",""),preferences.getString("myLastName",""),preferences.getString("myEmail",""),preferences.getString("myAvatarUrl",""),preferences.getString("myBiyografi",""),preferences.getInt("myId",0));
        return model;
    }
    public static void Logout(Context ctx){
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = preferences.edit();
        editor.putBoolean("isLogin",false);
        editor.apply();
    }
    public static void setEmail(Context ctx,String email){
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = preferences.edit();
        editor.putString("email",email);
        editor.apply();

    }
    public static void setPassword(Context ctx,String password){
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = preferences.edit();
        editor.putString("password",password);
        editor.apply();
    }
    public static void setToken(Context ctx, GetTokenModel getTokenModel){
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = preferences.edit();
        editor.putString("token",getTokenModel.getToken());
        editor.putString("expiration",getTokenModel.getExpiration());
        editor.putBoolean("isLogin",true);
        editor.apply();
    }

}
