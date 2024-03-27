package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class DecisionPanel extends JPanel {
    private int width;
    private int height;
    private JLabel jlabel = null;
    private MainPanel mainPanel;


    public DecisionPanel(MainPanel mainPanel, int width, int totalHeight) {
        this.width = 900;
        this.height = (int) (mainPanel.getHeight() * 5 / 7);
        this.mainPanel = mainPanel;
        this.jlabel = createNicelabel("DecisionPanel", true);
        setLayout(new BorderLayout());
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(width, height));
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        add(jlabel);

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

    private JLabel createNicelabel(String text, boolean isOpaque) {
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
    }


}
