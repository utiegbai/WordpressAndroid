package com.entpress.entpress.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by utimac on 26/01/2018.
 */
public class UserDetails implements  Parcelable {

    private String userId;

    private String name;

    private String username;

    private String email;

    private String bio;

    private String password;

    private String verified;

    private String created_at;

    private String updated_at;

    private String token;


    public UserDetails() {

    }

    public void setUserId(String userId){ this.userId = userId;}
    public String getUserId(){return this.userId;}
    public void setName(String name){this.name = name;}
    public String getName(){return this.name;}
    public void setUsername(String username){this.username=username;}
    public String getUsername(){return  this.username;}
    public void setEmail(String email){this.email = email;}
    public String getEmail(){return this.email;}
    public void setBio(String bio){ this.bio = bio;}
    public String getBio(){return this.bio;}
    public void setPassword(String password){ this.password = password;}
    public String getPassword(){return this.password;}
    public void setVerified(String verified){ this.verified = verified;}
    public String getVerified(){return this.verified;}
    public void setCreated_at(String created_at){this.created_at = created_at;}
    public String getCreated_at(){return this.created_at;}
    public void setUpdated_at(String updated_at){this.updated_at = updated_at;}
    public String getUpdated_at(){return this.updated_at;}
    public void setToken(String token){this.token = token;}
    public String getToken(){return this.token;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // create a bundle for the key value pairs
        Bundle bundle = new Bundle();
        // insert the key value pairs to the bundle
        bundle.putString("userid",userId);
        bundle.putString("name",name);
        bundle.putString("username",username);
        bundle.putString("email",email);
        bundle.putString("bio",bio);
        bundle.putString("password",password);
        bundle.putString("verified",verified);
        bundle.putString("created_at",created_at);
        bundle.putString("updated_at",updated_at);
        bundle.putString("token",token);
        // write the key value pairs to the parcel
        dest.writeBundle(bundle);
    }


    public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>() {

        @Override
        public UserDetails createFromParcel(Parcel source) {
            // read the bundle containing key value pairs from the parcel
            Bundle bundle = source.readBundle(getClass().getClassLoader());
            // instantiate a userdetails using values from the bundle
            UserDetails userDetails = new UserDetails();
            userDetails.setUserId(bundle.getString("userid"));
            userDetails.setName(bundle.getString("name"));
            userDetails.setUsername(bundle.getString("username"));
            userDetails.setEmail(bundle.getString("email"));
            userDetails.setBio(bundle.getString("bio"));
            userDetails.setPassword(bundle.getString("password"));
            userDetails.setVerified(bundle.getString("verified"));
            userDetails.setCreated_at(bundle.getString("created_at"));
            userDetails.setUpdated_at(bundle.getString("updated_at"));
            userDetails.setToken(bundle.getString("token"));
            return userDetails;
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }

    };
}
