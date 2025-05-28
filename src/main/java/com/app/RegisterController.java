package com.app;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterController {

    @FXML private TextField newUsernameField;
    @FXML private PasswordField newPasswordField;

    @FXML
    private void handleRegister() {
        String username = newUsernameField.getText();
        String password = newPasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Both fields are required!");
            return;
        }

        FileHandler.saveUser(username, password);
        showAlert("User registered successfully!");
        SceneManager.switchTo("login.fxml");
    }

    @FXML
    private void handleBack() {
        SceneManager.switchTo("login.fxml");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

