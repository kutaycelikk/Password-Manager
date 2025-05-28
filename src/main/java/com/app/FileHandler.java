package com.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.*;

public class FileHandler {

    private static final String USER_FILE = "users.txt";
    private static String currentUser;

    // Set the current user after successful login
    public static void setCurrentUser(String username) {
        currentUser = username;
    }

    // Generate the filename for this user's passwords
    private static String getPasswordFile() {
        return "passwords_" + currentUser + ".txt";
    }

    // Save new user credentials
    public static void saveUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Verify credentials from users.txt
    public static boolean verifyUser(String username, String password) {
        try {
            for (String line : Files.readAllLines(Paths.get(USER_FILE))) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Save a new password entry
    public static void savePassword(PasswordEntry entry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getPasswordFile(), true))) {
            writer.write(entry.toFileString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Overwrites the file with updated password list
    public static void overwritePasswords(ObservableList<PasswordEntry> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getPasswordFile()))) {
            for (PasswordEntry entry : list) {
                writer.write(entry.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load all passwords from the file
    public static ObservableList<PasswordEntry> loadPasswords() {
        ObservableList<PasswordEntry> list = FXCollections.observableArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(getPasswordFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                PasswordEntry entry = PasswordEntry.fromFileString(line);
                if (entry != null) list.add(entry);
            }
        } catch (IOException e) {
            // ignore if file doesn't exist yet
        }
        return list;
    }
}
