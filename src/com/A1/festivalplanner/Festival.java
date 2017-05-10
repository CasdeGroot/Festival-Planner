package com.A1.festivalplanner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Festival {

	private static Festival currentFestival;
	private GregorianCalendar beginTime;
	private GregorianCalendar endTime;
	private double price;
	private int maxVisitors;
	private int expectedVisitors;
	private String name;
	private String description;
	private ArrayList<Stage> stages = new ArrayList<>();
	public Festival(GregorianCalendar beginTime, GregorianCalendar endTime, double price, int maxVisitors,
			int expectedVisitors, String name, String description /*,ArrayList<Stage> stages*/) {
		super();
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.price = price;
		this.maxVisitors = maxVisitors;
		this.expectedVisitors = expectedVisitors;
		this.name = name;
		this.description = description;
	}

	/**
	 * Gets the current opened festival
	 *
	 * @return the current opened festival
	 */
	public static Festival getCurrentFestival()
	{
		return currentFestival;
	}

	/**
	 * Sets the current opened festival
	 *
	 * @param currentFestival festival to set as current
	 */
	public static void setCurrentFestival(Festival currentFestival)
	{
		Festival.currentFestival = currentFestival;
	}

	public GregorianCalendar getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(GregorianCalendar date) {
		this.beginTime = date;
	}

	public GregorianCalendar getEndTime()
	{
		return endTime;
	}

	public void setEndTime(GregorianCalendar endTime)
	{
		this.endTime = endTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void addStage(Stage stage)
	{
		stages.add(stage);
	}

	public int getMaxVisitors() {
		return maxVisitors;
	}

	public void setMaxVisitors(int maxVisitors) {
		this.maxVisitors = maxVisitors;
	}

	public int getExpectedVisitors() {
		return expectedVisitors;
	}

	public void setExpectedVisitors(int expectedVisitors) {
		this.expectedVisitors = expectedVisitors;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Stage> getStages() {
		return stages;
	}

	public void removeShow(int index)
	{
		stages.remove(index-1);
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description= description;
	}

	@Override
	public String toString()
	{
		return "festival name: " + name +
                "\n Description: " + description +
                "\n Date " + beginTime.get(Calendar.DATE) + "-" + beginTime.get(Calendar.MONTH) + "-" + beginTime.get(Calendar.YEAR) +
                "\n begin time: " + beginTime.get(Calendar.HOUR)+ ":" + beginTime.get(Calendar.MINUTE) +
			   "\n end time: " + endTime.get(Calendar.HOUR)+ ":" + endTime.get(Calendar.MINUTE) +
			   "\n Price: " + price +
			   "\n Max visitors: " + maxVisitors +
			   "\n Expected visitors: " + expectedVisitors +
                "\n total Stages: " + stages.size();
    }

}
