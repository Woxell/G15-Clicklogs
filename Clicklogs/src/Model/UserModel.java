package Model;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * This class represents a model for managing user data.
 * It ensures that a user data file exists
 * and initializes it with default users if necessary.
 * @author Isra Pdier
 */
public class UserModel {
    private static final String FILE_PATH = "users.txt";

    /**
     * Constructs a new UserModel and ensures the user data file exists.
     * If the file does not exist, it creates the file with default users.
     */
    public UserModel() {
        createFileIfNotExists();
    }

    /**
     * Creates the user data file if it does not already exist.
     * If the file is created, it is populated with default users.
     * This method handles any IOExceptions that occur during file creation and writing.
     */
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
}