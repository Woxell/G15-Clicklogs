package View;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.List;

import Controller.Controller;
import Model.Alt; //VIOLATES MVC

public class OutputPanel extends JPanel {
    private JTextArea textArea;

    public OutputPanel() {
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

    public void refreshOutputText(List<Alt> altList) {
        StringBuilder generatedText = new StringBuilder();
        for (Alt alt : altList) {
            generatedText.append(alt.getOutputText()).append(" ");
        }
        textArea.setText(String.valueOf(generatedText));
    }

    public String getText() {
        return textArea.getText();
    }
}





