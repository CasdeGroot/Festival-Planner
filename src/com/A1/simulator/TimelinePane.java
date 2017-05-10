package com.A1.simulator;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

class TimelinePane extends JPanel
{
    private final JLabel label;
    private final JSlider slider;
    private String time;
    private boolean isAdjusted = false;

    public TimelinePane(){
        label = new JLabel(time);
        setLayout(new FlowLayout());
        setBorder(BorderFactory.createRaisedBevelBorder());
        slider = new JSlider(0, 1);
        slider.setPreferredSize(new Dimension(600, 30));
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.addChangeListener(e ->
        {
            JSlider source = (JSlider) e.getSource();
            if(!source.getValueIsAdjusting()){
                this.isAdjusted = true;
            }
        });
        slider.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent keyEvent)
            {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent)
            {
                if (keyEvent.getKeyCode() == KeyEvent.VK_F3)
                {
                    System.out.println("Debug toggled");
                    Simulator.debug = !Simulator.debug;
                }

                if (keyEvent.getKeyCode() == KeyEvent.VK_F5)
                {
                    if (Simulator.easterEgg)
                        VisitorManager.setDefaultVisitorSprite();
                    else
                        VisitorManager.setVisitorSprite();
                    Simulator.easterEgg = !Simulator.easterEgg;
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent)
            {
            }
        });

        add(label);
        add(slider);


    }

    public void setSliderMaximum(int value){
        slider.setMaximum(value);
    }

    public void setTime(String time)
    {
        this.time = time;
        label.setText(time);
    }

    public void setSlider(int value)
    {
        slider.setValue(value);
    }

    public int getSlider(){
        if(isAdjusted){
            isAdjusted = false;
        }
        return slider.getValue();
    }

    public boolean isSliderAdjusted(){
        return isAdjusted;
    }
}
