package com.A1.simulator;

import java.util.ArrayList;

public class TileList extends ArrayList<Tile>
{
    public Tile getByPoint(int x, int y)
    {
        x = (int) Math.floor(x / 32);
        y = (int) Math.floor(y / 32);
        if (x < 0 || y < 0 || x >= 1920 || y >= 1280)
        {
            return null;
        }
        int index = y * 40 + x;
        //System.out.println(index);
        return super.get(index); //Such a hacker
    }

    public Tile getTileByXYIndex(int x, int y)
    {
        if (x < 0 || y < 0 || x >= 59 || y >= 39)
        {
            return null;
        }
        int index = y * 40 + x;
        //System.out.println(index);
        return super.get(index); //Such a hacker

    }

}
