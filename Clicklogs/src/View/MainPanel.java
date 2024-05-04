package View;

import javax.swing.*;
import java.awt.*;

import Controller.Controller;

/**
 * The MainPanel class represents the main panel of the application's GUI,
 * which contains the decision panel, output panel, and button panel.
 *
 * @author Mohammad, Andre
 */
public class MainPanel extends JPanel {

    private MainFrame mainFrame;
    private DecisionPanel decisionPanel;
    private OutputPanel outputPanel;
    private ButtonPanel buttonPanel;
    private Controller controller;

    /**
     * Constructs a new MainPanel object with the given main frame and controller.
     *
     * @param mainFrame  The main frame of the application.
     * @param controller The controller of the application.
     * @author Mohammad, Andre
     */
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

    /**
     * Returns the height of the main frame.
     *
     * @return The height of the main frame.
     * @author Mohammad, Andre
     */
    public int getHeight() {
        return mainFrame.getHeight();
    }

    /**
     * Notifies the controller that a button has been pressed.
     *
     * @param buttonType The type of button that was pressed.
     * @author Mohammad, Andre
     */
    public void buttonPressed(ButtonType buttonType) {
        controller.buttonPressed(buttonType);
    }
}
