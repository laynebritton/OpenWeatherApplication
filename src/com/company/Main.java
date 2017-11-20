package com.company;

public class Main {
    //This is an api call that worked for daily
    //http://api.openweathermap.org/data/2.5/weather?q=London&cnt=7&appid=2b290376f4e81ff3eb5ef82867095610

    //This is an api call that worked for forecast
    //http://api.openweathermap.org/data/2.5/forecast?q=Seattle&cnt=7&appid=2b290376f4e81ff3eb5ef82867095610
    public static void main(String[] args) throws Exception {
        MenuExample menuExample = new MenuExample();
        //Open Default Config
        String[] defaultGetter = new String[10];
        try {
            defaultGetter = menuExample.updateToDefault();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            menuExample.updateWeatherData(defaultGetter[0],Integer.parseInt(defaultGetter[1]));
            menuExample.updateAppTitle(defaultGetter[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        defaultGetter = null; //Deletes this array from the program
        menuExample.populateFavorites();
        menuExample.appFrame.revalidate();
        menuExample.appFrame.repaint();
    }
}
