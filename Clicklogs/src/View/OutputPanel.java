package View;

import javax.swing.*;
import java.awt.*;

public class OutputPanel extends JPanel {
    private MainPanel mainPanel;
    private int width;
    private int height;
    private JTextArea textArea;

    public OutputPanel(MainPanel mainPanel, int width, int height) {
        this.height = height - 600;
        this.width = width;
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());
        setBackground(Color.PINK);
        //setPreferredSize(new Dimension(400,300));
        add(new JLabel("Texten nedan genereras baserat på dina klickade alternativ ovan"), BorderLayout.NORTH);


        textArea = new JTextArea();
        textArea.setEditable(true);
        textArea.setBackground(Color.PINK);
        textArea.setText("denna text har genererats (hårdkodats men ja)");
        add(textArea, BorderLayout.CENTER);
        /*JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);*/
    }

    public void updateGeneratedText(String generatedText) {
        textArea.setText(generatedText);
    }
}





