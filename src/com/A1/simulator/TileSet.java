package com.A1.simulator;

class TileSet
{
	final int firstGid;
	private final String name;
	private final int tileWidth;
	private final int tileHeight;
	private final int tileCount;
	private final int columns;

	public TileSet(long firstGid, String name, long tileWidth, long tileHeight,
				   long tileCount, long columns)
	{
		this.firstGid = (int)firstGid;
		this.name = name;
		this.tileWidth = (int)tileWidth;
		this.tileHeight = (int)tileHeight;
		this.tileCount = (int)tileCount;
		this.columns = (int) columns;
	}
	public int getFirstGid()
	{
		return firstGid;
	}

	public String getName()
	{
		return name;
	}

	public int getTileWidth()
	{
		return tileWidth;
	}

	public int getTileHeight()
	{
		return tileHeight;
	}

	public int getTileCount()
	{
		return tileCount;
	}
	public int getColumns()
	{
		return columns;
	}


}
