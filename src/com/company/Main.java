package com.company;

public class Main {
    //This is an api call that worked for daily
    //http://api.openweathermap.org/data/2.5/weather?q=London&cnt=7&appid=2b290376f4e81ff3eb5ef82867095610

    //This is an api call that worked for forecast
    //http://api.openweathermap.org/data/2.5/forecast?q=Seattle&cnt=7&appid=2b290376f4e81ff3eb5ef82867095610
    public static void main(String[] args) throws Exception {
        /*
        WeatherRetriever weatherRetriever = new WeatherRetriever();
        weatherRetriever.getForecast("98802",1);

        weatherRetriever.loadWeatherCache();
        weatherRetriever.printWeeklyForecast();
        */
        MenuExample menuExample = new MenuExample();
        menuExample.weatherRetriever.getForecast("90059",1);
        menuExample.weatherRetriever.loadWeatherCache();
        menuExample.weatherRetriever.printWeeklyForecast();

        //menuExample.updateTextArea(menuExample.weatherRetriever.updateTextAreaWeekly());


    }
}
