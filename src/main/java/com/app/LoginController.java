package com.app;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Please enter both username and password.");
            return;
        }

        if (FileHandler.verifyUser(username, password)) {
            FileHandler.setCurrentUser(username); // setting up the user
            SceneManager.switchToDashboard(username);
        } else {
            showAlert("Incorrect username or password.");
        }
    }

    @FXML
    private void handleRegister() {
        SceneManager.switchTo("register.fxml");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
