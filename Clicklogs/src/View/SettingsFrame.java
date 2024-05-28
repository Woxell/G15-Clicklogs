package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creates a Popup menu for system settings
 * @author Robert
 */
public class SettingsFrame {

    private JPanel settingsPanel;
    private JCheckBox smartCheckBox;
    private JCheckBox previewCheckBox;
    private JCheckBox themeCheckBox;
    private JButton closeButton;
    private Boolean lightMode = false;

    /**
     * Constructor
     * @author Robert
     */
    public SettingsFrame(Controller controller, Boolean isSmart, Boolean preview, Boolean lightMode){
        setUp(controller, isSmart, preview, lightMode);
    }

    /**
     * Sets up Settings frame
     * @param controller
     * @param isSmart vital for smartCheckBox
     * @param preview vital for previewCheckBox
     * @param lightMode vital for themeCheckBox
     * @author Robert
     */
    private void setUp(Controller controller, Boolean isSmart, Boolean preview, Boolean lightMode){
        // Creating the Settings Frame
        // TODO: Change colors to a darker mode
        JFrame settingsFrame = new JFrame("System Settings");
        settingsFrame.setDefaultCloseOperation(settingsFrame.EXIT_ON_CLOSE);
        settingsFrame.setSize(250, 300);
        settingsFrame.setLayout(new BorderLayout());
        //settingsFrame.setUndecorated(true); // Removes close, min and max buttons in top right corner of frame
        settingsFrame.setLocationRelativeTo(null);

        // Creating panel for Frame
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout (3, 1));

        // Creating checkbox for User to choose "smart" sorting of alts
        smartCheckBox = new JCheckBox("Enable Smart Sorting");
        smartCheckBox.setSelected(isSmart); // If a user has already enabled smart sorting this will be shown in menu
        smartCheckBox.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        //smartCheckBox.setForeground(Color.WHITE); // Set font color
        smartCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.buttonPressed(ButtonType.SMART);
            }
        });

        previewCheckBox = new JCheckBox("Enable Preview Mode");
        previewCheckBox.setSelected(preview); // If a user has already enabled smart sorting this will be shown in menu
        previewCheckBox.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        //previewCheckBox.setForeground(Color.WHITE); // Set font color
        previewCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.buttonPressed(ButtonType.PREVIEW); // For demo day, removes warning
/*
                if(preview == false && previewCheckBox.isSelected()){ // Informs user of issues before buttonpressed is called
                    int choice = JOptionPane.showConfirmDialog(null, "Preview mode may cause performance issues," +
                            " are you sure you want to proceed?", "Preview mode", JOptionPane.YES_NO_OPTION);


                    *if (choice == JOptionPane.YES_NO_OPTION){
                        controller.buttonPressed(ButtonType.PREVIEW);
                    }else {
                        previewCheckBox.setSelected(false);
                    }


                }else {
                    controller.buttonPressed(ButtonType.PREVIEW);
                }
*/
            }

        });

        // Creating checkbox for user to change colortheme of UI
        themeCheckBox = new JCheckBox("Enable Light mode");
        themeCheckBox.setSelected(lightMode); // If a user has already enabled smart sorting this will be shown in menu
        themeCheckBox.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        //themeCheckBox.setForeground(Color.WHITE); // Set font color
        themeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.buttonPressed(ButtonType.THEME);
            }
        });

        // Creating a close button for menu
        closeButton = new JButton("Close Settings");
        setUpButtonStyle(closeButton);
        closeButton.addActionListener(e -> settingsFrame.dispose());

        settingsPanel.add(smartCheckBox);
        settingsPanel.add(previewCheckBox);
        settingsPanel.add(themeCheckBox);
        //settingsPanel.setBackground(Color.DARK_GRAY);

        settingsFrame.add(settingsPanel, BorderLayout.CENTER);
        settingsFrame.add(closeButton, BorderLayout.SOUTH);
        settingsFrame.setVisible(true);

        setDarkMode();
    }


    /**
     * Sets up the style for buttons.
     *
     * @param button The button to set up the style for
     * @author Mohamad
     * @author Robert
     */
    public void setUpButtonStyle(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        // Set Hover-highlighting
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (lightMode){
                    button.setBackground(Color.LIGHT_GRAY);
                }else {
                    button.setBackground(Color.decode("#4d4d4d"));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (lightMode){
                    button.setBackground(Color.WHITE);

                }else {
                    button.setBackground(Color.decode("#191919"));
                }
            }
        });
    }

    /**
     * Sets darkmode
     * @author Robert
     */
    public void setDarkMode(){
        lightMode = false;

        // Sets backgroundcolor
        settingsPanel.setBackground(Color.DARK_GRAY);
        closeButton.setBackground(new Color(0xFF181818, true));

        // Sets font color
        closeButton.setForeground(Color.WHITE);
        smartCheckBox.setForeground(Color.WHITE);
        previewCheckBox.setForeground(Color.WHITE);
        themeCheckBox.setForeground(Color.WHITE);
    }

    /**
     * Sets light
     * @author Robert
     */
    public void setLightMode(){
        lightMode = true;

        // Sets backgroundcolor
        settingsPanel.setBackground(Color.LIGHT_GRAY);
        closeButton.setBackground(Color.WHITE);

        // Sets font color
        closeButton.setForeground(Color.BLACK);
        smartCheckBox.setForeground(Color.BLACK);
        previewCheckBox.setForeground(Color.BLACK);
        themeCheckBox.setForeground(Color.BLACK);
    }
}

