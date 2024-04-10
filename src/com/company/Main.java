package com.company;

import javax.swing.*;

public class Main {
//The main sequence battle simulation

    public static void main(String[] args) {


        JFrame frame = new JFrame("Main");

        MainSequenceSimulatorGUI gui = new MainSequenceSimulatorGUI(frame);

        frame.setContentPane(gui.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
     //   frame.setSize(1000,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


	// write your code here
    }
}
