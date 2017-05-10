package com.A1.simulator;

import java.util.ArrayList;
import java.util.HashMap;

class SaveVisitors
{
	private final HashMap<Long, ArrayList<Visitor>> savedVisitors = new HashMap<>();

	public SaveVisitors()
	{
		
	}

	public void save(long currentTicks, ArrayList<Visitor> visitors)
	{
        //System.out.println("save key = " + currentTicks);
        savedVisitors.putIfAbsent(currentTicks, visitors);
    }

	public ArrayList<Visitor> getSave(long ticksKey)
	{
        //System.out.println("tickskey = " + ticksKey);
        return savedVisitors.get(ticksKey);
	}

}
