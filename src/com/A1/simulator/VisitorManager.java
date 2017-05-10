package com.A1.simulator;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

class VisitorManager
{
    private static ArrayList<Visitor> visitors = new ArrayList<>();
    private static Image visitorSprite;

    public static ArrayList<Visitor> getVisitors()
    {
    	return visitors;
    }

    public static void setVisitors(ArrayList<Visitor> newVisitors)
    {
    	visitors=newVisitors;
    }

    public static Image getVisitorSprite()
    {
        return visitorSprite;
    }

    public static void initialize()
    {
        setDefaultVisitorSprite();
    }

    public static void setVisitorSprite()
    {
        visitorSprite = new ImageIcon("res" + File.separator + "EE.png").getImage();
    }

    public static void setDefaultVisitorSprite()
    {
        visitorSprite = new ImageIcon("res" + File.separator + "sprite_person.png").getImage();
    }

    public static boolean isIntersecting(Visitor visitor)
    {
        //return visitors.parallelStream().anyMatch(vis -> vis.getBounds().intersects(v.getBounds()));
        return visitors.parallelStream().filter(v -> v != visitor).anyMatch(v -> v.getBounds().intersects(visitor.getBounds()));
    }


}
