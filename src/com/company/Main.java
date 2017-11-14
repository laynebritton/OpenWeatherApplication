package com.company;
import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.Scanner;

import org.json.*;
import sun.misc.IOUtils;

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
        URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=Seattle&cnt=7&appid=2b290376f4e81ff3eb5ef82867095610");
        URLConnection con = url.openConnection();
        File weatherCache = new File("data.json");


        FileReader fileReader = new FileReader(weatherCache);
        String data = "";

        int i;
        while((i = fileReader.read())!=-1){
            char ch = (char)i;
            data = data + ch;
        }
        JSONObject obj = new JSONObject(data);
        JSONArray forecastArray = obj.getJSONArray("list");

        /*
        Testing Print Statements
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
