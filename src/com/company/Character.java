package com.company;

import com.company.skills.SkillTemplate;
import com.company.types.Rank;
import com.company.types.Skill;

import java.util.*;

public class Character {


    public String name;
    public Integer soldier;
    public Integer agent;
    public Integer tech;
    public Integer combatRating;
    public Integer hitpoints;
    public Integer initiativeModifier;
    public Integer edge;
    public Integer defense;
    public LinkedHashMap<Skill,SkillTemplate> skills = new LinkedHashMap<>();

    Character(String name, Integer soldier, Integer agent, Integer tech){
        this.name = name;
        this.soldier = soldier;
        this.agent = agent;
        this.tech = tech;
        this.hitpoints = soldier + 6;
        this.combatRating = (int) Math.floor((float) (soldier + agent)/2);
        this.initiativeModifier = (int) Math.floor((float) (agent + tech)/2);
        this.defense = this.combatRating + 4;
        this.edge = 3;
    }

    Character(String name, Integer soldier, Integer agent, Integer tech, int[] skills){
        this.name = name;
        this.soldier = soldier;
        this.agent = agent;
        this.tech = tech;
        this.hitpoints = soldier + 6;
        this.combatRating = (int) Math.floor((float) (soldier + agent)/2);
        this.initiativeModifier = (int) Math.floor((float) (agent + tech)/2);
        this.defense = this.combatRating + 4;
        this.edge = 3;
        initiateSkills(skills);
    }

    public void initiateSkills(int[] values) {

        try {
            for (int i = 0; i < values.length; i++)
            {
                Skill skill = Skill.values()[i];
                Rank rank = Rank.values()[values[i]];
                skills.put(skill,new SkillTemplate(this, skill,rank));
            }
        } catch(Exception e)
        {
            System.out.println("Skills not declared properly.");
            e.printStackTrace();
        }

    }


}
