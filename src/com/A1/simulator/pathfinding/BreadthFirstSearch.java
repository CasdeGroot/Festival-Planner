package com.A1.simulator.pathfinding;

import java.util.HashMap;
import java.util.LinkedList;

import com.A1.simulator.Tile;

class BreadthFirstSearch
{

    public static HashMap<Tile, Integer> search(Tile start)
    {
        LinkedList<Tile> frontier = new LinkedList<>();
        frontier.push(start);


        HashMap<Tile, Integer> distance = new HashMap<>();
        distance.put(start, 0);

        while (!frontier.isEmpty())
        {
            Tile current = frontier.poll();
            current.getNeighbours().stream().filter(t -> !distance.containsKey(t)).forEach(t -> {
                frontier.add(t);
                distance.put(t, 1 + distance.get(current));

            });
        }

        return distance;
    }
}
