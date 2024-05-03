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
 * @author <Author Name>
 */
public class DecisionPanel extends JPanel {
    private Controller controller;
    private double ratio = 0.5;

    /**
     * Constructor to create a DecisionPanel object.
     *
     * @param mainPanel  The main panel.
     * @param controller The controller.
     * @author <Author/s Name>
     */
    public DecisionPanel(MainPanel mainPanel, Controller controller) {
        int height = (int) (mainPanel.getHeight() * ratio);
        this.controller = controller;
        setLayout(new FlowLayout());
        setBackground(Color.GRAY);
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
     * @author <Author/s Name>
     */
    public void refreshDisplayedAlts(List<Alt> altsToDisplay) {
        removeAll();
        for (Alt alt : altsToDisplay) {
            JButton altButton = new JButton(alt.getAltLabelText());
            altButton.setBackground(Color.WHITE);
            altButton.setEnabled(!alt.isChosen()); // Disables button if Alt has been chosen
            altButton.addActionListener(listener -> controller.altPressed(alt));
            add(altButton);
        }
        revalidate();
        repaint(); // fixes glitch for some reason
    }
}
