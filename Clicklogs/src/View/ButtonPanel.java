package View;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {
    private JButton undo, copy, reset, addAlt;
    private JList<Object> southPanelList;
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
        southPanelList = new JList<>();
        // southPanelList.setLocation(0, 50);
        southPanelList.setSize(width, height - 100);
        add(southPanelList);

        copy = new JButton("COPY");
        copy.setEnabled(true);
        copy.setSize(width / 3, 20);
        copy.setLocation(0, height - 50);
        copy.addActionListener(listener -> buttonPressed(ButtonType.COPY));

        undo = new JButton("UNDO");
        //undo.setEnabled(true);
        undo.setEnabled(true); //UNTIL THIS FUNCTIONALITY HAS BEEN ADDED
        undo.setSize(width / 3, 30);
        undo.setLocation(width / 3, height - 50);
        undo.addActionListener(listener -> buttonPressed(ButtonType.UNDO));

        reset = new JButton("RESET");
        reset.setEnabled(true);
        reset.setSize(width / 3, 30);
        reset.setLocation((width / 3) * 2, height - 50);
        reset.addActionListener(listener -> buttonPressed(ButtonType.RESET));

        addAlt = new JButton("ADD");
        //addAlt.setEnabled(true);
        addAlt.setEnabled(false); //UNTIL THIS FUNCTIONALITY HAS BEEN ADDED
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
    protected JList<Object> getSouthPanelList() {
        return southPanelList;
    }

    protected JButton getUndo() {
        return undo;
    }

    protected JButton getCopy() {
        return copy;
    }

    protected JButton getReset() {
        return reset;
    }

    protected JButton getAddAlt() {
        return addAlt;
    }

}
