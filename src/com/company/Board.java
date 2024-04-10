package com.company;

import com.company.types.AttackType;
import com.company.types.Directions;
import com.company.types.Faction;

import java.util.*;
import java.util.stream.Collectors;

public class Board {

    public List<Combatant> fighters = new ArrayList<>();
    public List<Combatant> crew = new ArrayList<>();
    public List<Combatant> temporaryCrew = new ArrayList<>();
    public List<Combatant> adversaries = new ArrayList<>();
    public List<Combatant> temporaryAdversaries = new ArrayList<>();
    public boolean playersWon;

    public BoardPosition[][] boardGrid = initializeBoard();




    public Board()
    {}


    public Board(Board b) {
        //this.crew.addAll(b.crew);
        //this.adversaries.addAll(b.adversaries);

        this.playersWon = b.playersWon;

        for (Combatant c : b.fighters)
        {
            Combatant nc = new Combatant(c);
            this.fighters.add(nc);
        }

        this.boardGrid = new BoardPosition[b.boardGrid.length][b.boardGrid[0].length];
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                BoardPosition bp = new BoardPosition(i,j);


                for (Combatant c : fighters)
                {
                    if (c.x == i && c.y == j)
                    {
                        bp.occupied = true;
                        bp.occupant = c;
                    }
                }
                this.boardGrid[i][j] = bp;
            }
        }
    }

    public BoardPosition[][] initializeBoard()
    {
        BoardPosition[][] boardPositions = new BoardPosition[10][10];
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                boardPositions[i][j] = new BoardPosition(i,j);
            }
        }
        return boardPositions;
    }

    public String printBoard()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                sb.append(boardGrid[i][j]);
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static int roleDice()
    {
        int dice = (int)(Math.random() * ((6-1)+1)) + 1;

        if (dice == 6)
        {
            dice = 5 + roleDice();
        }

        return dice;
    }

    public static int roleExplodingDice()
    {
        int dice = (int)(Math.random() * ((6-1)+1)) + 1;

        if (dice == 6)
        {
            dice = 5 + roleExplodingDice();
        }

        return dice;
    }


    public void simulateBattle()
    {
        for (Combatant c : fighters)
        {
            if (c.faction.equals(Faction.CREW))
            {
                crew.add(c);
            }
            if (c.faction.equals(Faction.ADVERSARY))
            {
                adversaries.add(c);
            }

            if (c.pet != null)
            {
                if (c.pet.faction.equals(Faction.CREW))
                {
                    crew.add(c);
                }
                if (c.pet.faction.equals(Faction.ADVERSARY))
                {
                    adversaries.add(c);
                }
            }
        }

        System.out.println("Starting battle...");
        System.out.println(printBoard());

        List<Combatant> turnOrder = new ArrayList<>();

        for (Combatant crewMate : crew)
        {
            crewMate.initiative = crewMate.character.initiativeModifier + roleDice();
            turnOrder.add(crewMate);
        }
        for (Combatant enemy : adversaries)
        {
            enemy.initiative = enemy.character.initiativeModifier + roleDice();
            turnOrder.add(enemy);
        }

        turnOrder.sort(Comparator.comparingInt((Combatant c) -> c.initiative).reversed());

        int i = 0;
        int turn = 1;

        boolean gameover = crew.isEmpty();
        // || temporaryCrew.isEmpty();

      while (!gameover)
      {
          Combatant currentTurn = turnOrder.get(i);

          if (!currentTurn.incapacitated) {
              if (currentTurn.faction.equals(Faction.CREW)) {

                  for (int attacks = 0; attacks < currentTurn.numbersOfAttack; attacks++) {
                      boolean viableMove = false;
                      List<Combatant> viableTargets = viableTargets(currentTurn);

                      if (viableTargets.isEmpty() && currentTurn.mobile) {
                          int old_x = currentTurn.x;
                          int old_y = currentTurn.y;

                          for (int j = 0; j < 2; j++) {
                              walkTowardsEnemy(currentTurn,adversaries);
                              viableTargets = viableTargets(currentTurn);
                              if (!viableTargets.isEmpty()) {
                                  viableMove = true;
                              }
                          }

                          if (currentTurn.x == old_x && currentTurn.y == old_y) {
                              currentTurn.canMove = false;
                          }
                      } else
                      {viableMove = true;
                      }

                          if (viableMove) {
                              int targetIndex = (int) (Math.random() * ((viableTargets.size() - 1) + 1)) + 1;

                              try {
                                  Combatant target = viableTargets.get(targetIndex - 1);


                              currentTurn.attack(target, AttackType.SINGLE, 0);
                              if (target.incapacitated) {
                                  boardGrid[target.x][target.y].removeOccupant();
                                  adversaries.remove(target);
                                  if (adversaries.isEmpty())
                                  {
                                      break;
                                  }
                              }
                              }catch(Exception e)
                              {
                                  System.out.println("Failed to assign target!");
                              }
                          }
                      }
              }


              if (currentTurn.faction.equals(Faction.ADVERSARY)) {

                  for (int attacks = 0; attacks < currentTurn.numbersOfAttack; attacks++) {
                      boolean viableMove = false;
                  List<Combatant> viableTargets = viableTargets(currentTurn);
                      if (viableTargets.isEmpty() && currentTurn.mobile) {
                          int old_x = currentTurn.x;
                          int old_y = currentTurn.y;

                          for (int j = 0; j < 2; j++) {
                              walkTowardsEnemy(currentTurn,crew);
                              viableTargets = viableTargets(currentTurn);
                              if (!viableTargets.isEmpty()) {
                                  viableMove = true;
                              }
                          }

                          if (currentTurn.x == old_x && currentTurn.y == old_y) {
                              currentTurn.canMove = false;
                          }
                      } else
                      {viableMove = true;
                      }
                      if (viableMove) {
                      int targetIndex = (int) (Math.random() * ((viableTargets.size() - 1) + 1)) + 1;
                      Combatant target = viableTargets.get(targetIndex - 1);
                      currentTurn.attack(target, AttackType.SINGLE, 0);
                      if (target.incapacitated) {
                          boardGrid[target.x][target.y].removeOccupant();
                          crew.remove(target);
                          if (crew.isEmpty())
                          {
                              break;
                          }
                      }
                      viableMove = true;
                  }
              }
              }
          }

          i++;
          if (i == turnOrder.size()) {
              System.out.println("Round " + turn + " is over.");
              turn++;
              i = 0;
          }

              /*
              if (!viableMove)
              {
                  break;
              } else {
                  viableMove = false;
              }
              */

              boolean noViableMoves = true;

              for (Combatant c : turnOrder)
              {
                  if (c.canMove)
                  {
                      noViableMoves = false;
                      break;
                  }
              }



              if (noViableMoves || adversaries.isEmpty() || crew.isEmpty())
              {
                  if(temporaryCrew.isEmpty() && temporaryAdversaries.isEmpty()) {
                      gameover = true;
                      break;
                  }
              }



          }


      System.out.println(printBoard());

      System.out.println("The battle is over");
      if (crew.isEmpty())
      {
          playersWon = false;
          System.out.println("The crew lost.");
      } else if (adversaries.isEmpty())
      {
          playersWon =  true;
          System.out.println("The enemies lost.");
      } else
      {
          System.out.println("The battle ended in a stall mate");
      }
    }


    public void walkTowardsEnemy(Combatant c, List<Combatant> opponents)
    {

        HashMap<BoardPath,Combatant>  distance = new HashMap<>();
        for (Combatant t : opponents)
        {
            int x = c.x - t.x;
            int y = c.y - t.y;

            Directions dir = null;

            if (x > 0 && y == 0)
            {
                dir = Directions.NORTH;
            }
            if (x < 0 && y == 0)
            {
                dir = Directions.SOUTH;
            }
            if (y > 0 && x == 0)
            {
                dir = Directions.WEST;
            }
            if (y < 0 && x == 0)
            {
                dir = Directions.EAST;
            }
            if (x > 0 && y > 0)
            {
                dir = Directions.NORTH_WEST;
            }
            if (x < 0 && y < 0)
            {
                dir = Directions.SOUTH_EAST;
            }
            if (x < 0 && y > 0)
            {
                dir = Directions.SOUTH_WEST;
            }
            if (x > 0 && y < 0)
            {
                dir = Directions.NORTH_EAST;
            }

            x = Math.abs(x);
            y = Math.abs(y);

            double dis = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));

            BoardPath p = new BoardPath(dis,dir);


            distance.put(p,t);
        }

        //        turnOrder.sort(Comparator.comparingInt((Combatant c) -> c.initiative).reversed());
        //   distances.addAll(distance.keySet().stream().map(boardPath -> boardPath.distance).collect(Collectors.toList()));

        List<BoardPath> distances = new ArrayList<>(distance.keySet());
        distances.sort(Comparator.comparingDouble((BoardPath p) -> p.distance));

        //Combatant closestTarget = distance.get(distances.get(0));

        BoardPath p = distances.get(0);



        int x = c.x;
        int y = c.y;


            if (p.dir == Directions.NORTH) {
                x = x - 1;
            }
            else if (p.dir == Directions.SOUTH) {
                x = x + 1;
            }
            else if (p.dir == Directions.EAST) {
                y = y + 1;
            }
            else if (p.dir == Directions.WEST) {
                y = y - 1;
            }
            else if (p.dir == Directions.NORTH_EAST)
            {
                x = x - 1;
                y = y + 1;
            }
            else if (p.dir == Directions.NORTH_WEST)
            {
                x = x - 1;
                y = y - 1;
            }
            else if (p.dir == Directions.SOUTH_EAST)
            {
                x = x + 1;
                y = y + 1;
            }
            else if (p.dir == Directions.SOUTH_WEST)
            {
                x = x - 1;
                y = y + 1;
            }

            if ( 0 <= x && x < 10 &&
                 0 <= y && y <10 &&
                    !boardGrid[x][y].occupied)
            {
                boardGrid[c.x][c.y].removeOccupant();
                boardGrid[x][y].setOccupant(c);

                System.out.println(c.character.name + " is moving towards " + distance.get(p).character.name);
            }

    }

    public List<Combatant> viableTargets(Combatant c)
    {

        List<Combatant> combatList = new ArrayList<>();

        int range = Weapon.rangeValues.get(c.getAttackRange());


            int x = c.x;
            int y = c.y;


            for (int j = x-range; j <= x+range; j++)
            {
                for (int k = y-range; k <= y+range; k++)
                {
                    if (j >= 0 && j < 10 && k >= 0 && k < 10)
                    {
                        if (boardGrid[j][k].occupied)
                        {
                            if (!boardGrid[j][k].occupant.faction.equals(c.faction))
                            {
                                if (!boardGrid[j][k].occupant.incapacitated) {
                                    combatList.add(boardGrid[j][k].occupant);
                                }
                            }
                        }
                    }
                }
            }



        return combatList;
    }
}
