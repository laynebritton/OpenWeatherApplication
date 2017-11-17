package com.company;

import org.json.*;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by laynebritton on 11/14/17.
 * This class exists to retrieve the weather data from the api and write to a local file
 */
public class WeatherRetriever {
    String urlCall;
    String currentWeatherCache;
    WeatherDay[] forecast;
    //URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=Moscow&cnt=7&units=imperial&appid=2b290376f4e81ff3eb5ef82867095610");
    //url = new URL("http://api.openweathermap.org/data/2.5/forecast?zip=59102&cnt=7&units=imperial&appid=2b290376f4e81ff3eb5ef82867095610");
    public WeatherRetriever(){
        forecast = new WeatherDay[16];
    }
    public void getForecast(String location, int locationType) throws Exception{
        /*  The api call is slightly different based upon the location type requested
        Location Type Values
        0 = City Name
        1 = Zip Code
        2 = City ID
        3 = Longitude and Latitude
         */
        try {
            String urlString = "";
            if (locationType == 0) {
                urlString = "http://api.openweathermap.org/data/2.5/forecast?q=" + location + "&cnt=7&units=imperial&appid=2b290376f4e81ff3eb5ef82867095610";
            } else if (locationType == 1) {
                urlString = "http://api.openweathermap.org/data/2.5/forecast?zip=" + location + "&cnt=7&units=imperial&appid=2b290376f4e81ff3eb5ef82867095610";
            } else if (locationType == 2){
                //City ID Call
            } else if (locationType == 3){
                //Lon Lat Call
            }
            URL url = new URL(urlString);
            URLConnection con = url.openConnection();

            //Taking Stream from Call and writing it to a file
            InputStream inputStream = con.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(new File("data3.json"));

            byte[] buf = new byte[512];
            while (true) {
                int len = inputStream.read(buf);
                if (len == -1) {
                    break;
                }
                fileOutputStream.write(buf, 0, len);
            }
            inputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("Data successfully retrieved from: " + location);
            return;
            //Done Writing File
        }catch(Exception e){
            System.out.println("Connection to api.openweathermap.org currently unavailable");
        }

    }
    public void loadWeatherCache() throws Exception{
    //Open json data and put it into a string so it can be manipulated
        File weatherCache = new File("data3.json");
        int i;
        FileReader fileReader = new FileReader(weatherCache);
        //String data = "";
        currentWeatherCache = "";

        while((i = fileReader.read())!=-1){
            char ch = (char)i;
            currentWeatherCache = currentWeatherCache + ch;
        }
        fileReader.close();
        //Data is now in string
        JSONObject obj = new JSONObject(currentWeatherCache);
        JSONArray forecastArray = obj.getJSONArray("list");
        for(i = 0; i < 7; i++){
            forecast[i] = new WeatherDay(i);
            forecast[i].currentTemperature = forecastArray.getJSONObject(i).getJSONObject("main").getDouble("temp");
            forecast[i].humidity = forecastArray.getJSONObject(i).getJSONObject("main").getDouble("humidity");
            forecast[i].atmosphericPressure = forecastArray.getJSONObject(i).getJSONObject("main").getDouble("pressure");
            forecast[i].windSpeed = forecastArray.getJSONObject(i).getJSONObject("wind").getDouble("speed");
            forecast[i].windDegree = forecastArray.getJSONObject(i).getJSONObject("wind").getDouble("deg");
            forecast[i].weatherDescription = forecastArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");
            forecast[i].location = obj.getJSONObject("city").getString("name");
        }
    }
    public void printWeeklyForecast(){
        for(int i=0; i < 7; i++){
            System.out.println("Location: " + forecast[i].location);
            System.out.println("Day: " + i);
            System.out.println("Current Temp: " + forecast[i].currentTemperature + " " + forecast[i].weatherDescription);
            System.out.println("Humidity: " + forecast[i].humidity);
            System.out.println("Atmospheric Pressure: " + forecast[i].atmosphericPressure);
            System.out.println("Wind Speed: " + forecast[i].windSpeed);
            System.out.println("Wind Degree: " + forecast[i].windDegree);
            System.out.println("\n");
        }
    }
    public void printDailyForecast(){

    }

    public JTextArea updateTextAreaWeekly(){
        JTextArea area = new JTextArea();
        for(int i=0; i < 7; i++){
            //area.append("Location: " + forecast[i].location + "\n");
            area.append("Day: " + i +"\n");
            area.append("Current Temp: " + forecast[i].currentTemperature + " " + forecast[i].weatherDescription +"\n");
            area.append("Humidity: " + forecast[i].humidity+"\n");
            area.append("Atmospheric Pressure: " + forecast[i].atmosphericPressure+"\n");
            area.append("Wind Speed: " + forecast[i].windSpeed+"\n");
            area.append("Wind Degree: " + forecast[i].windDegree+"\n");
            area.append("\n");

        }
        return area;
    }
    public JTextArea updateTextAreaDaily(){
        JTextArea area = new JTextArea();
        for(int i=0; i < 1; i++){
            //area.append("Location: " + forecast[i].location + "\n");
            area.append("Today's Forecast in " + forecast[i].location + "\n");
            area.append("Current Temp: " + forecast[i].currentTemperature + " " + forecast[i].weatherDescription +"\n");
            area.append("Humidity: " + forecast[i].humidity+"\n");
            area.append("Atmospheric Pressure: " + forecast[i].atmosphericPressure+"\n");
            area.append("Wind Speed: " + forecast[i].windSpeed+"\n");
            area.append("Wind Degree: " + forecast[i].windDegree+"\n");
            area.append("\n");

        }
        return area;
    }
    public String getCurrentLocationName(){
        return forecast[0].location;
    }
}