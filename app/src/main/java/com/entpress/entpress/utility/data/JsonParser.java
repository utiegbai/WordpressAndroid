package com.entpress.entpress.utility.data;


import android.content.Context;
import android.util.Log;


import com.entpress.entpress.models.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ray on 07/02/2018.
 */

public class JsonParser {

    public JsonParser(){

    }

    public static JSONObject getUser(UserDetails userDetails) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("userId",userDetails.getUserId());
            jsonObject.put("name",userDetails.getName());
            jsonObject.put("username",userDetails.getUsername());
            jsonObject.put("email",userDetails.getEmail());
            jsonObject.put("bio",userDetails.getBio());
            jsonObject.put("password",userDetails.getPassword());
            jsonObject.put("verified",userDetails.getVerified());
            jsonObject.put("created_at",userDetails.getCreated_at());
            jsonObject.put("updated_at",userDetails.getUpdated_at());
            jsonObject.put("token",userDetails.getToken());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("get user error",e.getMessage());
        }
        return jsonObject;
    }

    public static UserDetails getUser(JSONObject jsonObject) {
        UserDetails userDetails = new UserDetails();

        try {
            Log.e("name",jsonObject.getString("user_login"));
            Log.e("USERID", ""+jsonObject.getString("ID"));
            userDetails.setUserId(jsonObject.getString("ID"));
            userDetails.setName(jsonObject.getString("display_name"));
            userDetails.setUsername(jsonObject.getString("user_login"));
            userDetails.setEmail(jsonObject.getString("user_email"));
            userDetails.setBio(jsonObject.getString("user_url"));
            userDetails.setPassword(jsonObject.getString("user_pass"));
            userDetails.setVerified(jsonObject.getString("user_status"));
            userDetails.setCreated_at(jsonObject.getString("user_registered"));
            userDetails.setToken(jsonObject.getString("user_activation_key"));
            //TODO
            //remove below line and pass location direct
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("parse user error",e.getMessage());
        }
        return userDetails;
    }



}
