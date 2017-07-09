package view;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;


import com.example.ddopikmain.seedapplication.R;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public LoginButton facebook_button;
    @BindView(R.id.logout_button)
    public ImageButton logout_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        facebook_button = (LoginButton) findViewById(R.id.connectWithFbButton);


        if (AccessToken.getCurrentAccessToken() == null) {
            logout_button.setVisibility(View.VISIBLE);
            facebook_button.setVisibility(View.INVISIBLE);
        } else {
            logout_button.setVisibility(View.INVISIBLE);
            facebook_button.setVisibility(View.VISIBLE);
        }
        facebook_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutFromFacebook();
            }
        });
    }

    @OnClick(R.id.logout_button)
    public void logOutAction() {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }

    private void logoutFromFacebook() {
        if (AccessToken.getCurrentAccessToken() != null) {
            try {

                SharedPreferences prefs = getSharedPreferences("task_shared_pref", MODE_PRIVATE);
                long fb_id = prefs.getLong("fb_id", 0);
                GraphRequest graphRequest = new GraphRequest(AccessToken.getCurrentAccessToken(), "/ " + fb_id + "/permissions/", null,
                        HttpMethod.DELETE, new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse) {
                        LoginManager.getInstance().logOut();
                    }
                });

                graphRequest.executeAsync();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);

        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "ONE");
        adapter.addFragment(new TwoFragment(), "TWO");
        adapter.addFragment(new ThreeFragment(), "THREE");
//        adapter.addFragment(new FourFragment(), "Four");
//        adapter.addFragment(new FiveFragment(), "Five");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
