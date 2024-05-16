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
 * @author Andre,
 * @author Robert
 * @author Mohamad
 */
public class DecisionPanel extends JPanel {
    private Controller controller;
    private double ratio = 0.5;
    private boolean previewBoolean;
    private boolean lightMode = false;

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
        //setBackground(Color.BLACK);
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

        setDarkMode(); // Darkmode initial theme
        setVisible(true);
    }

    /**
     * Refreshes the displayed alternatives on the panel.
     *
     * @param altsToDisplay The list of alternatives to display.
     * @author Andre
     * @author Robert
     */
    public void refreshDisplayedAlts(List<Alt> altsToDisplay) {
        removeAll();
        for (Alt alt : altsToDisplay) {
            JButton altButton = new JButton(alt.getAltLabelText());
            setUpButtonStyle(altButton, alt);
            altButton.setToolTipText(alt.getOutputText());
            if (alt.isChosen()) {
                if (lightMode){
                    altButton.setBackground(Color.WHITE);
                }else {
                    altButton.setBackground(Color.BLACK);
                }
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
     * @author Robert
     */
    public void setUpButtonStyle(JButton button, Alt alt) {
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        if (lightMode){
            button.setForeground(Color.BLACK); // Set font color to Light mode
            button.setBackground(Color.WHITE);
        }else {
            button.setForeground(Color.WHITE); // Set font color to Dark mode
            button.setBackground(new Color(0xFF181818, true));
        }

        // Set Hover-highlighting
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            private JWindow previewWindow;

            public void mouseEntered(java.awt.event.MouseEvent evt) {

                // Switches highlight to lightmode if true
                if (lightMode){
                    button.setBackground(Color.LIGHT_GRAY);
                }else {
                    button.setBackground(Color.decode("#553C45"));
                }

                // Set preview window for alt output text if preview mode is enabled
                if (previewBoolean){
                    previewWindow = new JWindow();
                    previewWindow.setLayout(new FlowLayout());
                    previewWindow.add(new JLabel(alt.getOutputText()));
                    previewWindow.pack();

                    // Positioning previewWindow slightly below right corner of button
                    Point location = button.getLocationOnScreen();
                    previewWindow.setLocation(location.x + 35, location.y + 35);
                    previewWindow.setVisible(true);
                    button.setToolTipText(null);// Fixes bug where two previewWindows appear
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Switches to lightmode if true
                if (lightMode){
                    button.setBackground(Color.WHITE);
                }else {
                    button.setBackground(Color.decode("#191919"));
                }

                // Disposes previewWindow
                if (previewWindow != null){
                    previewWindow.setVisible(false);
                    previewWindow.dispose();
                }
            }
        });
    }

    /**
     * Sets previewboolean
     * @author Robert
     */
    public void setPreview(boolean previewBoolean) {
        this.previewBoolean = previewBoolean;
    }

    /**
     * Sets darkmode
     * @author Robert
     */
    public void setDarkMode(){
        lightMode = false;
        setBackground(new Color(0xFF181818, true));
    }

    /**
     * Sets lightmode
     * @author Robert
     */
    public void setLightMode(){
        lightMode = true;
        setBackground(Color.WHITE);
    }
}
