package View;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.List;

import Model.Alt; //VIOLATES MVC

/**
 * Adds a panel for displaying output text.
 *
 * @author Andre and Mohamad
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

        setLayout(new BorderLayout());
        setBackground(Color.PINK);
        add(new JLabel("Texten nedan genereras baserat på dina klickade alternativ ovan"), BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setBorder(new MatteBorder(2, 2, 2, 2, Color.PINK)); // Stroke around text area
        textArea.setBackground(Color.WHITE);
        textArea.setLineWrap(true); // Make sure text area uses line breaks
        textArea.setWrapStyleWord(true); // Make the line breaks at spaces to not break up words
        textArea.setFont(new Font("TAHOMA", Font.PLAIN,20));
        add(textArea, BorderLayout.CENTER);

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
        separator.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        separator.setBackground(new Color(0xCF31121E, true));
        separator.setPreferredSize(new Dimension(100, 6));
        add(separator, BorderLayout.NORTH);

        //panelTitel = new JLabel("Texten nedan genereras baserat på dina klickade alternativ ovan");
        panelTitel = new JLabel("Your log text:");
        panelTitel.setForeground(Color.decode("#c5c5c5")); // Set font color
        panelTitel.setOpaque(true);
        panelTitel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        panelTitel.setBackground(Color.decode("#191919"));
        add(panelTitel, BorderLayout.CENTER);

        textArea = new JTextArea();
        textArea.setBackground(Color.decode("#191919"));
        textArea.setLineWrap(true); // Make sure text area uses line breaks
        textArea.setWrapStyleWord(true); // Make the line breaks at spaces to not break up words
        //textArea.setFont(new Font("TAHOMA", Font.PLAIN,20));
        textArea.setFont(new Font("Arial", Font.BOLD, 15)); // Set font
        textArea.setForeground(Color.decode("#c5c5c5")); // Set font color
        textArea.setPreferredSize(new Dimension(100, 125));
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        add(textArea, BorderLayout.SOUTH);
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
}