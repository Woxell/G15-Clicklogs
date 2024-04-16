package Controller;

import Model.AltTree;
import View.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Controller {

    private DecisionPanel decisionPanel;
    private OutputPanel outputPanel;
    private ButtonPanel buttonPanel;
    private AltTree altTree;

    public Controller() {
        new MainFrame(this, 900, 700);
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("AltTree.txt"))){
            altTree = (AltTree) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading AltTree.txt");;
        }
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

    public static class Main {
        public static void main(String[] args) {
            new Controller();
        }
    }
}
