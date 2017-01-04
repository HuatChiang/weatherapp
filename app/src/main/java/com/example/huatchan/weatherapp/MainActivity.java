package com.example.huatchan.weatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

DBAdapter myDB;

    List<weatherDay> weatherList = new ArrayList<weatherDay>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button search = (Button)findViewById(R.id.SearchButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherHTTPClient data = new WeatherHTTPClient();
                String city = getCity();
                String country = getCountry();
               try {
                   data.execute(city, country);
               }
               catch (Exception e){
                   Toast.makeText(MainActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
               }
            }
        });


        populateWeatherList();
        registerClicks();

        myDB = new DBAdapter(this);
//        openDB();


        weatherDay test = myDB.getDay(1);
        Log.e("ERROR", test.getCondition());

    }


    private void openDB(){
        myDB = new DBAdapter(this);
        try {
            myDB.createDataBase();
        }
        catch( IOException ioe ) {
            Log.e("ERROR", "CANNOT CREATE A DATABASE");
            throw new Error("Unable to create database");
        }
        try {
            myDB.openDataBase();

        }
        catch(SQLException sqle){
            Log.e("ERROR", "SQLE ERROR THINGY");
            throw new Error("SQLE thingy");
        }
    }

//For debugging purposes
    private void populateWeatherList(){
//        weatherList.add(new weatherDay( R.drawable.asgore_side ,60, "Sunday", "Cold"));
//        weatherList.add(new weatherDay( R.drawable.asgore_side ,70, "Monday", "Hot"));
//        weatherList.add(new weatherDay( R.drawable.asgore_side ,73, "Tuesday", "Warm"));
//        weatherList.add(new weatherDay( R.drawable.asgore_side ,67, "Thursday", "Rain"));
//        weatherList.add(new weatherDay(R.drawable.asgore_side, 63, "Friday", "Snow"));
//        weatherList.add(new weatherDay(R.drawable.asgore_side, 74, "Saturday", "Cold"));
    }

    private void populateListView(){
        ArrayAdapter<weatherDay> weatherAdapter = new MyListAdapter();
        ListView list = (ListView)findViewById(R.id.weatherListView);
        list.setAdapter(weatherAdapter);
    }


    private void registerClicks(){
        ListView list = (ListView)findViewById(R.id.weatherListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                weatherDay clickedDay = weatherList.get(position);
                String message = "Dook";
//                String message = "You clicked " + position + " where the tempereature is " + clickedDay.getTempereature()
//                        + " id is " + id;
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

    }

    private class MyListAdapter extends ArrayAdapter<weatherDay>{
        public MyListAdapter(){
            super(MainActivity.this, R.layout.item_view, weatherList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           View itemView = convertView;
            if(itemView == null){   // to make sure have a root and not a null
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent , false); // Inflater will take XML file and "inflate" it into an object on screen
            }

            //Find weather to work with
            weatherDay currentDay = weatherList.get(position);

            //Populate list view

            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(currentDay.getIconID());

            //Make condition
            TextView condition = (TextView)itemView.findViewById(R.id.item_condition);
            condition.setText(currentDay.getCondition());

            //Make temp
            TextView temp = (TextView)itemView.findViewById(R.id.item_temp);
            temp.setText(String.format("%d", currentDay.getTempereature()));

            //Make Day
            TextView day = (TextView)itemView.findViewById(R.id.item_date);   // MUST put the "itemView" so that compiler knows which
            day.setText(currentDay.getDayOfWeek());                           //     XML file to take it from


            TextView theCity = (TextView)itemView.findViewById(R.id.theCity);
            theCity.setText(currentDay.getCity());

            TextView theCountry = (TextView)itemView.findViewById(R.id.theCountry);
            theCountry.setText(currentDay.getCountry());


            return itemView;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getCity (){
        String City;
        EditText city = (EditText)findViewById(R.id.City);
        City = city.getText().toString();

        return City;
    }

    public String getCountry(){
        String Country;
        EditText country = (EditText)findViewById(R.id.Country);
        Country = country.getText().toString();
        return Country;
    }

    public class WeatherHTTPClient extends AsyncTask<String, Void, String> {  // <Params (Goes into do in background), progress(for loading bar and stuff), result (goes into the postExecute parameter)>

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        @Override
        protected String doInBackground(String...params) { //String... param means take in number of object arrays
            try{
                String city = params[0];
                String country = params[1];
                String location = city + "," + country;
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=");    //Split the url here so person can manually enter the location
                String api =  "&APPID=4a79ec65749c3f3f6504e98d74acce74";
                connection = (HttpURLConnection)(new URL(url + location + api)).openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));  //BufferedReader reads from files
                String line = "";
                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                return buffer.toString();
            }
            catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            finally{
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            if(data != null) {
                Weather hoi = new Weather();
                try {
                    hoi = FetchWeather.getWeather(data);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ERROR", "json except");
                }
                double temp1 = hoi.temperature.getTemp() - 273.15;
                Log.e("ERROR", Double.toString(temp1));
                int temp = (int)temp1;


                weatherList.add(new weatherDay(getIconId(temp), temp, "Humidity: " + Float.toString(hoi.currentCondition.getHumidity()), "Condition: " + hoi.currentCondition.getDescription(), hoi.location.getCity(), hoi.location.getCountry()));
                Log.e("ERROR", hoi.location.getCity() + " " + hoi.location.getCountry());
                populateListView();
            }

        }

        private int getIconId(int temp){
            int i = 0;
            if(-50 < temp && temp < 200){
                i = R.drawable.sun;
                return i;
            }

            else{
                i = R.drawable.dook;
                //For debugging
                return i;
            }
        }


    }

}
