package presenter;

import android.content.Context;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.networkmodule.simpleJsonRequest.Url_JsonRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import model.WeatherModel;
import model.tables.CityWeather_Item;
import presenter.adapter.CitiesWeatherAdapter;
import presenter.util.JsonObjFetcher;
import view.CitiesFragment;

/**
 * Created by ddopi on 7/14/2017.
 */

public class CitiesPresenter {

    CitiesFragment citiesFragmentContext;
    Url_JsonRequest url_jsonRequest;
    private String weatherUr = "http://api.openweathermap.org/data/2.5/box/city?bbox=24,22,34,31,35&appid=d67dc92adba6a095840b1d873b1067b5";
    private Url_JsonRequest.PresenterRequest presenterRequest;
    JsonObjFetcher jsonObjFetcher = new JsonObjFetcher();
    private ArrayList<HashMap<String, String>> weatherItems;
    private WeatherModel weatherModel = new WeatherModel();
    public CitiesWeatherAdapter citiesWeatherAdapter;


    public CitiesPresenter(CitiesFragment frg_context) {
        citiesFragmentContext = frg_context;
    }

    public void loadUrl() {


        presenterRequest = new Url_JsonRequest.PresenterRequest() {
            @Override
            public void jsonRequest(JSONObject jsonObjectobj) {
                try { ///gitting life update Url
                    Log.e("CitiesPresenter", "Json converted----------->" + jsonObjectobj.toString());
                    weatherItems = jsonObjFetcher.fetchWeatherObj(jsonObjectobj);
                    Log.e("WeatherModel", " Weather Item----->" + weatherItems.get(0).get("weather_id"));
                    weatherModel.saveWeatherItem(weatherItems);

                } catch (Exception e) {        //inCase of netWork error or App is offline
                    Log.e("CitiesPresenter", "Error Loading Url Switch to offline data");
                    Toast.makeText(citiesFragmentContext.getActivity(), "Offline", Toast.LENGTH_LONG).show();
                }

                citiesFragmentContext.city_list.setAdapter(getCitiesAdapter());
                citiesWeatherAdapter.notifyDataSetChanged();
            }
        };

        url_jsonRequest = new Url_JsonRequest(citiesFragmentContext.getActivity()) {
            @Override
            public String getUrl() {
                return weatherUr;
            }

            @Override
            public Url_JsonRequest.PresenterRequest getPresenterRequest() {
                return presenterRequest;
            }
        };

        url_jsonRequest.sentRequest(false);
    }


    public CitiesWeatherAdapter getCitiesAdapter() {

        citiesWeatherAdapter = new CitiesWeatherAdapter(citiesFragmentContext, weatherModel.getCityWeather_Items());
        return citiesWeatherAdapter;
    }
}
