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

/**
 * Controller class, acts as intermediate between View and Model classes
 * @author Andre, Mohamad, Robert, Zahra, Isra
 */
public class Controller {

    private DecisionPanel decisionPanel;
    private OutputPanel outputPanel;
    private ButtonPanel buttonPanel;
    private final MainFrame mainFrame; // parent component for JOptionPanes
    private AltTree altTree;
    private final String filePath = "./src/Data/AltTree.dat";// Make sure WORKING DIRECTORY is set to "...\G15-Clicklogs\Clicklogs\"
    private int currentLevel = 0;
    private List<Alt> chosenAlts;
    private boolean smartSorting = false;


    /**
     * Constructor for the Controller class.
     * Initializes the main frame and sets up the initial state.
     *
     * @author Andre
     */
    public Controller() {

        mainFrame = new MainFrame(this, 700, 500);
        initialState();
    }


    /**
     * (Re)sets variables. Also updates gui to a clear output field and level 0 alts
     *
     * @author Andre
     * @author Robert
     */
    private void initialState() {
        currentLevel = 0;
        chosenAlts = new ArrayList<>();
        altTree = AltTree.readAltTree(filePath);
        List<Alt> levelZeroAlts = altTree.getAltsAtLevel(0);

        // If user has enabled smart sorting level 0 Alts will be sorted
        if (smartSorting){
            displaySort(levelZeroAlts);
        }

        decisionPanel.refreshDisplayedAlts(levelZeroAlts);
        outputPanel.refreshOutputText(chosenAlts);
    }

    /**
     * Refreshes the list of alternatives to display in the GUI.
     * Builds the list based on chosen alternatives and the next level alternatives.
     * Also updates the output text based on the chosen alternatives.
     *
     * @author Andre
     */
    private void refreshListToDisplay() {
        // Build list for display in GUI. Should be chosen alts + alts in next level
        List<Alt> altsToDisplay = new ArrayList<>(chosenAlts); // Start with chosen alts
        List<Alt> altChildren = new ArrayList<>(); // Start with chosen alts
        if (currentLevel < altTree.getMaxLevels()) { // Guard against end of decision tree
            List<Alt> nextLevelAlts = altTree.getAltsAtLevel(currentLevel); // Get all alt candidates in next level
            for (Alt nextLevelAlt : nextLevelAlts) {
                if (currentLevel > 0) { // Avoids parent-lookup for level 0
                    List<Alt> parents = nextLevelAlt.getAllParents(); // For each candidate, get all parents
                    for (Alt p : parents) { // ... traverse parents
                        if (p.isChosen()) {
                            altChildren.add(nextLevelAlt); // ... and only add candidate if one of its parents was chosen.
                        }
                    }
                }
            }
        }
        if (smartSorting){ // If user has chosen smart sorting
            altChildren = displaySort(altChildren);
        }
        altsToDisplay.addAll(altChildren);// Adds relevant children Alts at the end of altsToDisplay arraylist

        // Refresh GUI with new lists
        decisionPanel.refreshDisplayedAlts(altsToDisplay);
        outputPanel.refreshOutputText(chosenAlts);
    }

    /**
     * Bubblesort algorithm that sorts Alts in descending order based on their counter values
     * Higher values places at lower indexes in childrenAlts list
     * @param childrenAlts List of all children alts relevant for DecisionPanel
     * @return Sorted list
     * @author Robert
     */
    private List<Alt> displaySort(List<Alt> childrenAlts) {
        int n = childrenAlts.size();
        boolean swapped;

        do{
            swapped = false;
            for (int i = 0; i < n -1; i++){
                if (childrenAlts.get(i).getCounter() < childrenAlts.get(i + 1).getCounter()){
                    Alt temp = childrenAlts.get(i);
                    childrenAlts.set(i, childrenAlts.get(i + 1));
                    childrenAlts.set(i + 1, temp);
                    swapped = true;
                }
            }
            n--;
        }while(swapped);

        return childrenAlts;
    }

    /**
     * Handles selection of an alt in the GUI.
     *
     * @param alt The alternative selected.
     * @author Andre
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
     * @author Andre
     * @author Robert
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
            case SETTINGS:
                settingsMenu();
                break;
            case SMART:
                smartSorting = !smartSorting; // Flips boolean
                System.out.println("Boolean is now: " + smartSorting);
                break;
            default:
                System.out.println("Error in buttonPressed method");
        }
    }

    private void settingsMenu() {
        new SettingsFrame(this, smartSorting);
    }

    /**
     * Adds a new alternative.
     *
     * @author Zahra
     */
    private void addNewAlt() {
        System.out.println("Add button pressed");
        //TODO: implement functionality for adding custom Alt
        String labelText = JOptionPane.showInputDialog(mainFrame,
                "Enter label text for the new alternative");
        String outputText = JOptionPane.showInputDialog(mainFrame,
                "Enter output text for the new alternative");

        Alt newAlt = new Alt(labelText, outputText);
        altTree.addAlt(currentLevel, newAlt);
        refreshListToDisplay();
        JOptionPane.showMessageDialog(null, "Alternative added successfully");

    }

    /**
     * Copies the output text to the clipboard.
     *
     * @author Andre
     * @author Robert
     */
    private void copyToClipboard() {
        String output = outputPanel.getText(); // TODO: remove last space
        if (!(output.isEmpty())) { // If output is not empty output is copied to clipboard
            StringSelection selection = new StringSelection(output);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
        }
    }

    /**
     * Undoes the last choice made by the user.
     *
     * @author Andre, Mohamad
     */
    private void undoLastChoice() {
        if (currentLevel > 0) {  // cus it is not possible to undo when current level is 0.
            if (--currentLevel == 0) { // Bug fix, not pretty
                initialState();
            } else {
                chosenAlts.removeLast().setChosen(false); // Undo chosen state for last chosen alt then remove it from history of chosen alts.
                refreshListToDisplay();
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "No alternative has been chosen yet!",
                    "Wtf?", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Resets the decision tree.
     *
     * @author Andre
     * @author Robert
     */
    private void resetTree() {
        int choice = JOptionPane.showConfirmDialog(mainFrame, "Are you sure?",
                "Reset", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            updateAltTree();
            initialState();
        }
    }

    /**
     * If the user has chosen an Alt before Resetting, this method sets the boolean "chosen" to false for that Alt
     * and increases the counter of that Alt.
     * Additionally, this method saves the AltTree to filepath to preserve the updated counters in the Alt class
     * @author Robert
     */
    private void updateAltTree() {
        if (!chosenAlts.isEmpty()){ // if an Alt has been chosen all Alts chosen will be set to false
            for (Alt alt : chosenAlts){
                alt.setChosen(false);
                alt.increaseCounter();
                System.out.println("Alt: " + alt.getAltLabelText() + " Has counter: " + alt.getCounter());
            }

            altTree.saveAltTreeToFile(filePath); // Saves updated AltTree to filepath
        }
    }

    /**
     * Adds instances of the main panels to the controller.
     *
     * @param dPanel The DecisionPanel instance.
     * @param oPanel The OutputPanel instance.
     * @param bPanel The ButtonPanel instance.
     * @author Andre
     */
    public void addPanelInstances(DecisionPanel dPanel, OutputPanel oPanel, ButtonPanel bPanel) {
        decisionPanel = dPanel;
        outputPanel = oPanel;
        buttonPanel = bPanel;
    }


    public static class Main {
        public static void main(String[] args) {
            new Controller();
        }
    }
}

