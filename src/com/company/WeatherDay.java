package com.company;

/**
 * Created by laynebritton on 11/13/17.
 */
public class WeatherDay {
    int dayPosition;    //Used to track which day of the 7 day forecast this is. Goes from day 0 to 6 in a 7 day forecast
    double currentTemperature;
    String weatherDescription;
    double windSpeed;
    double windDegree;
    double atmosphericPressure;
    double humidity;
    String location;
    int locationType;
    String creationTime;
    String timeThisDayRepresents;
    double tempLow;
    double tempHigh;
    public WeatherDay(int day){
        dayPosition = day;
    }
}
