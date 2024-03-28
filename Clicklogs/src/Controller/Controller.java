package Controller;

import View.ButtonType;
import View.DecisionPanel;
import View.MainFrame;

public class Controller {

    private DecisionPanel decisionPanel;

    public Controller(){
        new MainFrame(this, 900,700);
        System.out.println("Dis new mainframe");
    }

    public void buttonPressed(ButtonType pressedButton) {
        switch(pressedButton){
            case ADD:
                System.out.println("Add button pressed");
                decisionPanel.addAltButton("Alternative");
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
    public void addDecisionPanelInstance(DecisionPanel decisionPanel){
        this.decisionPanel = decisionPanel;
    }
}
