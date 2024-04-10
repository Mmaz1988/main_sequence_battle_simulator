package com.company;

import com.company.types.Faction;

public class BoardPosition {

    public int x;
    public int y;
    public boolean occupied;
    public Combatant occupant;





    public BoardPosition(int x, int y)
    {
       this.x = x;
       this.y = y;
       this.occupied = false;
    }

    public BoardPosition(BoardPosition bp)
    {
        this.x = bp.x;
        this.y = bp.y;
        this.occupied = bp.occupied;
        if (bp.occupied)
        {
            this.occupant = bp.occupant;
        }
    }

    public void setOccupant(Combatant occupant) {
        if (!this.occupied) {
            this.occupant = occupant;
            this.occupied = true;
            this.occupant.x = this.x;
            this.occupant.y = this.y;
        } else
        {
            System.out.println("Board position already occupied by " + occupant.character.name);
        }
    }

    public  void removeOccupant()
    {
        this.occupant = null;
        this.occupied = false;
    }


    @Override
    public String toString() {
        if (this.occupied) {
            if (occupant.faction.equals(Faction.CREW)) {
                return "[c]";
            } else {
                return "[a]";
            }
        }
        return "[ ]";
    }

}


