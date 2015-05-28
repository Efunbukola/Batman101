package com.example.saboorsalaam.resizablegrid;

/**
 * Created by Saboor Salaam on 1/21/2015.
 */

public class Point {
    public int X;
    public int Y;
    public int belong;
    public Boolean ckeck;

    public Boolean isEqual(Point p){
        return (this.X == p.X && this.Y == p.Y);
    }
}
