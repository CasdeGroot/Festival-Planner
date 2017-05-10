package com.A1.festivalplanner;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AddShowFrame extends JFrame
{
    private final AgendaFrame agenda;
    private final JTextField artistName;
    private final JTextField genre;
    private final JTextField description;
    private final JTextField rating;
    private final JComboBox hour;
    private final JComboBox minutes;
    private final JComboBox hour2;
    private final JComboBox stages;
    private final JComboBox minutes2;
    private final JTextField name;

    public AddShowFrame(AgendaFrame agenda)
    {
        super("Add Show");
        this.agenda = agenda;
        JPanel p = new JPanel(new BorderLayout(10, 10));

        JPanel labels = new JPanel(new GridLayout(0, 1, 5, 5));
        labels.add(new JLabel("Begin-Time:", SwingConstants.RIGHT));
        labels.add(new JLabel("End-Time:", SwingConstants.RIGHT));
        labels.add(new JLabel("Show name:", SwingConstants.RIGHT));
        labels.add(new JLabel("Artist:", SwingConstants.RIGHT));
        labels.add(new JLabel("Genre:", SwingConstants.RIGHT));
        labels.add(new JLabel("Description:", SwingConstants.RIGHT));
        labels.add(new JLabel("Rating:", SwingConstants.RIGHT));
        labels.add(new JLabel("", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);

        JPanel time = new JPanel(new GridLayout(1, 2));
        JPanel time2 = new JPanel(new GridLayout(1, 2));

        JPanel p2 = new JPanel(new BorderLayout(1, 1));
        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));


        //input field for the name of the show
        name = new JTextField();
        controls.add(name);

        //array of stages currently in the festival
        Object[] stagesArray = Festival.getCurrentFestival().getStages().toArray();

        String[] hourArray = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        //comboBox to select startTime  hour(s)
        hour = new JComboBox<>(hourArray);
        time.add(hour);

        String[] minutesArray = {"00", "30"};
        //comboBox to select startTime minutes
        minutes = new JComboBox<>(minutesArray);
        time.add(minutes);

        //comboBox to select endTime hour(s)
        hour2 = new JComboBox<>(hourArray);
        time2.add(hour2);

        //comboBox to select endTime minute(s)
        minutes2 = new JComboBox<>(minutesArray);
        time2.add(minutes2);

        //comboBox of current Stages in the festival
        stages = new JComboBox<>(stagesArray);

        //input field for artist name
        artistName = new JTextField();
        controls.add(artistName);

        //input field for artist genre
        genre = new JTextField();
        controls.add(genre);

        //input field for artist description
        description = new JTextField();
        controls.add(description);

        //input field for artist rating
        rating = new JTextField();
        controls.add(rating);

        controls.add(stages);
        p2.add(time, BorderLayout.NORTH);
        p2.add(time2, BorderLayout.CENTER);
        p2.add(controls, BorderLayout.SOUTH);
        p.add(p2, BorderLayout.CENTER);

        JButton okButton = new JButton();
        okButton.setText("Ok");
        okButton.addActionListener(e -> okButton_OnClick());

        p.add(okButton, BorderLayout.SOUTH);

        setIconImage(WelcomeFrame.getImageIcon().getImage());
        setResizable(false);
        setSize(400, 285);
        setContentPane(p);
        setVisible(true);
    }

    private void okButton_OnClick()
    {
        Artist artistInput = null;
        boolean artistdouble = true;
        for (Stage stage : Festival.getCurrentFestival().getStages())
        {
            for (Show show : stage.getShows())
            {
                if (show.getArtist().getName().equals(artistName.getText()))
                {
                    artistInput = show.getArtist();
                    int begintijd = (show.getBeginTime().get(GregorianCalendar.HOUR_OF_DAY)) * 60 + (show.getBeginTime().get(GregorianCalendar.MINUTE));
                    int eindtijd = (show.getEndTime().get(GregorianCalendar.HOUR_OF_DAY)) * 60 + (show.getBeginTime().get(GregorianCalendar.MINUTE));
                    //(show.getBeginTime().get(GregorianCalendar.MINUTE));
                    int begintijdinput = Integer.valueOf((String) hour.getSelectedItem())* 60 + Integer.valueOf((String) minutes.getSelectedItem());
                    int eindtijdinput = Integer.valueOf((String) hour2.getSelectedItem())* 60 + Integer.valueOf((String) minutes2.getSelectedItem());
                    
                    if(!(begintijdinput >= eindtijd) && !(eindtijdinput <= begintijd))
                    	artistdouble = false;
                    }
                }
            }
        
        if (artistInput == null)
        {
            //create artist object with given information
            artistInput = new Artist(artistName.getText(), genre.getText(), description.getText());
            System.out.println("new artist");
        }
        int hourInput = Integer.valueOf((String) hour.getSelectedItem());
        int minutesInput = Integer.valueOf((String) minutes.getSelectedItem());
        int hourInput2 = Integer.valueOf((String) hour2.getSelectedItem());
        int minutesInput2 = Integer.valueOf((String) minutes2.getSelectedItem());
        Stage stagesInput = (Stage) stages.getSelectedItem();
        System.out.println(stagesInput.toString());
        // start time  with given information
        GregorianCalendar beginningTime = new GregorianCalendar(0, 0, 0, hourInput, minutesInput);

        //e end time with given information
        GregorianCalendar endingTime = new GregorianCalendar(0, 0, 0, hourInput2, minutesInput2);

        //rating with given information
        double ratingEnd = Double.parseDouble(rating.getText());

        //make a new show with the given information
        Show newShow = new Show(name.getText(), beginningTime, endingTime, artistInput, ratingEnd);
        //add the show to the given stage

        boolean timeAvailable = true;
        for (Show show : stagesInput.getShows())
        {
            if (beginningTime.after(show.getBeginTime()) && beginningTime.before(show.getEndTime()))
                timeAvailable = false;
            if (endingTime.after(show.getBeginTime()) && endingTime.before(show.getEndTime()))
                timeAvailable = false;
            if (beginningTime.equals(show.getBeginTime()) || endingTime.equals(show.getEndTime()))
                timeAvailable = false;
            if (beginningTime.before(show.getBeginTime()) && endingTime.after(show.getEndTime()))
                timeAvailable = false;
        }
        if (timeAvailable)
        {
        	if(artistdouble)
        	{
            stagesInput.addShow(newShow);
            agenda.rebuild();
        	}
        	else
        	{
        		System.out.println("The same artist is playing at this time");
        	}
        } else
            System.out.println("Already show at this time");
        setVisible(false);
    }
}
