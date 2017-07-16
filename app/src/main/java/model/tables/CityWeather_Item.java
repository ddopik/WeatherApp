package model.tables;

import io.realm.RealmObject;

/**
 * Created by ddopi on 7/14/2017.
 */

public class CityWeather_Item extends RealmObject {



    private int city_id;
    private String cityName;
    private Float cord_lon;
    private Float cord_lat;
    private Float temp;
    private Float temp_min;
    private Float temp_max;
    private Float pressure;
    private Float humidity;
    private float wind_spped;
    private Float deg;
    private String weather_main;
    private String weather_description;

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Float getCord_lon() {
        return cord_lon;
    }

    public void setCord_lon(Float cord_lon) {
        this.cord_lon = cord_lon;
    }

    public Float getCord_lat() {
        return cord_lat;
    }

    public void setCord_lat(Float cord_lat) {
        this.cord_lat = cord_lat;
    }

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Float getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Float temp_min) {
        this.temp_min = temp_min;
    }

    public Float getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Float temp_max) {
        this.temp_max = temp_max;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    public float getWind_spped() {
        return wind_spped;
    }

    public void setWind_spped(float wind_spped) {
        this.wind_spped = wind_spped;
    }

    public Float getDeg() {
        return deg;
    }

    public void setDeg(Float deg) {
        this.deg = deg;
    }

    public String getWeather_main() {
        return weather_main;
    }

    public void setWeather_main(String weather_main) {
        this.weather_main = weather_main;
    }

    public String getWeather_description() {
        return weather_description;
    }

    public void setWeather_description(String weather_description) {
        this.weather_description = weather_description;
    }




}
