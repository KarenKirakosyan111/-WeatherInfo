package com.karen.weatherinfo.model;


public class Country {

    private String countryName;
    private float temp;
    private float pressure;
    private float humidity;

    public Country(String countryName) {
        this.countryName = countryName;
    }

    public Country(String countryName, float temp, float pressure, float humidity) {
        this.countryName = countryName;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
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
