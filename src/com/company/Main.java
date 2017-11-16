package com.company;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import org.json.*;

public class Main {
    //This is an api call that worked for daily
    //http://api.openweathermap.org/data/2.5/weather?q=London&cnt=7&appid=2b290376f4e81ff3eb5ef82867095610

    //This is an api call that worked for forecast
    //http://api.openweathermap.org/data/2.5/forecast?q=Seattle&cnt=7&appid=2b290376f4e81ff3eb5ef82867095610
    public static void main(String[] args) throws Exception {

        WeatherRetriever weatherRetriever = new WeatherRetriever();
        weatherRetriever.getForecast("59044",1);

        weatherRetriever.loadWeatherCache();
        weatherRetriever.printWeeklyForecast();
        MenuExample menuExample = new MenuExample();


    }
}
