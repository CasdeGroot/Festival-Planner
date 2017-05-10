package com.A1.festivalplanner;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RemoveStageFrame extends JFrame
{
    private final AgendaFrame parent;
    private JComboBox stagesMenu;

    public RemoveStageFrame(AgendaFrame parent)
    {
        super("Remove stage");

        this.parent = parent;
        //execute when menuItem "remove Show" is pressed
        if(Festival.getCurrentFestival().getStages().size()!=0)
        {
	        final JPanel removeStagePanel = new JPanel(new BorderLayout(10, 10));
	        //comboBox with stages currently in the festival
            final JPanel dropDowns = new JPanel(new GridLayout(2, 1));
            //arrayList with stages currently in the festival
	        Object[] stagesArray = Festival.getCurrentFestival().getStages().toArray();
            stagesMenu = new JComboBox<>(stagesArray);

            JButton okButton = new JButton();
	        okButton.setText("Remove");
	        okButton.addActionListener(e -> okButton_OnClick());

            dropDowns.add(stagesMenu);
            dropDowns.add(okButton);
            removeStagePanel.add(dropDowns);
            setIconImage(WelcomeFrame.getImageIcon().getImage());
	        setSize(300,200); 
	        setContentPane(removeStagePanel);
	        setVisible(true);
	    }
    }
        
    private void okButton_OnClick()
    {
        Stage selectedStage = (Stage) stagesMenu.getSelectedItem();
        int indexOfSelectedStage = Festival.getCurrentFestival().getStages().indexOf(selectedStage);

        Festival.getCurrentFestival().removeShow(indexOfSelectedStage + 1);

        parent.rebuild();
    }


}
