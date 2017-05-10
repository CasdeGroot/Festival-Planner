package com.A1.simulator;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

class JSONReader
{
	public JSONReader()
	{
		
	}

	public JSONObject getJSON() {
		JSONParser parser = new JSONParser();
		try
		{
			return (JSONObject) parser.parse(new FileReader("res" + File.separator + "TileMap.json"));
		} catch (Exception e)
		{
			System.out.println(e);
		}
		return null;
	}
}
