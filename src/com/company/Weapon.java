package com.company;

import com.company.types.Range;
import com.company.types.Skill;

import java.util.Map;

public class Weapon {

    public String name;
    public Range range;
    public Integer damage;
    public Skill skill;

    public static Map<Range,Integer> rangeValues = Map.ofEntries(
            Map.entry(Range.SELF,0),
            Map.entry(Range.MELEE,1),
            Map.entry(Range.CLOSE,3),
            Map.entry(Range.MEDIUM,5),
            Map.entry(Range.LONG,8)
    );


    public Weapon(String name, Range range, Integer damage, Skill skill)
    {
        this.name = name;
        this.range = range;
        this.damage = damage;
        this.skill = skill;

    }
}
