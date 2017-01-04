package com.example.huatchan.weatherapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Huat Chan on 7/28/2016.
 */
public class FetchWeather {

    public static Weather getWeather(String data) throws JSONException {
        Weather weather = new Weather();

        JSONObject jobj = new JSONObject(data);  // Create object from data

        Location loc = new Location();  // Extract info

        JSONObject coordObj = getObject("coord", jobj);
        loc.setLatitudel(getFloat("lat", coordObj));
        loc.setLongitude(getFloat("lon", coordObj));

        JSONObject sysObj = getObject("sys", jobj);
        loc.setCountry(getString("country", sysObj));
        loc.setSunrise(getInt("sunrise", sysObj));
        loc.setSunset(getInt("sunset", sysObj));
        loc.setCity(getString("name", jobj));
        weather.location = loc;

        JSONArray jArr = jobj.getJSONArray("weather");

        JSONObject JSONweather = jArr.getJSONObject(0);
        weather.currentCondition.setWeatherid(getInt("id", JSONweather));
        weather.currentCondition.setCondition(getString("main", JSONweather));
        weather.currentCondition.setDescription(getString("description", JSONweather));
        weather.currentCondition.setIcon(getString("icon", JSONweather));

        JSONObject mainObj = getObject("main", jobj);
        weather.currentCondition.setHumidity(getInt("humidity", mainObj));
        weather.currentCondition.setPressure(getInt("pressure", mainObj));
        weather.temperature.setMaxTemp(getFloat("temp_max", mainObj));
        weather.temperature.setMinTemp(getFloat("temp_min", mainObj));
        weather.temperature.setTemp(getFloat("temp", mainObj));

// Wind
        JSONObject wObj = getObject("wind", jobj);
        weather.wind.setSpeed(getFloat("speed", wObj));
        weather.wind.setDeg(getFloat("deg", wObj));

// Clouds
        JSONObject cObj = getObject("clouds", jobj);
        weather.clouds.setPerc(getInt("all", cObj));

        return weather;
    }


    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);


    }
}
