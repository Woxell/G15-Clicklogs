package Model;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class UserModel {
    private static final String FILE_PATH = "users.txt";

    public UserModel() {
        createFileIfNotExists();
    }

    private void createFileIfNotExists() {
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
                List<String> defaultUsers = Arrays.asList("user1 password1", "user2 password2");
                Files.write(path, defaultUsers);
                System.out.println("File created with default users.");
            } catch (IOException e) {
                System.err.println("An error occurred while creating the file.");
                e.printStackTrace();
            }
        }
    }

    public boolean authenticate(String username, String password) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                String[] credentials = line.split(" ");
                if (credentials.length == 2) {
                    String fileUsername = credentials[0];
                    String filePassword = credentials[1];
                    if (fileUsername.equals(username) && filePassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
        return false;
    }
}