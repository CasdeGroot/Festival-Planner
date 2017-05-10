package com.A1.festivalplanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FileManager {
	private static String currentFile = "";

	public static void openFile()
	{
		JFileChooser fileChooser = new JFileChooser();
		
		//set the filter to only JSON files
		fileChooser.setFileFilter(new FileNameExtensionFilter("JSON save files", "json"));
		int returnVal = fileChooser.showOpenDialog(fileChooser);
		File file;
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			file = fileChooser.getSelectedFile();
			//open file
			System.out.println("Opening: " + file.getName() + ".");
			try
			{
				Gson g = new Gson();
				BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
				Festival.setCurrentFestival(g.fromJson(br, Festival.class));
				currentFile = file.getAbsolutePath();
				System.out.println(currentFile);
				new AgendaFrame();
			}
			catch (FileNotFoundException exception)
			{
				exception.printStackTrace();
			}
		}
		else
		{
			System.out.println("Open command cancelled by user.");
		}
	}

	public static void save()
	{
		if (currentFile.equals(""))
			saveAs();
		Gson g = new GsonBuilder().setPrettyPrinting().create();
		String json = g.toJson(Festival.getCurrentFestival());
		try(FileWriter fw = new FileWriter(new File(currentFile)))
		{
			fw.write(json);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void saveAs()
	{
		Gson g = new GsonBuilder().setPrettyPrinting().create();
		String json = g.toJson(Festival.getCurrentFestival());

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("JSON save files", "json"));
		int returnVal = fileChooser.showSaveDialog(fileChooser);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File f = fileChooser.getSelectedFile();
			try(FileWriter fw = new FileWriter(fileChooser.getSelectedFile()+".json"))
			{
				fw.write(json);
				currentFile = f.getAbsolutePath()+".json";
				System.out.println(currentFile);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
