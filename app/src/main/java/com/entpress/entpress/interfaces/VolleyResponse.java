package com.entpress.entpress.interfaces;

import com.android.volley.VolleyError;


/**
 * Created by ray on 02/04/2018.
 */
public interface VolleyResponse {
     void onSuccess(String result);
     void requestError(VolleyError error);
}
