package com.company;
import javax.swing.*;
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
        JFrame f = new JFrame("OpenWeather Application");

        /*
        weatherButton a = new weatherButton("Daily Weather",0,0,100,140);
        JButton b = new JButton("Click");
        b.setBounds(0,0,100,60);
        f.add(b);
        f.setSize(600,800);
        f.setLayout(null);
        f.setVisible(true);
        */
        URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=Seattle&cnt=7&units=imperial&appid=2b290376f4e81ff3eb5ef82867095610");
        URLConnection con = url.openConnection();
        File weatherCache = new File("data2.json");


        FileReader fileReader = new FileReader(weatherCache);
        String data = "";

        int i;
        while((i = fileReader.read())!=-1){
            char ch = (char)i;
            data = data + ch;
        }
        JSONObject obj = new JSONObject(data);
        JSONArray forecastArray = obj.getJSONArray("list");

        WeatherDay[] forecast = new WeatherDay[16];
        for(i = 0; i < 7; i++){
            forecast[i] = new WeatherDay(i);
            forecast[i].currentTemperature = forecastArray.getJSONObject(i).getJSONObject("main").getDouble("temp");
            forecast[i].humidity = forecastArray.getJSONObject(i).getJSONObject("main").getDouble("humidity");
            forecast[i].atmosphericPressure = forecastArray.getJSONObject(i).getJSONObject("main").getDouble("pressure");
            forecast[i].windSpeed = forecastArray.getJSONObject(i).getJSONObject("wind").getDouble("speed");
            forecast[i].windDegree = forecastArray.getJSONObject(i).getJSONObject("wind").getDouble("deg");
            forecast[i].weatherDescription = forecastArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");
        }
        for(i=0; i < 7; i++){
            System.out.println("Day: " + i);
            System.out.println("Current Temp: " + forecast[i].currentTemperature + " " + forecast[i].weatherDescription);
            System.out.println("Humidity: " + forecast[i].currentTemperature);
            System.out.println("Atmospheric Pressure: " + forecast[i].atmosphericPressure);
            System.out.println("Wind Speed: " + forecast[i].windSpeed);
            System.out.println("Wind Degree: " + forecast[i].windDegree);
            System.out.println("\n");
        }

        /*
        //Testing Print Statements
        System.out.println(data);

        String owmVersion = obj.getString("cod");
        System.out.println(owmVersion);

        String nestingTest = forecastArray.getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");
        //This goes to list->day0->weather->description
        System.out.println(nestingTest);
        */

    }


    public void callApi() throws Exception {
        URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=Seattle&cnt=7&appid=2b290376f4e81ff3eb5ef82867095610");
        URLConnection con = url.openConnection();


    }

}
