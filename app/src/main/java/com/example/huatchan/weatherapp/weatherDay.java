package com.example.huatchan.weatherapp;

/**
 * Created by Huat Chan on 6/28/2016.
 */
public class weatherDay {
    public String dayOfWeek;
    public int tempereature;
    public int iconID;
    public String condition;
    public String city;
    public String country;

    public weatherDay(int iconID, int tempereature, String dayOfWeek, String condition, String city, String country) {
        this.iconID = iconID;
        this.tempereature = tempereature;
        this.dayOfWeek = dayOfWeek;
        this.condition = condition;
        this.city = city;
        this.country = country;
    }

    public String getDayOfWeek() { return dayOfWeek; }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getTempereature() {
        return tempereature;
    }

    public void setTempereature(int tempereature) {
        this.tempereature = tempereature;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
