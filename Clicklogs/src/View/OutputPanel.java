package View;

import Controller.Controller;

import javax.naming.ldap.Control;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.StrokeBorder;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.plaf.metal.MetalBorders;
import java.awt.*;
import java.util.ArrayList;

public class OutputPanel extends JPanel {
    private MainPanel mainPanel;
    private Controller controller;
    private JTextArea textArea;

    public OutputPanel(MainPanel mainPanel, Controller controller) {
        this.mainPanel = mainPanel;
        this.controller = controller;
        controller.addOutputPanelInstance(this);
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
    public void updateGeneratedText(ArrayList<JButton> altList) {
        StringBuilder generatedText = new StringBuilder();
        for (JButton alt : altList) {
            generatedText.append(alt.getText()).append(" ");
        }
        textArea.setText(String.valueOf(generatedText));
    }
}





