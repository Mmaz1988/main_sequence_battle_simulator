package com.company.gui;

import com.company.Board;
import com.company.Combatant;
import com.company.MainSequenceSimulatorGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PopUp extends JPopupMenu {

    public MainSequenceSimulatorGUI gui;
    public BoardLabel gridLabel;
    public JMenuItem addCrew;
    public JMenuItem addEnemy;
    public JMenuItem remove;

    public PopUp(MainSequenceSimulatorGUI gui, BoardLabel gridLabel) {
        this.gui = gui;
        this.gridLabel = gridLabel;

        addCrew = new JMenuItem(new AbstractAction("Add selected crew") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gui.getCrewList().isSelectionEmpty() && !gridLabel.bp.occupied)
                {
                    Combatant selectedCombatant =  (Combatant) gui.getCrewList().getModel().getElementAt(gui.getCrewList().getSelectedIndex());
                    gridLabel.addCombatant(selectedCombatant);
                    gui.board.fighters.add(selectedCombatant);
                    ((DefaultListModel) gui.getCrewList().getModel()).remove(gui.getCrewList().getSelectedIndex());
                    gui.getCrewList().repaint();
                } else
                {
                    gui.getSimulationPane().setText("You have not selected a viable candidate or the field is already occupied.");
                }

            }
        });
        addEnemy = new JMenuItem(new AbstractAction("Add selected enemy") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gui.getEnemyList().isSelectionEmpty() && !gridLabel.bp.occupied)
                {
                    Combatant selectedCombatant =  (Combatant) gui.getEnemyList().getModel().getElementAt(gui.getEnemyList().getSelectedIndex());
                    gridLabel.addCombatant(selectedCombatant);
                    gui.board.fighters.add(selectedCombatant);
                    ((DefaultListModel) gui.getEnemyList().getModel()).remove(gui.getEnemyList().getSelectedIndex());
                    gui.getCrewList().repaint();
                } else
                {
                    gui.getSimulationPane().setText("You have not selected a viable candidate or the field is already occupied.");
                }
            }
        });

        remove = new JMenuItem(new AbstractAction("Remove figure") {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        add(addCrew);
        add(addEnemy);
        add(remove);

    }
}
