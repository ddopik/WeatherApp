package presenter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.ddopikmain.seedapplication.R;
import com.example.networkmodule.simpleJsonRequest.Url_JsonRequest;

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
                        showProgress(false);
                    } else {
                        showProgress(false);
                        Toast.makeText(activityContext, "please check your data and try again", Toast.LENGTH_LONG).show();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("LoginPresenter", "error fetching  --->" + e.getMessage());
                    showProgress(false);
                    Toast.makeText(activityContext, "please check your data and try again", Toast.LENGTH_LONG).show();
                }

            }
        };
        return presenterRequest;
    }

    @Override
    public void sentRequest(boolean casheState) {
         super.sentRequest(false);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        Log.e("LoginActivity", "inProgress");
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = loginActivity.getResources().getInteger(android.R.integer.config_shortAnimTime);
            loginActivity.mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginActivity.mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginActivity.mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            loginActivity.mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginActivity.mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginActivity.mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            loginActivity.mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginActivity.mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);

        }

        ///////////////////////////////////////////


    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {

        // Reset errors.
        loginActivity.mEmailView.setError(null);
        loginActivity.mPasswordView.setError(null);
        // Store values at the time of the login attempt.
        String email = loginActivity.mEmailView.getText().toString();
        String password = loginActivity.mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            loginActivity.mPasswordView.setError(loginActivity.getString(R.string.error_invalid_password));
            focusView = loginActivity.mPasswordView;
            cancel = true;
            showProgress(false);
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            loginActivity.mEmailView.setError(loginActivity.getString(R.string.error_field_required));
            focusView = loginActivity.mEmailView;
            cancel = true;
            showProgress(false);
        } else if (!isEmailValid(email)) {
            loginActivity.mEmailView.setError(loginActivity.getString(R.string.error_invalid_email));
            focusView = loginActivity.mEmailView;
            cancel = true;
            showProgress(false);
        }
//        showProgress(false);
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            showProgress(false);
        } else { ///Login Processes validation is true


            sentRequest(false);
        }
    }


    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
//        return email.contains("@");
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 2;
    }
}
