package View;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private MainFrame mainFrame;
    private DecisionPanel decisionPanel;
    private OutputPanel outputPanel;
    private ButtonPanel buttonPanel;
    private int width;
    private int height;

    public MainPanel(MainFrame mainFrame, int width, int height) {
        this.mainFrame = mainFrame;
        this.width = width;
        this.height = height;
        setLayout(new BorderLayout());

        // Initialize sub-panels
        decisionPanel = new DecisionPanel(this, width, height);
        outputPanel = new OutputPanel(this, width, height);
        buttonPanel = new ButtonPanel(this); // HAS THE SAME DIMENSIONS REGARDLESS OF MAINPANEL SIZE

        // Add sub-panels to the main panel
        add(decisionPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        mainFrame.pack();
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
