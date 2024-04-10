package com.company;

import com.company.types.AttackType;
import com.company.types.Faction;
import com.company.types.Range;
import com.company.types.Skill;

import java.util.List;

import static com.company.types.AttackType.MELEE;
import static com.company.types.AttackType.SINGLE;


public class Combatant {

    public Character character;
 //   public String behaviour;
 // public String cover;
    public Integer initiative;
    public Integer hp;
    public Weapon weapon;
    public Armor armor;
    public boolean incapacitated;
    public Integer damageSoak = 0;
    public Faction faction;
    public Range attackRange;
    public int x;
    public int y;
    public int numbersOfAttack = 1;
    public boolean mobile;
    public boolean canMove = true;
    public Combatant pet;

    public Range getAttackRange()
    {
        if (weapon != null)
        {
            return weapon.range;
        } else
        {
            return Range.MELEE;
        }
    }

    public Combatant(Character character)
    {
        this.character = character;
        this.hp = character.hitpoints;
        this.mobile = true;
    }

    public Combatant(Character character, Weapon weapon)
    {
        this.character = character;
        this.hp = character.hitpoints;
        this.weapon = weapon;
        this.mobile = true;
    }

    public Combatant(Character character, Weapon weapon, Armor armor)
    {
        this.character = character;
        this.hp = character.hitpoints;
        this.weapon = weapon;
        this.armor = armor;
        this.mobile = true;
    }

    public Combatant(Combatant c)
    {
        this.character = c.character;
        this.hp = c.hp;
        if (c.weapon != null) {
            this.weapon = new Weapon(c.weapon.name, c.weapon.range, c.weapon.damage, c.weapon.skill);
        }
        if (c.armor != null) {
            this.armor = new Armor(c.armor.name, c.armor.damageSoak);
        }
        this.x = c.x;
        this.y = c.y;
        this.faction = c.faction;
        this.damageSoak = c.damageSoak;
        this.attackRange = c.attackRange;
        this.numbersOfAttack = c.numbersOfAttack;
        this.mobile = c.mobile;
        if (!this.mobile)
        {
           this.canMove = false;
        }

        if (c.pet != null)
        {
            this.pet = new Combatant(c.pet);
        }
    }
    public void attack(Combatant target, AttackType attackType, Integer penalty)
    {
        Skill weaponSkill;
        String weaponName;
        if (!(weapon == null)) {
            weaponSkill = weapon.skill;
            weaponName = weapon.name;

            //           attackRange = weapon.range;
         } else
        {
            weaponSkill = Skill.UNARMED;
            weaponName = "fists";
//            attackRange = Range.MELEE;
        }

        if (attackType.equals(SINGLE)) {
            System.out.println(this.character.name + " tries to attack " + target.character.name + " with their " + weaponName + "...");
            int attackerRating = this.character.combatRating + character.skills.get(weaponSkill).translateRank() +
                    Board.roleExplodingDice() + penalty;

            int damage = attackerRating - target.character.defense;

            if ( damage > 0) {
                int damagesoak = target.damageSoak;

                if (!(target.armor == null)) {
                    damagesoak = damagesoak + target.armor.damageSoak;
                }

                if (damagesoak > 0) {
                    System.out.println("The armor soaked " + damagesoak + " damage.");
                }

                if (damage > damagesoak) {
                    System.out.println(target.character.name + " has been hit for " + (damage - damagesoak) + " damage.");
                    target.reduceHP(damage - damagesoak);
                } else
                {
                    System.out.println("The armor protected " + target.character.name + " from the attack.");
                }

            } else
            {
                System.out.println("... and misses.");
            }
        }

        if (attackType.equals(MELEE))
        {
            //...
        }

        System.out.print(System.lineSeparator());

    }

    public void randomAttack(List<Character> targets)
    {

    }

    public void reduceHP(Integer damage)
    {
        int newhp = hp - damage;

        if (newhp <= 0)
        {
            this.incapacitated = true;
            hp = 0;
            System.out.println(this.character.name + " has been incapacitated.");
        } else
        {
            this.hp = newhp;
        }


    }

    @Override
    public String toString() {
        return character.name;
    }

    public String toFullString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(character.name).append(" ");
        sb.append(System.lineSeparator());
        sb.append("Stats: s:").append(character.soldier).append(" / a:").append(character.agent).append(" / t:").append(character.tech).append(" ");
        sb.append(System.lineSeparator());
        if (!(weapon == null))
        {
            sb.append("Weapon: ").append(weapon.name).append(" ").append(" damage: ")
                    .append(weapon.damage).append(" ").append(" range: ").append(weapon.range.toString());
        }

        return sb.toString();
    }


}
