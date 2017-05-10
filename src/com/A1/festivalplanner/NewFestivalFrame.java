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

public class NewFestivalFrame extends JFrame
{
    private final JComboBox day;
    private final JComboBox month;
    private final JComboBox year;
    private final JTextField price;
    private final JTextField maxvisitors;
    private final JTextField festivaldescription;
    private final JTextField festivalname;
    private final JTextField expectedvisitors;
    private final JComboBox startTimeBox1;
    private final JComboBox startTimeBox2;
    private final JComboBox endTimeBox2;
    private final JComboBox endTimeBox1;

    public NewFestivalFrame()
    {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel p = new JPanel(new BorderLayout(10, 10));

        JPanel labels = new JPanel(new GridLayout(0, 1, 5, 5));
        labels.add(new JLabel("Date:", SwingConstants.RIGHT));
        labels.add(new JLabel("Time: ", SwingConstants.RIGHT));
        labels.add(new JLabel("Price:", SwingConstants.RIGHT));
        labels.add(new JLabel("Max Visitors:", SwingConstants.RIGHT));
        labels.add(new JLabel("Expected visitors:", SwingConstants.RIGHT));
        labels.add(new JLabel("Name:", SwingConstants.RIGHT));
        labels.add(new JLabel("Description:", SwingConstants.RIGHT));
        p.add(labels, BorderLayout.WEST);

        JPanel tijd = new JPanel(new GridLayout(1, 3));
        JPanel tijd2 = new JPanel(new GridLayout(1, 3));

        JPanel p2 = new JPanel(new BorderLayout(1, 1));
        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));


        //array of stages currently in the festival
        String[] dayarray = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        //combobox to select startTime  hour(s)
        day = new JComboBox<>(dayarray);
        tijd.add(day);
        String[] montharray = {"01", "02", "03", "04", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        //combobox to select startTime minutes
        month = new JComboBox<>(montharray);
        tijd.add(month);
        String[] yeararray = {"2016", "2017", "2018"};
        year = new JComboBox<>(yeararray);
        tijd.add(year);

        String[] startTime1 = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        String[] startTime2 = {"00", "30"};
        String[] endTime1 = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        String[] endTime2 = {"00", "30"};

        startTimeBox1 = new JComboBox<>(startTime1);
        startTimeBox2 = new JComboBox<>(startTime2);
        endTimeBox1 = new JComboBox<>(endTime1);
        endTimeBox2 = new JComboBox<>(endTime2);

        tijd2.add(startTimeBox1);
        tijd2.add(startTimeBox2);
        tijd2.add(new JLabel(" to"));
        tijd2.add(endTimeBox1);
        tijd2.add(endTimeBox2);
        //comboBox of current Stages in the festival

        //input field for artist name
        price = new JTextField();
        controls.add(price);
        //input field for artist genre
        maxvisitors = new JTextField();
        controls.add(maxvisitors);
        //input field for artist description
        expectedvisitors = new JTextField();
        controls.add(expectedvisitors);
        //input field for artist rating
        festivalname = new JTextField();
        controls.add(festivalname);
        //input field for the name of the show
        festivaldescription = new JTextField();
        controls.add(festivaldescription);

        JButton okButton = new JButton();
        okButton.addActionListener(e -> okButton_Pressed());
        okButton.setText("Create");
        //controls.add(okButton);

        p2.add(tijd, BorderLayout.NORTH);
        p2.add(tijd2, BorderLayout.CENTER);
        p2.add(controls, BorderLayout.SOUTH);

        p.add(p2, BorderLayout.CENTER);
        p.add(okButton, BorderLayout.SOUTH);

        //LayoutManager l = new GroupLayout(p);
        //p.setLayout(l);
        //show option pane with the comboboxes and textfields
        //JOptionPane.showMessageDialog(null, p, "New Festival", JOptionPane.QUESTION_MESSAGE);

        setIconImage(WelcomeFrame.getImageIcon().getImage());
        setContentPane(p);
        setSize(350, 230);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);


        /**/
    }

    private void okButton_Pressed()
    {
        int dayinput = Integer.valueOf((String) day.getSelectedItem());
        int monthinput = Integer.valueOf((String) month.getSelectedItem());
        int yearinput = Integer.valueOf((String) year.getSelectedItem());
        int beginTimeHourInput = Integer.valueOf((String) startTimeBox1.getSelectedItem());
        int beginTimeMinuteInput = Integer.valueOf((String) startTimeBox2.getSelectedItem());
        int endTimeHourInput = Integer.valueOf((String) endTimeBox1.getSelectedItem());
        int endTimeMinuteInput = Integer.valueOf((String) endTimeBox2.getSelectedItem());


        double priceEnd = Double.parseDouble(price.getText());
        int maxVisitorsEnd = Integer.valueOf(maxvisitors.getText());
        int expectedVisitorsEnd = Integer.valueOf(expectedvisitors.getText());

        // start time  with given information
        GregorianCalendar beginTime = new GregorianCalendar(dayinput, monthinput, yearinput, beginTimeHourInput, beginTimeMinuteInput);
        GregorianCalendar endTime = new GregorianCalendar(dayinput, monthinput, yearinput, endTimeHourInput, endTimeMinuteInput);

        Festival fes = new Festival(beginTime, endTime, priceEnd, maxVisitorsEnd, expectedVisitorsEnd, festivalname.getText(), festivaldescription.getText());
        Festival.setCurrentFestival(fes);
        new AgendaFrame();
        setVisible(false);

    }
}
