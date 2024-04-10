package com.company;

import com.company.gui.BoardLabel;
import com.company.gui.PopUp;
import com.company.gui.SkillRenderer;
import com.company.skills.SkillTemplate;
import com.company.types.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;

public class MainSequenceSimulatorGUI {


    private JPanel mainPanel;

    public  JFrame baseFrame;
    private JList<Combatant> crewList;
    private JList<Combatant> enemyList;
    private JPanel boardPanel;
    private JButton addCrewButton;
    private JButton addEnemyButton;
    private JButton simulateBattleButton;
    private JTextPane simulationPane;
    private JButton loadBoardButton;
    private JButton newBoardButton;
    private JButton addPlayerCrewButton;
    private JComboBox<Enemy> enemyComboBox;
    private JButton okButton;


    public Board board;

    public MainSequenceSimulatorGUI(JFrame frame)
    {
        this.baseFrame = frame;
   //     boardPanel = new JPanel();

        Board b = new Board();
        b.initializeBoard();

        this.board = b;

        loadBoard();
        DefaultListModel<Combatant> crewModel = new DefaultListModel<>();
        crewList.setModel(crewModel);

        DefaultListModel<Combatant> enemyModel = new DefaultListModel<>();
        enemyList.setModel(enemyModel);


        for (int i = 0; i < Enemy.values().length; i++)
        {
            enemyComboBox.addItem(Enemy.values()[i]);
        }


  //     mainPanel.add(new BoardGrid(10,10,20));
        addCrewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel charCreationPanel = new JPanel();
                charCreationPanel.setPreferredSize(new Dimension(400,500));
                charCreationPanel.setLayout(new BoxLayout(charCreationPanel,BoxLayout.Y_AXIS));

                JPanel namePanel = new JPanel();
                namePanel.add(new JLabel("Name:"));
                JTextField name = new JTextField(5);
                namePanel.add(name);
                namePanel.add(Box.createHorizontalStrut(15));
                charCreationPanel.add(namePanel);


                JTextField soldier = new JTextField(5);
                JTextField agent = new JTextField(5);
                JTextField tech = new JTextField(5);


                JPanel statPanel = new JPanel();

                statPanel.add(new JLabel("Soldier:"));
                statPanel.add(soldier);
                statPanel.add(Box.createHorizontalStrut(15)); // a spacer
                statPanel.add(new JLabel("Agent:"));
                statPanel.add(agent);
                statPanel.add(Box.createHorizontalStrut(15)); // a spacer
                statPanel.add(new JLabel("Tech:"));
                statPanel.add(tech);

                charCreationPanel.add(statPanel);

                JScrollPane listPane = new JScrollPane();
                listPane.setPreferredSize(new Dimension(250,200));
                DefaultListModel<Skill> skillModel = new DefaultListModel<>();
                JList<Skill> skillList = new JList<>();
                skillList.setModel(skillModel);
                skillList.setCellRenderer(new SkillRenderer());

                skillList.setEnabled(false);

                listPane.setViewportView(skillList);
                charCreationPanel.add(listPane);


                LinkedHashMap<Skill, Rank> skillRank = new LinkedHashMap<>();

                for (Skill skill : SkillTemplate.skillStats.keySet())
                {
                    skillModel.addElement(skill);
                    skillRank.put(skill,Rank.NONE);
                }
                ((SkillRenderer) skillList.getCellRenderer()).skillRanks = skillRank;


                skillList.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2)
                        {
                            int index = skillList.locationToIndex(e.getPoint());

                            if (skillRank.get(skillList.getModel().getElementAt(index)).equals(Rank.NONE))
                            {
                                skillRank.put(skillList.getModel().getElementAt(index), Rank.BASIC);
                                skillList.repaint();
                            }
                            else if (skillRank.get(skillList.getModel().getElementAt(index)).equals(Rank.BASIC))
                            {
                                skillRank.put(skillList.getModel().getElementAt(index), Rank.ADVANCED);
                                skillList.repaint();
                            } else if (skillRank.get(skillList.getModel().getElementAt(index)).equals(Rank.ADVANCED))
                            {
                                skillRank.put(skillList.getModel().getElementAt(index), Rank.MASTER);
                                skillList.repaint();
                            }
                            else if (skillRank.get(skillList.getModel().getElementAt(index)).equals(Rank.MASTER))
                            {
                                skillRank.put(skillList.getModel().getElementAt(index), Rank.NONE);
                                skillList.repaint();
                            }

                        }
                    }
                });

                JPanel addWeaponPanel = new JPanel();

                addWeaponPanel.add(new JLabel("weapon:"));
                JTextField weaponName = new JTextField(5);
                addWeaponPanel.add(weaponName);

                addWeaponPanel.add(new JLabel("damage:"));
                JTextField damage = new JTextField(5);
                addWeaponPanel.add(damage);

                Range[] ranges = Range.values();
                JComboBox<Range> combo = new JComboBox<>(ranges);
                addWeaponPanel.add(combo);

                Skill[] skills = Skill.values();
                JComboBox<Skill> comboSkills = new JComboBox<>(skills);
                addWeaponPanel.add(comboSkills);


                JPanel addArmorPanel = new JPanel();
                addArmorPanel.add(new JLabel("armor:"));
                JTextField armorName = new JTextField(5);
                addArmorPanel.add(armorName);

                addArmorPanel.add(new JLabel("soak:"));
                JTextField soak = new JTextField(5);
                addArmorPanel.add(soak);

                charCreationPanel.add(addWeaponPanel);
                charCreationPanel.add(addArmorPanel);

                int result = JOptionPane.showConfirmDialog(mainPanel, charCreationPanel,
                        "Create your character", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {

                    Integer sol;
                    Integer ag;
                    Integer t;

                    if (soldier.getText().equals(""))
                    {
                        sol = 0;
                    } else {
                        sol = Integer.parseInt(soldier.getText());
                    }
                    if (agent.getText().equals(""))
                    {
                        ag = 0;
                    } else {
                        ag = Integer.parseInt(agent.getText());
                    }

                    if (tech.getText().equals(""))
                    {
                        t = 0;
                    } else {
                        t = Integer.parseInt(tech.getText());
                    }

                    Character c = new Character(name.getText(), sol,ag,t);

                    for (Skill skill : skillRank.keySet())
                    {
                        c.skills.put(skill,new SkillTemplate(c,skill,skillRank.get(skill)));
                    }

                    Combatant cb = new Combatant(c);

                    if (!weaponName.getText().equals(""))
                    {
                        cb.weapon = new Weapon(weaponName.getText(),(Range) combo.getSelectedItem(),Integer.parseInt(damage.getText()),(Skill) comboSkills.getSelectedItem());
                    }

                    if (!armorName.getText().equals(""))
                    {
                        cb.armor = new Armor(armorName.getText(),Integer.parseInt(soak.getText()));
                    }

                    cb.faction = Faction.CREW;

                    crewModel.addElement(cb);




             //       System.out.println("x value: " + xField.getText());
                    //       System.out.println("y value: " + yField.getText());
                }

            }
        });
        addEnemyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel charCreationPanel = new JPanel();
                charCreationPanel.setPreferredSize(new Dimension(400,500));
                charCreationPanel.setLayout(new BoxLayout(charCreationPanel,BoxLayout.Y_AXIS));

                JPanel namePanel = new JPanel();
                namePanel.add(new JLabel("Name:"));
                JTextField name = new JTextField(5);
                namePanel.add(name);
                namePanel.add(Box.createHorizontalStrut(15));
                charCreationPanel.add(namePanel);


                JTextField soldier = new JTextField(5);
                JTextField agent = new JTextField(5);
                JTextField tech = new JTextField(5);


                JPanel statPanel = new JPanel();

                statPanel.add(new JLabel("Soldier:"));
                statPanel.add(soldier);
                statPanel.add(Box.createHorizontalStrut(15)); // a spacer
                statPanel.add(new JLabel("Agent:"));
                statPanel.add(agent);
                statPanel.add(Box.createHorizontalStrut(15)); // a spacer
                statPanel.add(new JLabel("Tech:"));
                statPanel.add(tech);

                charCreationPanel.add(statPanel);

                JScrollPane listPane = new JScrollPane();
                listPane.setPreferredSize(new Dimension(250,200));
                DefaultListModel<Skill> skillModel = new DefaultListModel<>();
                JList<Skill> skillList = new JList<>();
                skillList.setModel(skillModel);
                skillList.setCellRenderer(new SkillRenderer());

                skillList.setEnabled(false);

                listPane.setViewportView(skillList);
                charCreationPanel.add(listPane);


                LinkedHashMap<Skill, Rank> skillRank = new LinkedHashMap<>();

                for (Skill skill : SkillTemplate.skillStats.keySet())
                {
                    skillModel.addElement(skill);
                    skillRank.put(skill,Rank.NONE);
                }
                ((SkillRenderer) skillList.getCellRenderer()).skillRanks = skillRank;


                skillList.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2)
                        {
                            int index = skillList.locationToIndex(e.getPoint());

                            if (skillRank.get(skillList.getModel().getElementAt(index)).equals(Rank.NONE))
                            {
                                skillRank.put(skillList.getModel().getElementAt(index), Rank.BASIC);
                                skillList.repaint();
                            }
                            else if (skillRank.get(skillList.getModel().getElementAt(index)).equals(Rank.BASIC))
                            {
                                skillRank.put(skillList.getModel().getElementAt(index), Rank.ADVANCED);
                                skillList.repaint();
                            } else if (skillRank.get(skillList.getModel().getElementAt(index)).equals(Rank.ADVANCED))
                            {
                                skillRank.put(skillList.getModel().getElementAt(index), Rank.MASTER);
                                skillList.repaint();
                            }
                            else if (skillRank.get(skillList.getModel().getElementAt(index)).equals(Rank.MASTER))
                            {
                                skillRank.put(skillList.getModel().getElementAt(index), Rank.NONE);
                                skillList.repaint();
                            }

                        }
                    }
                });

                JPanel equipmentPanel = new JPanel();

                equipmentPanel.setLayout(new BoxLayout(equipmentPanel,BoxLayout.Y_AXIS));

                JPanel addWeaponPanel = new JPanel();

                addWeaponPanel.add(new JLabel("weapon:"));
                JTextField weaponName = new JTextField(5);
                addWeaponPanel.add(weaponName);

                addWeaponPanel.add(new JLabel("damage:"));
                JTextField damage = new JTextField(5);
                addWeaponPanel.add(damage);

                Range[] ranges = Range.values();
                JComboBox<Range> combo = new JComboBox<>(ranges);
                addWeaponPanel.add(combo);

                Skill[] skills = Skill.values();
                JComboBox<Skill> comboSkills = new JComboBox<>(skills);
                addWeaponPanel.add(comboSkills);


                JPanel addArmorPanel = new JPanel();
                addArmorPanel.add(new JLabel("armor:"));
                JTextField armorName = new JTextField(5);
                addArmorPanel.add(armorName);

                addArmorPanel.add(new JLabel("soak:"));
                JTextField soak = new JTextField(5);
                addArmorPanel.add(soak);

                equipmentPanel.add(addWeaponPanel);
                equipmentPanel.add(addArmorPanel);

                charCreationPanel.add(equipmentPanel);


                int result = JOptionPane.showConfirmDialog(mainPanel, charCreationPanel,
                        "Create your character", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {

                    Integer sol;
                    Integer ag;
                    Integer t;

                    if (soldier.getText().equals(""))
                    {
                        sol = 0;
                    } else {
                        sol = Integer.parseInt(soldier.getText());
                    }
                    if (agent.getText().equals(""))
                    {
                        ag = 0;
                    } else {
                        ag = Integer.parseInt(agent.getText());
                    }

                    if (tech.getText().equals(""))
                    {
                        t = 0;
                    } else {
                        t = Integer.parseInt(tech.getText());
                    }

                    Character c = new Character(name.getText(), sol,ag,t);

                    for (Skill skill : skillRank.keySet())
                    {
                        c.skills.put(skill,new SkillTemplate(c,skill,skillRank.get(skill)));
                    }


                    Combatant cb = new Combatant(c);

                    if (!weaponName.getText().equals(""))
                    {
                        cb.weapon = new Weapon(weaponName.getText(),(Range) combo.getSelectedItem(),Integer.parseInt(damage.getText()),(Skill) comboSkills.getSelectedItem());
                    }

                    cb.faction = Faction.ADVERSARY;

                    enemyModel.addElement(cb);




                    //       System.out.println("x value: " + xField.getText());
                    //       System.out.println("y value: " + yField.getText());
                }

            }
        });
        simulateBattleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runBattleSimulation();
            }
        });
        loadBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board = SampleBoards.testBoard();
                loadBoard();
            }
        });
        newBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board = new Board();
                board.initializeBoard();
                loadBoard();
            }
        });
        addPlayerCrewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultListModel<Combatant> cm = (DefaultListModel<Combatant>) crewList.getModel();
                cm.clear();
                cm.addAll(SampleBoards.createCrewList());


            }
        });
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(enemyComboBox.getSelectedIndex() == -1))
                {
                    DefaultListModel<Combatant> cm = (DefaultListModel<Combatant>) enemyList.getModel();
                    Combatant c = SampleBoards.returnEenemy(enemyComboBox.getItemAt(enemyComboBox.getSelectedIndex()));

                    cm.addElement(c);

                }
                {

                }
            }
        });
    }

//Getter and Setter;

    public void loadBoard()
    {
        boardPanel.removeAll();
        boardPanel.revalidate();

        boardPanel.setLayout(new GridLayout(10,10));
        BoardLabel[][] boardGrid =  new BoardLabel[10][10];

        for(int i=0; i<10; i++)
        {
            for(int j=0; j<10; j++)
            {
                boardGrid[i][j] = new BoardLabel(board.boardGrid[i][j]);
                boardGrid[i][j].setVisible(true);
                boardPanel.add(boardGrid[i][j]);

                boardGrid[i][j].setComponentPopupMenu(new PopUp(this,boardGrid[i][j]));

            }
        }

        boardPanel.repaint();


        /*
        for (Combatant c : board.crew)
        {
            crewModel.addElement(c);
        }
        crewList.setModel(crewModel);
         */




    }

    public void runBattleSimulation()
    {
        int i = 0;
        int wins = 0;
        int total = 1000;

        while (i < total) {
            Board b = new Board(board);
            b.simulateBattle();
            if (b.playersWon)
            {
                wins++;
            }
            i++;
        }
        double ratio = (double) wins / total;
        simulationPane.setText("Crew won " + (ratio * 100) + "% of the time.");
    }




    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel panel) {
        this.mainPanel = panel;
    }


    public void setCrewList(JList crewList) {
        this.crewList = crewList;
    }

    public JList getEnemyList() {
        return enemyList;
    }

    public void setEnemyList(JList enemyList) {
        this.enemyList = enemyList;
    }
    public JList getCrewList() {
        return crewList;
    }

    public JTextPane getSimulationPane() {
        return simulationPane;
    }

    public void setSimulationPane(JTextPane simulationPane) {
        this.simulationPane = simulationPane;
    }

    public JComboBox<Enemy> getEnemyComboBox() {
        return enemyComboBox;
    }

    public void setEnemyComboBox(JComboBox<Enemy> enemyComboBox) {
        this.enemyComboBox = enemyComboBox;
    }

}
