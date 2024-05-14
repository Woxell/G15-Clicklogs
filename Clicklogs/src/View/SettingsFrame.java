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

    /**
     * Constructor
     * @author Robert
     */
    public SettingsFrame(Controller controller, Boolean isSmart){
        // Creating the Settings Frame
        // TODO: Change colors to a darker mode
        JFrame settingsFrame = new JFrame("System Settings");
        settingsFrame.setDefaultCloseOperation(settingsFrame.EXIT_ON_CLOSE);
        settingsFrame.setSize(250, 300);
        settingsFrame.setLayout(new BorderLayout());

        // Creating panel for Frame
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout (0, 1));

        // Creating checkbox for User to choose "smart" sorting of alts
        JCheckBox smartSorting = new JCheckBox("Enable Smart Sorting");
        smartSorting.setSelected(isSmart); // If a user has already enabled smart sorting this will be shown in menu
        smartSorting.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        smartSorting.setForeground(Color.WHITE); // Set font color
        smartSorting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.buttonPressed(ButtonType.SMART);
            }
        });

        // Creating a close button for menu
        JButton closeButton = new JButton("Close Settings");
        setUpButtonStyle(closeButton);
        closeButton.addActionListener(e -> settingsFrame.dispose());

        settingsPanel.add(smartSorting);
        settingsPanel.setBackground(Color.BLACK);

        settingsFrame.add(settingsPanel, BorderLayout.CENTER);
        settingsFrame.add(closeButton, BorderLayout.SOUTH);
        settingsFrame.setVisible(true);
    }

    /**
     * Sets up the style for buttons.
     *
     * @param button The button to set up the style for
     * @author Mohamad
     */
    public void setUpButtonStyle(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        button.setForeground(Color.WHITE); // Set font color
        button.setBackground(new Color(0xFF181818, true));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        // Set Hover-highlighting
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#4d4d4d"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#191919"));
            }
        });
    }
}
