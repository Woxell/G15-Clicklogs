package View;

import Controller.Controller;
import Model.Alt;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.List;

public class OutputPanel extends JPanel {
    private MainPanel mainPanel;
    private Controller controller;
    private JTextArea textArea;

    public OutputPanel(MainPanel mainPanel, Controller controller) {
        this.mainPanel = mainPanel;
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(Color.PINK);
        add(new JLabel("Texten nedan genereras baserat på dina klickade alternativ ovan"), BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setBorder(new MatteBorder(2,2,2,2, Color.PINK)); // Stroke around text area
        textArea.setBackground(Color.WHITE);
        textArea.setLineWrap(true); // Make sure text area uses line breaks
        textArea.setWrapStyleWord(true); // Make the line breaks at spaces to not break up words
        add(textArea, BorderLayout.CENTER);
        /*JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);*/
    }

    //DEMO
    public void refreshOutputText(List<Alt> altList) {
        StringBuilder generatedText = new StringBuilder();
        for (Alt alt : altList) {
            generatedText.append(alt.getOutputText()).append(" ");
        }
        textArea.setText(String.valueOf(generatedText));
    }
}





