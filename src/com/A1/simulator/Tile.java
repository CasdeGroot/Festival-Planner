package com.A1.simulator;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Tile
{
    private final int x;
    private final int y;
    private final boolean walkable;
    private final ArrayList<Tile> neighbours = new ArrayList<>();

    public Tile(int x, int y, boolean walkable)
    {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
    }

    public ArrayList<Tile> getNeighbours()
    {
        return neighbours;
    }

    public boolean getWalkable()
    {
        return walkable;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Point2D getPoint2D()
    {
        return new Point2D.Double(x * 32 + 16, y * 32 + 16);
    }


    @Override
    public String toString()
    {
        return "[X:" + x + ",Y:" + y + "][" + getPoint2D() + "]";
    }

    @Override
    public boolean equals(Object o)
    {
        if (o.getClass() != Tile.class)
            return false;

        Tile t = (Tile) o;
        return (x == t.x && y == t.y);

    }
}
