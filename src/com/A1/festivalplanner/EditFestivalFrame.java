package com.A1.festivalplanner;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditFestivalFrame extends JFrame
{
    private final AgendaFrame parent;
    private final JTextField festivalName;
    private final JTextField festivalDescription;
    private final JTextField festivalPrice;
    private final JTextField maxVisitors;
    private final JTextField expVisitors;
    private final JComboBox beginTime1;
    private final JComboBox beginTime2;
    private final JComboBox endTime1;
    private final JComboBox endTime2;

    public EditFestivalFrame(AgendaFrame parent)
    {
        super("Edit Festival");
        this.parent = parent;
        JPanel festivalSettingsPanel = new JPanel(new GridLayout(10, 2));
        festivalName = new JTextField();
        festivalName.setText(Festival.getCurrentFestival().getName());
        festivalName.setEditable(false);
        JCheckBox nameLabel = new JCheckBox("change festival name");
        festivalDescription = new JTextField();
        festivalDescription.setText(Festival.getCurrentFestival().getDescription());
        festivalDescription.setEditable(false);
        JCheckBox descriptionLabel = new JCheckBox(" change festival description");
        festivalPrice = new JTextField();
        festivalPrice.setText(String.valueOf(Festival.getCurrentFestival().getPrice()));
        festivalPrice.setEditable(false);
        JCheckBox priceLabel = new JCheckBox("change festival price");
        maxVisitors = new JTextField();
        maxVisitors.setText(String.valueOf(Festival.getCurrentFestival().getMaxVisitors()));
        maxVisitors.setEditable(false);
        JCheckBox maxLabel = new JCheckBox("change maximum visitors");
        expVisitors = new JTextField();
        expVisitors.setText(String.valueOf(Festival.getCurrentFestival().getExpectedVisitors()));
        expVisitors.setEditable(false);
        JCheckBox expLabel = new JCheckBox("change expected visitors");
        JCheckBox checkBox1 = new JCheckBox();
        JCheckBox checkBox2 = new JCheckBox();
        JLabel checkBoxlabel1 = new JLabel("change starting time");
        JLabel checkBoxlabel2 = new JLabel("change ending time");
        String[] timeInputHours = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        String[] timeInputMinutes = {"00", "30"};
        beginTime1 = new JComboBox<>(timeInputHours);
        beginTime2 = new JComboBox<>(timeInputMinutes);
        beginTime1.setEditable(false);
        beginTime2.setEditable(false);
        endTime1 = new JComboBox<>(timeInputHours);
        endTime2 = new JComboBox<>(timeInputMinutes);
        endTime1.setEditable(false);
        endTime2.setEditable(false);

        festivalSettingsPanel.add(nameLabel);
        festivalSettingsPanel.add(festivalName);
        festivalSettingsPanel.add(descriptionLabel);
        festivalSettingsPanel.add(festivalDescription);
        festivalSettingsPanel.add(priceLabel);
        festivalSettingsPanel.add(festivalPrice);
        festivalSettingsPanel.add(maxLabel);
        festivalSettingsPanel.add(maxVisitors);
        festivalSettingsPanel.add(expLabel);
        festivalSettingsPanel.add(expVisitors);
        festivalSettingsPanel.add(checkBoxlabel1);
        festivalSettingsPanel.add(checkBox1);
        festivalSettingsPanel.add(checkBoxlabel2);
        festivalSettingsPanel.add(checkBox2);
        festivalSettingsPanel.add(beginTime1);
        festivalSettingsPanel.add(beginTime2);
        festivalSettingsPanel.add(endTime1);
        festivalSettingsPanel.add(endTime2);
        

        checkBox1.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {//checkbox has been selected
                System.out.println("hoi");
                beginTime1.setEditable(true);
                beginTime2.setEditable(true);

            } else
            {
                beginTime1.setEditable(false);
                beginTime2.setEditable(false);
            }
        });

        checkBox2.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {//checkbox has been selected
                endTime1.setEditable(true);
                endTime2.setEditable(true);
            } else
            {
                endTime1.setEditable(false);
                endTime2.setEditable(false);
            }
        });

        nameLabel.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {//checkbox has been selected
                festivalName.setEditable(true);
                festivalName.setEditable(true);
            } else
            {
                festivalName.setEditable(false);
                festivalName.setEditable(false);
            }
        });

        descriptionLabel.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {//checkbox has been selected
                festivalDescription.setEditable(true);
            } else
            {
                festivalDescription.setEditable(false);
            }
        });

        priceLabel.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {//checkbox has been selected
                festivalPrice.setEditable(true);

            } else
            {
                festivalPrice.setEditable(false);
            }
        });

        maxLabel.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {//checkbox has been selected
                maxVisitors.setEditable(true);
            } else
            {
                maxVisitors.setEditable(false);
            }
        });

        expLabel.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {//checkbox has been selected
                expVisitors.setEditable(true);
            } else
            {
                expVisitors.setEditable(false);
            }
        });

        JButton okButton = new JButton();
        okButton.setText("Ok");
        okButton.addActionListener(e -> okButton_OnClick());
        festivalSettingsPanel.add(okButton);
        setContentPane(festivalSettingsPanel);
        setVisible(true);
        setSize(300,300);
    }

    private void okButton_OnClick()
    {
        if (festivalName.isEditable())
            Festival.getCurrentFestival().setName(festivalName.getText());

        if (festivalDescription.isEditable())
            Festival.getCurrentFestival().setDescription(festivalDescription.getText());

        if (festivalPrice.isEditable())
            Festival.getCurrentFestival().setPrice(Integer.valueOf(festivalPrice.getText()));

        if (maxVisitors.isEditable())
            Festival.getCurrentFestival().setMaxVisitors(Integer.valueOf(maxVisitors.getText()));

        if (expVisitors.isEditable())
            Festival.getCurrentFestival().setExpectedVisitors(Integer.valueOf(expVisitors.getText()));
        if (beginTime1.isEditable())
        {
            GregorianCalendar beginningTime = new GregorianCalendar(0, 0, 0, Integer.valueOf((String) beginTime1.getSelectedItem()), Integer.valueOf((String) beginTime2.getSelectedItem()));

            if (beginningTime.getTime().before(Festival.getCurrentFestival().getBeginTime().getTime()))
            {
                Festival.getCurrentFestival().setBeginTime(beginningTime);
            }
        }
        if (endTime1.isEditable())
        {
            GregorianCalendar endingTime = new GregorianCalendar(0, 0, 0, Integer.valueOf((String) endTime1.getSelectedItem()), Integer.valueOf((String) endTime2.getSelectedItem()));
            if (endingTime.getTime().after(Festival.getCurrentFestival().getEndTime().getTime()))
            {
                Festival.getCurrentFestival().setEndTime(endingTime);
            }
        }
        parent.festivalInfo.setText("<HTML>" + Festival.getCurrentFestival().getName() +
                    "<BR> " + Festival.getCurrentFestival().getDescription() + "</HTML>"); 
        parent.rebuild();
    }

}
