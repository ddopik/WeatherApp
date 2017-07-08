package view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
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


import com.example.ddopikmain.seedapplication.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

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
    private CallbackManager callbackManager;

    @BindView(R.id.connectWithFbButton)
    public Button facebook_button;
    ProgressDialog progress;
    private String facebook_id,f_name, m_name, l_name, gender, profile_image, full_name, email_id;


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
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);

        Log.e("LoginActivity","---->OnCreate()");


        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });

        facebook_id=f_name= m_name= l_name= gender= profile_image= full_name= email_id="";

        //for facebook
//        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        //register callback object for facebook result
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                progress.show();
                Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                    facebook_id = profile.getId();
                    f_name = profile.getFirstName();
                    m_name = profile.getMiddleName();
                    l_name = profile.getLastName();
                    full_name = profile.getName();
                    profile_image = profile.getProfilePictureUri(400, 400).toString();
                }
                //Toast.makeText(FacebookLogin.this,"Wait...",Toast.LENGTH_SHORT).show();
                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    email_id = object.getString("email");
                                    gender = object.getString("gender");
                                    String profile_name = object.getString("name");
                                    long fb_id = object.getLong("id"); //use this for logout
                                    //Start new activity or use this info in your project.
                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    i.putExtra("type", "facebook");
                                    i.putExtra("facebook_id", facebook_id);
                                    i.putExtra("f_name", f_name);
                                    i.putExtra("m_name", m_name);
                                    i.putExtra("l_name", l_name);
                                    i.putExtra("full_name", full_name);
                                    i.putExtra("profile_image", profile_image);
                                    i.putExtra("email_id", email_id);
                                    i.putExtra("gender", gender);
                                    Log.e("LoginActivity","---->facebook true()");
//                                    progress.dismiss();
//                                    startActivity(i);
//                                    finish();
                                    Toast.makeText(LoginActivity.this, "facebook true", Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    Log.e("LoginActivity","-------Errore--->"+e.getMessage());
                                      e.printStackTrace();
                                }

                            }

                        });

                request.executeAsync();

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("LoginActivity","-------Errore--->"+error.getMessage());
                error.printStackTrace();

            }

            @Override
            public void onCancel() {

            }
        });
    }

    @OnClick(R.id.email_sign_in_button)
    public void startLogin() {
        if (MainApp.isInternetAvailable()) {
            showProgress(true);
            attemptLogin();
        } else {
            Toast.makeText(this, "Please check your internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {


        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else { ///Login Processes validation is true

//            loginPresenter.sendLoginRequest(mEmailView.getText().toString(), mPasswordView.getText().toString());

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

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        Log.e("LoginActivity","#####Calling");
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);

        }
    }


}

