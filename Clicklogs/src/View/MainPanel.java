package View;

import javax.swing.*;
import java.awt.*;
import Controller.Controller;

public class MainPanel extends JPanel {
    private MainFrame mainFrame;
    private DecisionPanel decisionPanel;
    private OutputPanel outputPanel;
    private ButtonPanel buttonPanel;

    public MainPanel(MainFrame mainFrame, Controller controller, int width, int height) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Initialize sub-panels
        decisionPanel = new DecisionPanel(this, controller);
        outputPanel = new OutputPanel(this, controller);
        buttonPanel = new ButtonPanel(this);

        controller.addPanelInstances(decisionPanel, outputPanel, buttonPanel);

        // Add sub-panels to the main panel
        add(decisionPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    protected DecisionPanel getDecisionPanel(){
        return decisionPanel;
    }
    protected ButtonPanel getButtonPanel(){
        return buttonPanel;
    }
    protected OutputPanel getOutputPanel(){
        return outputPanel;
    }
    public int getHeight(){
        return mainFrame.getHeight();
    }
}
