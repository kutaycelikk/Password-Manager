package com.app;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PasswordEntry {

    private final StringProperty site;
    private final StringProperty username;
    private final StringProperty password;

    public PasswordEntry(String site, String username, String password) {
        this.site = new SimpleStringProperty(site);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
    }

    public String getSite() {
        return site.get();
    }

    public String getUsername() {
        return username.get();
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty siteProperty() {
        return site;
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String toFileString() {
        return getSite() + "," + getUsername() + "," + getPassword();
    }

    public static PasswordEntry fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 3) {
            return new PasswordEntry(parts[0], parts[1], parts[2]);
        }
        return null;
    }
}
