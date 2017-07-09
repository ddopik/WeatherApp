package presenter.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

import presenter.LoginPresenter;
import presenter.SignUpPresenter;
import presenter.app_config.MainApp;

/**
 * Created by ddopi on 7/9/2017.
 */

public class SignUpRequsest {
    public static final String TAG = LoginRequest.class.getSimpleName();
    private RequestQueue requestQueue;
    Context activityContext;
    //        String activeCourcesUrl = "http://nomw.code-court.com/index/loginn/tobe/3162696a";
    String loginUrl ;
    private JSONObject mJsonObj;
    private Response.Listener<JSONObject> listener;
    private Map<String, String> params;
    private SignUpPresenter.SignUPresenterRequest signUpPresenterRequest;

    public SignUpRequsest(Context context) {
        activityContext = context;
    }

    public JSONObject sentRequest(Map<String,String> signUpFormMap, SignUpPresenter.SignUPresenterRequest signUPresenterRequestg) {
        http://www.smartpan.com.sa:5551/AndriodAPI/login?username=asd&password=123

//        Register(string name, string username, string password, string mail, int gender(0 male , 1 female), string mobile )
//        this.loginUrl = MainApp.loginUrl+"?username="+userName+"&password="+passWord;
        this.loginUrl = MainApp.loginUrl+"?name="+signUpFormMap.get("name")+"&username="+signUpFormMap.get("username")+"&password="+signUpFormMap.get("input_password")+"&mail="+signUpFormMap.get("input_email")+"&gender="+signUpFormMap.get("gender")+"&mobile="+signUpFormMap.get("mobile")+"";
        this.loginUrl.replaceAll("\\s+", ""); ///remove unnessasary spaces
        requestQueue = Volley.newRequestQueue(activityContext);
        this.signUpPresenterRequest =signUPresenterRequestg;
//            final String requestBody = getJSONObject(); JSON Obj to send to server

        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                    Log.e("VOLLEY--->onResponse","response true with"+ response.toString());
                try {
                    mJsonObj = new JSONObject(response);
                    signUpPresenterRequest.jsonRequest(mJsonObj);
                    Log.e(TAG, "Json converted----------->" + mJsonObj.toString());
//                    Log.e(TAG, "Json status----------->" + mJsonObj.get("success").toString());

                } catch (Exception e) {
                    Log.e(TAG, "Error---->: \"" + e.toString()+ "\"");
                    Log.e(TAG, "Could not parse Returned JSON as Response---->: \"" + response + "\"");
                    Log.e(TAG,"url is---->"+loginUrl);
                    signUpPresenterRequest.jsonRequest(null);
//                    Toast.makeText(activityContext,"please check your data and try again", Toast.LENGTH_LONG).show();
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
