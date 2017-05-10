package com.A1.festivalplanner;

import java.util.ArrayList;

public class Stage {
	private static int ID = 0;
	private final String name;
	private final String location; //Type should be changed to something useful for the simulator
	private final ArrayList<Show> shows = new ArrayList<>();
	private final int stageID;

	public Stage(String name, String location)
	{
		super();
		this.name = name;
		this.location = location;
		stageID = ID;
		ID++;
	}
	
	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public ArrayList<Show> getShows() {
		return shows;
	}
	
	public void addShow(Show show)
	{
		shows.add(show);
	}
	
	public void removeShow(int index)
	{
		if(index == 0)
			shows.remove(0);
		else
			shows.remove(index-1);
	}
	
	@Override
	public String toString()
	{
		return  name +  
				"\n || Location: " + location + "||"; 
	}

	public int getStageID() {
		return stageID;
	}
}
