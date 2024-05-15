package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.Alt;
import Model.AltTree;
import Model.UserModel;
import View.*;

/**
 * Controller class, acts as intermediate between View and Model classes
 * @author Andre, Mohamad, Robert, Zahra, Isra
 */
public class Controller {

    private DecisionPanel decisionPanel;
    private OutputPanel outputPanel;
    private ButtonPanel buttonPanel;
    private MainFrame mainFrame; // parent component for JOptionPanes
    private AltTree altTree;
    private final String filePath = "./src/Data/AltTree.dat";// Make sure WORKING DIRECTORY is set to "...\G15-Clicklogs\Clicklogs\"
    private int currentLevel = 0;
    private List<Alt> chosenAlts;
    private UserModel userModel;

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



        //Visa inloggningsskärmen först
        loginV.show();

        // Lyssna på händelser för inloggning och visa MainFrame vid framgångsrik inloggning
        loginV.setLoginButtonListener(e -> {
            String username = loginV.getUsername();
            String password = loginV.getPassword();
            if (userModel.authenticate(username, password)) {
               // loginV.showSuccess("Login successful!");
                loginV.hide();

                //Skapa en instans av MainFrame och visa den
                MainFrame mainFrame = new MainFrame(this,300,400);
                initialState();
            } else {
                loginV.showError("Invalid username or password.");
            }
        });

    }


    /**
     * (Re)sets variables. Also updates gui to a clear output field and level 0 alts
     *
     * @author Andre, Robert
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
     *
     * @author Andre
     */
    private void refreshListToDisplay() {
        // Build list for display in GUI. Should be chosen alts + alts in next level
        List<Alt> altsToDisplay = new ArrayList<>(chosenAlts); // Start with chosen alts
        if (currentLevel < altTree.getMaxLevels()) { // Guard against end of decision tree
            List<Alt> nextLevelAlts = altTree.getAltsAtLevel(currentLevel); // Get all alt candidates in next level
            for (Alt nextLevelAlt : nextLevelAlts) {
                if (currentLevel > 0) { // Avoids parent-lookup for level 0
                    List<Alt> parents = nextLevelAlt.getAllParents(); // For each candidate, get all parents
                    for (Alt p : parents) { // ... traverse parents
                        if (p.isChosen()) {
                            altsToDisplay.add(nextLevelAlt); // ... and only add candidate if one of its parents was chosen.
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
     * @author Andre, Robert
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
     */
    private void resetTree() {
        int choice = JOptionPane.showConfirmDialog(mainFrame, "Are you sure?",
                "Reset", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            initialState();
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