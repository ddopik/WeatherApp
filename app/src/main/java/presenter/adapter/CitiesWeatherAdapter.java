package presenter.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ddopikmain.seedapplication.R;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import model.tables.CityWeather_Item;
import view.CitiesFragment;
import view.LoginActivity;

/**
 * Created by ddopi on 7/15/2017.
 */

public class CitiesWeatherAdapter extends BaseAdapter {

    private  ArrayList<CityWeather_Item> cityWeather_items;
    private  ArrayList<CityWeather_Item> fixed_cityWeather_items;
    private CitiesFragment fragmentContext;
    private LayoutInflater inflater;
    private View mainView;
    private WeatherItem_viewHolder weatherItem_viewHolder;

    public CitiesWeatherAdapter(CitiesFragment context, ArrayList<CityWeather_Item> cityWeatherItemses)
    {
        this.cityWeather_items=cityWeatherItemses;
        this.fixed_cityWeather_items=new ArrayList<CityWeather_Item>();
        fixed_cityWeather_items.addAll(cityWeather_items);
        this.fragmentContext =context;

    }



    @Override
    public int getCount() {
        return cityWeather_items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null) {
            inflater = (LayoutInflater) fragmentContext.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.weather_item,null);
            weatherItem_viewHolder=new WeatherItem_viewHolder(convertView);
            convertView.setTag(weatherItem_viewHolder);
        }
        else
        {
            weatherItem_viewHolder=(WeatherItem_viewHolder) convertView.getTag();
        }






            weatherItem_viewHolder.item_id.setText(cityWeather_items.get(position).getCity_id()+"");
            weatherItem_viewHolder.city_name.setText("City Name : "+cityWeather_items.get(position).getCityName());
            weatherItem_viewHolder.weather_main.setText(cityWeather_items.get(position).getTemp()+" °C");
            weatherItem_viewHolder.item_wind_speed.setText("Wind : " +cityWeather_items.get(position).getWind_spped() +" m/s");
            weatherItem_viewHolder.item_wind_degree.setText("degree : "+cityWeather_items.get(position).getDeg()+"");
            weatherItem_viewHolder.item_pressure.setText("Pressure : "+cityWeather_items.get(position).getPressure()+" hpa");
            weatherItem_viewHolder.item_humidity.setText("Humidity : "+cityWeather_items.get(position).getHumidity()+" %");

        return convertView;
    }
    // Filter Class
    /// arraylist its a secondary array that hold  original  arrayList the adapter first initiated
    /// _data it is the dynamic array need to be cleared ant initialized to notify the adapter
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

//        this.fixed_cityWeather_items.addAll(cityWeather_items);
        cityWeather_items.clear();
        if (charText.length() == 0) {
            cityWeather_items.addAll(fixed_cityWeather_items);
        } else {
            for (int i=0;i<fixed_cityWeather_items.size();i++) {
                try {
                if (fixed_cityWeather_items.get(i).getCityName().toLowerCase(Locale.getDefault()).contains(charText)) {

                        cityWeather_items.add(fixed_cityWeather_items.get(i));
                }
                    }catch (Exception e)
                    {
                        Log.e("CitiesWeatherAdapter","Error--at ("+i+") --> "+e.getMessage());
                    }


            }
        }
        notifyDataSetChanged();
    }

    public class WeatherItem_viewHolder{

        @BindView(R.id.item_id)
        TextView item_id;
        @BindView(R.id.item_city_name)
        TextView city_name;
        @BindView(R.id.weather_main)
        TextView weather_main;
        @BindView(R.id.item_wind_speed)
        TextView item_wind_speed;
        @BindView(R.id.item_wind_degree)
        TextView item_wind_degree;
        @BindView(R.id.item_pressure)
        TextView item_pressure;
        @BindView(R.id.item_humidity)
        TextView item_humidity;

        public  WeatherItem_viewHolder(View view)
        {
            ButterKnife.bind(this,view);

        }
    }
}
