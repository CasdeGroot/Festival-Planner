package com.A1.simulator;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

class TileMap
{
	private final JSONObject jsonFile;

	private final ArrayList<TileLayer> tileLayers = new ArrayList<>();
	private final ArrayList<TileSet> tileSets = new ArrayList<>();
	private long width;
    private long height;
    private long tileWidth;
    private long tileHeight;


	public TileMap(JSONObject map)
	{
		this.jsonFile = map;
		makeMap();
		//makeTileLayers();
		//makeTileSets();
	}
	
	public ArrayList<TileLayer> makeTileLayers()
	{
		ArrayList<JSONObject> layers = new ArrayList<>();
		JSONArray totalLayers = (JSONArray) jsonFile.get("layers");
		for (Object totalLayer : totalLayers)
		{
			JSONObject layer = (JSONObject) totalLayer;
			layers.add(layer);
		}
		for(JSONObject layer : layers)
		{
			ArrayList<Long> data = (ArrayList<Long> )(layer.get("data"));
			long height = (long) layer.get("height");
			long width = (long) layer.get("width") ;
			
			long x = (long) layer.get("x");
			long y = (long) layer.get("y");
			long opacity = (long) layer.get("opacity");
			String name = (String) layer.get("name");
			String type = (String) layer.get("type");
			boolean visible = (boolean) layer.get("visible");
			TileLayer tileLayer = new TileLayer(data,height, width, x,y,opacity, name, type,visible);
			tileLayers.add(tileLayer);
		}
		return tileLayers;
	}
	private void makeMap()	
	{
		height = (long)jsonFile.get("height");
		width = (long) jsonFile.get("width");
		tileHeight = (long) jsonFile.get("tileheight");
		tileWidth = (long) jsonFile.get("tilewidth");
	}

	public ArrayList<TileSet> makeTileSets()
	{
		ArrayList<JSONObject> sets = new ArrayList<>();
		JSONArray totalTileSets = (JSONArray) jsonFile.get("tilesets");
		for (Object totalTileSet : totalTileSets)
		{
			JSONObject tileSet = (JSONObject) totalTileSet;
			sets.add(tileSet);
		}
		for(JSONObject tileSet : sets)
		{
			ArrayList<Terrain> terrains = new ArrayList<>();
			JSONArray totalTerrains = (JSONArray)tileSet.get("terrains");
			if(totalTerrains!=null)
			{
				for (Object totalTerrain : totalTerrains)
				{
					terrains.add(new Terrain());
				}
			}
			long firstGid = (long) tileSet.get("firstgid");
			String name = (String) tileSet.get("name");
			long tileWidth = (long)tileSet.get("tilewidth");
			long tileHeight = (long)tileSet.get("tileheight");
			long imageWidth = (long)tileSet.get("imagewidth");
			long imageHeight = (long)tileSet.get("imageheight");
			long margin = (long)tileSet.get("margin");
			long spacing = (long)tileSet.get("spacing");
			long tileCount = (long)tileSet.get("tilecount");
			long columns = (long)tileSet.get("columns");
			TileSet InputTileSet = new TileSet(firstGid, name, tileWidth, tileHeight, tileCount, columns);
			tileSets.add(InputTileSet);
		}
		return tileSets;
	}
	
	public int getTileWidth()
	{
		return (int) tileWidth;
	}
	
	public int getTileHeight()
	{
		return (int) tileHeight;
	}
	
	public int getMapHeight()
	{
		return (int) height;
	}
	public int getMapWidth()
	{
		return (int) width;
	}


}



