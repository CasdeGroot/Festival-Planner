package com.A1.festivalplanner;

import java.util.GregorianCalendar;

public class Show {
	private String name;
	private GregorianCalendar beginTime;
	private GregorianCalendar endTime;
	private Artist artist;
	
	private double rating;

	public Show(String name, GregorianCalendar beginTime, GregorianCalendar endTime, Artist artist, double rating) {
		super();
		this.name = name;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.artist = artist;
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public GregorianCalendar getBeginTime() {
		return beginTime;
	}

	public GregorianCalendar getEndTime() {
		return endTime;
	}

	public Artist getArtist() {
		return artist;
	}

	public double getRating() {
		return rating;
	}

	@Override
	public String toString()
	{
		return "Name: " + name; /*+ "\n" +
				"start time: " + beginTime.get(Calendar.HOUR_OF_DAY) + ":" + beginTime.get(Calendar.MINUTE) + "\n" +
				"end time: " + endTime.get(Calendar.HOUR_OF_DAY) + ":" + endTime.get(Calendar.MINUTE) + "\n" +
				"Artist: \n" + artist.toString() + 
				"\n Rating: " + rating;*/ 
				
	}
}
