package com.example.ddopi.numuu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ddopikmain.seedapplication.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import view.MainActivity;

public class FacebookLogin extends AppCompatActivity {


    //For facebook
    private CallbackManager callbackManager;

    private LoginButton facebook_button;
    ProgressDialog progress;
    private String facebook_id,f_name, m_name, l_name, gender, profile_image, full_name, email_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }
        try {
            getSupportActionBar().hide();
        }catch (NullPointerException e){

        }


        facebook_button=(LoginButton) findViewById(R.id.connectWithFbButton);
        facebook_button.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));


        progress=new ProgressDialog(FacebookLogin.this);
        progress.setMessage(getResources().getString(R.string.please_wait_facebooklogin));
        progress.setIndeterminate(false);
        progress.setCancelable(false);

        facebook_id=f_name= m_name= l_name= gender= profile_image= full_name= email_id="";
        //for facebook
        callbackManager = CallbackManager.Factory.create();
        // Callback registration
//        facebook_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                progress.show();
                Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                    facebook_id=profile.getId();
                    f_name=profile.getFirstName();
                    m_name=profile.getMiddleName();
                    l_name=profile.getLastName();
                    full_name=profile.getName();
                    profile_image=profile.getProfilePictureUri(400, 400).toString();
                }

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    Log.e("json is ",object.toString());
                                    Log.e("response is ",response.toString());
                                    String email = object.getString("email");
                                    String birthday = object.getString("birthday"); // 01/31/1980 format
                                    Log.e("json is ",object.toString());
                                    Log.e("response is ",response.toString());
                                    AccessToken c=AccessToken.getCurrentAccessToken();
                                    email_id=object.getString("email");
                                    gender=object.getString("gender");
                                    String profile_name=object.getString("name");
                                    long fb_id=object.getLong("id"); //use this for logout
//                                    Start new activity or use this info in your project.
                                    Intent i=new Intent(FacebookLogin.this, MainActivity.class);
                                    i.putExtra("type","facebook");
                                    i.putExtra("facebook_id",facebook_id);
                                    i.putExtra("f_name",f_name);
                                    i.putExtra("m_name",m_name);
                                    i.putExtra("l_name",l_name);
                                    i.putExtra("full_name",full_name);
                                    i.putExtra("profile_image",profile_image);
                                    i.putExtra("email_id",email_id);
                                    i.putExtra("gender",gender);
                                    progress.dismiss();
                                    startActivity(i);
//                                    finish();

                                }catch (Exception e)
                                {
                                    Log.e("FaceBook Login","error"+e.getMessage());
                                    progress.dismiss();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();


            }

                        @Override
            public void onCancel() {
                Toast.makeText(FacebookLogin.this,getResources().getString(R.string.login_canceled_facebooklogin), Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(FacebookLogin.this,getResources().getString(R.string.login_failed_facebooklogin),Toast.LENGTH_SHORT).show();
                Log.e("FaceBook Login","error"+error.getMessage());
                progress.dismiss();
            }
        });

        //facebook button click
        facebook_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(FacebookLogin.this, Arrays.asList("public_profile", "user_friends", "email"));
            }
        });
    }

    //for facebook callback result.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
