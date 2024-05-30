package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Klassen represents the login view for applikation.
 * It provides a simple GUI with fields for username and password,and a login button.
 * @author Isra Pdier
 * **/
public class LoginV {
    private JFrame frame;
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    /**
     * Constructs a new LoginV and initializes the GUI components
     * */
    public LoginV() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel userLabel = new JLabel("Username:");
        userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        passField = new JPasswordField();
        loginButton = new JButton("Login");

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel());
        panel.add(loginButton);

        frame.add(panel);
    }

    /**sets the action listener for login button
     *
     * @param listener the ActionListener to be added to the login button
     * */
    public void setLoginButtonListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    /**Gets the username entered by the user
    * @return the username as a String
     */
    public String getUsername() {
        return userField.getText();
    }

    /**
     * Gets the password entered by the user.
     *
     * @return the password as a String
     */
    public String getPassword() {
        return new String(passField.getPassword());
    }

    /**
     * Makes the login frame visible.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Hides the login frame.
     */
    public void hide() {
        frame.setVisible(false);
    }
    /**
     * Displays an error message in a dialog.
     *
     * @param message the error message to be displayed
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}