package presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.networkmodule.simpleJsonRequest.Url_JsonRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.LoginModel;
import presenter.app_config.MainApp;
import presenter.util.JsonObjFetcher;
import view.LoginActivity;
import view.SignUpActivity;

/**
 * Created by ddopi on 7/9/2017.
 */

public class SignUpPresenter extends Url_JsonRequest {


    private Context activityContext;
    private SignUpActivity signUpActivity;
    private String url = "http://nomw.code-court.com/index/loginn/tobe/3162696a";
    private HashMap loginHashMap;
    private String userName;
    private String passWord;
    private JSONObject jsonObjectobj;
    private JsonObjFetcher jsonObjFetcher = new JsonObjFetcher();
    private LoginModel loginModel = new LoginModel();
    private Map<String, String> signUpFormMap;


    public SignUpPresenter(Context context, Map<String, String> signUpHashMap) {
        super(context);
        activityContext = context;
        this.signUpFormMap=signUpHashMap;
    }

    @Override
    public String getUrl() {
        return MainApp.loginUrl + "?name=" + signUpFormMap.get("name") + "&username=" + signUpFormMap.get("username") + "&password=" + signUpFormMap.get("input_password") + "&mail=" + signUpFormMap.get("input_email") + "&gender=" + signUpFormMap.get("gender") + "&mobile=" + signUpFormMap.get("mobile") + "";

    }

    @Override
    public void sentRequest(boolean casheRequest) {
        super.sentRequest(false);
    }

    @Override
    public PresenterRequest getPresenterRequest() {

        return new PresenterRequest() {
            @Override
            public void jsonRequest(JSONObject jsonObjectobjRecived) {
                jsonObjectobj = jsonObjectobjRecived;
                try {
                    if (!jsonObjectobj.equals(null)) {
                        Log.e("SignUpPresenter", "sendSignUpRequest CallBack" + jsonObjectobj.toString());

                        Toast.makeText(activityContext, "True ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activityContext, LoginActivity.class);
                        activityContext.startActivity(intent);
                    } else {
                        Toast.makeText(activityContext, "please check your data and try again", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("SignUpPresenter", "error fetching" + e.getMessage());
                    Toast.makeText(activityContext, "please check your data and try again", Toast.LENGTH_LONG).show();
                }

            }
        };

    }


    public HashMap getLoginHashMap() {
        loginHashMap = new HashMap<String, String>();
        loginHashMap.put("user_id", "1");
        loginHashMap.put("user_name", "franky");
        loginHashMap.put("user_password", "123456");

        return loginHashMap;

    }

}


//    private Context activityContext;
//    private SignUpActivity signUpActivity;
//    private SignUpRequsest signUpRequsest;
//    private String url = "http://nomw.code-court.com/index/loginn/tobe/3162696a";
//    private HashMap loginHashMap;
//    private String userName;
//    private String passWord;
//    private JSONObject jsonObjectobj;
//    private SignUpPresenter.SignUPresenterRequest signUpPresenterRequest;
//    private JsonObjFetcher jsonObjFetcher=new JsonObjFetcher();
//    private LoginModel loginModel=new LoginModel();
//
//    public SignUpPresenter(Context context)
//    {
//        activityContext=context;
//        signUpRequsest =new SignUpRequsest(context);
//        signUpActivity=(SignUpActivity)activityContext;
//    }
//
//    public void sendSignUpRequest(Map<String,String> signUpFormMap)
//    {
//
//
//        signUpPresenterRequest=new SignUpPresenter.SignUPresenterRequest(){
//            @Override
//            public void jsonRequest(JSONObject jsonObjectobjRecived) {
//                jsonObjectobj=jsonObjectobjRecived;
//
//                try{
////                    if(jsonObjectobj.get("success").equals("ok")) {
//                    if(!jsonObjectobj.equals(null)) {
//                        Log.e("LoginPresenter", "sendSignUpRequest CallBack" + jsonObjectobj.toString());
////                        HashMap<String, String> map = jsonObjFetcher.fetchLoginObj(jsonObjectobj);
////                    loginModel.setUser(map); ////save user locally
//                        Toast.makeText(activityContext,"True ", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(activityContext, LoginActivity.class);
//                        activityContext.startActivity(intent);
//
//                    }
//                    else
//                    {
//
//                        Toast.makeText(activityContext,"please check your data and try again", Toast.LENGTH_LONG).show();
//
//
//                    }
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                    Log.e("LoginPresenter","error fetching"+e.getMessage());
//
//                    Toast.makeText(activityContext,"please check your data and try again", Toast.LENGTH_LONG).show();
//
//
//
//                }
//
//            }
//        };
//        signUpRequsest.sentRequest(signUpFormMap,signUpPresenterRequest);
//    }
//    public HashMap getLoginHashMap()
//    {
//        loginHashMap=new HashMap<String,String>();
//        loginHashMap.put("user_id","1");
//        loginHashMap.put("user_name","franky");
//        loginHashMap.put("user_password","123456");
//
//        return loginHashMap;
//
//    }
//
//
///**
// * Created by ddopi on 5/30/2017.
// */
//
//public static interface SignUPresenterRequest {
//    public void jsonRequest(JSONObject jsonObjectobj);
//}
