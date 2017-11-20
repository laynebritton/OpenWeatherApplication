package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by laynebritton on 11/14/17.
 */
public class MenuExample implements ActionListener, MouseListener {
    JMenu menu, submenu, defaultLocation,favorites;
    JMenuItem addToFavoritesButton,i2,i3,i4,i5, setAsDefault, goToDefault;
    JButton dailyButton,weeklyButton, updateButton;
    JTextArea textArea;
    JFrame appFrame;
    JScrollPane scrollPane,infoPane;
    WeatherRetriever weatherRetriever;
    JTextField locationEntry;
    JComboBox locationTypeSelector;
    String currentLocation;
    int currentLocationType;
    JMenu[] favoritesList;
    int totalFavorites;
    JFrame infoFrame;
    Timer updateTimer, infoTimer;
    boolean isDaily; //if it is false then the program is weekly. Program defaults to daily
    JScrollBar tempScrollBar;

    MenuExample(){
        isDaily = true; //Program defaults to daily view
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
        updateButton.addMouseListener(this);

        appFrame.add(dailyButton);
        appFrame.add(weeklyButton);
        appFrame.add(updateButton);
        appFrame.setTitle("OpenWeatherApp");

        menu = new JMenu("Menu");

        defaultLocation = new JMenu("Default Location");
        defaultLocation.addActionListener(this);
        goToDefault = new JMenuItem("Go to default location");
        goToDefault.addActionListener(this);
        setAsDefault = new JMenuItem("Set current location as default");
        setAsDefault.addActionListener(this);
        defaultLocation.add(goToDefault);
        defaultLocation.add(setAsDefault);

        favorites = new JMenu("Favorites");


        addToFavoritesButton = new JMenuItem("Add current location to favorites");
        addToFavoritesButton.addActionListener(this);

        i2 = new JMenuItem("End Program");
        i2.addActionListener(this);

        menu.add(addToFavoritesButton); menu.add(i2);

        menuBar.add(menu);
        menuBar.add(defaultLocation);
        menuBar.add(favorites);

        locationEntry = new JTextField();
        locationEntry.setBounds(20,500,150,30);
        appFrame.add(locationEntry);


        locationTypeSelector = new JComboBox(new String[] {"City Name", "Zip Code","City ID","Lat & Lon"});
        locationTypeSelector.setBounds(180,500,150,30);
        locationTypeSelector.addMouseListener(this);
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
        //currentLocation = location;
        weatherRetriever.loadWeatherCache(location);
    }

    public void setDefaultLocation(String currentLocation){
        BufferedWriter bufferedWriter = null;

        try{
            bufferedWriter = new BufferedWriter(new FileWriter("defaultLocation.txt"));
            bufferedWriter.write(currentLocation + " " + currentLocationType);
            bufferedWriter.flush();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            if (bufferedWriter!=null) try{
                bufferedWriter.close();
            } catch(IOException ioe){

            }
        }
    }

    public void addToFavorites(String currentLocation){
        BufferedWriter bufferedWriter = null;

        try{
            bufferedWriter = new BufferedWriter(new FileWriter("favorites.txt",true));
            bufferedWriter.write(currentLocation + " " + currentLocationType);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            if (bufferedWriter!=null) try{
                bufferedWriter.close();
            } catch(IOException ioe){

            }
        }
    }
    public void populateFavorites() throws Exception{
        totalFavorites = 0; //Resets and overwrites previous favorites
        File favoriteCache = new File("favorites.txt");
        favoritesList = new JMenu[50];  //Arbitrarily initialized to 50
        int i;
        FileReader fileReader = new FileReader(favoriteCache);
        String favoriteText = "";
        while((i = fileReader.read())!=-1){
            char ch = (char)i;
            favoriteText = favoriteText + ch;
        }
        fileReader.close();
        System.out.println(favoriteText);
        StringTokenizer stringTokenizer = new StringTokenizer(favoriteText);
        i = 0;
        while(stringTokenizer.hasMoreTokens()){
            favoritesList[i] = new JMenu();
            favoritesList[i].setText(stringTokenizer.nextToken() + " " + stringTokenizer.nextToken());
            JMenuItem use = new JMenuItem("Update");
            use.addActionListener(this);
            JMenuItem remove = new JMenuItem("Remove from favorites");
            remove.addActionListener(this);
            favoritesList[i].add(use);
            favoritesList[i].add(remove);
            favoritesList[i].addActionListener(this);
            favorites.add(favoritesList[i]);
            i++;
        }
        totalFavorites = i;
        for(i=i; i < 50; i++){
            favoritesList[i] = new JMenu();
            //Filling the remained of the array with initialized JMenus
            //To avoid null pointer exceptions later when checking for submenu interactions
            //Yes, it's gross. Time constraints have caused this
        }


    }
    
    public String[] updateToDefault() throws Exception{
        File defaultCache = new File("defaultLocation.txt");
        int i;
        FileReader fileReader = new FileReader(defaultCache);
        String defaultText = "";
        while((i = fileReader.read())!=-1){
            char ch = (char)i;
            defaultText = defaultText + ch;
        }
        String[] returnString = defaultText.split("\\s");
        fileReader.close();
        return returnString;
    }
    private void removeFromFavorites(String toDelete) throws Exception {
        File inputFile = new File("favorites.txt");
        File tempFile = new File("temp.txt");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;
        while((currentLine = reader.readLine())!=null){
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(toDelete)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        tempFile.renameTo(inputFile);
        System.out.println(toDelete + " has been removed from favorites");
    }

    public void startAutoUpdates(){
        updateTimer = new Timer();
        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    updateWeatherData(currentLocation,currentLocationType);
                    tempScrollBar = scrollPane.getVerticalScrollBar();
                    JScrollBar test = scrollPane.getVerticalScrollBar();
                    appFrame.remove(scrollPane);
                    updateAppTitle(weatherRetriever.getCurrentLocationName());
                    if(isDaily==true){

                        updateTextArea(weatherRetriever.updateTextAreaDaily());
                    }else{
                        updateTextArea(weatherRetriever.updateTextAreaWeekly());
                        scrollPane.getVerticalScrollBar().setValue(tempScrollBar.getValue());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },60*120,60*120);
    }
    public void cancelAutoUpdates(){
        updateTimer.cancel();
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if(actionEvent.getSource()==dailyButton){
            appFrame.remove(scrollPane);
            updateAppTitle(weatherRetriever.getCurrentLocationName());
            updateTextArea(weatherRetriever.updateTextAreaDaily());
            isDaily = true;
        }
        if(actionEvent.getSource()==weeklyButton){
            appFrame.remove(scrollPane);
            updateAppTitle(weatherRetriever.getCurrentLocationName());
            updateTextArea(weatherRetriever.updateTextAreaWeekly());
            isDaily = false;
        }
        if(actionEvent.getSource()==updateButton){
            try {
                updateWeatherData(locationEntry.getText(),locationTypeSelector.getSelectedIndex());
                currentLocation = locationEntry.getText();
                currentLocationType = locationTypeSelector.getSelectedIndex();
                appFrame.remove(scrollPane);
                if(isDaily==true){
                    updateAppTitle(weatherRetriever.getCurrentLocationName());
                    updateTextArea(weatherRetriever.updateTextAreaDaily());

                }else{
                    updateAppTitle(weatherRetriever.getCurrentLocationName());
                    updateTextArea(weatherRetriever.updateTextAreaWeekly());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if(actionEvent.getSource()==setAsDefault){
            setDefaultLocation(weatherRetriever.getCurrentLocationName());
        }
        if(actionEvent.getSource()==goToDefault){
            String[] defaultGetter = new String[10];
            try {
                defaultGetter = updateToDefault();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                updateWeatherData(defaultGetter[0],Integer.parseInt(defaultGetter[1]));
            } catch (Exception e) {
                e.printStackTrace();
            }
            updateAppTitle(defaultGetter[0]);
        }
        if(actionEvent.getSource()== addToFavoritesButton){
            for(int i = 0; i < totalFavorites; i++){ //Resets favorites bar
                favorites.remove(favoritesList[i]);
            }
            addToFavorites(weatherRetriever.getCurrentLocationName());
            try {
                populateFavorites();
            } catch (Exception e) {
                e.printStackTrace();
            }
            appFrame.revalidate();
            appFrame.repaint();
        }
        if(actionEvent.getSource()==i2){
            System.exit(0);
        }
        for(int i = 0; i < 50; i ++){
            if(actionEvent.getSource()==favoritesList[i].getMenuComponent(0)){  //Updates
                String temp = favoritesList[i].getText();
                String[] tempArray = temp.split("\\s");
                try {
                    updateWeatherData(tempArray[0],Integer.parseInt(tempArray[1]));
                    appFrame.remove(scrollPane);
                    if(isDaily==true){
                        updateAppTitle(weatherRetriever.getCurrentLocationName());
                        updateTextArea(weatherRetriever.updateTextAreaDaily());

                    }else{
                        updateAppTitle(weatherRetriever.getCurrentLocationName());
                        updateTextArea(weatherRetriever.updateTextAreaWeekly());
                    }
                    currentLocation = tempArray[0];
                    currentLocationType = Integer.parseInt(tempArray[1]);
                } catch (Exception e) {
                    //e.printStackTrace();
                }

                updateAppTitle(tempArray[0]);
            }
            if(actionEvent.getSource()==favoritesList[i].getMenuComponent(1)){  //remove
                try {
                    removeFromFavorites(favoritesList[i].getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    for(int j = 0; j < totalFavorites; j++){ //Resets favorites bar
                        favorites.remove(favoritesList[j]);
                    }
                    populateFavorites();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                appFrame.revalidate();
                appFrame.repaint();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        infoTimer = new Timer();
        infoTimer.schedule(new TimerTask() {    //All menus wait 5 seconds before appearing
            @Override
            public void run() {
                if (mouseEvent.getSource()==updateButton && infoFrame==null){
                    //JOptionPane.showMessageDialog(null,"Test\nTest2\nTest3\nlll");

                    JTextArea infoText = new JTextArea();

                    infoText.setText("To get weather data for a city:\n Enter its info into the text field\n Choose type of location from dropdown menu \n Click Update Button\n Then click either daily or weekly weather \n \n Latitude and longitude coordinates\n should be separated by a - symbol\n\n Examples\n Seattle (City example)\n 59102 (Zip code example) \n 524901 (City ID example) \n 30-175 (Lat&Lon Example)");
                    infoText.setBounds(0,0,300,250);
                    infoPane = new JScrollPane(infoText);

                    infoFrame = new JFrame();
                    infoFrame.getContentPane().add(infoText,BorderLayout.CENTER);
                    infoFrame.setSize(300,250);

                    infoFrame.setLocationRelativeTo(mouseEvent.getComponent());
                    Rectangle rect = infoFrame.getBounds();
                    //rect.x = rect.x +250;
                    rect.y = rect.y  - 200;
                    infoFrame.setBounds(rect);
                    infoFrame.add(infoPane);

                    infoFrame.setLayout(null);
                    infoFrame.setVisible(true);
                    infoFrame.revalidate();
                    infoFrame.repaint();

                };
            }
        },60*45,60*45);

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        infoTimer.cancel();
        if(infoFrame!=null && mouseEvent.getSource()==updateButton){
            infoFrame.dispose();

            infoFrame = null;
        }
    }
}
