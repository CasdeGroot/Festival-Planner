package com.A1.festivalplanner;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AddStageFrame extends JFrame
{
    private final JTextField stageName;
    private final JTextField location;
    private final AgendaFrame agenda;

    public AddStageFrame(AgendaFrame agenda)
    {
        super("Add Stage");
        this.agenda = agenda;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel p = new JPanel(new BorderLayout(10, 10));
        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JPanel labels = new JPanel(new GridLayout(0, 1, 5, 5));
        labels.add(new JLabel("Stage Name:", SwingConstants.RIGHT));
        labels.add(new JLabel("Location:", SwingConstants.RIGHT));
        labels.add(new JLabel("", SwingConstants.RIGHT));
        stageName = new JTextField();
        controls.add(stageName);
        location = new JTextField();
        controls.add(location);

        JButton okButton = new JButton();
        okButton.setText("Add stage");
        okButton.addActionListener(e -> okButton_Pressed());
        controls.add(okButton);

        p.add(labels, BorderLayout.WEST);
        p.add(controls, BorderLayout.CENTER);

        setIconImage(WelcomeFrame.getImageIcon().getImage());
        setSize(400, 200);
        setResizable(false);
        setContentPane(p);
        setVisible(true);
    }

    private void okButton_Pressed()
    {
        Stage stage = new Stage(stageName.getText(), location.getText());
        Festival.getCurrentFestival().addStage(stage);
        setVisible(false);
        agenda.rebuild();
    }

}
