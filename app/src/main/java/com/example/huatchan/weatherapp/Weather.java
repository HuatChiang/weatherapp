package com.example.huatchan.weatherapp;

/**
 * Created by Huat Chan on 7/30/2016.
 */
public class Weather {

    public Location location;
    public CurrentCondition currentCondition = new CurrentCondition();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Rain rain = new Rain();
    public Snow snow = new Snow()	;
    public Clouds clouds = new Clouds();

    public class CurrentCondition {
        private int weatherid;
        private String condition;
        private String description;
        private String icon;
        private float pressure;
        private float humidity;

        public int getWeatherid() {
            return weatherid;
        }

        public void setWeatherid(int weatherid) {
            this.weatherid = weatherid;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public float getPressure() {
            return pressure;
        }

        public void setPressure(float pressure) {
            this.pressure = pressure;
        }

        public float getHumidity() {
            return humidity;
        }

        public void setHumidity(float humidity) {
            this.humidity = humidity;
        }
    }

    public class Temperature{
        private float temp;
        private float maxTemp;
        private float minTemp;

        public float getMinTemp() {
            return minTemp;
        }

        public void setMinTemp(float minTemp) {
            this.minTemp = minTemp;
        }

        public float getTemp() {
            return temp;
        }

        public void setTemp(float temp) {
            this.temp = temp;
        }

        public float getMaxTemp() {
            return maxTemp;
        }

        public void setMaxTemp(float maxTemp) {
            this.maxTemp = maxTemp;
        }
    }

    public class Wind{
        private float speed;
        private float deg;

        public float getSpeed() {
            return speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }

        public float getDeg() {
            return deg;
        }

        public void setDeg(float deg) {
            this.deg = deg;
        }
    }

    public class Rain{
        private String time;
        private float amount;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }
    }

    public class Snow{
        private String time;
        private float amount;

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public class Clouds{
        private int perc;

        public int getPerc() {
            return perc;
        }

        public void setPerc(int perc) {
            this.perc = perc;
        }
    }


}
