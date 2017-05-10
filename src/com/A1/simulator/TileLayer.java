package com.A1.simulator;

import java.util.ArrayList;

class TileLayer
{
	private final ArrayList<Long> data;
	private final int height;
	private final String name;
	private final int opacity;
	private final String type;
	private final boolean visible;
	private final int width;
	private final int x;
	private final int y;

	public TileLayer(ArrayList<Long> data,long height,long width,long x,long y,long opacity,String name,String type,boolean visible)	
	{
		this.data = data;
		this.height = (int) height;
		this.width = (int)width;
		this.x = (int)x;
		this.y = (int)y;
		this.opacity = (int)opacity;
		this.name = name;
		this.type = type;
		this.visible = visible;
	}
	
	public ArrayList<Long> getData()
	{
		return data;
	}
	
	public String toString()
	{
		return "Name: " + name + "\n Data: " + data + "/n height: " + height + "\n width: " + width + "\n x: " + x + "\n y: " + y + 
			   "\n opacity: " + opacity + "\n type: " + type + "\n visible: " + visible;
	}

	public String getName()
	{
		return name;
	}
}
