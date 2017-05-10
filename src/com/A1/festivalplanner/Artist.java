package com.A1.festivalplanner;

public class Artist {
	private final String name;
	private final String genre;
	private final String description;
	
	public Artist(String name, String genre, String description) {
		super();
		this.name = name;
		this.genre = genre;
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public String getName() {
		return name;
	}

	public String getGenre() {
		return genre;
	}

	@Override
	public String toString()
	{
		return "Name: " + name + "\n genre: " + genre + "\n description: " + description; 
	}
}
