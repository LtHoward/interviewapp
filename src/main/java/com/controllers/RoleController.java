package com.controllers;

import java.io.IOException;

import com.interviewapp.App;
import com.model.Major;
import com.model.Role;
import com.model.Year;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class RoleController {

    @FXML private ComboBox<Role> roleComboBox;
    @FXML private ComboBox<Major> majorComboBox;
    @FXML private ComboBox<Year> yearComboBox;
    @FXML private StackPane overlaypane;
    @FXML private Button handlecontinue;
    @FXML private Button continuestep;
    @FXML private Label errorLabel;

    @FXML
    private void initialize() {
        roleComboBox.getItems().addAll(Role.values());
        majorComboBox.getItems().addAll(Major.values());
        yearComboBox.getItems().addAll(Year.values());

        overlaypane.setVisible(false);
        overlaypane.setMouseTransparent(true);

        if (errorLabel != null) {
            errorLabel.setText("");
        }
    }

    /**
     * Handles the action when the user selects a role from the combo box.
     * If the selected role is STUDENT, it shows the overlay pane for 
     * selecting major and year. Otherwise, it hides the overlay pane.
     * @param event the action event triggered by selecting a role
     */
    @FXML
    private void handleComboBoxAction(ActionEvent event) {
        Role role = roleComboBox.getValue();

        if (role == null) {
            return;
        }

        if (role == Role.STUDENT) {
            overlaypane.setVisible(true);
            overlaypane.setMouseTransparent(false);
        } else {
            overlaypane.setVisible(false);
            overlaypane.setMouseTransparent(true);
        }

        if (errorLabel != null) {
            errorLabel.setText("");
        }
    }

    @FXML
    private void handleMajor(ActionEvent event) {
        if (errorLabel != null) {
            errorLabel.setText("");
        }
    }

    @FXML
    private void handleYear(ActionEvent event) {
        if (errorLabel != null) {
            errorLabel.setText("");
        }
    }

    /**
     * Handles the action when the user clicks the continue button. 
     * It checks if a role is selected.
     * @param event the action event triggered by clicking the continue button
     * @throws IOException if there is an error loading the next scene
     */
    @FXML
    private void switchtonext(ActionEvent event) throws IOException {
        Role role = roleComboBox.getValue();

        if (role == null) {
            errorLabel.setText("Please select a role.");
            return;
        }

        if (role == Role.STUDENT) {
            overlaypane.setVisible(true);
            overlaypane.setMouseTransparent(false);
            return;
        }

        SignupState.setSelectedRole(role);
        SignupState.setSelectedMajor(null);
        SignupState.setSelectedYear(null);

        App.setRoot("signup");
    }

    /**
     * Handles the action when the user clicks the continue button on the overlay pane.
     * @param event the action event triggered by clicking the continue button on the overlay pane
     * @throws IOException if there is an error loading the next scene
     */
    @FXML
    private void switchtosignup(ActionEvent event) throws IOException {
        Role role = roleComboBox.getValue();
        Major major = majorComboBox.getValue();
        Year year = yearComboBox.getValue();

        if (role == null) {
            errorLabel.setText("Please select a role.");
            return;
        }

        if (role != Role.STUDENT) {
            errorLabel.setText("This step is only for students.");
            return;
        }

        if (major == null || year == null) {
            errorLabel.setText("Please select your major and year.");
            return;
        }

        SignupState.setSelectedRole(role);
        SignupState.setSelectedMajor(major);
        SignupState.setSelectedYear(year);

        App.setRoot("signup");
    }
}