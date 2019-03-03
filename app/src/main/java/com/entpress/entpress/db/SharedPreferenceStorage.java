package com.entpress.entpress.db;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.entpress.entpress.App;
import com.entpress.entpress.models.UserDetails;

import java.util.ArrayList;

import static com.entpress.entpress.constants.Constants.PREFS.GCM_TOKEN;
import static com.entpress.entpress.constants.Constants.PREFS.USER_DATA;
import static com.entpress.entpress.constants.Constants.PREFS.USER_INTERESTS;
import static com.entpress.entpress.constants.Constants.PREFS.USER_REGISTRATION_STEP;


/**
 * Created by ray on 08/02/2018.
 */
public class SharedPreferenceStorage {

    public static void setUserData(UserDetails userDetails){
        SharedPreferences sharedPrefs;
        SharedPreferences.Editor editor;
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userDetails);
        editor.putString(USER_DATA, json);
        editor.apply();
    }

    public static UserDetails getUserData(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString(USER_DATA, "");
        return gson.fromJson(json, UserDetails.class);
    }

    public static void setUserRegistrationStep(int type){
        SharedPreferences sharedPrefs;
        SharedPreferences.Editor editor;
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        editor = sharedPrefs.edit();
        editor.putInt(USER_REGISTRATION_STEP, type);
        editor.apply();
    }

    public static int getUserRegistrationStep(){
        return PreferenceManager.getDefaultSharedPreferences(App.getContext()).getInt(USER_REGISTRATION_STEP, 0);
    }

    public static void setUserInterests(ArrayList interests){
        SharedPreferences sharedPrefs;
        SharedPreferences.Editor editor;
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(interests);
        editor.putString(USER_INTERESTS, json);
        editor.apply();
    }

    public static ArrayList getUserInterests(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString(USER_INTERESTS, "");
        return gson.fromJson(json, ArrayList.class);
    }

    public static void setUserGCMToken(String token){
        SharedPreferences sharedPrefs;
        SharedPreferences.Editor editor;
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        editor = sharedPrefs.edit();
        editor.putString(GCM_TOKEN, token);
        editor.apply();
    }

    public static String getUserGCMToken(){
        return PreferenceManager.getDefaultSharedPreferences(App.getContext()).getString(GCM_TOKEN, "");
    }
}
