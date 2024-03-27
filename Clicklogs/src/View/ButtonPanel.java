package View;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {
    private JButton undo, copy, reset, addAlt;
    private JList<Object> southPanelList;
    private MainFrame mainFrame;
    private MainPanel mainPanel;

    // private JLabel titleLeftPanel;
    private int width = 500; //minimum width?
    private int height = 50; //always same height

    public ButtonPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        this.setLayout(new FlowLayout());
        setPreferredSize(new Dimension(width, height));
        setUp();
        setVisible(true);
    }

    private void setUp() {
        southPanelList = new JList<>();
        // southPanelList.setLocation(0, 50);
        southPanelList.setSize(width, height - 100);
        this.add(southPanelList);


        copy = new JButton("COPY");
        copy.setEnabled(true);
        copy.setSize(width / 3, 20);
        copy.setLocation(0, height - 50);
        // copy.addActionListener(mainFrame.buttonPressed(ButtonType.COPY));
        this.add(copy);


        undo = new JButton("UNDO");
        undo.setEnabled(true);
        undo.setSize(width / 3, 30);
        undo.setLocation(width / 3, height - 50);
        // undo.addActionListener(mainFrame.buttonPressed(ButtonType.UNDO));
        this.add(undo);


        reset = new JButton("RESET");
        reset.setEnabled(true);
        reset.setSize(width / 3, 30);
        reset.setLocation((width / 3) * 2, height - 50);
        //reset.addActionListener(mainFrame.buttonPressed(ButtonType.RESET));
        this.add(reset);


        addAlt = new JButton("ADD");
        addAlt.setEnabled(true);
        addAlt.setSize(width / 3, 30);
        addAlt.setLocation(width - 50, (height / 3) * 3);
        // addAlt.addActionListener(mainFrame.buttonPressed(ButtonType.ADD));
        this.add(addAlt);
    }

    protected JList<Object> getRightPanelList() {
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
