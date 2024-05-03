package View;

import javax.swing.*;
import java.awt.*;

import Controller.Controller;

public class MainPanel extends JPanel {
    private MainFrame mainFrame;
    private DecisionPanel decisionPanel;
    private OutputPanel outputPanel;
    private ButtonPanel buttonPanel;
    private Controller controller;

    public MainPanel(MainFrame mainFrame, Controller controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;
        setLayout(new BorderLayout());

        // Initialize sub-panels
        decisionPanel = new DecisionPanel(this, controller);
        outputPanel = new OutputPanel();
        buttonPanel = new ButtonPanel(this);

        controller.addPanelInstances(decisionPanel, outputPanel, buttonPanel);

        // Add sub-panels to the main panel
        add(decisionPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public int getHeight(){
        return mainFrame.getHeight();
    }

    public void buttonPressed(ButtonType buttonType) {
        controller.buttonPressed(buttonType);
    }
}
