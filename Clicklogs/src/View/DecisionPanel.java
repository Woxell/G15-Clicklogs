package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import Controller.Controller;
import Model.Alt; //VIOLATES MVC, ONLY FOR TEST DAY


public class DecisionPanel extends JPanel {
    private MainPanel mainPanel;
    private Controller controller;


    public DecisionPanel(MainPanel mainPanel, Controller controller) {
        int height = (int) (mainPanel.getHeight() * 0.7);
        this.mainPanel = mainPanel;
        this.controller = controller;
        setLayout(new FlowLayout());
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(1, height));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Handle resize event to make size of this panel dynamic
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int height = (int) (mainPanel.getHeight() * 0.7); // Calculate the new height
                setPreferredSize(new Dimension(1, height));
                revalidate(); // Revalidate the layout to reflect the changes
            }
        });

        setVisible(true);
    }

    public void refreshDisplayedAlts(List<Alt> displayedAlts) {
        removeAll();

        for(Alt alt : displayedAlts) { //adds all relevant buttons to panel
            JButton altButton = new JButton();
            altButton.setText(alt.getAltLabelText() + " ");
            altButton.setBackground(Color.WHITE);
            if(alt.isChosen()){
                altButton.setEnabled(false); //if alt has been chosen before
            }else{
                altButton.setEnabled(true);
                altButton.addActionListener(listener -> controller.altPressed(alt)); //if alt has not been chosen before
            }
            add(altButton);
        }
        revalidate();
    }
}
