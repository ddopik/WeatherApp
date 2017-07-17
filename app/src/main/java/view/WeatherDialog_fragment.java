package view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ddopikmain.seedapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import model.WeatherModel;
import model.tables.CityWeather_Item;

/**
 * Created by ddopi on 7/16/2017.
 */

public class WeatherDialog_fragment extends DialogFragment {
    private EditText mEditText;

    @BindView(R.id.item_id)
    TextView item_id;
    @BindView(R.id.item_city_name)
    TextView city_name;
    @BindView(R.id.current_main_temp)
    TextView current_main_temp;
    @BindView(R.id.current_max_temp)
    TextView current_max_temp;
    @BindView(R.id.current_min_temp)
    TextView current_min_temp;
    @BindView(R.id.item_wind_speed)
    TextView item_wind_speed;
    @BindView(R.id.item_wind_degree)
    TextView item_wind_degree;
    @BindView(R.id.item_pressure)
    TextView item_pressure;
    @BindView(R.id.item_humidity)
    TextView item_humidity;
    @BindView(R.id.item_description)
    TextView item_description;

    CityWeather_Item cityWeather_item;
    WeatherModel weatherModel=new WeatherModel();
    Unbinder unbinder;

    public WeatherDialog_fragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static WeatherDialog_fragment newInstance(String title) { ///gitting frg instance
        WeatherDialog_fragment frag = new WeatherDialog_fragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String strtext = getArguments().getString("city_id");
        return inflater.inflate(R.layout.weather_dialog_fragment, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder= ButterKnife.bind(this,view);
        // Get field from view
//        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        // Fetch arguments from bundle and set title
        int id = getArguments().getInt("city_id",0);
        cityWeather_item=weatherModel.getSingleItem(id);


        getDialog().setTitle(cityWeather_item.getCityName()+"");

        current_main_temp.setText(cityWeather_item.getTemp()+"");
        current_max_temp.setText("max : "+cityWeather_item.getTemp_max()+" °C");
        current_min_temp.setText("min : "+cityWeather_item.getTemp_min()+" °C");
        item_wind_speed.setText("Wind : "+cityWeather_item.getWind_spped()+" m/s");
        item_wind_degree.setText("degree : "+cityWeather_item.getDeg()+"`");
        item_pressure.setText("Pressure : "+cityWeather_item.getPressure()+"hpa");
        item_humidity.setText("Humidity : "+cityWeather_item.getHumidity()+" %");
        item_description.setText("Description : "+cityWeather_item.getWeather_description()+".");


        // Show soft keyboard automatically and request focus to field
//        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
