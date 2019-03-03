package com.entpress.entpress.utility.api;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.entpress.entpress.interfaces.VolleyResponse;

import java.io.UnsupportedEncodingException;

/**
 * Created by utimac on 11/24/2015.
 */

public class ApiCall {
    RequestQueue requestQueue;
    VolleySingleton volley;
    //UserDetails loginUser = App.localDb.getUserData();

    public ApiCall(){

    }

    public void get(final VolleyResponse response, String url) {
        volley = VolleySingleton.getInstance();
        requestQueue = volley.getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.GET,url , new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String result) {
                        response.onSuccess(result);

                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        response.requestError(error);
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String auth = "Token " + loginUser.getToken();
                headers.put("Authorization", auth);
                return headers;
            }*/

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse networkResponse) {
                try {
                    String jsonString = new String(networkResponse.data,
                            HttpHeaderParser.parseCharset(networkResponse.headers));
                    return Response.success(jsonString,
                            HttpHeaderParser.parseCacheHeaders(networkResponse));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
   }

    public void post(final VolleyResponse response, String url, final String requestBody) {
        volley = VolleySingleton.getInstance();
        requestQueue = volley.getRequestQueue();
       StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String result) {
                    Log.i("VOLLEY", result);
                    response.onSuccess(result);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                    response.requestError(error);
                }
            }) {
           @Override
           public String getBodyContentType() {
               return "application/json; charset=utf-8";
           }

           @Override
           public byte[] getBody() throws AuthFailureError {
               try {
                   return requestBody == null ? null : requestBody.getBytes("utf-8");
               } catch (UnsupportedEncodingException uee) {
                   VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                   return null;
               }
           }

           @Override
           protected Response<String> parseNetworkResponse(NetworkResponse networkResponse) {
               try {
                   String jsonString = new String(networkResponse.data,
                           HttpHeaderParser.parseCharset(networkResponse.headers));
                   return Response.success(jsonString,
                           HttpHeaderParser.parseCacheHeaders(networkResponse));
               } catch (UnsupportedEncodingException e) {
                   return Response.error(new ParseError(e));
               }
           }
           /*@Override
           public Map<String, String> getHeaders() throws AuthFailureError {
               Map<String, String> headers = new HashMap<String, String>();
               String auth = "Token " + loginUser.getToken();
               headers.put("Authorization", auth);
               return headers;
           }*/
       };
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }

}
