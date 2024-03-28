package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OutputPanel extends JPanel {
    private MainPanel mainPanel;
    private JTextArea textArea;

    public OutputPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
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





