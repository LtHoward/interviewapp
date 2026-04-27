package com.controllers;

import java.io.IOException;

import com.interviewapp.App;
import com.model.Major;
import com.model.Role;
import com.model.User;
import com.model.UserManager;
import com.model.Year;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private Label errorLabel;
    @FXML private Button signupbutton;
    @FXML private Button loginbutton;
<<<<<<< HEAD
    @FXML private Button continuebutton;
    @FXML private StackPane overlaypane;
    @FXML private StackPane topoverlaypane;
    @FXML private StackPane roleoverlay;
    @FXML private Button handlecontinue;
    @FXML private Button continuestep;
=======
>>>>>>> ef1e8b62d667382097fd7a9eb1b54db924dd2fa8

    private final UserManager userManager = UserManager.getInstance();

<<<<<<< HEAD
    private UserManager userManager = UserManager.getInstance();

    
    @FXML
    private void initialize() {

        // populate combos safely
        if (roleComboBox != null) {
            roleComboBox.getItems().setAll(Role.values());
        }

        if (majorComboBox != null) {
            majorComboBox.getItems().setAll(Major.values());
        }

        if (yearComboBox != null) {
            yearComboBox.getItems().setAll(Year.values());
        }

        // SAFE overlay setup (prevents white screen bug)
        if (overlaypane != null) {
            overlaypane.setVisible(false);
            overlaypane.setMouseTransparent(true);
        }

        if (topoverlaypane != null) {
            topoverlaypane.setVisible(false);
            topoverlaypane.setMouseTransparent(true);
        }

        if (roleoverlay != null) {
            roleoverlay.setVisible(true);
        }

        if (errorLabel != null) {
            errorLabel.setText("");
        }
    }
    
     @FXML
    void handleComboBoxAction(ActionEvent event) {
        Role selectedRole = roleComboBox.getValue();
    }

    @FXML
    void handleMajor(ActionEvent event) throws IOException{
        Major selectedMajor = majorComboBox.getValue();
    }

    @FXML
    void handleYear(ActionEvent event) throws IOException{
        Year selectedYear = yearComboBox.getValue();
    }

    @FXML
    void switchtonext(ActionEvent event) throws IOException{
        Role role = roleComboBox.getValue();
        Major major = majorComboBox.getValue();
        Year year = yearComboBox.getValue();
        if (role == null) {
            return;
        }
        if(role == Role.STUDENT) {
            overlaypane.setVisible(true);
            overlaypane.setMouseTransparent(false);
        } else {
        overlaypane.setVisible(false);
        overlaypane.setMouseTransparent(true);
        }

        if (role == Role.CONTRIBUTOR || role == Role.ADMINISTRATOR) {
            topoverlaypane.setVisible(true);
            topoverlaypane.setMouseTransparent(false);
        }else {
            topoverlaypane.setVisible(false);
            topoverlaypane.setMouseTransparent(true);
=======
    @FXML
    private void initialize() {
        if (errorLabel != null) {
            errorLabel.setText("");
>>>>>>> ef1e8b62d667382097fd7a9eb1b54db924dd2fa8
        }
    }

    @FXML
<<<<<<< HEAD
    void switchtosignup(ActionEvent event) throws IOException{
        topoverlaypane.setVisible(true);
    }

    @FXML 
=======
>>>>>>> ef1e8b62d667382097fd7a9eb1b54db924dd2fa8
    private void handlesignup(ActionEvent event) throws IOException {

<<<<<<< HEAD
        if(firstname == null || lastname == null || email == null || password == null || username == null || role == null) {
=======
        if(errorLabel != null) {
            errorLabel.setText("");
        }

        if (usernameField == null || emailField == null || passwordField == null
                || firstNameField == null || lastNameField == null) {
>>>>>>> ef1e8b62d667382097fd7a9eb1b54db924dd2fa8
            return;
        }

        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();

        Role role = SignupState.getSelectedRole();
        Major major = SignupState.getSelectedMajor();
        Year year = SignupState.getSelectedYear();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()
                || firstName.isEmpty() || lastName.isEmpty()) {
            if (errorLabel != null) errorLabel.setText("Please fill in all required fields.");
            return;
        }

        if (role == null) {
            if (errorLabel != null) errorLabel.setText("Please go back and select an account role first.");
            return;
        }

        if (role == Role.STUDENT && (major == null || year == null)) {
            if (errorLabel != null) errorLabel.setText("Please complete your academic information first.");
            return;
        }

        if (userManager.getUser(username) != null) {
            if (errorLabel != null) errorLabel.setText("Username already exists.");
            return;
        }

        if(!userManager.isPasswordValid(password)) {
            if (errorLabel != null) errorLabel.setText("Password must be at least 8 characters long and contain at least a letter, a number, and a special character.");
            return;
        }

        boolean success = userManager.addUser(
                username, email, password, firstName, lastName, role, major, year
        );

        if (!success) {
            if (errorLabel != null) errorLabel.setText("Could not create account. Please try again.");
            return;
        }

        User user = userManager.getUser(username);
        SignupState.clear();

        if (role == Role.STUDENT) {
            FXMLLoader loader = App.setRootWithLoader("studentDashboard");
            StudentDashboardController controller = loader.getController();
            controller.setUser(user);
        } else {
            FXMLLoader loader = App.setRootWithLoader("contributorDashboard");
            ContributorDashboardController controller = loader.getController();
            controller.setUser(user);
        }
    }
    @FXML
    private void switchToLogin(ActionEvent event) throws IOException {
        SignupState.clear();
        App.setRoot("login");
    }
}