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

