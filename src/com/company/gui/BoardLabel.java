package com.company.gui;

import com.company.Board;
import com.company.BoardPosition;
import com.company.Combatant;
import com.company.types.Faction;

import javax.swing.*;
import java.awt.*;

public class BoardLabel extends JLabel {

    public BoardPosition bp;
    public BoardLabel(BoardPosition bp)
    {
        super();
        this.bp  = bp;
        this.setHorizontalAlignment(CENTER);
        this.setVerticalAlignment(CENTER);
        this.setFont(new Font("Arial",Font.BOLD,18));

        if (bp.occupied) {
            ((JLabel) this).setText(bp.occupant.character.name.substring(0,1));
                  this.setToolTipText(bp.occupant.toFullString());
            if (bp.occupant.faction.equals(Faction.CREW))
                this.setBackground(new Color(0,255,0,50));

            if (bp.occupant.faction.equals(Faction.ADVERSARY))
                this.setBackground(new Color(0,0,255, 50));
        } else
        {
            this.setBackground(Color.white);
        }

        this.setOpaque(true);
        this.setBorder(BorderFactory.createLineBorder(Color.blue));
        this.setPreferredSize(new Dimension(50,50));

    }

    public void addCombatant(Combatant c)
    {
            bp.setOccupant(c);
            if (c.faction.equals(Faction.CREW))
                this.setBackground(new Color(0,255,0,50));

            if (c.faction.equals(Faction.ADVERSARY))
                this.setBackground(new Color(0,0,255, 50));

                ((JLabel) this).setText(c.character.name.substring(0,1));
                this.setToolTipText(c.toFullString());

    }

    public void removeCombatant()
    {
        bp.removeOccupant();
        this.setBackground(Color.white);
    }

}
