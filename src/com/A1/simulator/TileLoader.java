package com.A1.simulator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

class TileLoader extends JFrame
{
    private static TimelinePane timeline;
    private static MapPanel panel;
    private static ImageIcon imageiconSimulation;
    private static JPanel gridLayout;

    public TileLoader(boolean exitOnClose)
    {
        super("TileMap");
        if (exitOnClose)
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        else
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initialize();

        gridLayout = new JPanel(new BorderLayout());
        gridLayout.add(panel, BorderLayout.CENTER);
        gridLayout.add(timeline, BorderLayout.NORTH);

        imageiconSimulation = new ImageIcon("res" + File.separator + "Simulation_Icon.png");

        setIconImage(imageiconSimulation.getImage());
        setContentPane(gridLayout);
        setVisible(true);
        setSize(new Dimension(1200, 1200));



    }

    //initialize the tileLoader
    private static void initialize()
    {
        JSONReader jsonReader = new JSONReader();
        //read the json and create a tileMap 
        TileMap tileMap = new TileMap(jsonReader.getJSON());
        //make a MapPanel with the created tileMap
        timeline = new TimelinePane();
        panel = new MapPanel(tileMap, timeline);
        //panel.setTicks();
    }
}
