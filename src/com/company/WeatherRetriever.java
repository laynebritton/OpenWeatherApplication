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
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.StringTokenizer;

/**
 * Created by laynebritton on 11/14/17.
 * This class exists to retrieve the weather data from the api and write to a local file
 */
public class WeatherRetriever {
    String currentWeatherCache;
    WeatherDay[] forecast;
    int numberOfWeatherDays;

    //URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=Moscow&cnt=7&units=imperial&appid=2b290376f4e81ff3eb5ef82867095610");
    //url = new URL("http://api.openweathermap.org/data/2.5/forecast?zip=59102&cnt=7&units=imperial&appid=2b290376f4e81ff3eb5ef82867095610");
    public WeatherRetriever(){
        forecast = new WeatherDay[56];
    }
    public void getForecast(String location, int locationTypeSetter) throws Exception{
        /*  The api call is slightly different based upon the location type requested
        Location Type Values
        0 = City Name
        1 = Zip Code
        2 = City ID
        3 = Longitude and Latitude
         */
        try {
            String urlString = "";
            if (locationTypeSetter == 0) {
                String[] temp = location.split("\\-");
                String oldLocationHolder = location;    //Handles multiword names. Useless if single word name
                if(temp.length==2){
                    location = temp[0]+" "+temp[1];
                }
                if(temp.length==3){
                    location = temp[0]+" "+temp[1]+" "+temp[2];
                }
                urlString = "http://api.openweathermap.org/data/2.5/forecast?q=" + location + "&cnt=56&units=imperial&appid=2b290376f4e81ff3eb5ef82867095610";
                location = oldLocationHolder;
            } else if (locationTypeSetter == 1) {
                urlString = "http://api.openweathermap.org/data/2.5/forecast?zip=" + location + "&cnt=56&units=imperial&appid=2b290376f4e81ff3eb5ef82867095610";
            } else if (locationTypeSetter == 2){
                urlString = "http://api.openweathermap.org/data/2.5/forecast?id=" + location + "&cnt=56&units=imperial&appid=2b290376f4e81ff3eb5ef82867095610";
            } else if (locationTypeSetter == 3){
                String[] temp = location.split("\\-");
                System.out.println(temp[0]);
                System.out.println(temp[1]);
                urlString = "http://api.openweathermap.org/data/2.5/forecast?lat=" + temp[0] + "&lon=" + temp[1] + "&cnt=56&appid=2b290376f4e81ff3eb5ef82867095610";
            }
            URL url = new URL(urlString);
            URLConnection con = url.openConnection();

            //Taking Stream from Call and writing it to a file
            InputStream inputStream = con.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(location + "Data.json"));

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
    public void loadWeatherCache(String location) throws Exception{
    //Open json data and put it into a string so it can be manipulated
        File weatherCache = new File(location + "Data.json");
        Path file = FileSystems.getDefault().getPath(location + "Data.json");
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
        System.out.println("Weather Last Updated: " + attr.creationTime());
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
        numberOfWeatherDays = forecastArray.length();
        for(i = 0; i < numberOfWeatherDays; i++){
            forecast[i] = new WeatherDay(i);
            forecast[i].timeThisDayRepresents = forecastArray.getJSONObject(i).getString("dt_txt");
            forecast[i].creationTime = attr.creationTime().toString();
            forecast[i].currentTemperature = forecastArray.getJSONObject(i).getJSONObject("main").getDouble("temp");
            forecast[i].tempLow = forecastArray.getJSONObject(i).getJSONObject("main").getDouble("temp_min");
            forecast[i].tempHigh = forecastArray.getJSONObject(i).getJSONObject("main").getDouble("temp_max");
            forecast[i].humidity = forecastArray.getJSONObject(i).getJSONObject("main").getDouble("humidity");
            forecast[i].atmosphericPressure = forecastArray.getJSONObject(i).getJSONObject("main").getDouble("pressure");
            forecast[i].windSpeed = forecastArray.getJSONObject(i).getJSONObject("wind").getDouble("speed");
            forecast[i].windDegree = forecastArray.getJSONObject(i).getJSONObject("wind").getDouble("deg");
            forecast[i].weatherDescription = forecastArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");
            //forecast[i].sunrise =
            //forecast[i].location = obj.getJSONObject("city").getString("name");
            forecast[i].location = location;
        }
    }
    public void printWeeklyForecast(){
        for(int i=0; i < numberOfWeatherDays; i++){
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
        area.append("Last Updated: " + forecast[0].creationTime+"\n");
        for(int i=0; i < numberOfWeatherDays; i++){
            //area.append("Location: " + forecast[i].location + "\n");
            area.append(forecast[i].timeThisDayRepresents + "\n");
            area.append("Temperature: " + forecast[i].currentTemperature + " " + forecast[i].weatherDescription +"\n");
            area.append("Temp Low: " + forecast[i].tempLow+"\n");
            area.append("Temp High: " + forecast[i].tempHigh+"\n");
            area.append("Humidity: " + forecast[i].humidity+"\n");
            area.append("Atmospheric Pressure: " + forecast[i].atmosphericPressure+"\n");
            area.append("Wind Speed: " + forecast[i].windSpeed+"\n");
            area.append("Wind Degree: " + forecast[i].windDegree+"\n");
            area.append("\n\n");

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
            area.append("Last Updated: " + forecast[i].creationTime+"\n");
        }
        return area;
    }
    public String getCurrentLocationName(){
        return forecast[0].location;
    }
}