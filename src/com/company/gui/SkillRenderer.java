package com.company.gui;

import com.company.Combatant;
import com.company.types.Rank;
import com.company.types.Skill;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SkillRenderer extends DefaultListCellRenderer {

    private Border padBorder = new EmptyBorder(3,3,3,3);
    public LinkedHashMap<Skill,Rank> skillRanks = new LinkedHashMap<>();

    public SkillRenderer()
    {

    }

    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        Component c = super.getListCellRendererComponent(
                list,value,index,isSelected,cellHasFocus);
        JLabel l = (JLabel)c;
        //Changed to toString() instead of casting so that different objects can be rendered by this
        String f =  value.toString() + " " + skillRanks.get((Skill) value).toString();
        l.setText(f);
        l.setIcon(null);
        l.setBorder(padBorder);


        return l;

    }

}
