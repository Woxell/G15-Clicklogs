package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import Controller.Controller;
import Model.Alt; //VIOLATES MVC

/**
 * This class represents a panel for displaying decision alternatives.
 *
 * @author Andre, Robert and Mohamad
 */
public class DecisionPanel extends JPanel {
    private Controller controller;
    private double ratio = 0.5;

    /**
     * Constructor to create a DecisionPanel object.
     *
     * @param mainPanel  The main panel.
     * @param controller The controller.
     * @author Andre
     */
    public DecisionPanel(MainPanel mainPanel, Controller controller) {
        int height = (int) (mainPanel.getHeight() * ratio);
        this.controller = controller;
        setLayout(new FlowLayout());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(1, height));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Handle resize event to make size of this panel dynamic
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int height = (int) (mainPanel.getHeight() * ratio); // Calculate the new height
                setPreferredSize(new Dimension(1, height));
                revalidate(); // Revalidate the layout to reflect the changes
            }
        });

        setVisible(true);
    }

    /**
     * Refreshes the displayed alternatives on the panel.
     *
     * @param altsToDisplay The list of alternatives to display.
     * @author Andre and Robert
     */
    public void refreshDisplayedAlts(List<Alt> altsToDisplay) {
        removeAll();
        for (Alt alt : altsToDisplay) {
            JButton altButton = new JButton(alt.getAltLabelText());
            setUpButtonStyle(altButton);
            altButton.setToolTipText(alt.getOutputText());
            if (alt.isChosen()) {
                altButton.setBackground(Color.BLACK);
                altButton.removeMouseListener(altButton.getMouseListeners()[1]);
            }
            altButton.setEnabled(!alt.isChosen()); // Disables button if Alt has been chosen
            altButton.addActionListener(listener -> controller.altPressed(alt));
            add(altButton);
        }
        revalidate();
        repaint(); // fixes glitch for some reason
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
