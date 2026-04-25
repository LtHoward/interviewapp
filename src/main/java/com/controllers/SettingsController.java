package com.controllers;

import com.interviewapp.App;
import com.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class SettingsController {

    private User currentUser;

    @FXML
    public void initialize() {
        // Keep empty for now.
        // This matches the current settings.fxml and avoids null pointer crashes.
    }

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    private void handleResetPassword(ActionEvent event) {
        try {
            App.setRootWithLoader("resetpassword");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteAccount(ActionEvent event) {
        try {
            App.setRootWithLoader("deleteaccount");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader loader = App.setRootWithLoader("studentDashboard");
            StudentDashboardController controller = loader.getController();
            controller.setUser(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}