package view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.ddopikmain.seedapplication.R;
import com.example.networkmodule.simpleJsonRequest.Url_JsonRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import presenter.CitiesPresenter;
import presenter.adapter.CitiesWeatherAdapter;

/**
 * Created by ddopi on 7/14/2017.
 */

public class CitiesFragment extends Fragment {


    private View mainView;
    private CitiesPresenter citiesPresenter;
    private Unbinder unbinder;
    private Url_JsonRequest url_jsonRequest;
    @BindView(R.id.search_view)
    public SearchView search_view;
    @BindView(R.id.city_list)
    public ListView city_list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView=inflater.inflate(R.layout.cities_fragment, container, false);
        unbinder=ButterKnife.bind(this,mainView);
        citiesPresenter=new CitiesPresenter(this);
        citiesPresenter.loadUrl();



        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                citiesPresenter.citiesWeatherAdapter.filter(newText);
                return false;
            }
        });

        return mainView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
