package com.company;

public class Main {
    //Hovering over the update weather data button for three seconds provides
    //A brief menu describing how to use the application

    //In order for offline caching to work, the location requested needs to have
    //been connected to by internet previously

    //The application needs its defaultLocation.txt file in order to launch
    //automatic weather updates while in use.
    //If the file is gone, just set any location as the default location the relaunch the app
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
            menuExample.currentLocation=defaultGetter[0];
            menuExample.currentLocationType=Integer.parseInt(defaultGetter[1]);
            menuExample.updateTextArea(menuExample.weatherRetriever.updateTextAreaDaily());
            menuExample.updateAppTitle(defaultGetter[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        defaultGetter = null; //Deletes this array from the program
        menuExample.populateFavorites();
        menuExample.appFrame.revalidate();
        menuExample.appFrame.repaint();
        menuExample.startAutoUpdates(); //This only works if the application has a default location prior to launch
    }
}
