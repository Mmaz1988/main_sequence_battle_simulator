package com.company;

import com.company.types.Directions;

public class BoardPath {

    public Double distance;
    public Directions dir;

    public BoardPath(double distance, Directions dir)
    {
        this.distance = distance;
        this.dir = dir;
    }
}
