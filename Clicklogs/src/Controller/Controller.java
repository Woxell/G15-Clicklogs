package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

import Model.Alt;
import Model.AltTree;
import Model.UserModel;
import View.*;

/**
 * Controller class, acts as intermediate between View and Model classes
 *
 * @author Andre, Mohamad, Robert, Zahra, Isra
 */
public class Controller {
    private DecisionPanel decisionPanel;
    private OutputPanel outputPanel;
    private ButtonPanel buttonPanel;
    private SettingsFrame settingsFrame;
    private MainFrame mainFrame; //parent component for JOptionPanes
    private AltTree altTree;
    private final String filePath = "./src/Data/AltTree.dat";// Make sure WORKING DIRECTORY is set to "...\G15-Clicklogs\Clicklogs\"
    private int currentLevel = 0;
    private List<Alt> chosenAlts;
    private UserModel userModel;
    private boolean smartBoolean = false;
    private boolean previewBoolean = false;
    private boolean themeBoolean = false; // False = Dark theme, True = Light Theme
    private LoginV loginV;

    /**
     * Constructor for the Controller class.
     * Initializes the main frame and sets up the initial state.
     *
     * @author Andre
     */
    public Controller(UserModel userModel, LoginV loginV) {
        this.userModel = userModel;
        this.loginV = loginV;

        loginV.show(); //open log in view first
        loginV.setLoginButtonListener(e -> { //Listen for login events and display the MainFrame upon successful login.
            String username = loginV.getUsername();
            String password = loginV.getPassword();

            if (true) {
                loginV.hide();
                MainFrame mainFrame = new MainFrame(this, 700, 500);
                initialState();
            } else {
                loginV.showError("Invalid username or password.");
            }
        });
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

        for (Alt alt : levelZeroAlts) {
            System.out.println("Alt: " + alt.getAltLabelText());
        }

        // If user has enabled smart sorting level 0 Alts will be sorted
        if (smartBoolean) {
            smartSort(levelZeroAlts);
        }

        decisionPanel.refreshDisplayedAlts(levelZeroAlts);
        outputPanel.refreshOutputText(chosenAlts); // TODO INSPECT WHY PARAMETER IS NECESSARY FOR refreshOutputText
    }

    /**
     * Refreshes the list of alternatives to display in the GUI.
     * Builds the list based on chosen alternatives and the next level alternatives.
     * Also updates the output text based on the chosen alternatives.
     *
     * @author Andre
     * @author Robert
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

        if (smartBoolean) { // If user has chosen smart sorting
            altChildren = smartSort(altChildren);
        }

        altsToDisplay.addAll(altChildren);// Adds relevant children Alts at the end of altsToDisplay arraylist
        // Refresh GUI with new lists
        decisionPanel.refreshDisplayedAlts(altsToDisplay);
        outputPanel.refreshOutputText(chosenAlts);
    }

    /**
     * Bubblesort algorithm that sorts Alts in descending order based on their counter values
     * Higher values places at lower indexes in childrenAlts list
     *
     * @param childrenAlts List of all children alts relevant for DecisionPanel
     * @return Sorted list
     * @author Robert
     */
    private List<Alt> smartSort(List<Alt> childrenAlts) {
        int n = childrenAlts.size();
        boolean swapped;

        do {
            swapped = false;
            for (int i = 0; i < n - 1; i++) {
                if (childrenAlts.get(i).getCounter() < childrenAlts.get(i + 1).getCounter()) {
                    Alt temp = childrenAlts.get(i);
                    childrenAlts.set(i, childrenAlts.get(i + 1));
                    childrenAlts.set(i + 1, temp);
                    swapped = true;
                }
            }
            n--;
        } while (swapped);

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
                setSmartSorting();
                break;
            case PREVIEW:
                setPreviewMode();
                break;
            case THEME:
                setTheme();
                break;
            default:
                System.out.println("Error in buttonPressed method");
        }
    }

    /**
     * Toggles smart sorting on/off and refreshes DecisionPanel
     *
     * @author Robert
     */
    private void setSmartSorting() {
        smartBoolean = !smartBoolean; // Flips boolean

        if (currentLevel > 0) { // Refreshes buttons to display if alts have already been chosen
            refreshListToDisplay();
        } else { // Reads level 0 alts if no alts have been chosen
            initialState();
        }
    }

    /**
     * Switches color-theme between light and dark mode and refreshes DecisionPanel
     *
     * @author Robert
     */
    private void setTheme() {
        themeBoolean = !themeBoolean; // Flips boolean

        if (themeBoolean) {
            outputPanel.setLightMode();
            buttonPanel.setLightMode();
            decisionPanel.setLightMode();
            settingsFrame.setLightMode();
        } else {
            outputPanel.setDarkMode();
            buttonPanel.setDarkMode();
            decisionPanel.setDarkMode();
            settingsFrame.setDarkMode();
        }

        if (currentLevel > 0) { // Refreshes buttons to display if alts have already been chosen
            refreshListToDisplay();
        } else { // Reads level 0 alts if no alts have been chosen
            initialState();
        }
    }

    /**
     * Flips boolean and turns on preview mode in DecisionPanel
     *
     * @author Robert
     */
    private void setPreviewMode() {
        previewBoolean = !previewBoolean;
        decisionPanel.setPreview(previewBoolean);
    }

    /**
     * Opens the settings menu by initializing and displaying a SettingsFrame.
     * The settings menu allows the user to configure various options such as
     * smartBoolean, previewBoolean, and themeBoolean.
     *
     * @author Robbert
     */
    private void settingsMenu() {
        settingsFrame = new SettingsFrame(this, smartBoolean, previewBoolean, themeBoolean);
    }

    /**
     * Adds a new alternative.
     *
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
     * Presents a dialog to the user with a list of alternatives
     * and allows to choose a parent and child to the new alternative .
     * Returns the selected alternatives as a list.
     *
     * @param message    The message to display in the dialog.
     * @param candidates The list of Alt objects to choose from.
     * @return A list of Alt objects that were selected by the user.
     * If no selection is made or the candidates list is empty, an empty list is returned.
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
     *
     * @author Andre
     * @author Robert
     */
    private void copyToClipboard() {
        String output = outputPanel.getText();
        if (!(output.isEmpty())) { //If output is not empty output is copied to clipboard
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
            if (--currentLevel == 0) { //Bug fix, not pretty
                initialState();
            } else {
                //Undo chosen state for last chosen alt then remove it from history of chosen alts.
                chosenAlts.removeLast().setChosen(false);
                refreshListToDisplay();
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "No alternative has been chosen yet!", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Resets the decision tree.
     *
     * @author Andre
     * @author Robert
     */
    private void resetTree() {
        int choice = JOptionPane.showConfirmDialog(mainFrame, "Are you sure?", "Reset", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            updateAltTree();
            initialState();
        }
    }

    /**
     * If the user has chosen an Alt before Resetting, this method sets the boolean "chosen" to false for that Alt
     * and increases the counter of that Alt.
     * Additionally, this method saves the AltTree to filepath to preserve the updated counters in the Alt class
     *
     * @author Robert
     */
    private void updateAltTree() {
        if (!chosenAlts.isEmpty()) { // If an Alt has been chosen all chosen Alts chosen will be set to false
            for (Alt alt : chosenAlts) {
                alt.setChosen(false);
                alt.increaseCounter(); // Increases alt counter by +1
            }
            altTree.saveAltTreeToFile(filePath); // Saves updated Alt-counters in AltTree to filepath
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
            SwingUtilities.invokeLater(() -> {
                UserModel userModel = new UserModel();
                LoginV loginView = new LoginV();
                Controller controller = new Controller(userModel, loginView);
            });
        }
    }
}

