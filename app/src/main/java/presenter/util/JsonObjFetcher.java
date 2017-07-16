package presenter.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ddopi on 5/30/2017.
 */

public class JsonObjFetcher {


    public HashMap fetchLoginObj(JSONObject mobj) {

        JSONObject obj;
        HashMap<String, String> map ;
        try {
//             obj = (JSONObject) mobj.get("status_msg");
             map = new HashMap<String, String>();

            map.put("user_id", mobj.get("Id").toString());
            map.put("success", mobj.get("success").toString());
            map.put("UserName", mobj.get("UserName").toString());
            return map;
        }catch (Exception e)
        {
            Log.e("JsonObjFetcher","Error fetching Recived JSON obj");
            return null;
        }
    }

    public ArrayList<HashMap<String,String>> fetchCourcesObj(JSONObject jsonObject)
    {
        try {

            HashMap<String, String>  map ;

            ArrayList<HashMap<String,String>> courceList=new ArrayList<HashMap<String,String>>();
            JSONArray courcesObj=jsonObject.getJSONArray("status_msg");
            for (int i=0;i<courcesObj.length();i++)
                  {


                      map = new HashMap<String, String>();
                      map.put("id", courcesObj.getJSONObject(i).get("id").toString());
                      map.put("trainer_name", courcesObj.getJSONObject(i).get("trainer_id").toString());
                      map.put("institute_name", courcesObj.getJSONObject(i).get("institute_id").toString());
                      map.put("cource_name", courcesObj.getJSONObject(i).get("name").toString());
                      map.put("cource_type", courcesObj.getJSONObject(i).get("type").toString());
                      map.put("cource_active", courcesObj.getJSONObject(i).get("active").toString());
                      map.put("cource_promotion_details", courcesObj.getJSONObject(i).get("promotion details").toString());
                      map.put("cource_target", courcesObj.getJSONObject(i).get("target").toString());
                      map.put("cource_discription", courcesObj.getJSONObject(i).get("description").toString());
                      map.put("cource_added_date", courcesObj.getJSONObject(i).get("added_date").toString());
                      map.put("cource_updated_date", courcesObj.getJSONObject(i).get("updated_date").toString());
                      map.put("cource_duration", courcesObj.getJSONObject(i).get("duration").toString());
                      map.put("cource_date_start", courcesObj.getJSONObject(i).get("date_start").toString());
                      map.put("cource_date_end", courcesObj.getJSONObject(i).get("date_end").toString());
//                      map.put("cource_location", courcesObj.getJSONObject(i).get("cource_location").toString());
//                      map.put("cource_day_left", courcesObj.getJSONObject(i).get("cource_day_left").toString());

                      Log.e("JsonObjFetcher","fetchCourcesObj---->"+map.get("id"));
                      courceList.add(i,map);
            }

            Log.e("JsonObjFetcher","fetchCourcesObj has fetched : "+courceList.size());
            return courceList;
        }catch (Exception e)
        {
            Log.e("JsonObjFetcher---->",e.getMessage());
            Log.e("JsonObjFetcher","Error fetching Recived JSON obj");
            return null;
        }
    }

    public ArrayList<HashMap<String,String>> fetchWeatherObj(JSONObject jsonObject)
    {
        try {

            HashMap<String, String>  map ;

            ArrayList<HashMap<String,String>> courceList=new ArrayList<HashMap<String,String>>();
            JSONArray weatherObj=jsonObject.getJSONArray("list");
            for (int i=0;i<weatherObj.length();i++)
                  {
                      map = new HashMap<String, String>();
                      map.put("weather_id", weatherObj.getJSONObject(i).get("id").toString());
                      map.put("weather_name", weatherObj.getJSONObject(i).get("name").toString());
                      map.put("Weather_lat", weatherObj.getJSONObject(i).getJSONObject("coord").get("Lat").toString());
                      map.put("Weather_Lon", weatherObj.getJSONObject(i).getJSONObject("coord").get("Lon").toString());
                      map.put("Weather_temp", weatherObj.getJSONObject(i).getJSONObject("main").get("temp").toString());
                      map.put("Weather_temp_min", weatherObj.getJSONObject(i).getJSONObject("main").get("temp_min").toString());
                      map.put("Weather_temp_max", weatherObj.getJSONObject(i).getJSONObject("main").get("temp_max").toString());
                      map.put("Weather_pressure", weatherObj.getJSONObject(i).getJSONObject("main").get("pressure").toString());
                      map.put("Weather_humidity", weatherObj.getJSONObject(i).getJSONObject("main").get("humidity").toString());
                      map.put("Weather_speed", weatherObj.getJSONObject(i).getJSONObject("wind").get("speed").toString());
                      map.put("Weather_deg", weatherObj.getJSONObject(i).getJSONObject("wind").get("deg").toString());
                      map.put("Weather_main", weatherObj.getJSONObject(i).getJSONArray("weather").getJSONObject(0).get("main").toString());
                      map.put("Weather_description", weatherObj.getJSONObject(i).getJSONArray("weather").getJSONObject(0).get("description").toString());


                      Log.e("JsonObjFetcher","fetch_weather_Obj_id---->"+map.get("weather_id"));
                      Log.e("JsonObjFetcher","fetch_weather_Obj_weather_name---->"+map.get("weather_name"));
                      Log.e("JsonObjFetcher","fetch_weather_Obj_Weather_Lon---->"+map.get("Weather_Lon"));
                      Log.e("JsonObjFetcher","fetch_weather_Obj_Weather_Lon---->"+map.get("Weather_lat"));
                      Log.e("JsonObjFetcher","fetch_weather_Obj_Weather_temp---->"+map.get("Weather_temp"));
                      courceList.add(i,map);
            }

            Log.e("JsonObjFetcher","fetchCourcesObj has fetched : "+courceList.size());
            return courceList;
        }catch (Exception e)
        {
            Log.e("JsonObjFetcher---->",e.getMessage());
            Log.e("JsonObjFetcher","Error fetching Recived JSON obj");
            return null;
        }
    }

}
