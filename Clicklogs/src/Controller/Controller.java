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
    private MainFrame mainFrame; //parent component for JOptionPanes
    private AltTree altTree;
    private final String filePath = "./src/Data/AltTree.dat"; //Make sure WORKING DIRECTORY is set to "...\G15-Clicklogs\Clicklogs\"
    private int currentLevel = 0;
    private List<Alt> chosenAlts;

    /**
     * Constructor for the Controller class.
     * Initializes the main frame and sets up the initial state.
     */
    public Controller() {
       showWelcomeMessage();
        mainFrame = new MainFrame(this, 700, 500);
        initialState();
    }

    private void showWelcomeMessage(){
        String welcomeMessage = "Welcome to the Desision Support System!\n\n"
                + "This application helps you make decision by presenting alternatives and chose the option that suits the customer's case.\n"
                + "You can:\n"
                + ".Copy your choices to the clipboard\n"
                + ".Undo your last choice\n"
                + ".Reset all the alternative you have chosen\n"
                + ".Add new alternative to the tree\n\n"
                + "Please log in to continue.";
        JOptionPane.showMessageDialog(null, welcomeMessage, "Welcome",JOptionPane.INFORMATION_MESSAGE);
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
        // Build list for display in GUI. Should be chosen alts + alts in next level
        List<Alt> altsToDisplay = new ArrayList<>(chosenAlts); // Start with chosen alts
        if (currentLevel < altTree.getMaxLevels()) { // Guard against end of decision tree
            List<Alt> nextLevelAlts = altTree.getAltsAtLevel(currentLevel); // Get all alt candidates in next level

            for (Alt nextLevelAlt : nextLevelAlts) {
                if (currentLevel == 0) { // För nivå 0, lägg till direkt
                    altsToDisplay.add(nextLevelAlt);
                } else {
                    List<Alt> parents = nextLevelAlt.getAllParents(); // För alla andra nivåer, kontrollera föräldrar
                    for (Alt p : parents) { //... traverse parents
                        if (p.isChosen()) {
                            altsToDisplay.add(nextLevelAlt); //... and only add candidate if one of its parents was chosen.
                            break; // Ingen anledning att fortsätta efter att ett valt förälder har hittats
                        }
                    }
                }
            }
        }
        // Refresh GUI with new lists
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
     * @author Zahraa
     */
    private void addNewAlt() {
        String labelText = JOptionPane.showInputDialog(mainFrame, "Enter label text for the new alternative");
        if (labelText == null || labelText.trim().isEmpty()) {
            System.out.println("Operation cancelled by user");
            return;
        }

        String outputText = JOptionPane.showInputDialog(mainFrame, "Enter output text for the new alternative");
        if (outputText == null || outputText.trim().isEmpty()) {
            System.out.println("Operation cancelled by user");
            return;
        }

        // Hämta alternativ på föregående nivå (föräldrar) och på nästa nivå (barn)
        List<Alt> parentCandidates = currentLevel > 0 ? altTree.getAltsAtLevel(currentLevel - 1) : new ArrayList<>();
        List<Alt> childCandidates = currentLevel < altTree.getMaxLevels() - 1 ? altTree.getAltsAtLevel(currentLevel + 1) : new ArrayList<>();

        List<Alt> chosenParents = chooseAlts("Choose parents for the new alternative", parentCandidates);
        List<Alt> chosenChildren = chooseAlts("Choose children for the new alternative", childCandidates);

        Alt newAlt = new Alt(chosenParents, labelText, outputText);
        for (Alt parent : chosenParents) {
            parent.addChild(newAlt);
        }
        for (Alt child : chosenChildren) {
            newAlt.addChild(child);
            child.addParent(newAlt);
        }
        altTree.addAlt(currentLevel, newAlt);
        refreshListToDisplay();
        altTree.saveAltTreeToFile(filePath);
        JOptionPane.showMessageDialog(mainFrame, "Alternative added successfully");
    }
    /**
     * Presents a dialog to the user with a list of alternatives and allows to choose a parent and child to the new alternative .
     * Returns the selected alternatives as a list.
     *
     * @param message The message to display in the dialog.
     * @param candidates The list of Alt objects to choose from.
     * @return A list of Alt objects that were selected by the user. If no selection is made or the candidates list is empty, an empty list is returned.
     * @author Zahraa
     */

    private List<Alt> chooseAlts(String message, List<Alt> candidates) {
        if (candidates.isEmpty()) {
            return new ArrayList<>();
        }

        String[] altLabels = candidates.stream().map(Alt::getAltLabelText).toArray(String[]::new);

        JList<String> list = new JList<>(altLabels);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JOptionPane.showMessageDialog(mainFrame, new JScrollPane(list), message, JOptionPane.PLAIN_MESSAGE);

        List<Alt> chosenAlts = new ArrayList<>();
        for (int index : list.getSelectedIndices()) {
            chosenAlts.add(candidates.get(index));
        }
        return chosenAlts;
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
            JOptionPane.showMessageDialog(mainFrame, "No alternative has been chosen yet!", "Wtf?", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Resets the decision tree.
     */
    private void resetTree() {
        int choice = JOptionPane.showConfirmDialog(mainFrame, "Are you sure?", "Reset", JOptionPane.YES_NO_OPTION);
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
