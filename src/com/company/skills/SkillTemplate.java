package com.company.skills;

import com.company.Character;
import com.company.types.Rank;
import com.company.types.Skill;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SkillTemplate {

    public  Character character;
    public Skill name;
    public Rank rank;
    public String stat;
    public Integer modifier;

    public static LinkedHashMap<Skill,String> skillStats = new LinkedHashMap<>() {{
            put(Skill.ACROBATICS,"agent");
            put(Skill.ATHLETICS,"soldier");
            put(Skill.AWARENESS,"tech");
            put(Skill.GUNNERY,"combat_rating");
            put(Skill.HACKING,"tech");
            put(Skill.INTERACTION,"agent");
            put(Skill.KNOWLEDGE,"tech");
            put(Skill.LARCENY,"agent");
            put(Skill.MELEE, "combat_rating");
            put(Skill.RANGED,"combat_rating");
            put(Skill.STEALTH,"agent");
            put(Skill.UNARMED,"combat_rating");
            put(Skill.VEHICLE,"agent");
    }};


    public SkillTemplate(Character character, Skill name, Rank rank)
    {
        this.character = character;
        this.name = name;
        this.rank = rank;


        switch (skillStats.get(name)) {
            case "soldier" -> modifier = character.soldier + translateRank();
            case "agent" -> modifier = character.agent + translateRank();
            case "tech" -> modifier = character.tech + translateRank();
            case "combat_rating" -> modifier = character.combatRating + translateRank();
        }



    }

    public Integer translateRank()
    {
        if (this.rank.equals(Rank.BASIC))
        {
            return 2;
        }
        if (this.rank.equals(Rank.ADVANCED))
        {
            return 4;
        }
        if (this.rank.equals(Rank.MASTER))
        {
            return 6;
        }

        return 0;
    }

    public  Integer getModifier()
    {
        return switch (skillStats.get(name)) {
            case "soldier" -> character.soldier + translateRank();
            case "agent" -> character.agent + translateRank();
            case "tech" -> character.tech + translateRank();
            case "combat_rating" -> modifier = character.combatRating + translateRank();
            default -> 0;
        };
    }

}
