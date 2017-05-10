package com.A1.simulator.pathfinding;

import java.util.HashMap;

import com.A1.simulator.Tile;

public class PathFinding
{
    private static final HashMap<Tile, HashMap<Tile, Integer>> paths = new HashMap<>(); //Tile = tile in front of stage, xxx = Map thingy

    public static HashMap<Tile, HashMap<Tile, Integer>> getPaths()
    {
        return paths;
    }
    
    public static void initialize(SimpleGraph graph)
    {
        Tile left = graph.getTiles().getTileByXYIndex(13, 19);
        Tile right = graph.getTiles().getTileByXYIndex(28, 19);

        Tile bottom = graph.getTiles().getTileByXYIndex(21, 31);
        Tile top = graph.getTiles().getTileByXYIndex(20, 7);

        Tile exit = graph.getTiles().getTileByXYIndex(4, 0);
        
        Tile blatter = graph.getTiles().getTileByXYIndex(10,34);
        Tile hunger = graph.getTiles().getTileByXYIndex(32,34);

        paths.put(left, BreadthFirstSearch.search(left));
        paths.put(right, BreadthFirstSearch.search(right));

        paths.put(bottom, BreadthFirstSearch.search(bottom));
        paths.put(top, BreadthFirstSearch.search(top));

        paths.put(exit, BreadthFirstSearch.search(exit));
        
        paths.put(hunger, BreadthFirstSearch.search(hunger));
        paths.put(blatter, BreadthFirstSearch.search(blatter));
    }
}
