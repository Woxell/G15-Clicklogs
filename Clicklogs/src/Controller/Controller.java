package Controller;

import View.*;

import javax.swing.*;
import java.util.ArrayList;

public class Controller {

    private DecisionPanel decisionPanel;
    private OutputPanel outputPanel;
    private ButtonPanel buttonPanel;

    public Controller() {
        new MainFrame(this, 900, 700);
    }

    public void buttonPressed(ButtonType pressedButton) {
        switch (pressedButton) {
            case ADD:
                System.out.println("Add button pressed");

                //DEMO
                decisionPanel.addAltButton("Alternative");
                updateOutputPanel();

                break;
            case COPY:
                System.out.println("Copy button pressed");
                break;
            case UNDO:
                System.out.println("Undo button pressed");
                break;
            case RESET:
                System.out.println("Reset button pressed");
                break;
            default:
                System.out.println("Error in buttonPressed method");
        }
    }

    public void addPanelInstances(DecisionPanel dp, OutputPanel op, ButtonPanel bp) {
        decisionPanel = dp;
        outputPanel = op;
        buttonPanel = bp;
    }

    public void updateOutputPanel() {
        outputPanel.updateGeneratedText(decisionPanel.getAltList());
    }
}
