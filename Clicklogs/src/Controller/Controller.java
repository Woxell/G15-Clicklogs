package Controller;

import View.ButtonType;
import View.MainFrame;

public class Controller {

    public Controller(){
        new MainFrame(this, 900,700);
    }

    public void buttonPressed(ButtonType pressedButton) {
    }
}
