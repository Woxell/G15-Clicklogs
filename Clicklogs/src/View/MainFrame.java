package View;

import javax.swing.*;
import java.awt.*;
import Controller.Controller;

/**
 * @author André Woxell
 */
public class MainFrame extends JFrame {

    /**
     * Constructor.
     * @author André Woxell
     */
    public MainFrame(Controller controller, int width, int height) {

        setSize(width, height);
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Clicklogs");
        setResizable(true);
        MainPanel mainPanel = new MainPanel(this, controller);
        add(mainPanel);
        setVisible(true);
    }
}
