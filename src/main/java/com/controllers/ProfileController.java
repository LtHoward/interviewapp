package com.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.interviewapp.App;
import com.model.Student;
import com.model.Title;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ProfileController {

    public ProfileController() {
        super();
    }

    private User currentUser;

    @FXML private Label usernameLabel;
    @FXML private Label roleLabel;
    @FXML private Label nameLabel;
    @FXML private Label majorLabel;
    @FXML private Label aboutLabel;
    @FXML private Label achievementsLabel;
    @FXML private Label skillsLabel;
    @FXML private Label statisticsLabel;

    // ── Set user and populate UI ───────────────────────────────────────

    public void setUser(User user) {
        this.currentUser = user;
        populateProfile();
    }

    private void populateProfile() {
        if (currentUser == null) return;

        if (usernameLabel != null) usernameLabel.setText(currentUser.getUsername());
        if (roleLabel != null)     roleLabel.setText(formatEnum(currentUser.getStatus()));

        String first = currentUser.getFirstName() != null ? currentUser.getFirstName() : "";
        String last  = currentUser.getLastName()  != null ? currentUser.getLastName()  : "";
        if (nameLabel != null) nameLabel.setText((first + " " + last).trim());

        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;

            if (majorLabel != null) {
                majorLabel.setText("Major: " + formatEnum(student.getMajor()));
            }

            if (aboutLabel != null) {
                aboutLabel.setText(
                    "A " + formatEnum(student.getSkillLevel()) + " " +
                    formatEnum(student.getMajor()) + " student."
                );
            }

            if (achievementsLabel != null) {
                Title equipped = student.getProgression().getEquippedTitle();
                achievementsLabel.setText(
                    "Equipped Title: " + formatEnum(equipped) +
                    "\nUnlocked Titles: " + student.getProgression().getUnlockedTitles().size()
                );
            }

            if (skillsLabel != null) {
                skillsLabel.setText(
                    "Skill Level: " + formatEnum(student.getSkillLevel()) +
                    "\nMajor: " + formatEnum(student.getMajor())
                );
            }

            if (statisticsLabel != null) {
                statisticsLabel.setText(
                    "Solved Questions: " + student.getSolvedQuestions() +
                    "\nCurrent Streak: " + student.getCurrentStreak() +
                    "\nLevel: " + student.getProgression().getLevel() +
                    "\nXP: " + student.getProgression().getPoints()
                );
            }
        } else {
            if (aboutLabel != null)        aboutLabel.setText("User profile");
            if (achievementsLabel != null) achievementsLabel.setText("No student achievements available.");
            if (skillsLabel != null)       skillsLabel.setText("Role: " + formatEnum(currentUser.getStatus()));
            if (statisticsLabel != null)   statisticsLabel.setText("No student statistics available.");
        }
    }

    // ── Edit handlers ──────────────────────────────────────────────────

    @FXML
    private void handleEditProfile(ActionEvent event) {
        // Not in scope per requirements — placeholder kept
        System.out.println("Edit Profile clicked");
    }

    /**
     * Edit About section: lets the student change their skill level and major.
     * Valid skill levels: BEGINNER, INTERMEDIATE, ADVANCED
     * Valid majors:       COMPUTER_SCIENCE, COMPUTER_ENGINEERING, etc.
     */
    @FXML
    private void handleEditAbout(ActionEvent event) {
        if (!(currentUser instanceof Student)) return;
        Student student = (Student) currentUser;

        // Build a two-field dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit About");
        dialog.setHeaderText("Update your skill level and major");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField skillField = new TextField(student.getSkillLevel() != null
                ? student.getSkillLevel().name() : "");
        TextField majorField = new TextField(student.getMajor() != null
                ? student.getMajor().name() : "");

        grid.add(new Label("Skill Level\n(BEGINNER / INTERMEDIATE / ADVANCED):"), 0, 0);
        grid.add(skillField, 1, 0);
        grid.add(new Label("Major\n(e.g. COMPUTER_SCIENCE):"), 0, 1);
        grid.add(majorField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String newSkill = skillField.getText().trim().toUpperCase();
            String newMajor = majorField.getText().trim().toUpperCase();

            try {
                JSONArray users = UserJsonService.loadUsers();
                JSONObject studentData = UserJsonService.getStudentData(users, currentUser.getId().toString());

                if (studentData != null) {
                    studentData.put("skillLevel", newSkill);
                    studentData.put("major", newMajor);
                    UserJsonService.saveUsers(users);
                    showSuccess("About section updated successfully.");
                    populateProfile(); // refresh labels
                } else {
                    showError("Could not find your student data in users.json.");
                }
            } catch (Exception e) {
                showError("Failed to save: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Edit Achievements: lets the student change their equipped title.
     * Only titles already in unlockedTitles are accepted.
     */
    @FXML
    private void handleEditAchievements(ActionEvent event) {
        if (!(currentUser instanceof Student)) return;
        Student student = (Student) currentUser;

        Title equipped = student.getProgression().getEquippedTitle();
        List<?> unlocked = student.getProgression().getUnlockedTitles();

        // Show a ChoiceDialog with only their unlocked titles
        List<String> titleNames = unlocked.stream()
                .map(t -> formatEnum((Enum<?>) t))
                .collect(Collectors.toList());

        if (titleNames.isEmpty()) {
            showError("You have no unlocked titles to equip.");
            return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(
                equipped != null ? formatEnum(equipped) : titleNames.get(0),
                titleNames
        );
        dialog.setTitle("Edit Achievements");
        dialog.setHeaderText("Choose your equipped title");
        dialog.setContentText("Equipped Title:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(chosen -> {
            // Convert display name back to enum key (e.g. "Syntax Scout" → "SYNTAX_SCOUT")
            String enumKey = chosen.toUpperCase().replace(" ", "_");
            try {
                JSONArray users = UserJsonService.loadUsers();
                JSONObject progression = UserJsonService.getProgression(users, currentUser.getId().toString()
            );

                if (progression != null) {
                    progression.put("equippedTitle", enumKey);
                    UserJsonService.saveUsers(users);
                    showSuccess("Equipped title updated to: " + chosen);
                    populateProfile();
                } else {
                    showError("Could not find progression data in users.json.");
                }
            } catch (Exception e) {
                showError("Failed to save: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    /**
     * Edit Skills: same fields as About (skill level + major) but shown from
     * the Skills card. Reuses the same JSON fields so both cards stay in sync.
     */
    @FXML
    private void handleEditSkills(ActionEvent event) {
        // Skills card shows the same data as About — delegate to that handler
        handleEditAbout(event);
    }

    /**
     * Edit Statistics: lets the student update currentStreak, level, and XP (points).
     * solvedQuestions is intentionally read-only (earned through gameplay).
     */
    @FXML
    private void handleEditStatistics(ActionEvent event) {
        if (!(currentUser instanceof Student)) return;
        Student student = (Student) currentUser;

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Statistics");
        dialog.setHeaderText("Update your streak, level, and XP");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField streakField = new TextField(String.valueOf(student.getCurrentStreak()));
        TextField levelField  = new TextField(String.valueOf(student.getProgression().getLevel()));
        TextField xpField     = new TextField(String.valueOf(student.getProgression().getPoints()));

        grid.add(new Label("Current Streak:"), 0, 0);
        grid.add(streakField, 1, 0);
        grid.add(new Label("Level:"),          0, 1);
        grid.add(levelField, 1, 1);
        grid.add(new Label("XP (Points):"),    0, 2);
        grid.add(xpField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                int newStreak = Integer.parseInt(streakField.getText().trim());
                int newLevel  = Integer.parseInt(levelField.getText().trim());
                int newXP     = Integer.parseInt(xpField.getText().trim());

                JSONArray users = UserJsonService.loadUsers();
                JSONObject progression = UserJsonService.getProgression(users, currentUser.getId().toString());

                if (progression != null) {
                    progression.put("currentStreak", (long) newStreak);
                    progression.put("level", (long) newLevel);
                    progression.put("points", (long) newXP);
                    UserJsonService.saveUsers(users);
                    showSuccess("Statistics updated successfully.");
                    populateProfile();
                } else {
                    showError("Could not find progression data in users.json.");
                }
            } catch (NumberFormatException e) {
                showError("Streak, Level, and XP must all be whole numbers.");
            } catch (Exception e) {
                showError("Failed to save: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // ── Navigation ─────────────────────────────────────────────────────

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = App.setRootWithLoader("dashboard");
            DashboardController controller = loader.getController();
            controller.setUser(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ── Helpers ────────────────────────────────────────────────────────

    private String formatEnum(Enum<?> value) {
        if (value == null) return "None";
        String[] words = value.name().toLowerCase().split("_");
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (words[i].isEmpty()) continue;
            formatted.append(Character.toUpperCase(words[i].charAt(0)))
                     .append(words[i].substring(1));
            if (i < words.length - 1) formatted.append(" ");
        }
        return formatted.toString();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Saved");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
