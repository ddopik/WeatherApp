package presenter.network;

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

import presenter.app_config.MainApp;
import presenter.LoginPresenter;

/**
 * Created by ddopik on 5/28/2017.
 */

public class LoginRequest {

    public static final String TAG = LoginRequest.class.getSimpleName();
    private RequestQueue requestQueue;
    Context activityContext;
//        String activeCourcesUrl = "http://nomw.code-court.com/index/loginn/tobe/3162696a";
    String loginUrl ;
    private JSONObject mJsonObj;
    private Response.Listener<JSONObject> listener;
    private Map<String, String> params;
    private LoginPresenter.LoginPresenterRequest loginPresenterRequest;

    public LoginRequest(Context context) {
        activityContext = context;
    }

    public JSONObject sentRequest(final String userName, final String passWord, LoginPresenter.LoginPresenterRequest loginCallBackRequest) {
        http://www.smartpan.com.sa:5551/AndriodAPI/login?username=asd&password=123
            this.loginUrl = MainApp.loginUrl+"?username="+userName+"&password="+passWord;
            this.loginUrl.replaceAll("\\s+", ""); ///remove unnessasary spaces
            requestQueue = Volley.newRequestQueue(activityContext);
            loginPresenterRequest=loginCallBackRequest;
//            final String requestBody = getJSONObject(); JSON Obj to send to server

            StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                    Log.e("VOLLEY--->onResponse","response true with"+ response.toString());
                    try {
                        mJsonObj = new JSONObject(response);
                        loginPresenterRequest.jsonRequest(mJsonObj);
                        Log.e(TAG, "Json converted----------->" + mJsonObj.toString());
                        Log.e(TAG, "Json status----------->" + mJsonObj.get("success").toString());

                    } catch (Exception e) {
                        Log.e(TAG, "Error---->: \"" + e.toString()+ "\"");
                        Log.e(TAG, "Could not parse Returned JSON as Response---->: \"" + response + "\"");
                        Log.e(TAG, "user name was-->"+userName+"--Password is--->"+passWord);
                        Log.e(TAG,"url is---->"+loginUrl);
                        loginPresenterRequest.jsonRequest(null);
//                        Toast.makeText(activityContext,"please check your data and try again",Toast.LENGTH_LONG).show();
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

    public String getJSONObject() {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Title", "Android Volley Demo");
            jsonBody.put("Author", "BNK");
            return jsonBody.toString();
        } catch (Throwable t) {
            Log.e(TAG, "Could create JSON Map");
            return null;
        }

    }
}
