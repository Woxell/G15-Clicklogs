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

    /**
     * Constructor for the Controller class.
     * Initializes the main frame and sets up the initial state.
     */
    public Controller() {
        new MainFrame(this, 700, 500);
        initialState();
    }

    /**
     * (Re)sets variables. Also updates gui to a clear output field and level 0 alts
     */
    private void initialState() {
        currentLevel = 0;
        chosenAlts = new ArrayList<>();
        altTree = AltTree.readAltTree(filePath);
        decisionPanel.refreshDisplayedAlts(altTree.getAltsAtLevel(0));
        outputPanel.refreshOutputText(chosenAlts);
    }

    /**
     * Refreshes the list of alternatives to display in the GUI.
     * Builds the list based on chosen alternatives and the next level alternatives.
     * Also updates the output text based on the chosen alternatives.
     */
    private void refreshListToDisplay() {
        //Build list for display in GUI. Should be chosen alts + alts in next level
        List<Alt> altsToDisplay = new ArrayList<>(chosenAlts); //Start with chosen alts
        if (currentLevel < altTree.getMaxLevels()) { //Guard against end of decision tree
            List<Alt> nextLevelAlts = altTree.getAltsAtLevel(currentLevel); //Get all alt candidates in next level

            for (Alt nextLevelAlt : nextLevelAlts) {
                if (currentLevel > 0) { //Avoids parent-lookup for level 0
                    List<Alt> parents = nextLevelAlt.getAllParents(); //For each candidate, get all parents
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

    /**
     * Handles selection of an alt in the GUI.
     *
     * @param alt The alternative selected.
     */
    public void altPressed(Alt alt) {
        currentLevel++;
        alt.setChosen(true);
        chosenAlts.add(alt);
        refreshListToDisplay();
    }

    /**
     * Handles button presses from the GUI.
     *
     * @param pressedButton The type of button pressed.
     */
    public void buttonPressed(ButtonType pressedButton) {
        switch (pressedButton) {
            case ADD:
                addNewAlt();
                break;
            case COPY:
                copyToClipboard();
                break;
            case UNDO:
                undoLastChoice();
                break;
            case RESET:
                resetTree();
                break;
            default:
                System.out.println("Error in buttonPressed method");
        }
    }

    /**
     * Adds a new alternative.
     */
    private void addNewAlt() {
        System.out.println("Add button pressed");
        //TODO: implement functionality for adding custom Alt
    }

    /**
     * Copies the output text to the clipboard.
     */
    private void copyToClipboard() {
        String output = outputPanel.getText(); //TODO: remove last space
        if (!(output.isEmpty())) { //If output is not empty output is copied to clipboard
            StringSelection selection = new StringSelection(output);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
        }
    }

    /**
     * Undoes the last choice made by the user.
     */
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

    /**
     * Resets the decision tree.
     */
    private void resetTree() {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure?", "Reset", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            initialState();
        }
    }

    /**
     * Adds instances of the main panels to the controller.
     *
     * @param dp The DecisionPanel instance.
     * @param op The OutputPanel instance.
     * @param bp The ButtonPanel instance.
     */
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
