package Controller;

import View.LoginView;

import javax.swing.*;
import java.sql.*;

public class LoginControll {
    private Connection connection;
    public final String url = "jdbc:postgresql://pgserver.mau.se:5432/";
    public final String user = "ao7866";
    public final String pass = "h82w8f72";
    private Connection conn;
    private LoginView loginView;
    public Statement statement;

  public LoginControll() throws SQLException {
        // connection = DriverManager.getConnection("jdbc:postgresql://pgserver.mau.se:5432/");
        this.loginView = new LoginView(this);
        databaseConnection();

    }
    public LoginControll(LoginView loginView) {
        this.loginView = loginView;
    }

    public boolean authenticate(String username, String password) {
        // Implementera autentiseringslogiken här
        return username.equals("admin") && password.equals("admin");
    }



    public void databaseConnection() {

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, pass);
            statement = conn.createStatement();
            System.out.println("connected successfully to database " + user);
        } catch (Exception e) {
            System.out.println("Error establishing database conn: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "SQL exception ! Terminating\n Error establishing database conn: \n" + e,
                    "Connection To Data Base Failed!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public boolean authenticate(String username, int password) throws SQLException {
        // Skapa en SQL-fråga för att hämta användarinformation baserat på användarnamn och lösenord
        String query = "SELECT * FROM AltAdmin WHERE name =? and id =?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, username);
        statement.setInt(2, password);

        // Utför frågan och kontrollera om användaren finns
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public static class Loginmain {
        public static void main(String[] args) throws SQLException {
            // Skapa en användarhanterare och inloggningsvy
            LoginControll userManager = new LoginControll();
            LoginView loginView = new LoginView(userManager);
        }
    }
}
