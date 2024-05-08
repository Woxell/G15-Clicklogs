package View;

import Controller.Controller;
import Controller.LoginControll;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginView extends JFrame{
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private LoginControll loginControll;
    private Controller controller;

    public LoginView(LoginControll loginControll) {
        this.loginControll = loginControll;

        frame = new JFrame("Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }



    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 165, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String passwordString = new String(passwordField.getPassword()).trim();
                try {
                    // Konvertera lösenordet till ett heltal
                    Integer password = Integer.parseInt(passwordString);

                    // Anropa authenticate-metoden med användarnamn och lösenord
                    if (loginControll.authenticate(username, password)) {
                        JOptionPane.showMessageDialog(frame, "Login successful!");
                        // Implementera logik för att öppna huvudapplikationen här
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid username or password. Please try again.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid password format. Please enter a valid integer password.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel.add(loginButton);
    }


}

