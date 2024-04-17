package Controller;

import Model.Alt;
import Model.AltTree;
import View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
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
    private List<Alt> chosenAlts = new ArrayList<>(); //FOR TEST DAY, VIOLATES MVC


    public Controller() {
        new MainFrame(this, 900, 700);
        altTree = AltTree.readAltTree(filePath);
        decisionPanel.refreshDisplayedAlts(new ArrayList<>(altTree.getAltsAtLevel(0))); //feeds GUI with alts in level 0
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
            chosenAlts.removeLast(); //then remove it from history of chosen alts.
            //Refresh GUI-items
            outputPanel.refreshOutputText(chosenAlts);
            refreshListToDisplay();

        } else {
            JOptionPane.showMessageDialog(null, "No alternative has been chosen yet!");
        }
    }

    private void copyPressed() {
        String output = outputPanel.getText();
        if (!(output.isEmpty())) { //If output is not empty output is copied to clipboard
            StringSelection selection = new StringSelection(output);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
        }
    }

    private void resetPressed() {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure?", "Reset", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            currentLevel = 0;
            altTree = AltTree.readAltTree(filePath);
            decisionPanel.refreshDisplayedAlts(new ArrayList<>(altTree.getAltsAtLevel(0)));
            outputPanel.refreshOutputText(chosenAlts = new ArrayList<>());
        }
    }

    public void altPressed(Alt alt) {
        alt.setChosen(true);
        chosenAlts.add(alt);
        currentLevel++;
        refreshListToDisplay();
    }

    private void refreshListToDisplay() {
        //Build list for display in GUI. Should be chosen alts + alts in next level
        List<Alt> altsToDisplay = new ArrayList<>(chosenAlts); //Start with chosen alts
        if (currentLevel < altTree.getMaxLevels()) { //Guard against end of decision tree
            List<Alt> newLevelAlts = altTree.getAltsAtLevel(currentLevel); //Get all possible alts in next level

            for (Alt alt : newLevelAlts) {
                if (currentLevel > 0) {
                    List<Alt> parents = alt.getParents(); //For each candidate, get all parents
                    for (Alt p : parents) { //... traverse parents
                        if (p.isChosen() && p.equals(alt)) {
                            altsToDisplay.add(alt); //... and only add candidate if one of its parents was chosen.
                        }
                    }
                } else {  // Add all candidates cus those on level 0 have no parents.
                    altsToDisplay.add(alt);
                }
            }
        }

        //Final steps, refresh GUI with new lists
        decisionPanel.refreshDisplayedAlts(altsToDisplay);
        outputPanel.refreshOutputText(chosenAlts);
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
