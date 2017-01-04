//package com.example.huatchan.weatherapp;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import org.json.JSONException;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
///**
// * Created by Huat Chan on 8/2/2016.
// */
//public class WeatherHTTPClient extends AsyncTask<String, Void, String>{  // <Params (Goes into do in background), progress(for loading bar and stuff), result (goes into the postExecute parameter)>
//
//        HttpURLConnection connection = null;
//        BufferedReader reader = null;
//        String url = "http://api.openweathermap.org/data/2.5/weather?q=";   //Brasilia,Brazil&APPID=4a79ec65749c3f3f6504e98d74acce74  (might need this)
//
//
//    @Override
//    protected String doInBackground(String...param) { //String... param means take in number of object arrays
//        try{
//            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=Brasilia,Brazil&APPID=4a79ec65749c3f3f6504e98d74acce74");
//            connection = (HttpURLConnection)url.openConnection();
//            connection.connect();
//
//            InputStream stream = connection.getInputStream();
//
//            StringBuffer buffer = new StringBuffer();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));  //BufferedReader reads from files
//            String line = "";
//            while((line = reader.readLine()) != null){
//                buffer.append(line);
//            }
//            return buffer.toString();
//        }
//        catch(MalformedURLException e){  //Alwayse need to throw exception when making url
//            e.printStackTrace();
//        }
//        catch(IOException e){
//            e.printStackTrace();
//        }
//        finally{
//            if(connection != null) {
//                connection.disconnect();
//            }
//            try {
//                if(reader != null) {
//                    reader.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(String data) {
//        super.onPostExecute(data);
//        if(data != null) {
//            Log.e("ERROR", data + "Boop");
//            Weather hoi = new Weather();
//            try {
//                hoi = FetchWeather.getWeather(data);
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//                Log.e("ERROR", "json except");
//            }
//                int temp = hoi.clouds.getPerc();
//                Log.e("ERROR", Integer.toString(temp));
//
//        }
//
//    }
//
//
//}
//
