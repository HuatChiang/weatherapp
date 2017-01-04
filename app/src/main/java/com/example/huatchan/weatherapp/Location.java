package com.example.huatchan.weatherapp;

import java.io.Serializable;

/**
 * Created by Huat Chan on 7/30/2016.
 */
public class Location implements Serializable{  //Serializable means it helps to store data

    private float longitude;
    private float latitudel;
    private long sunset;
    private long sunrise;
    private String country;
    private String city;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitudel() {
        return latitudel;
    }

    public void setLatitudel(float latitudel) {
        this.latitudel = latitudel;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
