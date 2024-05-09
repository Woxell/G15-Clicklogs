package View;

import javax.swing.*;
import java.awt.*;
/**
 *@author Zahraa
  */

public class ButtonPanel extends JPanel {
    private MainPanel mainPanel;

    // private JLabel titleLeftPanel;
    private int width = 500; //minimum width?
    private int height = 50; //always same height

    public ButtonPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(width, height));
        setUp();
        setVisible(true);
    }

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

        JButton addAlt = new JButton("ADD NEW Alternative");
        addAlt.setEnabled(true);
        addAlt.setSize(width / 3, 30);
        addAlt.setLocation(width - 50, (height / 3) * 3);
        addAlt.addActionListener(listener -> buttonPressed(ButtonType.ADD));

        add(copy);
        add(undo);
        add(reset);
        add(addAlt);
    }

    private void buttonPressed(ButtonType button){
        mainPanel.buttonPressed(button);
    }
}
