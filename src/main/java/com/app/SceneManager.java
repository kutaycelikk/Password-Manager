package com.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private static Stage stage;

    // Set the main stage once, used globally for switching scenes
    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    // General method to switch to any FXML scene
    public static void switchTo(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(SceneManager.class.getResource("/com/app/" + fxmlFile));
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Example: Switch to dashboard with username if needed
    public static void switchToDashboard(String username) {
        switchTo("dashboard.fxml");
    }
}
