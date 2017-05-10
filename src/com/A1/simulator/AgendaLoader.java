package com.A1.simulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.A1.festivalplanner.Festival;
import com.google.gson.Gson;

class AgendaLoader
{

    public static void openFile(){
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
            try{
                Gson g = new Gson();
                BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
                Festival.setCurrentFestival(g.fromJson(br, Festival.class));
                String currentFile = file.getAbsolutePath();
                System.out.println(currentFile);
            }catch(FileNotFoundException exception){
                exception.printStackTrace();
            }
        } else
        {
            System.out.println("Open command cancelled by user.");
        }
    }
}
