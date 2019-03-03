package com.entpress.entpress.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by utimac on 26/01/2018.
 */
public class Uploads implements  Parcelable {

    private String file_name,file;

    public Uploads() {

    }

    public void setFilename(String file_name){ this.file_name = file_name;}
    public String getFilename(){return this.file_name;}

    public void setFile(String file){ this.file = file;}
    public String getFile(){return this.file;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // create a bundle for the key value pairs
        Bundle bundle = new Bundle();
        // insert the key value pairs to the bundle
        bundle.putString("file_name",file_name);
        bundle.putString("file",file);
        // write the key value pairs to the parcel
        dest.writeBundle(bundle);
    }


    public static final Creator<Uploads> CREATOR = new Creator<Uploads>() {

        @Override
        public Uploads createFromParcel(Parcel source) {
            // read the bundle containing key value pairs from the parcel
            Bundle bundle = source.readBundle(getClass().getClassLoader());
            // instantiate a userdetails using values from the bundle
            Uploads interests = new Uploads();
            interests.setFilename(bundle.getString("file_name"));
            interests.setFile(bundle.getString("file"));
            return interests;
        }

        @Override
        public Uploads[] newArray(int size) {
            return new Uploads[size];
        }

    };
}
