package View;

import javax.swing.*;
import java.awt.*;
/**
 * The ButtonPanel class represents a panel containing buttons for various actions.
 * It is used within the main user interface to provide functionality such as copying, undoing,
 * resetting, and adding new alternatives
 * @author Zahraa alqassab
 * */

/**
 * Adds panel with buttons to MainPanel
 * Button functionalities include: Add, Copy, Reset and Undo

 *
 * @author Andre, Robert and Mohamad
 */
public class ButtonPanel extends JPanel {
    private MainPanel mainPanel;
    private int width = 500; // minimum width?
    private int height = 50; // always same height

    /**
     * Constructor for ButtonPanel class
     *
     * @param mainPanel MainPanel
     * @author Andre
     */
    public ButtonPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.decode("#191919"));
        setUp();
        setVisible(true);
    }

    /**
     * Initialises Add, Undo, Reset and Copy buttons and adds them to ButtonPanel
     *
     * @author Andre, Robert and Mohamad
     */
    private void setUp() {
        JButton copy = new JButton("COPY");
        setUpButtonStyle(copy);
        copy.setLocation(0, height - 50);
        copy.addActionListener(listener -> buttonPressed(ButtonType.COPY));

        JButton undo = new JButton("UNDO");
        setUpButtonStyle(undo);
        undo.setLocation(width / 3, height - 50);
        undo.addActionListener(listener -> buttonPressed(ButtonType.UNDO));

        JButton reset = new JButton("RESET");
        setUpButtonStyle(reset);
        reset.setLocation((width / 3) * 2, height - 50);
        reset.addActionListener(listener -> buttonPressed(ButtonType.RESET));

        JButton addAlt = new JButton("ADD");
        setUpButtonStyle(addAlt);
        addAlt.setLocation(width - 50, (height / 3) * 3);
        addAlt.addActionListener(listener -> buttonPressed(ButtonType.ADD));

        add(copy);
        add(undo);
        add(reset);
        add(addAlt);
    }

    /**
     * Sets up the style for buttons.
     *
     * @param button The button to set up the style for
     * @author Mohamad
     */
    public void setUpButtonStyle(JButton button) {
        button.setSize(width / 3, 30);
        button.setFont(new Font("Arial", Font.BOLD, 12)); // Set font
        button.setForeground(Color.decode("#c5c5c5")); // Set font color
        button.setBackground(Color.decode("#191919"));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        // Set Hover-highlighting
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#553C45"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#191919"));
            }
        });
    }

    /**
     * Function is called when actionListeners for Add, Undo, Reset or Copy are triggered
     *
     * @param button Which button was pressed
     * @author Robert
     */
    private void buttonPressed(ButtonType button){
        mainPanel.buttonPressed(button);
    }
}
