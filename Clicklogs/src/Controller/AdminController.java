package Controller;

import Model.Alt;
import Model.AltTree;
import View.AdminUI;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class AdminController {

    private AltTree altTree;
    private AdminUI ui;
    private String filePath = "./src/Data/AltTree.dat";

    public AdminController() {
        ui = new AdminUI();
        firstPrompt();

        ui.closeScanner();
    }

    public void firstPrompt() {
        boolean stillChoosing = true;
        while (stillChoosing) {
            int choice = ui.askUserInt("""
                    Load existing file or create new?
                    1: Load existing file
                    2: Create new file
                    """);
            switch (choice) {
                case 1:
                    //TODO: method to load existing file
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

            //Incrementer
            if (level == maxLevels - 1) {
                level = ui.askUserBoolean("Max level. Finished with all alternatives? (y/n): ") ? level + 1 : level;

            } else {
                level = ui.askUserBoolean("Continue to next level? (y/n): ") ? level + 1 : level;
            }
        }
        saveAltTreeToFile(altTree);
    }

    public void saveAltTreeToFile(AltTree altTree) {
        boolean saveFailed = true;
        do {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(altTree);
                saveFailed = false;
            } catch (IOException e) {
                System.out.println("Error saving to " + filePath);
                System.err.println(e);
                ui.askUserString("Enter anything to try again...");
            }
        } while (saveFailed);
        System.out.println("File saved successfully to " + filePath);
    }

    public static class AdminMain {
        public static void main(String[] args) {
            new AdminController();
        }
    }
}
