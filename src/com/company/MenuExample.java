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

    MenuExample(){
        JFrame f = new JFrame("OpenWeatherMap Application Version 0.1");
        JMenuBar menuBar = new JMenuBar();
        dailyButton = new JButton();
        dailyButton.setBounds(100,10,200,60);
        dailyButton.setText("Daily Weather");
        dailyButton.addActionListener(this);

        weeklyButton = new JButton();
        weeklyButton.setBounds(400,10,200,60);
        weeklyButton.setText("Weekly Weather");

        f.add(dailyButton);
        f.add(weeklyButton);
        f.setTitle("OpenWeatherApp: " + "Test Location");
        menu = new JMenu("Menu");
        defaultLocation = new JMenu("Default Location");
        favorites = new JMenu("Favorites");
        submenu = new JMenu("Sub Menu");
        i1 = new JMenuItem("Add To Favorites");
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
        f.setJMenuBar(menuBar);
        f.setSize(800,600);
        f.setLayout(null);
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==dailyButton){
            //Switch to daily data
        }
        if(actionEvent.getSource()==weeklyButton){
            //Switch to weekly data
        }
        if(actionEvent.getSource()==i2){
            System.exit(0);
        }
    }
}
