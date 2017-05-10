package com.A1.simulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import com.A1.festivalplanner.Festival;
import com.A1.festivalplanner.Show;
import com.A1.festivalplanner.Stage;
import com.A1.simulator.pathfinding.PathFinding;
import com.A1.simulator.pathfinding.SimpleGraph;

class MapPanel extends JPanel implements ActionListener
{
    public static final int SECONDS_PER_TICK = 4;
    public static final int SAVES_PER_HOUR = 2;
    private static int height;
    private static int width;
    private final TimelinePane timeline;
    private final ArrayList<TileLayer> tileLayers;
    private final ArrayList<TileSet> tileSets;
    private final int tileWidth;
    private final int tileHeight;
    private final TileList tiles = new TileList();
    private final SaveVisitors saveVisitors = new SaveVisitors();
    private final ArrayList<BufferedImage> subImages = new ArrayList<>();
    private final ArrayList<BufferedImage> objectImages = new ArrayList<>();
    private final AffineTransform MaptransForm;
    private final AffineTransform paneltransForm;
    private final Timer timer;
    private SimpleGraph graph;
    private Date lastUpdate = new Date();
    private Calendar festivalTime;
    private int totalPopularity;
    private double scale = 1;
	private int x = 0;
	private int y = 0;
	private int x2 = 0;
	private int y2 = 0;
    private long currentFestivalTime;
    private long currentTick = 0;
    private long simulationTicks;
    private int hour;
    private boolean firstRun = true;
    private HashMap<Show,Stage> currentShowsPlaying;
    private boolean drawStringStage1;
    private boolean drawStringStage2;
    private boolean drawStringStage3;
    private boolean drawStringStage4;
    private String  showStage1;
    private String showStage2;
    private String showStage3;
    private String showStage4;
    private JTextField hunger;
    private JTextField blatter;
    private JTextField visitorNR;
    private boolean leftButton;
    private Visitor selectedVisitor;

    public MapPanel(TileMap map, TimelinePane timeline){
		//initialize the mapPanel
        this.timeline = timeline;
        tileWidth = map.getTileWidth();
		tileHeight = map.getTileHeight();
		tileLayers = map.makeTileLayers();
		tileSets = map.makeTileSets();
		width = map.getMapWidth();
		height = map.getMapHeight();
		MaptransForm = new AffineTransform();
		paneltransForm = new AffineTransform();
        //make the tiles to draw (create the subimages)
        makeTiles();
        drawTextField();
        //set the size of the mapPanel;
		setSize(new Dimension(640, 480));

		//Some things required for our keylistener
		setFocusable(true);
		requestFocus();
        setTicks();

        timeline.setSliderMaximum((int) simulationTicks / (3600 / (SECONDS_PER_TICK * 2)));
        
		//add a mouseWheellistener to zoom
        addMouseWheelListener(new MouseAdapter()
        {
            public void mouseWheelMoved(MouseWheelEvent me)
            {
            	Point2D p1 = me.getPoint();
            	Point2D p2 = null;

            	try {
					p2 = MaptransForm.inverseTransform(p1, null);
				} catch (java.awt.geom.NoninvertibleTransformException e) {
					e.printStackTrace();
				}

            	scale -= (0.1 * me.getWheelRotation());
            	scale = Math.max(0.1, scale);
            	MaptransForm.setToIdentity();
            	MaptransForm.translate(p1.getX(), p1.getY());
            	MaptransForm.scale(scale, scale);
                MaptransForm.translate(-p2.getX(), -p2.getY());

            	revalidate();
            	repaint();

            	revalidate();
                repaint();
            }
		});

		for (; VisitorManager.getVisitors().size() < Festival.getCurrentFestival().getExpectedVisitors(); )
		{
			VisitorManager.getVisitors().add(new Visitor(this));
        }

        //add an mouseMotionListener to drag and drop the map
        addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent me){
            	if(leftButton)
            	{
                x2 = me.getX();
                y2 = me.getY();
                MaptransForm.translate(x2 - x, y2 - y);
                x = x2;
                y = y2;
            	}
                repaint();
            }
        });

        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent me3){
            	if(MouseEvent.BUTTON1 == me3.getButton())
            	{
            	leftButton = true;
                x = me3.getX();
                y = me3.getY();
            	}
            	
            	if(me3.getButton() == MouseEvent.BUTTON3)
            	{
            		leftButton = false;
            		for (Visitor v : VisitorManager.getVisitors())
            		{
            			if(v.getBounds().contains(me3.getPoint())== true)
            			{
            				selectedVisitor = v;
            			}
            		}
            	}
            }
        });


        grabFocus();
        timer = new Timer(1000 / 60, this);
        timer.start();
    }

    public int getTileWidth()
    {
        return tileWidth;
    }

    public int getTileHeight()
    {
        return tileHeight;
    }

	//paint the map;
    public void paintComponent(Graphics g)
    {
		//initialize paintComponent
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setTransform(paneltransForm);
			drawMap(g2);
			drawStageStrings(g2);
		if (Simulator.debug)
			drawDebug(g2);

		if(selectedVisitor != null)
		{
			hunger.setText("" + selectedVisitor.getHunger());
			visitorNR.setText("" + selectedVisitor.getNR());
			blatter.setText("" + selectedVisitor.getBlatter());
			selectedVisitor.drawBounds(g2);
		}
        for (Visitor v : VisitorManager.getVisitors())
        {
            if(!v.getDelete())
            	v.draw(g2);
        }
    }

    public SimpleGraph getGraph()
    {
        return graph;
    }
    
    public void drawTextField()
    {
    	JLabel label = new JLabel("clicked visitor info");
    	label.setBounds(41*32, 28*32, 128,32);
    	JLabel visitorNRLabel = new JLabel("visitor nr");
    	visitorNRLabel.setBounds(42 * 32, 29 * 32, 64, 32);
    	visitorNR= new JTextField();
    	visitorNR.setBounds(46 * 32, 29 * 32, 64, 32);
    	JLabel hungerLabel = new JLabel("hunger");
    	hungerLabel.setBounds(42 * 32, 30 * 32, 64, 32);
    	hunger = new JTextField();
    	hunger.setBounds(46 * 32, 30 * 32, 64, 32);
    	JLabel blatterLabel = new JLabel("blatter");
    	blatterLabel.setBounds(42 * 32, 31 * 32, 64, 32);
    	blatter = new JTextField();
    	blatter.setBounds(46 * 32, 31 * 32, 64, 32);
    	
    	this.add(label);
    	this.add(visitorNRLabel);
    	this.add(visitorNR);
    	this.add(hungerLabel);
    	this.add(hunger);
    	this.add(blatterLabel);
    	this.add(blatter);
    }
	private void drawDebug(Graphics2D g2)
	{

		Date newUpdate = new Date();
		long diff = 1000 / (newUpdate.getTime() - lastUpdate.getTime());
		lastUpdate = newUpdate;

		g2.drawString("FrameTime: " + diff, 10, 10);
		g2.drawString("FestivalTime " + festivalTime.get(Calendar.HOUR) + ":" + festivalTime.get(Calendar.MINUTE) + ":" + festivalTime.get(Calendar.SECOND) , 10, 20);

		for (int index = 0; index < tileLayers.size(); index++)
		{
			//go through all columns and rows
			for (int y = 0; y < height; y++)
			{
				for (int x = 0; x < width; x++)
				{
                    //g2.drawString((tiles.get(x, y).getWalkable() ? "" : "x"), x * tileWidth - 16, y * tileHeight - 16);
                    HashMap<Tile, HashMap<Tile, Integer>> paths = PathFinding.getPaths();
                    Tile beginTile = graph.getTiles().getTileByXYIndex(28, 19);
                    HashMap<Tile, Integer> distances = paths.get(beginTile);
					Tile currentTile = graph.getTiles().getTileByXYIndex(x-1, y-1);

                    Integer distance = distances.get(currentTile);

                    g2.drawString((distance == null ? "X" : distance.toString()), x * tileWidth - 16, y * tileHeight - 16);
                }
			}
		}
	}

    private void drawMap(Graphics2D g2)
    {
        //set the transform
		g2.setTransform(MaptransForm);
		int i = 0;
		//go through the layers
		for (int index = 0; index < tileLayers.size(); index++)
		{
			//go through all columns and rows
			for (int y = 0; y < height; y++)
			{
				for (int x = 0; x < width; x++)
				{
					//create an affinetransform
                    AffineTransform tileTransform = new AffineTransform();
                    //add a translate with the x and the y multiplied by the tileWidth/height so it will be placed correctly
                    tileTransform.translate(x * tileWidth, y * tileHeight);
                    //draw the tile with the affinetransform previously made
                    g2.drawImage(subImages.get(i), tileTransform, null);
                    //draw the tile with the affinetransform previously made
                    g2.drawImage(subImages.get(i), tileTransform, null);
                    i++;
                }
			}
		}
	}

	@Override
    public void actionPerformed(ActionEvent a) // update method
    {
        VisitorManager.getVisitors().forEach(Visitor::update);

        if(currentTick % (3600 / SECONDS_PER_TICK / SAVES_PER_HOUR) == 0 && firstRun)
        { // save every 30 minutes
            saveVisitors.save(currentTick, VisitorManager.getVisitors());
        }

        if(currentTick > simulationTicks){
            firstRun = false;
        }

        whereAtFestival(currentTick);
        currentTick++;

        // parse time for nice layout and stuff
        String minute;
        if(festivalTime.get(Calendar.MINUTE) < 10) minute = "0" + festivalTime.get(Calendar.MINUTE);
        else minute = "" + festivalTime.get(Calendar.MINUTE);
        String second;
        if(festivalTime.get(Calendar.SECOND) < 10) second = "0" + festivalTime.get(Calendar.SECOND);
        else second = "" + festivalTime.get(Calendar.SECOND);
        timeline.setTime(festivalTime.get(Calendar.HOUR_OF_DAY) + ":" + minute + ":" + second);

        if(firstRun){
            if(currentTick % (3600 / SECONDS_PER_TICK / SAVES_PER_HOUR) == 0){
                timeline.setSlider(((int) currentTick) / (3600 / (SECONDS_PER_TICK * SAVES_PER_HOUR)));
            }
        }
        else{
            if(timeline.isSliderAdjusted()){
                currentTick = timeline.getSlider() * (3600 / (SECONDS_PER_TICK * SAVES_PER_HOUR));
                VisitorManager.setVisitors(saveVisitors.getSave(currentTick));
            }else if(currentTick % (3600 / SECONDS_PER_TICK / SAVES_PER_HOUR) == 0){
                timeline.setSlider(((int) currentTick) / (3600 / (SECONDS_PER_TICK * SAVES_PER_HOUR)));
            }
        }

        repaint();
	}

    private void drawStageStrings(Graphics2D g){
        Font font = new Font("Arial", Font.BOLD, 30);
        g.setFont(font);
        g.setColor(Color.BLACK);

        g.drawString("ETEN", 9 * 32, 33*32);
        g.drawString("WC", 31*32, 33*32);
        if(drawStringStage1){
            g.drawString(showStage1, 17 * 32, 13 * 32);
        }
        if(drawStringStage2){
            AffineTransform at = new AffineTransform();
            at.translate(24 * 32, 18 * 32);
            at.rotate(-Math.PI / 2);
            Font font2 = font.deriveFont(at);
            g.setFont(font2);
            g.drawString(showStage2, 0, 0);
        }
        if(drawStringStage3){
        	g.setFont(font);
            g.drawString(showStage3, 17 * 32, 27 * 32);
        }
        if(drawStringStage4){
            AffineTransform at = new AffineTransform();
            at.translate(19 * 32, 18 * 32);
            at.rotate(-Math.PI / 2);
            Font font2 = font.deriveFont(at);
            g.setFont(font2);
            g.drawString(showStage4, 0,0);
        }
    }
    private void makeTiles()
    {
		// Gets the images form the tilesets.
		ArrayList<BufferedImage> tileImages = new ArrayList<>();
		//go throught the tileSet arrayList
        for (TileSet tileSet : tileSets)
        {
            try
            {
				//make a bufferedImage with the tilewidth and tileheight given in the JSON file
				//BufferedImage bufferedImage = new BufferedImage(tileSet.getImageWidth(), tileSet.getImageHeight(), BufferedImage.TYPE_INT_ARGB);
				//give the bufferedImage an image to take a subImage from to draw on the tile
				BufferedImage bufferedImage = ImageIO.read(new File("res" + File.separator + tileSet.getName() + ".png"));
				//add the BFI to an ArrayList with BFI
				tileImages.add(bufferedImage);
            } catch (IOException e)
            {
				e.printStackTrace();
			}
		}
		// go through all the tileLayers
		int t = 0;


        for (TileLayer tileLayer : tileLayers)
        {
			// go through all data ArrayList filled with the GID's
			for (int tileIndex = 0; tileIndex < tileLayer.getData().size(); tileIndex++)
				{
					long data = tileLayer.getData().get(tileIndex);
					//check if the tile isnt empty (GID > 0
                    if (data != 0)
                    {
						if (tileLayer.getName().equals("Pad Stages"))
						{

							int x = tileIndex % width;
							int y = (tileIndex - x) / width;
							if (data == 2821)
                                tiles.add(new Tile(x, y, true));
                            else
                                tiles.add(new Tile(x, y, false));
                        }

						//walkthrough all the tilesets
						for (int i = 0; i < tileSets.size(); i++)
						{
							//check if the data is between the First GID and the FGID+tileCount
							if (data > tileSets.get(i).getFirstGid()
                                    && data < tileSets.get(i).firstGid + tileSets.get(i).getTileCount())
                            {
								//if the data is between the FGID and the Tilecount use the tileset
								TileSet tileSet = tileSets.get(i);
								//configure the row of the tile
								int y = ((int) data - tileSet.getFirstGid()) / tileSet.getColumns()
										* tileSet.getTileHeight();
								//configure the column of the tile
								int x = (((int) data - tileSet.getFirstGid()) % tileSet.getColumns())
										* tileSet.getTileWidth();
								//get the width of the tile
								int w = tileSets.get(i).getTileWidth();
								//get the height of the tile
								int h = tileSets.get(i).getTileHeight();
								//take a subimage of the selected tileSet with the column, row, width and height value
								if(t!=tileLayers.size()-1)
								subImages.add(tileImages.get(i).getSubimage(x, y, w, h));
								else{
									subImages.add(tileImages.get(i).getSubimage(x, y, w, h));
									objectImages.add(tileImages.get(i).getSubimage(x, y, w, h));}
							}

                        }
                    } else
                    {
						//add an empty tile to the ArrayList
                        if(t != tileLayers.size() - 1){
                            subImages.add(null);
                        }else{
                            objectImages.add(null);
                            subImages.add(null);
                        }
                    }
				}
		}
        graph = new SimpleGraph(tiles);
        PathFinding.initialize(graph);
    }

    private void setTicks()
    {
        long simTicks;
        long totalTime;
        
        if(Festival.getCurrentFestival() == null){
            AgendaLoader.openFile();
            //System.out.println(Festival.getCurrentFestival().toString());
        }
        currentFestivalTime = Festival.getCurrentFestival().getBeginTime().getTimeInMillis();
        GregorianCalendar beginTime = Festival.getCurrentFestival().getBeginTime();
        GregorianCalendar endTime = Festival.getCurrentFestival().getEndTime();
        totalTime = endTime.getTimeInMillis() - beginTime.getTimeInMillis();
        simTicks = totalTime / 1000 / SECONDS_PER_TICK;

        this.simulationTicks = simTicks;
    }

    private void whereAtFestival(long currentTicks)
    {
    	boolean festivalFinished = true;
    	long tempCurrentFestivalTime = currentFestivalTime + currentTicks * 4000;
    	festivalTime = Calendar.getInstance();
      	festivalTime.setTimeInMillis(tempCurrentFestivalTime);
      	hour = festivalTime.getTime().getHours();
        HashMap<Show, Stage> currentShowsPlaying = new HashMap<>();
        //System.out.println("currentFestivalTime: " + festivalTime.get(Calendar.HOUR_OF_DAY) + ":" + festivalTime.get(Calendar.MINUTE) + ":" + festivalTime.get(Calendar.SECOND));
        if(currentTicks % 225 == 0){
        	totalPopularity = 0;
    		drawStringStage1 = false;
    		drawStringStage1 = false;
    		drawStringStage1 = false;
    		drawStringStage1 = false;
    	for(Stage stage : Festival.getCurrentFestival().getStages())
    	{
    		for(Show show : stage.getShows())
    		{
				if(hour>=show.getBeginTime().getTime().getHours()&&hour<show.getEndTime().getTime().getHours())
    			{
    				currentShowsPlaying.put(show, stage);
    				totalPopularity += show.getRating();
    				festivalFinished = false;
    			}
     		}
    	}

            //noinspection UnusedAssignment
            int totalVisitors = VisitorManager.getVisitors().size();
            ArrayList<Visitor> newVisitors = VisitorManager.getVisitors();
            java.util.Collections.shuffle(newVisitors, new Random(currentTick));
            int i = 0;
            for(Show show : currentShowsPlaying.keySet()){
    		int visitorsToSetTarget =  i + (int)((show.getRating()/totalPopularity) * totalVisitors);
    		for(; i < visitorsToSetTarget; i++)
    		{

                switch ((currentShowsPlaying.get(show)).getStageID())
                {
                    case 0:
                        newVisitors.get(i).setTarget(0);
                        drawStringStage1 = true;
                        showStage1= show.getName() + " - " + show.getArtist().getName();
                        break;
                    case 1:
                    	newVisitors.get(i).setTarget(1);
                    	drawStringStage2 = true;
                    	showStage2 = show.getName() + " - " + show.getArtist().getName();
                        break;
                    case 2:
                    	newVisitors.get(i).setTarget(2);
                    	drawStringStage3 = true;
                    	showStage3 = show.getName() + " - " + show.getArtist().getName();
                        break;
                    case 3:
                    	newVisitors.get(i).setTarget(3);
                    	drawStringStage4 = true;
                    	showStage4 = show.getName() + " - " + show.getArtist().getName();
                        break;   
                }

    		}

    	}
            if(festivalFinished)
            {
            	for(Visitor v : newVisitors)
            	{
            		v.setTarget(4);
            	}
            }
    	VisitorManager.setVisitors(newVisitors);
    	}
    }
}
