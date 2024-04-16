package View;

import javax.swing.*;
import Controller.Controller;

import java.awt.*;


public class MainFrame extends JFrame {

    private Controller controller;
    private MainPanel mainPanel;
    public MainFrame(Controller controller, int width, int height){
        this.controller = controller;
        setSize(width, height);
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Clicklogs");
        setResizable(true); // se kravdokument
        mainPanel = new MainPanel(this, controller, width, height);
        add(mainPanel);

        setVisible(true);
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void buttonPressed(ButtonType pressedButton){
        controller.buttonPressed(pressedButton);
    }

}
