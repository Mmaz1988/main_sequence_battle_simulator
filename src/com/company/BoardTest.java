package com.company;

import com.company.types.Faction;
import com.company.types.Range;
import com.company.types.Skill;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testBoard()
    {}



    @Test
    void printBoardTest4()
    {
        int i = 0;
        int wins = 0;

        int total = 1000;

        Board b = new Board();

        Character hero = new Character("hero1",5,3,2,new int[]{0,0,0,2,0,0,0,0,1,3,0,0,0});
        Combatant heroCombat = new Combatant(hero,new Weapon("gun", Range.MEDIUM,5, Skill.RANGED), new Armor("bullet-proof west",2));
        heroCombat.faction = Faction.CREW;

        Character baddie6 = new Character("baddie6",5,3,2,new int[]{0,0,0,2,0,0,0,0,1,3,0,0,0});
        Combatant baddieCombat6 = new Combatant(baddie6,new Weapon("gun", Range.MEDIUM,5, Skill.RANGED), new Armor("bullet-proof west",2));
        baddieCombat6.faction = Faction.ADVERSARY;

        b.fighters.add(heroCombat);
        b.fighters.add(baddieCombat6);

        b.initializeBoard();
        b.boardGrid[3][0].setOccupant(heroCombat);
        b.boardGrid[3][6].setOccupant(baddieCombat6);

        while (i < total) {

            Board b1 = new Board(b);


            //       System.out.println(b.printBoard());

            b1.simulateBattle();

            if (b1.playersWon)
            {
                wins++;
            }
            i++;
        }

        double ratio = (double) wins / total;
        System.out.println("Crew won " + (ratio * 100) + "% of the time.");
    }

}