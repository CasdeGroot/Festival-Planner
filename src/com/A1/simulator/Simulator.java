package com.A1.simulator;

public class Simulator
{
    public static boolean debug;
    public static boolean easterEgg;

    private static boolean isInstance = false;

    public Simulator()
    {
        new TileLoader(isInstance);

        VisitorManager.initialize();
    }

    public static void main(String[] args)
    {
        isInstance = true;
        new Simulator();
    }
}
