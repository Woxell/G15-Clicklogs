package Controller;

import View.ButtonType;
import View.MainFrame;

public class Controller {

    public Controller(){
        new MainFrame(this, 900,700);
        System.out.println("Dis new mainframe");
    }

    public void buttonPressed(ButtonType pressedButton) {
    }
}
