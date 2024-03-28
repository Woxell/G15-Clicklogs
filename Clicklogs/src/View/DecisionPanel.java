package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import Controller.Controller;


public class DecisionPanel extends JPanel {
    private JLabel jLabel;
    private MainPanel mainPanel;
    private Controller controller;
    private ArrayList<JButton> altList = new ArrayList<>();


    public DecisionPanel(MainPanel mainPanel, Controller controller) {
        int height = (int) (mainPanel.getHeight() * 0.7);
        this.mainPanel = mainPanel;
        this.controller = controller;
        setLayout(new FlowLayout());
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(1, height));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        controller.addDecisionPanelInstance(this);

        //Test buttons
        for (int i = 1; i <= 2; i++) {
            if (i > 1) {
                add(new JLabel("->"));
            }
            JButton altButton = new JButton();
            altButton.setText("Alternative " + i);
            altButton.setBackground(Color.WHITE);
            int buttonNumber = altList.size();
            altButton.addActionListener(listener -> buttonPressed(buttonNumber));
            altList.add(altButton);
            add(altList.getLast());
        }

        /*jLabel = createNicelabel("DecisionPanel", true);
        add(jLabel);*/

        // Handle resize event to make size of this panel dynamic
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int height = (int) (mainPanel.getHeight() * 0.7); // Calculate the new height
                setPreferredSize(new Dimension(1, height));
                revalidate(); // Revalidate the layout to reflect the changes
            }
        });


        setVisible(true);
    }

    public void addAltButton(String altText) {
        add(new JLabel("->"));
        JButton altButton = new JButton();
        altButton.setText(altText + " " + (altList.size()+1));
        altButton.setBackground(Color.WHITE);
        int buttonNumber = altList.size();
        altButton.addActionListener(listener -> buttonPressed(buttonNumber));
        altList.add(altButton);
        add(altList.getLast());
        revalidate();
    }
    private void buttonPressed(int altNumber){
        controller.buttonPressed(ButtonType.ADD);
        altList.get(altNumber).setBackground(Color.GREEN);
        altList.get(altNumber).setEnabled(false);

    }

    /*private JLabel createNicelabel(String text, boolean isOpaque) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (isOpaque) {// Set rounded border and shaded background
            label.setBackground(new Color(220, 220, 220));
            label.setOpaque(true);  // Ensure the background color is visible
            label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));

            Font biggerFont = label.getFont().deriveFont(Font.PLAIN, 20);
            label.setFont(biggerFont);
        }
        return label;
    }*/

    public ArrayList<JButton> getAltList() {
        return altList;
    }
}
