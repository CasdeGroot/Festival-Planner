package com.A1.simulator.pathfinding;

import com.A1.simulator.Tile;
import com.A1.simulator.TileList;

public class SimpleGraph
{
    private final TileList tiles;

    public SimpleGraph(TileList list)
    {
        tiles = list;
        buildEdgeList();
    }

    public TileList getTiles()
    {
        return tiles;
    }

    private void buildEdgeList() //3edgy5me
    {
        for (Tile t : tiles)
        {
            int x = t.getX();
            int y = t.getY();

            Tile left = tiles.getTileByXYIndex(x - 1, y);
            Tile right = tiles.getTileByXYIndex(x + 1, y);

            Tile up = tiles.getTileByXYIndex(x, y - 1);
            Tile down = tiles.getTileByXYIndex(x, y + 1);

            Tile leftUp = tiles.getTileByXYIndex(x - 1, y - 1);
            Tile leftDown = tiles.getTileByXYIndex(x - 1, y + 1);

            Tile rightUp = tiles.getTileByXYIndex(x + 1, y - 1);
            Tile rightDown = tiles.getTileByXYIndex(x + 1, y + 1);

            if (left != null)
            {
                if (left.getWalkable())
                    t.getNeighbours().add(left);
            }

            if (right != null)
            {
                if (right.getWalkable())
                    t.getNeighbours().add(right);
            }

            if (up != null)
            {
                if (up.getWalkable())
                    t.getNeighbours().add(up);
            }

            if (down != null)
            {
                if (down.getWalkable())
                    t.getNeighbours().add(down);
            }

            if (leftUp != null)
            {
                if (leftUp.getWalkable())
                    t.getNeighbours().add(leftUp);
            }

            if (leftDown != null)
            {
                if (leftDown.getWalkable())
                    t.getNeighbours().add(leftDown);
            }

            if (rightUp != null)
            {
                if (rightUp.getWalkable())
                    t.getNeighbours().add(rightUp);
            }

            if (rightDown != null)
            {
                if (rightDown.getWalkable())
                    t.getNeighbours().add(rightDown);
            }
        }
    }
}
