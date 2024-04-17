package View;

import javax.swing.*;
import Controller.Controller;

import java.awt.*;


public class MainFrame extends JFrame {

    public MainFrame(Controller controller, int width, int height){
        setSize(width, height);
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Clicklogs");
        setResizable(true);
        MainPanel mainPanel = new MainPanel(this, controller, width, height);
        add(mainPanel);

        setVisible(true);
    }
}
