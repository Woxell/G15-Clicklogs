package Controller;

import java.util.List;

import Model.Alt;
import Model.AltTree;
import View.AdminUI;

/**
 * This class handles the admin-tasks for creating and editing decision tree files.
 *
 * @author André Woxell
 */
public class AdminController {

    private AltTree altTree;
    private AdminUI ui;
    private String filePath = "./src/Data/AltTree.dat";

    /**
     * Constructor
     *
     * @author André Woxell
     */
    public AdminController() {
        ui = new AdminUI();
        firstPrompt();
        ui.closeScanner();
    }

    /**
     * Method for handling user input at first stage.
     *
     * @author André Woxell
     */
    public void firstPrompt() {
        boolean stillChoosing = true;
        while (stillChoosing) {
            int choice = ui.askUserInt("""
                    Load existing file or create new?
                    1: Edit existing file
                    2: Create new file
                    """);
            switch (choice) {
                case 1:
                    // TODO: method to edit existing file
                    stillChoosing = false;
                    break;
                case 2:
                    buildNewTree(ui.askUserInt("How many levels will the tree have?"));
                    stillChoosing = false;
                    break;
                default:
                    ui.invalidInput();
            }
        }
    }

    /**
     * This method is an iterative process for creating and building a decision tree with
     * alternatives and how they are related to each other. The number of iterations is
     * determined by how many levels there are in the decision tree.
     *
     * @param maxLevels
     * @author André Woxell
     */
    public void buildNewTree(int maxLevels) {
        altTree = new AltTree(maxLevels);
        int level = 0;

        while (level < maxLevels) {
            ui.print("-----------------------------------\nAdding alternative for level " + level);
            String labelText = ui.askUserString("What to write on the alternative button?");
            String outputText = ui.askUserString("What text will this alternative write in output?");
            Alt newAlt = new Alt(labelText, outputText);

            try {
                if (level > 0) {
                    List<Alt> potentialParents = altTree.getAltsAtLevel(level - 1);
                    ui.printAltList(potentialParents);
                    String parents = ui.askUserString("Which of these will be its parents? List them separated by comma (e.g. \"1,4,5\")");
                    String[] splitParents = parents.split(",");
                    for (String index : splitParents) {
                        newAlt.addParent(potentialParents.get(Integer.parseInt(index)));
                    }
                }
            } catch (Exception e) {
                ui.print("Something went wrong, please retry creating last alternative...");
                continue;
            }
            altTree.addAlt(level, newAlt);

            // Incrementer
            if (level == maxLevels - 1) {
                level = ui.askUserBoolean("Max level. Finished with all alternatives? (y/n): ") ? level + 1 : level;
            } else {
                level = ui.askUserBoolean("Continue to next level? (y/n): ") ? level + 1 : level;
            }
        }
        altTree.saveAltTreeToFile(filePath);
    }

    /**
     * Main method for running AdminController class.
     *
     * @author André Woxell
     */
    public static class AdminMain {
        public static void main(String[] args) {
            new AdminController();
        }
    }
}
