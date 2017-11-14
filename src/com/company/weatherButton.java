package com.company;

import javax.swing.*;

/**
 * Created by laynebritton on 11/10/17.
 */
public class weatherButton {
    public weatherButton(String name, int xCord, int yCord, int height, int width){
        JButton button = new JButton(name);
        button.setBounds(xCord,yCord,width,height);
    }
}
