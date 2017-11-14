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
       // File weatherCache = new File("weatherCache.json");
        //FileOutputStream writer = new FileOutputStream(weatherCache);

        //InputStream inputStream = con.getInputStream();
        //File weatherCache = new File();
        File weatherCache = new File("data.json");


        FileReader fileReader = new FileReader(weatherCache);
        String data = "";

        int i;
        while((i = fileReader.read())!=-1){
            char ch = (char)i;
            data = data + ch;
        }
        System.out.println(data);

        JSONObject obj = new JSONObject(weatherCache);
        String str = "{ \"cod\":\"200\",\"message\":0.1746,\"cnt\":7}";
        JSONObject obj2 = new JSONObject(str);
        String n = obj2.getString("cod");
        int j = obj2.getInt("message");
        System.out.println(n);
        System.out.println(j);


        //String owmVersion = obj.getJSONObject("cod").getString("omwVersion");
        /*
        BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream)));
        String line = null;
        while((line = bufferedReader.readLine()) != null){
            System.out.println(line);
        }
        */

    }


    public void callApi() throws Exception {
        URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q=Seattle&cnt=7&appid=2b290376f4e81ff3eb5ef82867095610");
        URLConnection con = url.openConnection();


    }

}
