package com.company.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BoardGrid extends JPanel{

    private JLabel[][] gridLabels;

    public BoardGrid(int rows, int cols, int cellWidth)
    {
        gridLabels = new JLabel[rows][cols];

        Dimension labelPrefSize = new Dimension(cellWidth,cellWidth);
        for (int row = 0; row < gridLabels.length; row++) {
            for (int col = 0; col < gridLabels[row].length; col++) {
                JLabel myLabel = new JLabel();
            //    myLabel.setOpaque(true);
                Border border = BorderFactory.createLineBorder(Color.blue,1);
                myLabel.setBorder(border);
                myLabel.setBackground(Color.white);

                myLabel.setPreferredSize(labelPrefSize);
                add(myLabel);
                gridLabels[row][col] = myLabel;
            }
        }
    }






}
