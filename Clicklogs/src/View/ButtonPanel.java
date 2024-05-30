package View;

import javax.swing.*;
import java.awt.*;

/**
 * The ButtonPanel class represents a panel containing buttons for various actions.
 * It is used within the main user interface to provide functionality such as copying, undoing,
 * resetting, and adding new alternatives
 * @author Zahraa alqassab
 * @author Andre
 * @author Robert
 * @author Mohamad
 */
public class ButtonPanel extends JPanel {

    private MainPanel mainPanel;
    private int width = 500; // Minimum width?
    private int height = 50; // Always same height
    private JButton copy;
    private JButton undo;
    private JButton addAlt;
    private JButton reset;
    private JButton settings;
    private Boolean lightMode = false;

    /**
     * Constructor for ButtonPanel class
     * @param mainPanel MainPanel
     * @author Zahraa
     * @author Robert
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
     * @author Andre
     * @author Robert
     * @author Mohamad
     * @author Zahraa
     */
    private void setUp() {

        copy = new JButton("COPY");
        setUpButtonStyle(copy);
        copy.setLocation(0, height - 50);
        copy.addActionListener(listener -> buttonPressed(ButtonType.COPY));

        undo = new JButton("UNDO");
        setUpButtonStyle(undo);
        undo.setLocation(width / 3, height - 50);
        undo.addActionListener(listener -> buttonPressed(ButtonType.UNDO));

        reset = new JButton("RESET");
        setUpButtonStyle(reset);
        reset.setLocation((width / 3) * 2, height - 50);
        reset.addActionListener(listener -> buttonPressed(ButtonType.RESET));

        addAlt = new JButton("ADD");
        setUpButtonStyle(addAlt);
        addAlt.setLocation(width - 50, (height / 3) * 3);
        addAlt.addActionListener(listener -> buttonPressed(ButtonType.ADD));

        settings = new JButton(" ⚙ ");
        setUpButtonStyle(settings);
        settings.setLocation(width - 50, (height / 3) * 3);
        settings.addActionListener(listener -> buttonPressed(ButtonType.SETTINGS));

        setDarkMode(); // Darkmode initial theme

        add(copy);
        add(undo);
        add(reset);
        add(addAlt);
        add(settings);
    }

    /**
     * Sets up the style for buttons.
     * @param button The button to set up the style for
     * @author Mohamad
     * @author Robert
     */
    public void setUpButtonStyle(JButton button) {

        button.setSize(width / 3, 30);
        button.setFont(new Font("Arial", Font.BOLD, 12)); // Set font
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        // Set Hover-highlighting
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {

                // Switches to lightmode if true
                if (lightMode){
                    button.setBackground(Color.LIGHT_GRAY);
                }else {
                    button.setBackground(Color.decode("#553C45"));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {

                // Switches to lightmode if true
                if (lightMode){
                    button.setBackground(Color.WHITE);
                }else {
                    button.setBackground(Color.decode("#191919"));
                }
            }
        });
    }

    /**
     * Function is called when actionListeners for Add, Undo, Reset, Settings or Copy are triggered
     * @param button Which button was pressed
     * @author Zahraa
     */
    private void buttonPressed(ButtonType button){
        mainPanel.buttonPressed(button);
    }

    /**
     * Sets darkmode
     * @author Robert
     */
    public void setDarkMode(){

        lightMode = false;
        setBackground(Color.decode("#191919"));
        copy.setBackground(Color.decode("#191919"));
        undo.setBackground(Color.decode("#191919"));
        reset.setBackground(Color.decode("#191919"));
        addAlt.setBackground(Color.decode("#191919"));
        settings.setBackground(Color.decode("#191919"));

        copy.setForeground(Color.decode("#c5c5c5"));
        undo.setForeground(Color.decode("#c5c5c5"));
        reset.setForeground(Color.decode("#c5c5c5"));
        addAlt.setForeground(Color.decode("#c5c5c5"));
        settings.setForeground(Color.decode("#c5c5c5"));
    }

    /**
     * Sets lightmode
     * @author Robert
     */
    public void setLightMode(){

        lightMode = true;
        setBackground(Color.WHITE);
        copy.setBackground(Color.WHITE);
        undo.setBackground(Color.WHITE);
        reset.setBackground(Color.WHITE);
        addAlt.setBackground(Color.WHITE);
        settings.setBackground(Color.WHITE);

        copy.setForeground(Color.BLACK);
        undo.setForeground(Color.BLACK);
        reset.setForeground(Color.BLACK);
        addAlt.setForeground(Color.BLACK);
        settings.setForeground(Color.BLACK);
    }
}
