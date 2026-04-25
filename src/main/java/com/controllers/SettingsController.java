package com.controllers;

import com.interviewapp.App;
import com.model.DataWriter;
import com.model.User;
import com.model.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Optional;

public class SettingsController {

    private User currentUser;

    private final UserManager userManager = UserManager.getInstance();

    @FXML 
    private TextField firstNameField;

    @FXML 
    private TextField lastNameField;
    
    @FXML 
    private TextField usernameField;

    @FXML 
    private TextField emailField;

    @FXML 
    private TextField newPasswordField;

    @FXML 
    private TextField confirmPasswordField;

    @FXML 
    private Label feedbackLabel;

    /**
     * Sets the current user for this controller and populates 
     * the fields with the user's current information.
     * @param user the user to set as the current user and populate the fields with their information
     */
    public void setUser(User user) {
        this.currentUser = user;

        User managedUser = findManagedUser(user);
        if (managedUser == null) {
            managedUser = user;
        }

        if (managedUser != null) {
            if (firstNameField != null) firstNameField.setText(nullToEmpty(managedUser.getFirstName()));
            if (lastNameField != null) lastNameField.setText(nullToEmpty(managedUser.getLastName()));
            if (usernameField != null) usernameField.setText(nullToEmpty(managedUser.getUsername()));
            if (emailField != null) emailField.setText(nullToEmpty(managedUser.getEmail()));
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            App.setRootWithLoader("login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of confirming changes to the user's information. 
     * Validates the input and updates the user's information in the backend if valid.
     * @param event the action event triggered by the confirm changes button
     */
    @FXML
    private void handleConfirmChanges(ActionEvent event) {
        if (currentUser == null) {
            setFeedback("No active user found.");
            return;
        }

        User managedUser = findManagedUser(currentUser);
        if (managedUser == null) {
            setFeedback("User could not be found in the backend.");
            return;
        }

        String newFirstName = firstNameField == null ? nullToEmpty(managedUser.getFirstName()) : firstNameField.getText().trim();
        String newLastName = lastNameField == null ? nullToEmpty(managedUser.getLastName()) : lastNameField.getText().trim();
        String newUsername = usernameField == null ? nullToEmpty(managedUser.getUsername()) : usernameField.getText().trim();
        String newEmail = emailField == null ? nullToEmpty(managedUser.getEmail()) : emailField.getText().trim();

        if (newFirstName.isEmpty() || newLastName.isEmpty() || newUsername.isEmpty() || newEmail.isEmpty()) {
            setFeedback("First name, last name, username, and email cannot be empty.");
            return;
        }

        User usernameOwner = userManager.getUser(newUsername);
        if (usernameOwner != null && !usernameOwner.getId().equals(managedUser.getId())) {
            setFeedback("That username is already taken.");
            return;
        }

        managedUser.setFirstName(newFirstName);
        managedUser.setLastName(newLastName);
        managedUser.setUsername(newUsername);
        managedUser.setEmail(newEmail);

        DataWriter.saveUsers();
        currentUser = managedUser;

        setFeedback("Changes saved successfully.");
    }

    /**
     * Handles the action of resetting the user's password. 
     * Validates the input and updates the user's password in the backend if valid.
     * @param event the action event triggered by the reset password button
     */
    @FXML
    private void handleResetPassword(ActionEvent event) {
        if (currentUser == null) {
            setFeedback("No active user found.");
            return;
        }

        User managedUser = findManagedUser(currentUser);
        if (managedUser == null) {
            setFeedback("User could not be found in the backend.");
            return;
        }

        String newPassword = newPasswordField == null ? "" : newPasswordField.getText().trim();
        String confirmPassword = confirmPasswordField == null ? "" : confirmPasswordField.getText().trim();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            setFeedback("Fill in both password fields.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            setFeedback("Passwords do not match.");
            return;
        }

        if (!userManager.isPasswordValid(newPassword)) {
            setFeedback("Password must be 8+ chars with a letter, number, and special character.");
            return;
        }

        if (managedUser.getPassword().equals(newPassword)) {
            setFeedback("New password must be different from the current password.");
            return;
        }

        boolean updated = userManager.resetPassword(managedUser.getUsername(), newPassword);
        if (!updated) {
            setFeedback("Password update failed.");
            return;
        }

        currentUser = findManagedUser(managedUser);

        if (newPasswordField != null) newPasswordField.clear();
        if (confirmPasswordField != null) confirmPasswordField.clear();

        setFeedback("Password reset successfully.");
    }

    /**
     * Handles the action of deleting the user's account. 
     * Prompts the user for confirmation and deletes the account from the backend if confirmed.
     * @param event the action event triggered by the delete account button
     */
    @FXML
    private void handleDeleteAccount(ActionEvent event) {
        if (currentUser == null) {
            setFeedback("No active user found.");
            return;
        }

        User managedUser = findManagedUser(currentUser);

        if (managedUser == null) {
            setFeedback("User could not be found in the backend.");
            return;
        }

        Alert firstConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        firstConfirm.setTitle("Delete Account");
        firstConfirm.setHeaderText("Are you sure you want to delete your account?");
        firstConfirm.setContentText("This will permanently remove your account from the backend user file.");

        Optional<ButtonType> firstResult = firstConfirm.showAndWait();
        if (firstResult.isEmpty() || firstResult.get() != ButtonType.OK) {
            return;
        }

        Alert secondConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        secondConfirm.setTitle("Final Confirmation");
        secondConfirm.setHeaderText("This action cannot be undone.");
        secondConfirm.setContentText("Press OK again to permanently delete this account.");

        Optional<ButtonType> secondResult = secondConfirm.showAndWait();
        if (secondResult.isEmpty() || secondResult.get() != ButtonType.OK) {
            return;
        }

        boolean removed = removeManagedUser(managedUser);

        if (!removed) {
            setFeedback("Account deletion failed.");
            return;
        }

        DataWriter.saveUsers();

        Alert deletedAlert = new Alert(Alert.AlertType.INFORMATION);
        deletedAlert.setTitle("Account Deleted");
        deletedAlert.setHeaderText(null);
        deletedAlert.setContentText("Your account has been permanently deleted.");
        deletedAlert.showAndWait();

        try {
            App.setRootWithLoader("login");
        } catch (Exception e) {
            e.printStackTrace();
            setFeedback("Account deleted, but navigation to login failed.");
        }
    }


    /**
     * Handles the action of navigating back to the student dashboard.
     * @param event the action event triggered by the back button
     */
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

    /**
     * Finds and returns the managed user from the backend user list that corresponds to the given target user.
     * @param target the user to find the corresponding managed user for
     * @return the managed user from the backend user list that corresponds to the given target user, or null if no such user is found
     */
    private User findManagedUser(User target) {
        if (target == null) {
            return null;
        }

        ArrayList<User> users = userManager.getUsers();

        for (User user : users) {
            if (user.getId().equals(target.getId())) {
                return user;
            }
        }

        return null;
    }

    /**
     * Removes the given target user from the backend user list.
     * @param target the user to remove from the backend user list  
     * @return true if the target user was successfully removed from the backend user list, false otherwise
     */
    private boolean removeManagedUser(User target) {
        if (target == null) {
            return false;
        }

        ArrayList<User> users = userManager.getUsers();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(target.getId())) {
                users.remove(i);
                return true;
            }
        }

        return false;
    }

    /**
     * Sets the feedback message to be displayed to the user.
     * @param message the feedback message to be displayed to the user
     */
    private void setFeedback(String message) {
        if (feedbackLabel != null) {
            feedbackLabel.setText(message);
        }
    }

    /**
     * Utility method to convert null strings to empty strings to avoid null pointer exceptions when setting field values.
     * @param value the string value to convert from null to empty
     * @return the original string value if it is not null, or an empty string if the original value is null
     */
    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}