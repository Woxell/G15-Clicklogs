package View;

import javax.swing.*;
import java.awt.*;
import Controller.Controller;

/**
 * The MainPanel class represents the main panel of the application's GUI, which contains the decision panel, output panel,
 * and button panel.
 *
 * @author Andre
 * @author
 */
public class MainPanel extends JPanel {
    /**
     * The main frame of the application.
     */
    private MainFrame mainFrame;

    /**
     * The decision panel, where the user can input their decisions.
     */
    private DecisionPanel decisionPanel;

    /**
     * The output panel, where the application displays its output.
     */
    private OutputPanel outputPanel;

    /**
     * The button panel, where the user can interact with the application.
     */
    private ButtonPanel buttonPanel;

    /**
     * The controller, which manages the application's logic.
     */
    private Controller controller;

    /**
     * Constructs a new MainPanel object with the given main frame and controller.
     *
     * @param mainFrame The main frame of the application.
     * @param controller The controller of the application.
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
     */
    public int getHeight(){
        return mainFrame.getHeight();
    }

    /**
     * Notifies the controller that a button has been pressed.
     *
     * @param buttonType The type of button that was pressed.
     */
    public void buttonPressed(ButtonType buttonType) {
        controller.buttonPressed(buttonType);
    }
}
