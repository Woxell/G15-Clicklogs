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
    private JLabel jLabel;
    private MainPanel mainPanel;
    private Controller controller;
    private ArrayList<JButton> buttonList = new ArrayList<>();


    public DecisionPanel(MainPanel mainPanel, Controller controller) {
        //int height = (int) (mainPanel.getHeight() * 0.7);
        this.mainPanel = mainPanel;
        this.controller = controller;
        /*setLayout(new FlowLayout());
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

         */
        setUp();
    }

    //MAJOR TEST MIGHT REMOVE LATER
    private void setUp(){
        int height = (int) (mainPanel.getHeight() * 0.7);
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
        /*for(JButton b : buttonList){ //removes all existing buttons from panel
            remove(b);
        }

         */
        removeAll();
        setUp();
        buttonList.clear();
        for(Alt alt : displayedAlts) { //adds all relevant buttons to panel
            JButton altButton = new JButton();
            altButton.setText(alt.getAltLabelText() + " ");
            altButton.setBackground(Color.WHITE);
            if(!(alt.isChosen())){
                altButton.setEnabled(true);
                altButton.addActionListener(listener -> altPressed(alt)); //if alt has not been chosen before
            }else{
                altButton.setEnabled(false); //if alt has been chosen before
            }
            buttonList.add(altButton);
            //add(buttonList.getLast());
            add(altButton);
        }
        revalidate();
    }

    private void altPressed(Alt alt){
        alt.becomesChosen();
        controller.altPressed(alt);
    }

    //DEMO
    private void buttonPressed(int altNumber){
        controller.buttonPressed(ButtonType.ADD);
        buttonList.get(altNumber).setBackground(Color.GREEN);
        buttonList.get(altNumber).setEnabled(false);

    }

    public ArrayList<JButton> getButtonList() {
        return buttonList;
    }
}
