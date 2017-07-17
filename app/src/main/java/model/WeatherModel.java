package model;

import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.RealmResults;
import model.tables.CityWeather_Item;
import model.tables.User;
import presenter.app_config.MainApp;

/**
 * Created by ddopi on 7/14/2017.
 */

public class WeatherModel {


    public void saveWeatherItem(ArrayList<HashMap<String, String>> weatherItem) {


        for (int i = 0; i < weatherItem.size(); i++) {
            try {
                MainApp.realm.beginTransaction();
                CityWeather_Item cityWeather_item = MainApp.realm.createObject(CityWeather_Item.class);
                cityWeather_item.setCity_id(Integer.parseInt(weatherItem.get(i).get("weather_id")));
                cityWeather_item.setCityName(weatherItem.get(i).get("weather_name"));
                cityWeather_item.setCord_lat(Float.parseFloat((weatherItem.get(i).get("Weather_lat"))));
                cityWeather_item.setCord_lon(Float.parseFloat(weatherItem.get(i).get("Weather_Lon")));
                cityWeather_item.setTemp(Float.parseFloat(weatherItem.get(i).get("Weather_temp")));
                cityWeather_item.setTemp_min(Float.parseFloat(weatherItem.get(i).get("Weather_temp_min")));
                cityWeather_item.setTemp_max(Float.parseFloat(weatherItem.get(i).get("Weather_temp_max")));
                cityWeather_item.setPressure(Float.parseFloat(weatherItem.get(i).get("Weather_pressure")));
                cityWeather_item.setHumidity(Float.parseFloat(weatherItem.get(i).get("Weather_humidity")));
                cityWeather_item.setWind_spped(Float.parseFloat(weatherItem.get(i).get("Weather_speed")));
                cityWeather_item.setDeg(Float.parseFloat(weatherItem.get(i).get("Weather_deg")));
                cityWeather_item.setWeather_main(weatherItem.get(i).get("Weather_main"));
                cityWeather_item.setWeather_description(weatherItem.get(i).get("Weather_description"));
                MainApp.realm.commitTransaction();
//                saveItemToRealm(cityWeather_item);

            } catch (Exception e) {
                Log.e("WeatherModel", "Error Saving Weather Item----->" + e.getMessage());
            }

        }
    }

//    public RealmResults<CityWeather_Item> getCityWeather_Items()
//    {
//        RealmResults<CityWeather_Item> CityWeather_Items=MainApp.realm.where(CityWeather_Item.class).findAll();
//        return CityWeather_Items;
//    }

    public ArrayList<CityWeather_Item> getCityWeather_Items() {
        ArrayList<CityWeather_Item> arrayList=new ArrayList<CityWeather_Item>();
        RealmResults<CityWeather_Item> CityWeather_Items = MainApp.realm.where(CityWeather_Item.class).findAll();
        for (CityWeather_Item item : CityWeather_Items) {
            arrayList.add(item);
        }
        return arrayList;
    }

    public CityWeather_Item getSingleItem(int itemID)
    {
        CityWeather_Item cityWeather_item=MainApp.realm.where(CityWeather_Item.class).equalTo("city_id",itemID).findFirst();
        return cityWeather_item;
    }

}
