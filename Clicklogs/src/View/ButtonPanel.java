package View;

import javax.swing.*;
import java.awt.*;

/**
 * Adds panel with buttons to MainPanel
 * Button functionalities include: Add, Copy, Reset and Undo
 * @author Andre, Robert (and Zahra?)
 */
public class ButtonPanel extends JPanel {
    private MainPanel mainPanel;
    private int width = 500; // minimum width?
    private int height = 50; // always same height

    /**
     * Constructor for ButtonPanel class
     * @param mainPanel MainPanel
     * @author Andre (and Zahra?)
     */
    public ButtonPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(width, height));
        setUp();
        setVisible(true);
    }

    /**
     * Initialises Add, Undo, Reset and Copy buttons and adds them to ButtonPanel
     * @author Andre, Robert (and Zahra?)
     */
    private void setUp() {
        JButton copy = new JButton("COPY");
        copy.setSize(width / 3, 20);
        copy.setLocation(0, height - 50);
        copy.addActionListener(listener -> buttonPressed(ButtonType.COPY));

        JButton undo = new JButton("UNDO");
        undo.setSize(width / 3, 30);
        undo.setLocation(width / 3, height - 50);
        undo.addActionListener(listener -> buttonPressed(ButtonType.UNDO));

        JButton reset = new JButton("RESET");
        reset.setSize(width / 3, 30);
        reset.setLocation((width / 3) * 2, height - 50);
        reset.addActionListener(listener -> buttonPressed(ButtonType.RESET));

        JButton addAlt = new JButton("ADD");
        addAlt.setEnabled(true);
        addAlt.setSize(width / 3, 30);
        addAlt.setLocation(width - 50, (height / 3) * 3);
        addAlt.addActionListener(listener -> buttonPressed(ButtonType.ADD));

        add(copy);
        add(undo);
        add(reset);
        add(addAlt);
    }

    /**
     * Function is called when actionListeners for Add, Undo, Reset or Copy are triggered
     * @param button Which button was pressed
     * @author Robert
     */
    private void buttonPressed(ButtonType button){
        mainPanel.buttonPressed(button);
    }
}
