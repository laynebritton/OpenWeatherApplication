package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by laynebritton on 11/14/17.
 */
public class MenuExample implements ActionListener {
    JMenu menu, submenu, defaultLocation,favorites;
    JMenuItem i1,i2,i3,i4,i5;
    JButton dailyButton,weeklyButton, updateButton;
    JTextArea textArea;
    JFrame appFrame;
    JScrollPane scrollPane;
    WeatherRetriever weatherRetriever;
    JTextField locationEntry;
    JComboBox locationTypeSelector;


    MenuExample(){
        weatherRetriever = new WeatherRetriever();
        appFrame = new JFrame("OpenWeatherMap Application Version 0.1");
        JMenuBar menuBar = new JMenuBar();

        dailyButton = new JButton();
        dailyButton.setBounds(100,10,200,60);
        dailyButton.setText("Daily Weather");
        dailyButton.addActionListener(this);


        weeklyButton = new JButton();
        weeklyButton.setBounds(400,10,200,60);
        weeklyButton.setText("Weekly Weather");
        weeklyButton.addActionListener(this);

        updateButton = new JButton();
        updateButton.setBounds(500,500,200,50);
        updateButton.setText("Update Weather Data");
        updateButton.addActionListener(this);

        appFrame.add(dailyButton);
        appFrame.add(weeklyButton);
        appFrame.add(updateButton);
        appFrame.setTitle("OpenWeatherApp");

        menu = new JMenu("Menu");

        defaultLocation = new JMenu("Default Location");
        defaultLocation.addActionListener(this);

        favorites = new JMenu("Favorites");
        submenu = new JMenu("Sub Menu");

        i1 = new JMenuItem("Add To Favorites");
        i1.addActionListener(this);

        i2 = new JMenuItem("End Program");
        i2.addActionListener(this);

        i3 = new JMenuItem("Item 3");
        i4 = new JMenuItem("Item 4");
        i5 = new JMenuItem("Item 5");
        menu.add(i1); menu.add(i2); menu.add(i3);
        submenu.add(i4); submenu.add(i5);

        menu.add(submenu);
        menuBar.add(menu);
        menuBar.add(defaultLocation);
        menuBar.add(favorites);

        locationEntry = new JTextField();
        locationEntry.setBounds(20,500,150,30);
        appFrame.add(locationEntry);

        //Dropdown Menu
        /*
        locationTypeSelector = new JToolBar("Testing Shit");
        locationTypeSelector.setRollover(true);
        JButton delete = new JButton("test1");
        locationTypeSelector.add(new JComboBox(new String[] {"opt1", "opt2","opt3","opt4"}));
        */
        locationTypeSelector = new JComboBox(new String[] {"City Name", "Zip Code","City ID","Lat & Lon"});
        locationTypeSelector.setBounds(180,500,150,30);
        appFrame.add(locationTypeSelector);

        appFrame.setJMenuBar(menuBar);
        appFrame.setSize(800,600);
        appFrame.setLayout(null);
        appFrame.setVisible(true);

        scrollPane = new JScrollPane();     //The scrollPane is initialized and added to frame
        scrollPane.setBounds(0,0,0,0);  //So that is can be rewritten easier when the pane is updated
        appFrame.add(scrollPane);
    }


    public void updateTextArea(JTextArea area){
        //textArea = area;
        area.setBounds(20,100,500,400);
        scrollPane = new JScrollPane(area);
        scrollPane.setBounds(20,100,500,400);
        scrollPane.repaint();
        appFrame.add(scrollPane);
        appFrame.revalidate();
        appFrame.repaint();
    }
    public void updateAppTitle(String currentLocation){
        appFrame.setTitle("OpenWeatherApp: " + currentLocation);
    }
    public void updateWeatherData(String location, int locationType) throws Exception {
        /*
        Location Type Values
        0 = City Name
        1 = Zip Code
        2 = City ID
        3 = Longitude and Latitude
         */
        weatherRetriever.getForecast(location,locationType);
        weatherRetriever.loadWeatherCache();
        weatherRetriever.printWeeklyForecast();
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==dailyButton){
            appFrame.remove(scrollPane);
            updateAppTitle(weatherRetriever.getCurrentLocationName());
            updateTextArea(weatherRetriever.updateTextAreaDaily());
        }
        if(actionEvent.getSource()==weeklyButton){
            appFrame.remove(scrollPane);
            updateAppTitle(weatherRetriever.getCurrentLocationName());
            updateTextArea(weatherRetriever.updateTextAreaWeekly());
        }
        if(actionEvent.getSource()==updateButton){
            try {
                updateWeatherData(locationEntry.getText(),locationTypeSelector.getSelectedIndex());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if(actionEvent.getSource()==i1){
            //add to favorites
        }
        if(actionEvent.getSource()==i2){

            System.exit(0);
        }
    }
}
