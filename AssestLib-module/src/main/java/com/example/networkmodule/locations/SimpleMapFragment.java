package com.example.networkmodule.locations;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

 ;

import com.example.networkmodule.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;



/**
 * Created by ddopi on 7/14/2017.
 */

public class SimpleMapFragment extends Fragment  {

    MapView mMapView;
    private GoogleMap googleMap;
//    private WeatherModel weatherModel=new WeatherModel();
//    private ArrayList<CityWeather_Item> cityWeather_items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);

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
//                cityWeather_items= weatherModel.getCityWeather_Items();
//                for(CityWeather_Item item:cityWeather_items)
//                {
//
//                    LatLng city_cordination = new LatLng(item.getCord_lat(),item.getCord_lon());
//                    googleMap.addMarker(new MarkerOptions().position(city_cordination).title(item.getCityName()).snippet(item.getTemp()+"C")).setTag(item);
//                }

                GPSTrackerٍSingleton gpsTracker=new GPSTrackerٍSingleton(getActivity());
                gpsTracker.getLocation();
//                Toast.makeText(getActivity(),"lat"+gpsTracker.getLatitude(),Toast.LENGTH_SHORT).show();

                // For showing a move to my location button
                GPSTrackerٍSingleton.checkPermission(getActivity());
                googleMap.setMyLocationEnabled(true);

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
        {

            @Override
            public void onInfoWindowClick(Marker marker)
            {
//                implement youtr action here
//                CityWeather_Item cityWeather_item=(CityWeather_Item) marker.getTag();
//                int id=cityWeather_item.getCity_id();
//                ((Activity)getActivity()).showWeatherDialog(id);

            }

        });
                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });





        return rootView;
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
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }



}

