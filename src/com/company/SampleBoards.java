package com.company;

import com.company.types.Enemy;
import com.company.types.Faction;
import com.company.types.Range;
import com.company.types.Skill;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentMap;



public class SampleBoards {

    /*
    public static LinkedHashMap<Enemy,Combatant> enemyMapping = new LinkedHashMap<>() {
        {
            put(Enemy.BRAWLER,createBrawler());
            put(Enemy.SOLDIER_A, createSoldierA());
            put(Enemy.SOLDIER_B, createSoldierB());
            put(Enemy.RIFLE_MAN,createRifleMan());
            put(Enemy.COMBAT_DRUID,createCombatDruid());
            put(Enemy.HIGH_RANK_SOLDIER,createHighRankSoldier());
            put(Enemy.MILITARY_LEADER, createMilitaryLeader());
            put(Enemy.LIGHT_TURRET,createLightTurret());
            put(Enemy.HEAVY_TURRET,createHeavyTurret());
            put(Enemy.VARUTHA_LEAF,createVaruthaLeaf());
            put(Enemy.VARUTHA_SOLDIER,createVaruthaSoldier());
            put(Enemy.VARUTHA_SPY,createVaruthaSpy());
            put(Enemy.VARUTHA_MOTHERNODE,createVaruthaMotherNode()); }};
     */
    public static Combatant returnEenemy(Enemy enemy)
    {
        Combatant c = null;
        switch(enemy) {
            case BRAWLER -> c = new Combatant(createBrawler());
            case SOLDIER_A -> c = new Combatant(createSoldierA());
            case SOLDIER_B -> c = new Combatant(createSoldierB());
            case RIFLE_MAN -> c = new Combatant(createRifleMan());
            case COMBAT_DRUID -> c = new Combatant(createCombatDroid());
            case HIGH_RANK_SOLDIER -> c = new Combatant(createHighRankSoldier());
            case MILITARY_LEADER -> c = new Combatant(createMilitaryLeader());
            case LIGHT_TURRET -> c = new Combatant(createLightTurret());
            case HEAVY_TURRET -> c = new Combatant(createHeavyTurret());
            case VARUTHA_LEAF -> c = new Combatant(createVaruthaLeaf());
            case VARUTHA_SOLDIER -> c = new Combatant(createVaruthaSoldier());
            case VARUTHA_SPY -> c = new Combatant(createVaruthaSpy());
            case VARUTHA_MOTHERNODE -> c = new Combatant(createVaruthaMotherNode());
            case DOG -> c = new Combatant(createOmukadeDog());
        }
        return c;
    }

    public static Board testBoard()
    {
        Board b = new Board();

        Character claw = new Character("Claw",4,4,2,new int[]{1,1,0,0,0,0,0,0,2,0,2,0,0});
        Weapon claws = new Weapon("claws",Range.MELEE,3,Skill.MELEE);
        claw.initiativeModifier = claw.initiativeModifier+1;
        Combatant cpet = new Combatant(claw,claws);
        cpet.damageSoak = 2;
        cpet.faction = Faction.CREW;


        Character baddie6 = new Character("baddie6",5,3,2,new int[]{0,0,0,2,0,0,0,0,1,2,0,0,0});
        Combatant baddieCombat6 = new Combatant(baddie6,new Weapon("gun", Range.MEDIUM,1, Skill.RANGED), new Armor("bullet-proof west",2));
        baddieCombat6.faction = Faction.ADVERSARY;

        b.fighters.add(cpet);
        b.fighters.add(baddieCombat6);

        b.initializeBoard();
        b.boardGrid[3][3].setOccupant(cpet);
        b.boardGrid[3][6].setOccupant(baddieCombat6);

        return b;
    }


    public static List<Combatant> createCrewList()
    {
        List<Combatant> crewList = new ArrayList<>();

        Character marcus = new Character("Mardo",6,4,0, new int[]{0,2,0,0,0,0,0,0,2,2,0,0,0});
        Weapon bigSword = new Weapon("big sword",Range.MELEE,4,Skill.MELEE);
        Armor exoArmor = new Armor("exo armor",4);
        Combatant m = new Combatant(marcus,bigSword,exoArmor);
        //tough
        m.damageSoak = 2;
        m.numbersOfAttack = 2;
        m.faction = Faction.CREW;
        crewList.add(m);

        Character alex = new Character("Knex",4,6,0, new int[]{1,1,0,0,0,1,0,2,0,0,0,1,0});
        Armor paddedClothing = new Armor("padded clothing",2);
        Combatant a = new Combatant(alex,null,paddedClothing);

        a.faction = Faction.CREW;
        crewList.add(a);

        Character carina = new Character("Paw",2,2,6,new int[]{0,0,2,0,2,0,1,0,0,1,0,0,0});
        Weapon lightPistol = new Weapon("light pistol", Range.CLOSE,4,Skill.RANGED);
        Combatant c = new Combatant(carina,lightPistol);

        c.faction = Faction.CREW;
        crewList.add(c);

        Character claw = new Character("Claw",3,3,2,new int[]{1,1,0,0,0,0,0,0,2,0,2,0,0});
        Weapon claws = new Weapon("claws",Range.MELEE,3,Skill.MELEE);
        claw.initiativeModifier = claw.initiativeModifier+1;
        Combatant cpet = new Combatant(claw,claws);
        cpet.damageSoak = 2;
        cpet.faction = Faction.CREW;
        crewList.add(cpet);

        Character havoc = new Character("Havoc",3,5,2,new int[]{1,0,1,1,0,0,0,0,0,2,1,0,0});
        Weapon rifle = new Weapon("heavy rifle",Range.LONG,6,Skill.RANGED);
        havoc.initiativeModifier = havoc.initiativeModifier + 1;
        Combatant h = new Combatant(havoc,rifle);
        h.damageSoak = h.damageSoak + 3;

        h.faction = Faction.CREW;
        crewList.add(h);

        Character fabi = new Character("Brian",2,3,5,new int[]{1,0,0,0,0,1,2,0,0,0,2,0,0});
        Weapon telekeneticBlast = new Weapon("telekenetic blast", Range.MEDIUM,4,Skill.INTERACTION);
        Combatant f = new Combatant(fabi,telekeneticBlast);
        f.damageSoak = f.damageSoak + 2;

        f.faction = Faction.CREW;
        crewList.add(f);

        Character damian = new Character("Damian",2,3,5,new int[]{0,2,0,0,0,0,0,0,2,2,0,0,0});
        Weapon sword = new Weapon("sword", Range.MELEE,4,Skill.MELEE);
        Combatant d = new Combatant(damian,sword);
        d.damageSoak = f.damageSoak + 4;

        d.faction = Faction.CREW;
        crewList.add(d);

        Character raphael = new Character("Raph",2,3,5,new int[]{1,0,0,0,0,1,2,0,0,0,2,0,0});
        Weapon gungun = new Weapon("gun", Range.MEDIUM,4,Skill.RANGED);
        Combatant r = new Combatant(raphael,gungun);
        r.damageSoak = r.damageSoak + 2;

        r.faction = Faction.CREW;
        crewList.add(r);

        return crewList;

    }

    //Soldiers

    public static Combatant createBrawler()
    {

        Character brawler = new Character("Brawler", 4,4,2,new int[]{1,0,0,0,0,0,0,0,1,0,0,1,0});
        Combatant c = new Combatant(brawler);
        c.faction = Faction.ADVERSARY;
        return c;

    }

    public static Combatant createOmukadeDog()
    {

        Character dog = new Character("Drat", 5,3,1,new int[]{1,1,0,0,0,0,0,0,2,0,0,2,0});
        Weapon w = new Weapon("teeth and claws",Range.MELEE,4, Skill.MELEE);
        Combatant c = new Combatant(dog);
        c.weapon = w;
        c.damageSoak = c.damageSoak + 2;
        c.faction = Faction.ADVERSARY;
        return c;

    }




    public static Combatant createSoldierA()
    {
        Character soldier = new Character("Soldier (RANGED)", 5,4,1,new int[]{1,0,0,0,0,0,0,0,1,2,0,0,0});
        Weapon gun = new Weapon("light pistol",Range.CLOSE,5,Skill.RANGED);
        Combatant c = new Combatant(soldier);
        c.weapon = gun;
        c.faction = Faction.ADVERSARY;
        return c;
    }

    public static Combatant createSoldierB()
    {
        Character soldier = new Character("Soldier (MELEE)", 5,4,1,new int[]{1,0,0,0,0,0,0,0,2,1,0,0,0});
        Weapon baton = new Weapon("baton",Range.MELEE,3,Skill.MELEE);
        Combatant c = new Combatant(soldier);
        c.weapon = baton;
        c.faction = Faction.ADVERSARY;
        return c;
    }

    public static Combatant createRifleMan()
    {
        Character soldier = new Character("Rifle man", 5,4,1,new int[]{1,0,0,0,0,0,0,0,1,2,0,0,0});
        Weapon rifle = new Weapon("rifle",Range.LONG,5,Skill.RANGED);
        Combatant c = new Combatant(soldier);
        c.weapon = rifle;
        c.damageSoak = c.damageSoak + 1;
        c.faction = Faction.ADVERSARY;
        return c;
    }

    public static Combatant createCombatDroid()
    {
        Character soldier = new Character("Combat droid", 5,3,2,new int[]{0,0,0,0,0,0,0,0,2,2,0,0,0});
        Weapon pistol = new Weapon("heavy pistol",Range.MEDIUM,4,Skill.RANGED);
        Combatant c = new Combatant(soldier);
        c.damageSoak = c.damageSoak + 4;
        c.numbersOfAttack = 2;
        c.weapon = pistol;
        c.faction = Faction.ADVERSARY;
        return c;
    }

    public static Combatant createHighRankSoldier()
    {
        Character soldier = new Character("High-rank soldier", 6,4,2,new int[]{0,0,0,0,0,0,0,0,2,2,0,0,0});
        Weapon pistol = new Weapon("heavy rifle",Range.LONG,6,Skill.RANGED);
        Armor armor = new Armor("Armored clothing",2);
        Combatant c = new Combatant(soldier);
        c.weapon = pistol;
        c.faction = Faction.ADVERSARY;
        return c;
    }

    public static Combatant createMilitaryLeader()
    {
        Character soldier = new Character("Military Leader", 6,4,2,new int[]{1,0,0,0,0,0,0,0,3,3,0,0,0});
        Weapon pistol = new Weapon("heavy pistol",Range.LONG,6,Skill.RANGED);
        Armor armor = new Armor("Armored clothing",2);
        Combatant c = new Combatant(soldier);
        c.weapon = pistol;
        c.armor = armor;
        c.numbersOfAttack = 2;
        c.faction = Faction.ADVERSARY;
        return c;
    }

    //Stationary

    public static Combatant createLightTurret()
    {
        Character soldier = new Character("Light Turret", 4,4,2,new int[]{0,0,0,0,0,0,0,0,0,2,0,0,0});
        Weapon pistol = new Weapon("mounted rifle",Range.MEDIUM,4,Skill.RANGED);
        Combatant c = new Combatant(soldier);
        c.damageSoak = c.damageSoak + 2;
        c.numbersOfAttack = 2;
        c.weapon = pistol;
        c.faction = Faction.ADVERSARY;
        c.mobile = false;
        return c;
    }
    public static Combatant createHeavyTurret()
    {
        Character soldier = new Character("Heavy Turret", 4,4,2,new int[]{0,0,0,0,0,0,0,0,0,2,0,0,0});
        Weapon pistol = new Weapon("mounted heavy rifle",Range.MEDIUM,5,Skill.RANGED);
        Combatant c = new Combatant(soldier);
        c.damageSoak = c.damageSoak + 3;
        c.numbersOfAttack = 2;
        c.weapon = pistol;
        c.faction = Faction.ADVERSARY;
        c.mobile = false;
        return c;
    }

    //Tech aliens
    public static Combatant createVaruthaLeaf()
    {
        return null;
    }

    public static Combatant createVaruthaSoldier()
    {
        return null;
    }

    public static Combatant createVaruthaSpy()
    {
        return null;
    }

    public static Combatant createVaruthaMotherNode()
    {
       return null;
    }

    //Slimey Aliens


}

