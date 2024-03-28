package View;

import Controller.Controller;

import javax.naming.ldap.Control;
import javax.swing.*;
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
        textArea.setBackground(Color.PINK);
        textArea.setText(". . .");
        add(textArea, BorderLayout.CENTER);
        /*JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);*/
    }

    public void updateGeneratedText(ArrayList<JButton> altList) {
        StringBuilder generatedText = new StringBuilder();
        for(JButton alt : altList){
            generatedText.append(alt.getText() + " ");
        }
        textArea.setText(String.valueOf(generatedText));
    }
}





