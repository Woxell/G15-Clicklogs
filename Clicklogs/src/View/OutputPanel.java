package View;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import Model.Alt;

/**
 * Adds a panel for displaying output text.
 *
 * @author Andre
 * @author Robert
 * @author Mohamad
 * @author Isra
 */
public class OutputPanel extends JPanel {

    private JLabel separator;
    private JLabel panelTitel;
    private JTextArea textArea;

    /**
     * Constructor for OutputPanel class
     *
     * @author Andre
     */
    public OutputPanel() {

        setUp();
    }

    /**
     * Initialises separator, panelTitel and textArea, sets up the style and adds them to OutputPanel.
     *
     * @author Andre and Mohamad
     */
    private void setUp() {

        setLayout(new BorderLayout());

        separator = new JLabel();
        separator.setOpaque(true);
        separator.setPreferredSize(new Dimension(100, 6));
        add(separator, BorderLayout.NORTH);

        panelTitel = new JLabel("Din logg:");
        panelTitel.setOpaque(true);
        panelTitel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        add(panelTitel, BorderLayout.CENTER);

        textArea = new JTextArea();
        textArea.setLineWrap(true); // Make sure text area uses line breaks
        textArea.setWrapStyleWord(true); // Make the line breaks at spaces to not break up words
        textArea.setFont(new Font("Arial", Font.BOLD, 15)); // Set font
        textArea.setPreferredSize(new Dimension(100, 125));
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        add(textArea, BorderLayout.SOUTH);

        // Sets darkmode as initial theme
        setDarkMode();
    }

    /**
     * Refreshes the output text displayed in the panel based on the provided list of alternatives.
     *
     * @param altList the list of alternatives to generate output text from
     * @author Andre
     */
    public void refreshOutputText(List<Alt> altList) {

        StringBuilder generatedText = new StringBuilder();
        for (Alt alt : altList) {
            generatedText.append(alt.getOutputText()).append(" ");
        }
        textArea.setText(String.valueOf(generatedText));
    }

    /**
     * Gets the text displayed in the output panel.
     *
     * @return the text displayed in the text area.
     * @author Andre
     */
    public String getText() {

        return textArea.getText();
    }

    /**
     * Sets darkmode
     *
     * @author Robert
     */
    public void setDarkMode() {

        separator.setBackground(new Color(0xCF31121E, true));
        panelTitel.setBackground(Color.decode("#191919"));
        panelTitel.setForeground(Color.decode("#c5c5c5"));
        textArea.setBackground(Color.decode("#191919"));
        textArea.setForeground(Color.decode("#c5c5c5"));
    }

    /**
     * Sets lightmode
     *
     * @author Robert
     */
    public void setLightMode() {

        separator.setBackground(Color.LIGHT_GRAY);
        panelTitel.setBackground(Color.WHITE);
        panelTitel.setForeground(Color.BLACK);
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.BLACK);
    }
}