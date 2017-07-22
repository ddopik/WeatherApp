package view;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.ddopikmain.seedapplication.R;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ddopi on 7/14/2017.
 */

public class WeatherActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @BindView(R.id.refreash_btn)
    public Button refreshBtn;
    private   WeatherActivity.ViewPagerAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {

        adapter = new WeatherActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CitiesFragment(), "Cities");
        adapter.addFragment(new MapFragment(), "Map");
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @OnClick(R.id.refreash_btn)
    public void refreshActivityBtn() {
        adapter.notifyDataSetChanged();

    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
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
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public void showWeatherDialog(int city_id) {
        FragmentManager fm = getSupportFragmentManager();
        WeatherDialog_fragment weatherDialog_fragment = WeatherDialog_fragment.newInstance("Weather title");
        weatherDialog_fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        ///////
        Bundle bundle = new Bundle();
        bundle.putInt("city_id", city_id);
// set Fragmentclass Arguments
        weatherDialog_fragment.setArguments(bundle);
        weatherDialog_fragment.show(fm, "weatherDialog");
    }

}
