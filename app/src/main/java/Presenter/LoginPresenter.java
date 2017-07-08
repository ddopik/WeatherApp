package Presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;



import org.json.JSONObject;

import java.util.HashMap;

import Model.LoginModel;
import Presenter.NetWork.LoginRequest;
import Presenter.Util.JsonObjFetcher;
import View.LoginActivity;
import View.MainActivity;

/**
 * Created by ddopi on 5/28/2017.
 */

public class LoginPresenter {


    private Context activityContext;
    private LoginActivity loginActivity;
    private LoginRequest loginRequest;
    private String url = "http://nomw.code-court.com/index/loginn/tobe/3162696a";
    private HashMap loginHashMap;
    private String userName;
    private String passWord;
    private JSONObject jsonObjectobj;
    private LoginPresenterRequest loginPresenterRequest;
    private JsonObjFetcher jsonObjFetcher=new JsonObjFetcher();
    private LoginModel loginModel=new LoginModel();

public LoginPresenter(Context context)
{
    activityContext=context;
    loginRequest=new LoginRequest(context);
    loginActivity=(LoginActivity)activityContext;
}

public void sendLoginRequest(String loginUserName, String loginPassWord)
{
    this.userName=loginUserName;
    this.passWord=loginPassWord;

    loginPresenterRequest=new LoginPresenterRequest(){
        @Override
        public void jsonRequest(JSONObject jsonObjectobjRecived) {
           jsonObjectobj=jsonObjectobjRecived;

            try{
                if(jsonObjectobj.get("status").equals("true")) {
                    Log.e("LoginPresenter", "sendLoginRequest CallBack" + jsonObjectobj.toString());
                    HashMap<String, String> map = jsonObjFetcher.fetchLoginObj(jsonObjectobj);
                    loginModel.setUser(map); ////save user locally
                    Toast.makeText(activityContext,"Welcome " +map.get("username"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activityContext, MainActivity.class);
                    activityContext.startActivity(intent);
                    loginActivity.showProgress(false);
                }
                else
                {
                    loginActivity.showProgress(false);
                    Toast.makeText(activityContext,"please check your data and try again", Toast.LENGTH_LONG).show();

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                loginActivity.showProgress(false);
                Toast.makeText(activityContext,"please check your data and try again", Toast.LENGTH_LONG).show();


            }

        }
    };
    loginRequest.sentRequest(userName,passWord,loginPresenterRequest);
}
public HashMap getLoginHashMap()
{
    loginHashMap=new HashMap<String,String>();
    loginHashMap.put("user_id","1");
    loginHashMap.put("user_name","franky");
    loginHashMap.put("user_password","123456");

    return loginHashMap;

}


    /**
     * Created by ddopi on 5/30/2017.
     */

    public static interface LoginPresenterRequest {
        public void jsonRequest(JSONObject jsonObjectobj);
    }
}
