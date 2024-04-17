package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

import Model.Alt;
import Model.AltTree;
import View.*;

public class Controller {

    private DecisionPanel decisionPanel;
    private OutputPanel outputPanel;
    private ButtonPanel buttonPanel;
    private AltTree altTree;
    private final String filePath = "./src/Data/AltTree.dat"; //Make sure WORKING DIRECTORY is set to "...\G15-Clicklogs\Clicklogs\"
    private int currentLevel = 0;
    private List<Alt> chosenAlts;


    public Controller() {
        new MainFrame(this, 900, 700);
        initialState();
    }

    private void initialState() {
        currentLevel = 0;
        chosenAlts = new ArrayList<>();
        altTree = AltTree.readAltTree(filePath);
        decisionPanel.refreshDisplayedAlts(altTree.getAltsAtLevel(0));
        outputPanel.refreshOutputText(chosenAlts);
    }

    private void refreshListToDisplay() {
        //Build list for display in GUI. Should be chosen alts + alts in next level
        List<Alt> altsToDisplay = new ArrayList<>(chosenAlts); //Start with chosen alts
        if (currentLevel < altTree.getMaxLevels()) { //Guard against end of decision tree
            List<Alt> nextLevelAlts = altTree.getAltsAtLevel(currentLevel); //Get all alt candidates in next level

            for (Alt nextLevelAlt : nextLevelAlts) {
                if (currentLevel > 0) { //Avoids parent-lookup for level 0
                    List<Alt> parents = nextLevelAlt.getParents(); //For each candidate, get all parents
                    for (Alt p : parents) { //... traverse parents
                        if (p.isChosen()) {
                            altsToDisplay.add(nextLevelAlt); //... and only add candidate if one of its parents was chosen.
                        }
                    }
                }
            }
        }
        //Refresh GUI with new lists
        decisionPanel.refreshDisplayedAlts(altsToDisplay);
        outputPanel.refreshOutputText(chosenAlts);
    }

    public void buttonPressed(ButtonType pressedButton) {
        switch (pressedButton) {
            case ADD:
                addNewAlt();
                System.out.println("Add button pressed");
                break;
            case COPY:
                copyToClipboard();
                System.out.println("Copy button pressed");
                break;
            case UNDO:
                undoLastChoice();
                System.out.println("Undo button pressed");
                break;
            case RESET:
                resetTree();
                System.out.println("Reset button pressed");
                break;
            default:
                System.out.println("Error in buttonPressed method");
        }
    }

    private void addNewAlt() {
        //TODO: implement functionality for adding custom Alt
    }

    private void copyToClipboard() {
        String output = outputPanel.getText(); //TODO: remove last space
        if (!(output.isEmpty())) { //If output is not empty output is copied to clipboard
            StringSelection selection = new StringSelection(output);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
        }
    }

    private void undoLastChoice() {
        if (currentLevel > 0) {  // cus it is not possible to undo when current level is 0.
            if (--currentLevel == 0) { //Bug fix, not pretty
                initialState();
            } else {
                chosenAlts.removeLast().setChosen(false); //Undo chosen state for last chosen alt then remove it from history of chosen alts.
                refreshListToDisplay();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No alternative has been chosen yet!", "Wtf?", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void resetTree() {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure?", "Reset", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            initialState();
        }
    }

    public void altPressed(Alt alt) {
        currentLevel++;
        alt.setChosen(true);
        chosenAlts.add(alt);
        refreshListToDisplay();
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
