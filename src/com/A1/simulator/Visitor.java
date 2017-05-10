package com.A1.simulator;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

import com.A1.simulator.pathfinding.PathFinding;

class Visitor
{
	private final TileList tiles;
	private static int ID;
	private int visitorNR;
	private final MapPanel panel;
	private Point2D location;
	private double direction;
	private Tile nextTile;
	private HashMap<Tile, Integer> path;
	private Tile destinationTile;
	private boolean destinationReached;
	private Tile currentTile;
	private boolean delete=false;
	private boolean timeout=false;
	private double hunger;
	private double blatter;
	private boolean eating;
	private boolean peeing;
	/**
	 * Update method, gets called 60 times per second
	 */
	private boolean collision = false;

	/**
	 * Constructor
	 *
	 * @param panel MapPanel
	 */
	public Visitor(MapPanel panel)
	{
		this.location = new Point2D.Double((Math.random() * 5 + 1) * panel.getTileWidth(), panel.getTileHeight() / 2);
		this.panel = panel;
		tiles = panel.getGraph().getTiles();
		currentTile = tiles.getByPoint((int) location.getX(), (int) location.getY());
		setTarget(0);
		calcNextTile();
		this.hunger = 100 + Math.random() * 500;
		this.blatter = 300 + Math.random() * 1000;
		ID++;
		visitorNR = ID;
	}
	
	public int getNR()
	{
		return visitorNR;
	}
	
	public double getHunger()
	{
		return hunger;
	}
	
	public double getBlatter()
	{
		return blatter;
	}
	
	public boolean getDelete()
	{
		return delete;
	}

	public void update()
	{
		hunger -= Math.random() * 0.5;
		blatter -= Math.random() * 0.5;
		if(!delete)
		{
		if(hunger <= 0)
		{
			setTarget(5);
			eating = true;
			hunger = (int)(100 + Math.random() * 500);
			
		}
		if(destinationReached)
		{
			direction += Math.PI/5;
			location = new Point2D.Double(location.getX() + Math.cos(direction) * 10,
					location.getY() + Math.sin(direction) * 10);
			if(eating)
			{
				eating = false;
			}
			if(peeing)
			{
				peeing = false;
			}
			destinationReached = false;
			
		}
		
		if(blatter <= 0)
		{
			setTarget(6);
			peeing = true;
			blatter = (int) (300 + Math.random() * 1000);
			
		}
		}
		if(!timeout)
		{
			if (nextTile == null)
				return;
	
			currentTile = tiles.getByPoint((int) location.getX(), (int) location.getY());
	
			if (VisitorManager.isIntersecting(this))
			{
				Tile newTile = null;
				while (newTile == null)
				{
					newTile = getEvasionTile(currentTile);
					if(newTile==null)
					{
						timeout=true;
						break;
					}
					nextTile = newTile;
				}
				
			}
	
	
			if (destinationTile.equals(currentTile))
			{
				destinationReached = true;
				if(destinationTile.equals(panel.getGraph().getTiles().getTileByXYIndex(4, 0)))
				{
					delete=true;
				}
			}
	
			if (nextTile.equals(currentTile) && !destinationReached)
			{
				calcNextTile();
			}
	
			if (!destinationReached)
			{
				// region Move Logic
				Point2D target = nextTile.getPoint2D();
				double dx = target.getX() - location.getX();
				double dy = target.getY() - location.getY();
	
				direction = Math.atan2(dy, dx);
	
				double speed = 10;
	
				location = new Point2D.Double(location.getX() + Math.cos(direction) * speed,
						location.getY() + Math.sin(direction) * speed);
			}
		}
		else
			timeout=false;
				
	}
	// endregion

	/**
	 * Draws the visitor
	 *
	 * @param g2 Graphics device
	 */
	public void draw(Graphics2D g2)
	{
		AffineTransform transform = new AffineTransform();
		transform.translate(location.getX() - VisitorManager.getVisitorSprite().getWidth(null) / 2,
				location.getY() - VisitorManager.getVisitorSprite().getHeight(null) / 2);

		transform.scale(0.3, 0.3);

		transform.rotate(direction - 0.5 * Math.PI, VisitorManager.getVisitorSprite().getWidth(null) / 2,
				VisitorManager.getVisitorSprite().getHeight(null) / 2);
		g2.drawImage(VisitorManager.getVisitorSprite(), transform, null);
		//g2.draw(getBounds());
	}

	public Tile getEvasionTile(Tile currentTile)
	{
		Tile newTile = null;
		int r = (int) Math.round(Math.random());
		Point2D diff = new Point2D.Double(nextTile.getX() - currentTile.getX(), nextTile.getY() - currentTile.getY());
		if (diff.getX() == 1)
		{
			if (r == 0)
				newTile = tiles.getTileByXYIndex(currentTile.getX(), currentTile.getY() + 1);
			if (r == 1)
				newTile = tiles.getTileByXYIndex(currentTile.getX(), currentTile.getY() - 1);
		} else if (diff.getY() == 1)
		{
			if (r == 0)
				newTile = tiles.getTileByXYIndex(currentTile.getX() + 1, currentTile.getY());
			if (r == 1)
				newTile = tiles.getTileByXYIndex(currentTile.getX() - 1, currentTile.getY());
		} else
		{
			newTile = nextTile;
		}
		if (newTile == null)
			newTile = nextTile;

		if (newTile.getWalkable())
			return newTile;
		return null;
	}

	/**
	 * Calculates the next tile to walk to
	 */
	private void calcNextTile()
	{
		ArrayList<Tile> neighbours = currentTile.getNeighbours();
		int currentInt = path.get(currentTile);
		for (Tile t : neighbours)
		{
			if (path.get(t) < currentInt)
			{
				nextTile = t;
				break;
			}

		}
	}

	public void setTarget(int stage)
	{
		if(stage == 4)
		{
			peeing = false;
			eating = false;
		}
		
		if(peeing || eating)
		{
			
		}
		else
		{
		HashMap<Tile, HashMap<Tile, Integer>> paths = PathFinding.getPaths();
		ArrayList<Tile> keys = new ArrayList<>(paths.keySet());
		switch(stage)
		{
			case(0):
				destinationTile = panel.getGraph().getTiles().getTileByXYIndex(20, 7);
				break;
			case(1):
				destinationTile = panel.getGraph().getTiles().getTileByXYIndex(28, 19);
				break;
			case(3):
				destinationTile = panel.getGraph().getTiles().getTileByXYIndex(21, 31);
				break;
			case(2):
				destinationTile = panel.getGraph().getTiles().getTileByXYIndex(13, 19);
				break;
			case(4):
				destinationTile = panel.getGraph().getTiles().getTileByXYIndex(4, 0);
				break;
			case(5):
				destinationTile = panel.getGraph().getTiles().getTileByXYIndex(10, 34);
				break;
			case(6):
				destinationTile = panel.getGraph().getTiles().getTileByXYIndex(32, 34);
				break;
		}
		destinationReached = false;
		path = paths.get(destinationTile);
		delete=false;
		}
	}

	public void drawBounds(Graphics2D g)
	{
		g.draw(getBounds());
	}
	public Rectangle2D getBounds()
	{
		double size = (32 / 0.5) * 0.3;
		return new Rectangle2D.Double(location.getX() - 32, location.getY() - 32, size, size);
	}
}