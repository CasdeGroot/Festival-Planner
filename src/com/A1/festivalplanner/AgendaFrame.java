package com.A1.festivalplanner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;

import com.A1.simulator.Simulator;

public class AgendaFrame extends JFrame
{
	
    private final String VERSION_NUMBER = "2016-02-26";
    private final ArrayList<JButton> buttons = new ArrayList<>();
    //Java Swing variables
    private final Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
    public JLabel festivalInfo;
    private JPanel borderLayout1;
    private JPanel calendarPanel;
    private JScrollPane scrollPane;
    private JLabel infoShow;
    private JPanel gridLayoutStages;
    private JPanel gridLayoutAgenda;
    private JScrollPane scrollPaneStages;
    //agenda variables
    private int festivalBeginTime;
    private int festivalEndTime;
    private int festivalDuration;
    private JPanel borderLayout2;

    public AgendaFrame()
    {
        super("Festival Planner");

        if (Festival.getCurrentFestival() == null)
            throw new RuntimeException("You stupid cunt, Festival is still null");


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        borderLayout1 = new JPanel(new BorderLayout());
        borderLayout2 = new JPanel(new BorderLayout());

        festivalInfo = new JLabel("<HTML><center>" + Festival.getCurrentFestival().getName() +
                "<BR> <font size='4'>" + Festival.getCurrentFestival().getDescription() + "</font></center></HTML>");
        festivalInfo.setFont(new Font("", Font.BOLD, 20));
        festivalInfo.setHorizontalAlignment(getWidth());
        festivalInfo.setVerticalAlignment(getHeight());
        festivalInfo.setBorder(BorderFactory.createLineBorder(Color.black));

        infoShow = new JLabel("<html><html>");
        infoShow.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        borderLayout1.add(festivalInfo, BorderLayout.NORTH);
        borderLayout1.add(borderLayout2, BorderLayout.CENTER);
        borderLayout2.add(infoShow, BorderLayout.SOUTH);
        calendarPanel = makeCalendar();
        borderLayout2.add(calendarPanel, BorderLayout.CENTER);


        fillCalendar();

        setIconImage(WelcomeFrame.getImageIcon().getImage());

        setMinimumSize(new Dimension(640, 480));
        setContentPane(borderLayout1);
        setJMenuBar(makeMenu());
        setSize(640, 480);
        setVisible(true);

    }

    public ArrayList<JButton> getButtons()
    {
        return buttons;
    }

    public void rebuild()
    {
        borderLayout2.remove(calendarPanel);
        calendarPanel = makeCalendar();
        borderLayout2.add(calendarPanel);
        this.fillCalendar();
        setVisible(true);
    }

    //make the menubar
    private JMenuBar makeMenu()
    {
        JMenuBar menuBar = new JMenuBar();
        UIManager.put("MenuItem.FONT", new Font("Arial", Font.BOLD, 17));
        JMenu file = new JMenu("File");
        JMenuItem new1 = new JMenuItem("New");

        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem saveAs = new JMenuItem("Save As");
        JMenuItem close = new JMenuItem("Close");
        JMenuItem import1 = new JMenuItem("Import");
        JMenuItem export = new JMenuItem("Export");

        JMenu edit = new JMenu("Edit");
        JMenuItem addShow = new JMenuItem("Add Show");
        JMenuItem removeShow = new JMenuItem("Remove Show");
        JMenuItem addStage = new JMenuItem("Add Stage");
        JMenuItem removeStage = new JMenuItem("Remove Stage");
        JMenuItem removeSelected = new JMenuItem("Remove Selected");

        JMenu tools = new JMenu("Tools");
        JMenuItem simulator = new JMenuItem("Simulator");
        JMenuItem preferences = new JMenuItem("Preferences");
        JMenuItem festivalSettings = new JMenuItem("Festival Settings");

        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        JMenuItem website = new JMenuItem("Website");

        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(tools);
        menuBar.add(help);

        file.add(new1);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.add(import1);
        file.add(export);
        file.add(close);

        edit.add(addShow);
        edit.add(removeShow);
        edit.add(addStage);
        edit.add(removeStage);
        edit.add(removeSelected);

        tools.add(simulator);
        tools.add(preferences);
        tools.add(festivalSettings);

        help.add(about);
        help.add(website);


        new1.addActionListener(event -> new NewFestivalFrame());

        open.addActionListener(event -> FileManager.openFile());
        save.addActionListener(event -> FileManager.save());
        saveAs.addActionListener(event -> FileManager.saveAs());
        close.addActionListener(event -> System.exit(0));
        import1.addActionListener(event -> System.out.println("test"));
        export.addActionListener(event -> {
            /*Rectangle screenRect = new Rectangle(frame.getSize());
            BufferedImage capture = new Robot().createScreenCapture(screenRect);
            ImageIO.write(capture, "bmp", new File(args[0]));*/
            System.out.println("test");
        });
        addShow.addActionListener(event -> new AddShowFrame(this));
        removeShow.addActionListener(event -> new RemoveShowFrame(this));
        addStage.addActionListener(event -> {
            //execute when menuItem "add Stage" is presse
            new AddStageFrame(this);
        });
        removeStage.addActionListener(event -> new RemoveStageFrame(this));
        removeSelected.addActionListener(event -> {
            //execute when menuItem "remove selected" is pressed
            System.out.println("test");


        });
        simulator.addActionListener(event -> {
            //execute when menuItem "simulator" is pressed
            new Simulator();
            //new TileLoader(false);  
        });
        preferences.addActionListener(event -> {
            //execute when menuItem "preferences" is pressed
            System.out.println("test");
        });
        festivalSettings.addActionListener(event -> new EditFestivalFrame(this));


        about.addActionListener(event -> {
            //execute when menuItem "about" is pressed
            JOptionPane.showMessageDialog(null, "Made by students from Avans:" +
                    "\n\n Nick van Gils" +
                    "\n Cas de Groot" +
                    "\n Michael van der Net" +
                    "\n Camiel Buskens" +
                    "\n Yorick Sedee" +
                    "\n Remco Korevaar" +
                    "\n\nVersion: " + VERSION_NUMBER, "About", JOptionPane.INFORMATION_MESSAGE);
        });
        website.addActionListener(event -> {
            //execute when menuItem "website" is pressed
            try
            {
                //open the website of the festival
                String URL = "http://www.ishetaltijdvoorbier.nl/";
                Desktop.getDesktop().browse(java.net.URI.create(URL));
            } catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        });
        return menuBar;
    }

    private JPanel makeCalendar()
    {
        //festival startingtime, ending time and duration
        festivalBeginTime = Festival.getCurrentFestival().getBeginTime().get(Calendar.HOUR_OF_DAY);
        festivalEndTime = Festival.getCurrentFestival().getEndTime().get(Calendar.HOUR_OF_DAY);
        festivalDuration = festivalEndTime - festivalBeginTime;

        JPanel borderLayout = new JPanel(new BorderLayout());
        gridLayoutStages = new JPanel(new GridLayout(Festival.getCurrentFestival().getStages().size() + 1, 1));
        gridLayoutAgenda = new JPanel(new GridLayout(Festival.getCurrentFestival().getStages().size() + 1, festivalDuration));
        scrollPane = new JScrollPane();
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        scrollPaneStages = new JScrollPane();

        //put the JLabels with the time in the gridlayout
        makeTimeLabels();
        //make the buttons for the calendar
        makeButtons();
        //put the stages in the calendar
        makeStages();
        //add everything to scrollpanes and then combine the scrollpanes to another borderlayout
        scrollPaneStages.getViewport().add(gridLayoutStages);
        scrollPane.getViewport().add(gridLayoutAgenda);
        scrollPaneStages.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        borderLayout.add(scrollPane, BorderLayout.CENTER);
        borderLayout.add(scrollPaneStages, BorderLayout.WEST);
        return borderLayout;
    }

    private void fillCalendar()
    {
        //the index of the currentStage we're at
        int stageCount = 0;
        for (Stage stage : Festival.getCurrentFestival().getStages())
        {
            for (final Show show : stage.getShows())
            {
                int showBeginTime = show.getBeginTime().get(Calendar.HOUR_OF_DAY);
                int showEndTime = show.getEndTime().get(Calendar.HOUR_OF_DAY);
                //duration of the show
                int showDuration = showEndTime - showBeginTime;
                int buttonsInARow;
                int firstButton;
                if (stageCount == 0)
                {
                    //color all buttons for the duration of the show
                    for (int index = 0; index < showDuration * 2 + 1; index++)
                    {
                        //number of buttons in a row
                        buttonsInARow = buttons.size() / Festival.getCurrentFestival().getStages().size();
                        //check if the index of the button isn't bigger than the size of the ArrayList of buttons
                        if ((stageCount * buttonsInARow + index) < buttons.size())
                        {
                            //check if the index of the button isn't smaller than 0
                            if ((stageCount * buttonsInARow + index) > 0)
                            {
                                //the first button to represent the starting time of the show
                                firstButton = (((showBeginTime - festivalBeginTime) * 2) - 1);
 
                                //get the first button and translate it the stage we are at in the for loop, then add the index to give that button another color
                                buttons.get(firstButton + (stageCount * buttonsInARow) + index).setBackground(Color.GREEN);
                                buttons.get(firstButton + (stageCount * buttonsInARow) + index).setText(show.getName());
                                buttons.get(firstButton + (stageCount * buttonsInARow) + index).addActionListener(e -> {
                                    //Execute when button is pressed
                                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                                    //get Information of the show and display it
                                    infoShow.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                                    infoShow.setText("<html><p><b>Show name:</b> " + show.getName() +
                                            "<br><b>Time:</b> " + format.format(show.getBeginTime().getTime()) +
                                            " - " + format.format(show.getEndTime().getTime()) +
                                            "<br><b>Artist:</b> " + show.getArtist().getName() +
                                            "<br><b>Artist description:</b> " + show.getArtist().getDescription() +
                                            "<br><b>Genre:</b> " + show.getArtist().getGenre() +
                                            "<br><b>Rating:</b> " + show.getRating() + "</p></html>");
                                    infoShow.setBackground(Color.LIGHT_GRAY);
                                    infoShow.setFont(new Font("Arial", Font.PLAIN, 18));
                                });
                            }
                        }
                    }
                } else
                {
                    for (int i = 0; i < showDuration * 2; i++)
                    {
                        //number of buttons in a row
                        buttonsInARow = buttons.size() / Festival.getCurrentFestival().getStages().size();
                        //check if the button index is smaller than the size of the arrayList of buttons
                        if ((stageCount * buttonsInARow + i) < buttons.size())
                        {
                            //check if the button index is bigger than zero
                            if ((stageCount * buttonsInARow + i) > 0)
                            {
                                //first button that represents the begin time of the show
                                firstButton = ((showBeginTime - festivalBeginTime) * 2);
                                //get the first button and translate it to the given row (stage) and add the index
                                buttons.get(firstButton + (stageCount * buttonsInARow) + i).setBackground(Color.GREEN);
                                buttons.get(firstButton + (stageCount * buttonsInARow) + i).setText(show.getName());
                                buttons.get(firstButton + (stageCount * buttonsInARow) + i).addActionListener(e -> {

                                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                                    //Execute when button is pressed
                                    //give the information of the show
                                    infoShow.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                                    infoShow.setText("<html><p><b>Show name: </b>" + show.getName() +
                                            "<br><b>Time:</b> " + format.format(show.getBeginTime().getTime()) +
                                            " - " + format.format(show.getEndTime().getTime()) +
                                            "<br><b>Artist:</b> " + show.getArtist().getName() +
                                            "<br><b>Artist description:</b> " + show.getArtist().getDescription() +
                                            "<br><b>Genre:</b> " + show.getArtist().getGenre() +
                                            "<br><b>Rating:</b> " + show.getRating() + "</p></html>");
                                    infoShow.setBackground(Color.LIGHT_GRAY);
                                    infoShow.setFont(new Font("Arial", Font.PLAIN, 18));
                                });
                            }
                        }
                    }
                }

            }
            stageCount++;
        }
    }

    private void makeStages()
    {
        if (gridLayoutStages != null)
            scrollPaneStages.remove(gridLayoutStages);
        gridLayoutStages = new JPanel(new GridLayout(Festival.getCurrentFestival().getStages().size() + 1, 1));
        //fill the grildlayout with stages with the stages that are currently in the grid layout

        //add the "TIME" JLabel
        JLabel time = new JLabel("Time", SwingConstants.CENTER);
        time.setFont(new Font("Comic sans", Font.PLAIN, 18));
        gridLayoutStages.add(time);

        for (int k = 0; k < (Festival.getCurrentFestival().getStages().size()); k++)
        {
            final Stage selectedStage = Festival.getCurrentFestival().getStages().get(k);
            JButton stageButton2 = new JButton(selectedStage.getName());
            stageButton2.addActionListener(e -> {
                infoShow.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                infoShow.setBackground(Color.LIGHT_GRAY);
                infoShow.setFont(new Font("Arial", Font.PLAIN, 18));
                infoShow.setText("<HTML> <b>name: </b>" + selectedStage.getName() + "</BR>" +
                        "<BR> <b>Location: </b>" + selectedStage.getLocation() + "</BR></HTML>");

            });
            stageButton2.setBackground(Color.GRAY);
            stageButton2.setForeground(Color.WHITE);
            stageButton2.setFont(new Font("Comic sans", Font.PLAIN, 18));
            gridLayoutStages.add(stageButton2);
        }
        scrollPaneStages.getViewport().add(gridLayoutStages);
    }

    private void makeTimeLabels()
    {
        //festival startingtime, ending time and duration
        festivalBeginTime = Festival.getCurrentFestival().getBeginTime().get(Calendar.HOUR_OF_DAY);
        festivalEndTime = Festival.getCurrentFestival().getEndTime().get(Calendar.HOUR_OF_DAY);
        festivalDuration = festivalEndTime - festivalBeginTime;
        //fill the borderlayout with the time (JLabels)
        for (int t = festivalBeginTime; t <= festivalBeginTime + festivalDuration; t++)
        {
            JLabel label = new JLabel(t + ":00", SwingConstants.LEFT);
            gridLayoutAgenda.add(label);
            label.setBorder(border);
            if (t != festivalBeginTime + festivalDuration)
            {
                JLabel label2 = new JLabel(t + ":30", SwingConstants.LEFT);
                label2.setBorder(border);
                gridLayoutAgenda.add(label2);
            }
        }
    }

    private void makeButtons()
    {
        //festival startingtime, ending time and duration
        festivalBeginTime = Festival.getCurrentFestival().getBeginTime().get(Calendar.HOUR_OF_DAY);
        festivalEndTime = Festival.getCurrentFestival().getEndTime().get(Calendar.HOUR_OF_DAY);
        festivalDuration = festivalEndTime - festivalBeginTime;

        scrollPane.remove(gridLayoutAgenda);
        gridLayoutAgenda = new JPanel(new GridLayout(Festival.getCurrentFestival().getStages().size() + 1, festivalDuration));
        //total of buttons we need
        makeTimeLabels();
        int totalButtons = (Festival.getCurrentFestival().getStages().size() * festivalDuration * 2) + (Festival.getCurrentFestival().getStages().size() - 1);

        buttons.clear();
        //fill the grid layout with the buttons
        for (int s = 0; s <= totalButtons; s++)
        {
            JButton button = new JButton("");
            button.setBackground(Color.LIGHT_GRAY);
            button.setForeground(Color.RED);
            buttons.add(button);
            gridLayoutAgenda.add(button);
        }
        scrollPane.getViewport().add(gridLayoutAgenda);
    }
}