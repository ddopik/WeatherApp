package presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import com.example.networkmodule.Url_JsonRequest;

import org.json.JSONObject;

import java.util.HashMap;

import model.LoginModel;
import presenter.app_config.MainApp;
import presenter.util.JsonObjFetcher;
import view.LoginActivity;
import view.MainActivity;

/**
 * Created by ddopi on 5/28/2017.
 */

public class LoginPresenter extends Url_JsonRequest {


    private Context activityContext;
    private LoginActivity loginActivity;
    private PresenterRequest presenterRequest;
//    private String url = "http://nomw.code-court.com/index/loginn/tobe/3162696a";
    private HashMap loginHashMap;
    private String userName;
    private String passWord;
    private JSONObject jsonObjectobj;
    private JsonObjFetcher jsonObjFetcher = new JsonObjFetcher();
    private LoginModel loginModel = new LoginModel();


    public LoginPresenter(Context context, String userName, String passWord) {
        super(context);
        activityContext = context;
        loginActivity = (LoginActivity) activityContext;
        this.userName = userName;
        this.passWord = passWord;

    }

    @Override
    public String getUrl() {
        return MainApp.loginUrl + "?username=" + userName + "&password=" + passWord;
    }

    @Override
    public PresenterRequest getPresenterRequest() {

        this.presenterRequest = new PresenterRequest() {
            @Override
            public void jsonRequest(JSONObject jsonObjectobjRecived) {
                jsonObjectobj = jsonObjectobjRecived;

                    try {
                    if (jsonObjectobj.get("success").equals("ok")) {
                        Log.e("LoginPresenter", "sendSignUpRequest CallBack" + jsonObjectobj.toString());
                        HashMap<String, String> map = jsonObjFetcher.fetchLoginObj(jsonObjectobj);
//                    loginModel.setUser(map); ////save user locally
                        Toast.makeText(activityContext, "Welcome " + map.get("UserName"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activityContext, MainActivity.class);
                        activityContext.startActivity(intent);
                        loginActivity.showProgress(false);
                    } else {
                        loginActivity.showProgress(false);
                        Toast.makeText(activityContext, "please check your data and try again", Toast.LENGTH_LONG).show();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("LoginPresenter", "error fetching" + e.getMessage());
                    loginActivity.showProgress(false);
                    Toast.makeText(activityContext, "please check your data and try again", Toast.LENGTH_LONG).show();
                }

            }
        };
        return presenterRequest;
    }

    @Override
    public JSONObject sentRequest() {
        return super.sentRequest();
    }

//    public HashMap getLoginHashMap() {
//        loginHashMap = new HashMap<String, String>();
//        loginHashMap.put("user_id", "1");
//        loginHashMap.put("user_name", "franky");
//        loginHashMap.put("user_password", "123456");
//
//        return loginHashMap;
//
//    }


}
