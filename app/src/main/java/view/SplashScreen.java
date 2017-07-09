package view;

/**
 * Created by ddopi on 6/15/2017.
 */


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import com.example.ddopikmain.seedapplication.R;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;


public class SplashScreen extends AppCompatActivity {

    ProgressBar mprogressBar;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.splash_screeen);

    }

    @Override
    protected void onResume() {
        super.onResume();
        splashStart();
    }

    public void splashStart() {
        mprogressBar = (ProgressBar) findViewById(R.id.circular_progress_bar);
        ObjectAnimator anim = ObjectAnimator.ofInt(mprogressBar, "progress", 0, 31);
        anim.setDuration(1000);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AccessToken.getCurrentAccessToken() == null) {
                    Intent i=new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                }
                else
                {
                    Intent i=new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                }
            }
        }, 1000);
    }

    private void logoutFromFacebook(){
        try {
//            if (AccessToken.getCurrentAccessToken() == null) {
//                Intent i=new Intent(SplashScreen.this, LoginActivity.class);
//                startActivity(i);
//            }
            SharedPreferences prefs = getSharedPreferences("task_shared_pref", MODE_PRIVATE);
            long fb_id=prefs.getLong("fb_id",0);
            GraphRequest graphRequest=new GraphRequest(AccessToken.getCurrentAccessToken(), "/ "+fb_id+"/permissions/", null,
                    HttpMethod.DELETE, new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse graphResponse) {
                    LoginManager.getInstance().logOut();
                }
            });

            graphRequest.executeAsync();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }
}