package view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ddopi.numuu.FacebookLogin;
import com.example.ddopikmain.seedapplication.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import presenter.app_config.MainApp;
import presenter.LoginPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    //    /For facebook
//    private CallbackManager callbackManager;

//    @BindView(R.id.connectWithFbButton)
//    public LoginButton facebook_button;
//    ProgressDialog progress;
//    private String facebook_id, f_name, m_name, l_name, gender, profile_image, full_name, email_id;


    // UI references.
    @BindView(R.id.email)
    public AutoCompleteTextView mEmailView;
    @BindView(R.id.password)
    public EditText mPasswordView;
    @BindView(R.id.email_sign_in_button)
    public Button email_sign_in_button;
    @BindView(R.id.login_progress)
    public View mProgressView;
    @BindView(R.id.login_form)
    public View mLoginFormView;
    @BindView(R.id.sign_up)
    TextView sign_up;
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Log.e("LoginActivity", "---->OnCreate()");
/////////////////////////////////
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {

        }


        Fb_FragmentButton singleNewsActivity = new Fb_FragmentButton();
                    getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fb_switch_fragment, singleNewsActivity, null)
                    .commit();

/////////////////////////////////



        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });

    }

    @OnClick(R.id.email_sign_in_button)
    public void startLogin() {
        loginPresenter = new LoginPresenter(this,mEmailView.getText().toString(), mPasswordView.getText().toString());
        if (MainApp.isInternetAvailable()) {
            loginPresenter.showProgress(true);
            loginPresenter.attemptLogin();
        } else {
            Toast.makeText(this, "Please check your internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.sign_up)
    public void launchSignUp() {
        Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(i);
    }






}

// facebook_button = (LoginButton) findViewById(R.id.connectWithFbButton);
//         progress = new ProgressDialog(LoginActivity.this);
//         progress.setMessage(getResources().getString(R.string.please_wait_facebooklogin));
//         progress.setIndeterminate(false);
//         progress.setCancelable(false);
//
//         facebook_id = f_name = m_name = l_name = gender = profile_image = full_name = email_id = "";
//         //for facebook
//         callbackManager = CallbackManager.Factory.create();
//         // Callback registration
//         LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//@Override
//public void onSuccess(LoginResult loginResult) {
//        // App code
//        progress.show();
//        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//@Override
//public void onCompleted(JSONObject object, GraphResponse response) {
//        Log.v("LoginActivity", response.toString());
//        try {
//        Log.e("json is ", object.toString());
//        Log.e("response is ", response.toString());
//        String email = object.getString("email");
//        String birthday = object.getString("birthday"); // 01/31/1980 format
//
//        email_id = object.getString("email");
//        gender = object.getString("gender");
//        String profile_name = object.getString("name");
//        long fb_id = object.getLong("id"); //use this for logout
////                                    Start new activity or use this info in your project.
//
//
//        SharedPreferences.Editor editor = getSharedPreferences("task_shared_pref", MODE_PRIVATE).edit();
//        editor.putLong("fb_id", fb_id);
//        editor.apply();
//
//
//        Intent i = new Intent(LoginActivity.this, MainActivity.class);
//        i.putExtra("type", "facebook");
//        i.putExtra("facebook_id", facebook_id);
//        i.putExtra("f_name", f_name);
//        i.putExtra("m_name", m_name);
//        i.putExtra("l_name", l_name);
//        i.putExtra("full_name", full_name);
//        i.putExtra("profile_image", profile_image);
//        i.putExtra("email_id", email_id);
//        i.putExtra("gender", gender);
//        progress.dismiss();
//        startActivity(i);
//        finish();
//
//        } catch (Exception e) {
//        Log.e("FaceBook Login", "error" + e.getMessage());
//        progress.dismiss();
//        }
//
//        }
//        });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,email,gender,birthday");
//        request.setParameters(parameters);
//        request.executeAsync();
//
//
//        }
//
//@Override
//public void onCancel() {
//        Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_canceled_facebooklogin), Toast.LENGTH_SHORT).show();
//        progress.dismiss();
//        }
//
//@Override
//public void onError(FacebookException error) {
//        Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_failed_facebooklogin), Toast.LENGTH_SHORT).show();
//        Log.e("FaceBook Login", "error" + error.getMessage());
//        progress.dismiss();
//        }
//        });
//
//        //facebook button click
//        facebook_button.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends", "email"));
//        }
//        });