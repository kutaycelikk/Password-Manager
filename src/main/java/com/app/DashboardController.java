package com.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class DashboardController {

    @FXML private TextField siteField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML private TableView<PasswordEntry> passwordTable;
    @FXML private TableColumn<PasswordEntry, String> siteColumn;
    @FXML private TableColumn<PasswordEntry, String> userColumn;
    @FXML private TableColumn<PasswordEntry, String> passColumn;

    private ObservableList<PasswordEntry> passwordData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        siteColumn.setCellValueFactory(new PropertyValueFactory<>("site"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        passwordTable.setItems(passwordData);
        passwordData.addAll(FileHandler.loadPasswords());

        // Enable hover and click-to-copy for username and password
        addCopyFeature(userColumn, "username");
        addCopyFeature(passColumn, "password");
    }

    @FXML
    private void handleAdd() {
        String site = siteField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (site.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }

        PasswordEntry entry = new PasswordEntry(site, username, password);
        passwordData.add(entry);
        FileHandler.savePassword(entry);

        siteField.clear();
        usernameField.clear();
        passwordField.clear();
    }

    // Deletes selected password entry from table and file
    @FXML
    private void handleDelete() {
        PasswordEntry selected = passwordTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            passwordData.remove(selected);
            FileHandler.overwritePasswords(passwordData);
        } else {
            showAlert("Please select an entry to delete.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // Adds clickable & hover-highlightable cells that copy to clipboard
    private void addCopyFeature(TableColumn<PasswordEntry, String> column, final String type) {
        column.setCellFactory(new Callback<TableColumn<PasswordEntry, String>, TableCell<PasswordEntry, String>>() {
            @Override
            public TableCell<PasswordEntry, String> call(TableColumn<PasswordEntry, String> param) {
                return new TableCell<PasswordEntry, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setTooltip(null);
                            setStyle("");
                        } else {
                            setText(item);
                            setTooltip(new Tooltip("Click to copy " + type));

                            setOnMouseClicked(event -> {
                                ClipboardContent content = new ClipboardContent();
                                content.putString(item);
                                Clipboard.getSystemClipboard().setContent(content);
                            });

                            setOnMouseEntered(e -> setStyle("-fx-font-weight: bold;"));
                            setOnMouseExited(e -> setStyle("-fx-font-weight: normal;"));
                        }
                    }
                };
            }
        });
    }
}
