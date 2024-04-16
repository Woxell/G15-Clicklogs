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
        /*for(int i = 0; i < altTree.size(); i++){
            List<Alt> alts = altTree.getAltsAtLevel(i);
            for (Alt alt : alts) {
                System.out.println("Level " + i + ": " + alt.getAltLabelText());
            }
        }*/
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
        if (++currentLevel == altTree.getMaxLevels()) {
            System.out.println("Reached end of decision tree!");
            return;
        }

        chosenAlts.add(alt);
        for (int i = 0; i < chosenAlts.size(); i++){
            if(!(chosenAlts.get(i).isChosen())){
                chosenAlts.remove(i);
            }
        }
        List<Alt> newLevelAlts = altTree.getAltsAtLevel(currentLevel);
        List<Alt> altsToDisplay = new ArrayList<>(chosenAlts);
        for(Alt a : newLevelAlts){
            List<Alt> parents = a.getParents();
            for(Alt p : parents){
                if(p.isChosen() && p.equals(alt)){
                    altsToDisplay.add(a);
                }
            }
            /*if(a.getParent().equals(alt)){
                altsToDisplay.add(a);
            }*/
        }
        


        for (Alt a : altsToDisplay) {
            System.out.println( ": " + a.getAltLabelText());
        }
    }

    public void addPanelInstances(DecisionPanel dp, OutputPanel op, ButtonPanel bp) {
        decisionPanel = dp;
        outputPanel = op;
        buttonPanel = bp;
    }

    public void updateOutputPanel(List<Alt> chosenAlts) {
        outputPanel.updateGeneratedText(decisionPanel.getButtonList());
    }

    public static class Main {
        public static void main(String[] args) {
            new Controller();
        }
    }
}
