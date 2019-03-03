package com.entpress.entpress.models;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by utimac on 26/01/2018.
 */
public class Gallery implements  Parcelable {

    private String path;
    private String type;
    private Bitmap bitmap = null;

    public Gallery() {

    }

    public void setPath(String path){ this.path = path;}
    public String getPath(){return this.path;}
    public void setType(String type){ this.type = type;}
    public String getType(){return this.type;}
    public void setBitmap(Bitmap bitmap){ this.bitmap = bitmap;}
    public Bitmap getBitmap(){return this.bitmap;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // create a bundle for the key value pairs
        Bundle bundle = new Bundle();
        // insert the key value pairs to the bundle
        bundle.putString("path",path);
        bundle.putString("type",type);
        if(bitmap!=null) bundle.putParcelable("bitmap",bitmap);
        // write the key value pairs to the parcel
        dest.writeBundle(bundle);
    }


    public static final Creator<Gallery> CREATOR = new Creator<Gallery>() {

        @Override
        public Gallery createFromParcel(Parcel source) {
            // read the bundle containing key value pairs from the parcel
            Bundle bundle = source.readBundle(getClass().getClassLoader());
            // instantiate a userdetails using values from the bundle
            Gallery gallery = new Gallery();
            gallery.setPath(bundle.getString("path"));
            gallery.setType(bundle.getString("type"));
            if(bundle.getParcelable("bitmap")!=null)
            gallery.setBitmap((Bitmap)bundle.getParcelable("bitmap"));

            return gallery;
        }

        @Override
        public Gallery[] newArray(int size) {
            return new Gallery[size];
        }

    };
}
