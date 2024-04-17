package Controller;

import Model.Alt;
import Model.AltTree;
import View.*;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private DecisionPanel decisionPanel;
    private OutputPanel outputPanel;
    private ButtonPanel buttonPanel;
    private AltTree altTree;
    private String filePath = "./src/Data/AltTree.dat";
    private int currentLevel = 0;
    private List<Alt> chosenAlts; //FOR TEST DAY, VIOLATES MVC


    public Controller() {
        new MainFrame(this, 900, 700);

        readAltTree();

        chosenAlts = new ArrayList<>();
        decisionPanel.refreshDisplayedAlts(new ArrayList<>(altTree.getAltsAtLevel(0))); //feeds GUI with alts in level 0
    }

    private void readAltTree() {
        boolean readFailed = true;
        do {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
                altTree = (AltTree) ois.readObject();
                readFailed = false;
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Error reading from " + filePath + "\nTry again?");
                System.err.println(e);
            }
        } while (readFailed);
    }

    public void buttonPressed(ButtonType pressedButton) {
        switch (pressedButton) {
            case ADD:
                System.out.println("Add button pressed");
                break;
            case COPY:
                copyPressed();
                System.out.println("Copy button pressed");
                break;
            case UNDO:
                undoPressed();
                System.out.println("Undo button pressed");
                break;
            case RESET:
                resetPressed();
                System.out.println("Reset button pressed");
                break;
            default:
                System.out.println("Error in buttonPressed method");
        }
    }

    private void undoPressed() {
        if (currentLevel > 0) {  // cus it is not possible to undo when current level is 0.
            chosenAlts.getLast().setChosen(false); //Undo chosen state for last chosen alt
            chosenAlts.removeLast(); //Then remove it from history of chosen alts.
            outputPanel.refreshOutputText(chosenAlts); //Output text becomes undone

            refreshListToDisplay();

        } else {
            System.out.println("No alternative has been chosen yet!");
        }
    }

    private void copyPressed() {
        outputPanel.copyToClipboard();
    }

    private void resetPressed() {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure?", "Reset", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            chosenAlts = new ArrayList<>();
            currentLevel = 0;
            readAltTree();
            decisionPanel.refreshDisplayedAlts(new ArrayList<>(altTree.getAltsAtLevel(0)));
            outputPanel.refreshOutputText(chosenAlts);
        }
    }

    public void altPressed(Alt alt) {
        //Add alt to history of chosen alts
        chosenAlts.add(alt);

        refreshListToDisplay();
    }

    private void refreshListToDisplay() {
        //Build list for display in GUI. Should be chosen alts + alts in next level
        List<Alt> altsToDisplay = new ArrayList<>(chosenAlts); //Start with chosen alts
        List<Alt> newLevelAlts = altTree.getAltsAtLevel(currentLevel - 1); //Get all possible alts in previous level

        for (Alt a : newLevelAlts) {
            if (currentLevel > 1) {
                List<Alt> parents = a.getParents(); //For each candidate, get all parents
                for (Alt p : parents) { //... traverse parents
                    if (p.isChosen()) {
                        altsToDisplay.add(a); //... and only add candidate if one of its parents was chosen.
                    }
                }
            } else {  // Add all candidates cus those on level 0 have no parents.
                altsToDisplay.add(a);
            }
        }

        //Final steps, decrement to previous level and update GUI.
        currentLevel--;
        outputPanel.refreshOutputText(chosenAlts);
        decisionPanel.refreshDisplayedAlts(altsToDisplay);
    }

    public void addPanelInstances(DecisionPanel dp, OutputPanel op, ButtonPanel bp) {
        decisionPanel = dp;
        outputPanel = op;
        buttonPanel = bp;
    }

    public static class Main {
        public static void main(String[] args) {
            new Controller();
        }
    }
}
