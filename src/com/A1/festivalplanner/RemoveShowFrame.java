package com.A1.festivalplanner;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RemoveShowFrame extends JFrame
{
    private final AgendaFrame parent;
    private JComboBox showsMenu;
    private Show selectedShow;
    private Stage selectedStage;

    public RemoveShowFrame(AgendaFrame parent)
    {
    	super("Remove show");
    	this.parent = parent;
        if(Festival.getCurrentFestival().getStages().size()!=0)
        {
	
	        
	        //execute when menuItem "remove Show" is pressed
	        JPanel removeShowPanel = new JPanel(new BorderLayout(10, 10));
	        JPanel dropdowns = new JPanel(new GridLayout(2, 1));
	
	        //arrayList with stages currently in the festival
	        Object[] getStages = Festival.getCurrentFestival().getStages().toArray();
	        //comboBox with stages currently in the festival
			JComboBox<?> stagesMenu = new JComboBox<>(getStages);
	
	        //get the selected stage
	        selectedStage = (Stage) stagesMenu.getSelectedItem();
	        //make an array with the selected shows of the selected stage
	        Object[] getShows = selectedStage.getShows().toArray();
	
	        //fill a comboboc with the selected shows array
			showsMenu = new JComboBox<>(getShows);
	
	        //fill a combobox with the selected shows array
			JComboBox showsMenu1 = new JComboBox<>(getShows);
	
	        //get the selected show
	        stagesMenu.addActionListener(e -> {
	
	            Stage selectedStage1 = (Stage) stagesMenu.getSelectedItem();
	            //make an array with the selected shows of the selected stage
	            showsMenu1.removeAllItems();
	            Object[] getShows1 = selectedStage1.getShows().toArray();
	
	            //fill a comboboc with the selected shows array
	            if (getShows1.length > 0)
	            {
	                for (Object s : getShows1)
	                {
	                    Show show = (Show) s;
	                    showsMenu1.addItem(s);
	                    showsMenu1.repaint();
	                }
	            }
	            //fill a combobox with the selected shows array
	            //showsMenu1 = new JComboBox(getShows1);
	            //get the selected show
	            System.out.println(selectedStage1.getShows().toString());
	            selectedShow = (Show) showsMenu1.getSelectedItem();
	            System.out.println(selectedStage1);
	            //dropdowns.add(stagesMenu);
	            //dropdowns.add(showsMenu11);
	            removeShowPanel.add(dropdowns);
	
	
	        });
	
	        selectedShow = (Show) showsMenu1.getSelectedItem();
	        System.out.println(selectedStage.getShows().size());
	        dropdowns.add(stagesMenu);
	        dropdowns.add(showsMenu1);
	        removeShowPanel.add(dropdowns);
	
	        JButton okButton = new JButton();
	        okButton.setText("Remove");
	        okButton.addActionListener(e -> okButton_OnClick());
	
	        removeShowPanel.add(okButton, BorderLayout.SOUTH); 
	        setIconImage(WelcomeFrame.getImageIcon().getImage());
	        setContentPane(removeShowPanel);
	        setVisible(true);
	
	
	        //Object[] removeshowoptions = {"One", "Two", "Three"};
	        //String input = (String)JOptionPane.showInputDialog(null,"Which show do you want to remove?","Remove Show",JOptionPane.QUESTION_MESSAGE,null,removeshowoptions,"One");
	    }
    }

    private void okButton_OnClick()
    {
      	if(selectedStage.getShows().size()!=0)
      	{
	    	int removeShowIndex = selectedStage.getShows().indexOf(selectedShow);
	        System.out.println(removeShowIndex);
	        //remove the show
	        selectedStage.removeShow(removeShowIndex);
	        System.out.println(Festival.getCurrentFestival().getStages().get(Festival.getCurrentFestival().getStages().indexOf(selectedStage)).toString());
	
	        parent.rebuild();
      	}
    }


}
