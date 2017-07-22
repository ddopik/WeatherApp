package view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ddopikmain.seedapplication.R;
import com.example.networkmodule.locations.GPSTrackerٍSingleton;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import butterknife.ButterKnife;

import butterknife.Unbinder;
import model.WeatherModel;
import model.tables.CityWeather_Item;



/**
 * Created by ddopi on 7/14/2017.
 */

public class MapFragment extends Fragment  {

    MapView mMapView;
    private GoogleMap googleMap;
    private WeatherModel weatherModel=new WeatherModel();
    private ArrayList<CityWeather_Item> cityWeather_items;
    private GPSTrackerٍSingleton gpsTracker;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,final Bundle savedInstanceState) {
       final View rootView = inflater.inflate(R.layout.map_fragment, container, false);
        unbinder=ButterKnife.bind(this,rootView);
        loadMap(rootView,savedInstanceState);
        Log.e("MapFragment","MapFragment ----> OnCreate()");

        return rootView;
    }

    public void loadMap(View rootView, Bundle savedInstanceState)
    {

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());



        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For dropping a marker at a point on the Map

                cityWeather_items= weatherModel.getCityWeather_Items();
                for(CityWeather_Item item:cityWeather_items)
                {

                    LatLng city_cordination = new LatLng(item.getCord_lat(),item.getCord_lon());
                    googleMap.addMarker(new MarkerOptions().position(city_cordination).title(item.getCityName()).snippet(item.getTemp()+"C")).setTag(item);
                }
                gpsTracker=new GPSTrackerٍSingleton(getActivity()){
                    @Override
                    public Fragment getFragmentContext() {
                        return MapFragment.this;
                    }
                    @Override
                    public GoogleMap getMap(){ return  getGoogleMap();}
                };
                // For showing a move to my location button
                gpsTracker.getLocation();
                gpsTracker.setMyCurrentLocationEnabled(true);


                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
                {

                    @Override
                    public void onInfoWindowClick(Marker marker)
                    {
                        CityWeather_Item cityWeather_item=(CityWeather_Item) marker.getTag();
                        int id=cityWeather_item.getCity_id();
                        ((WeatherActivity)getActivity()).showWeatherDialog(id);

                    }

                });
                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    public GoogleMap getGoogleMap()
    {
        return this.googleMap;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (gpsTracker.checkPermission(getActivity())) {
            googleMap.setMyLocationEnabled(true);
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }



}

