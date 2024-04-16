package Controller;

import Model.Alt;
import Model.AltTree;
import View.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))){
            altTree = (AltTree) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        chosenAlts = new ArrayList<>();
        decisionPanel.refreshDisplayedAlts(new ArrayList<>(altTree.getAltsAtLevel(0))); //feeds GUI with alt:s in level 0
    }

    public void buttonPressed(ButtonType pressedButton) {
        switch (pressedButton) {
            case ADD:
                System.out.println("Add button pressed");

                break;
            case COPY:
                System.out.println("Copy button pressed");
                break;
            case UNDO:
                System.out.println("Undo button pressed");
                break;
            case RESET:
                System.out.println("Reset button pressed");
                break;
            default:
                System.out.println("Error in buttonPressed method");
        }
    }

    public void altPressed(Alt alt){
        //Add alt to history of chosen alts
        chosenAlts.add(alt);
        /*for (int i = 0; i < chosenAlts.size(); i++){
            if(!(chosenAlts.get(i).isChosen())){
                chosenAlts.remove(i);
            }
        }*/

        //Guard against end of decision tree
        if (currentLevel+1 == altTree.getMaxLevels()) {
            outputPanel.refreshOutputText(chosenAlts);
            System.out.println("Reached end of decision tree!");
            return;
        }

        //Build list for display in GUI. Should be chosen alts + alts in next level
        List<Alt> altsToDisplay = new ArrayList<>(chosenAlts); //Start with chosen alts
        List<Alt> newLevelAlts = altTree.getAltsAtLevel(currentLevel+1); //Get all possible alts in next level
        for(Alt a : newLevelAlts){
            List<Alt> parents = a.getParents(); //For each candidate, get all parents
            for(Alt p : parents){ //... traverse parents
                if(p.isChosen() && p.equals(alt)){
                    altsToDisplay.add(a); //... and only add candidate if one of its parents was chosen.
                }
            }
        }

        //Final steps, increment to next level and update GUI.
        currentLevel++;
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
