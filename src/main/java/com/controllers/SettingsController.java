package com.controllers;

import com.interviewapp.App;
import com.model.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SettingsController {

    private User currentUser;

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private ComboBox<String> majorComboBox;
    @FXML private ComboBox<String> yearComboBox;
    @FXML private Label feedbackLabel;

    @FXML
    public void initialize() {
        majorComboBox.setItems(FXCollections.observableArrayList(
            "Computer Science", "Computer Engineering", "Information Technology",
            "Software Engineering", "Cybersecurity", "Data Science", "Other"
        ));
        yearComboBox.setItems(FXCollections.observableArrayList(
            "Freshman", "Sophomore", "Junior", "Senior", "Graduate"
        ));
    }

    public void setUser(User user) {
        this.currentUser = user;
        populateFields();
    }

    private void populateFields() {
        if (currentUser != null) {
            firstNameField.setText(currentUser.getFirstName());
            lastNameField.setText(currentUser.getLastName());
            emailField.setText(currentUser.getEmail());
            // Uncomment these once you confirm the method names on your User model:
            // usernameField.setText(currentUser.getUsername());
            // majorComboBox.setValue(currentUser.getMajor());
            // yearComboBox.setValue(currentUser.getYear());
        }
    }

    @FXML
    private void handleSave(ActionEvent event) {
        if (currentUser != null) {
            currentUser.setFirstName(firstNameField.getText().trim());
            currentUser.setLastName(lastNameField.getText().trim());
            currentUser.setEmail(emailField.getText().trim());
            // currentUser.setUsername(usernameField.getText().trim());
            // currentUser.setMajor(majorComboBox.getValue());
            // currentUser.setYear(yearComboBox.getValue());
            feedbackLabel.setText("Changes saved!");
        }
    }

    @FXML
    private void handleAccountNav(ActionEvent event) {
        // Already on profile/account view — could highlight the button or reload
    }

    @FXML
    private void handleRewardsNav(ActionEvent event) {
        // Navigate to rewards screen when ready
    }

    @FXML
    private void handleResetPassword(ActionEvent event) {
        try {
            FXMLLoader loader = App.setRootWithLoader("resetpassword");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteAccount(ActionEvent event) {
        try {
            FXMLLoader loader = App.setRootWithLoader("deleteaccount");
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