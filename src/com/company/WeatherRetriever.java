package com.company;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by laynebritton on 11/14/17.
 * This class exists to retrieve the weather data from the api and write to a local file
 */
public class WeatherRetriever {
    String urlCall;
    //URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=Moscow&cnt=7&units=imperial&appid=2b290376f4e81ff3eb5ef82867095610");
    //url = new URL("http://api.openweathermap.org/data/2.5/forecast?zip=59102&cnt=7&units=imperial&appid=2b290376f4e81ff3eb5ef82867095610");
    public WeatherRetriever(){}
    public void getForecast(String location, int zeroCityoneZipcode) throws Exception{
        String urlString = "";
        if(zeroCityoneZipcode==0){
            urlString = "http://api.openweathermap.org/data/2.5/forecast?q=" + location + "&cnt=7&units=imperial&appid=2b290376f4e81ff3eb5ef82867095610";
        }else if(zeroCityoneZipcode == 1){
            urlString = "http://api.openweathermap.org/data/2.5/forecast?zip=" + location + "&cnt=7&units=imperial&appid=2b290376f4e81ff3eb5ef82867095610";
        }
        URL url = new URL(urlString);
        URLConnection con = url.openConnection();

        //Taking Stream from Call and writing it to a file
        InputStream inputStream = con.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(new File("data3.json"));

        byte[] buf = new byte[512];
        while(true){
            int len = inputStream.read(buf);
            if(len == -1){
                break;
            }
            fileOutputStream.write(buf,0,len);
        }
        inputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
        return;
        //Done Writing File
    }
}