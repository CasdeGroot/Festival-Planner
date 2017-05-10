package com.A1.festivalplanner;

import java.awt.FlowLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomeFrame extends JFrame
{

	private static ImageIcon imageicon;
	
    public WelcomeFrame()
    {
        super("Festival Planner");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Open existing festival or start new?");
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JButton openExisting = new JButton("Open existing");
        JButton newFestival = new JButton("New festival");
        panel.add(label);

        panel.add(newFestival);
        panel.add(openExisting);


        imageicon = new ImageIcon("res" + File.separator + "Festival_Icon.png");
        setIconImage(imageicon.getImage());


        openExisting.addActionListener(e ->
        {
            FileManager.openFile();
            setVisible(false);
        });

        newFestival.addActionListener(e -> {
            new NewFestivalFrame();
            setVisible(false);
        });

        setContentPane(panel);
        setSize(300, 150);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

    }
    
    public static ImageIcon getImageIcon()
    {
    	return imageicon;
    }
    
   
}
