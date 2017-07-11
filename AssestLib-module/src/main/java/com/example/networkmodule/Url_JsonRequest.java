package com.example.networkmodule;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by ddopi on 7/10/2017.
 *
 *   if you parent need field to be intialized before abstract method in the child being impleented
 *   call parent constructur with super(required fiels,required fiels,required fiels);
 *   1---case when parent constructor calling abstract method  through it's constructor
 */

public abstract class Url_JsonRequest {


    private RequestQueue requestQueue;
    Context activityContext;
    private JSONObject mJsonObj;
    private Response.Listener<JSONObject> listener;
    private Map<String, String> params;
    private PresenterRequest presenterRequest;
    private   String loginUrl;

    public abstract String getUrl();
    public abstract PresenterRequest getPresenterRequest();

    public Url_JsonRequest(Context context) {
        activityContext = context;
    }
    public JSONObject sentRequest() {


        loginUrl=this.getUrl().replaceAll("\\s+", ""); ///remove unnessasary spaces
        requestQueue = Volley.newRequestQueue(activityContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mJsonObj = new JSONObject(response);
                    getPresenterRequest().jsonRequest(mJsonObj);
                    Log.e(TAG, "Json converted----------->" + mJsonObj.toString());
                    Log.e(TAG,"url is---->"+loginUrl);
                } catch (Exception e) {
                    Log.e(TAG, "Error---->: \"" + e.toString()+ "\"");
                    Log.e(TAG, "Could not parse Returned JSON as Response---->: \"" + response + "\"");
                    Log.e(TAG,"url is---->"+loginUrl);
                    getPresenterRequest().jsonRequest(null);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", "error with" + error.toString());
            }
        }) {

        };
        requestQueue.add(stringRequest);
        return mJsonObj;
    }


    public  interface PresenterRequest {
        //// implement this interFace an handle recived obj from Request
        public void jsonRequest(JSONObject jsonObjectobj);
    }
}
