package com.entpress.entpress.utility.api;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.entpress.entpress.App;


/**
 * Created by ray on 04-02-2018.
 */
public class VolleySingleton {
    private static VolleySingleton sInstance=null;
    private RequestQueue mRequestQueue;

    private VolleySingleton(){
        mRequestQueue = Volley.newRequestQueue(App.getContext());
    }
    public static VolleySingleton getInstance(){
        if(sInstance==null)
        {
            sInstance=new VolleySingleton();
        }
        return sInstance;
    }
    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}
