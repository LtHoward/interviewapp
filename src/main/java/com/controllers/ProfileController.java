package com.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.interviewapp.App;
import com.model.Major;
import com.model.SkillLevel;
import com.model.Student;
import com.model.Title;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ProfileController {

    public ProfileController() {
        super();
    }

    private User currentUser;

    // ── Where images get saved (relative to project root) ─────────────
    private static final String IMAGE_DIR =
        "src/main/resources/com/interviewapp/images/";

    @FXML private Label usernameLabel;
    @FXML private Label roleLabel;
    @FXML private Label nameLabel;
    @FXML private Label majorLabel;
    @FXML private Label aboutLabel;
    @FXML private Label achievementsLabel;
    @FXML private Label skillsLabel;
    @FXML private Label statisticsLabel;
    @FXML private ImageView profileImageView;   // headshot in banner
    @FXML private ImageView bannerImageView;    // banner background

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

            if (majorLabel != null)
                majorLabel.setText("Major: " + formatEnum(student.getMajor()));

            if (aboutLabel != null)
                aboutLabel.setText("A " + formatEnum(student.getSkillLevel()) + " " +
                    formatEnum(student.getMajor()) + " student.");

            if (achievementsLabel != null) {
                Title equipped = student.getProgression().getEquippedTitle();
                achievementsLabel.setText(
                    "Equipped Title: " + formatEnum(equipped) +
                    "\nUnlocked Titles: " + student.getProgression().getUnlockedTitles().size()
                );
            }

            // ── Skills: show as comma-separated list ───────────────────
            if (skillsLabel != null) {
                ArrayList<String> skills = student.getSkills();
                if (skills == null || skills.isEmpty()) {
                    skillsLabel.setText("No skills added yet. Click Edit to add your skills.");
                } else {
                    skillsLabel.setText(String.join(", ", skills));
                }
            }

            if (statisticsLabel != null) {
                statisticsLabel.setText(
                    "Solved Questions: " + student.getSolvedQuestions() +
                    "\nCurrent Streak: "  + student.getCurrentStreak() +
                    "\nLevel: "           + student.getProgression().getLevel() +
                    "\nXP: "              + student.getProgression().getPoints()
                );
            }

        } else {
            if (aboutLabel != null)        aboutLabel.setText("User profile");
            if (achievementsLabel != null) achievementsLabel.setText("No student achievements available.");
            if (skillsLabel != null)       skillsLabel.setText("No student skills available.");
            if (statisticsLabel != null)   statisticsLabel.setText("No student statistics available.");
        }
    }

    // ── Edit Profile: headshot, banner, major ──────────────────────────

    @FXML
    private void handleEditProfile(ActionEvent event) {
        if (!(currentUser instanceof Student)) return;
        Student student = (Student) currentUser;

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Profile");
        dialog.setHeaderText("Update your profile picture, banner, and major");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);

        // Major field (editable)
        TextField majorField = new TextField(
            student.getMajor() != null ? student.getMajor().name() : "");
        grid.add(new Label("Major\n(e.g. COMPUTER_SCIENCE,\nCOMPUTER_ENGINEERING):"), 0, 0);
        grid.add(majorField, 1, 0);

        // Headshot picker
        Label headshotStatus = new Label("No file chosen");
        Button headShotBtn = new Button("Choose Headshot...");
        final File[] headShotFile = {null};
        headShotBtn.setOnAction(e -> {
            File chosen = openImageChooser("Choose Profile Picture", dialog);
            if (chosen != null) {
                headShotFile[0] = chosen;
                headshotStatus.setText(chosen.getName());
            }
        });
        grid.add(new Label("Profile Picture:"), 0, 1);
        grid.add(headShotBtn, 1, 1);
        grid.add(headshotStatus, 1, 2);

        // Banner picker
        Label bannerStatus = new Label("No file chosen");
        Button bannerBtn = new Button("Choose Banner...");
        final File[] bannerFile = {null};
        bannerBtn.setOnAction(e -> {
            File chosen = openImageChooser("Choose Banner Image", dialog);
            if (chosen != null) {
                bannerFile[0] = chosen;
                bannerStatus.setText(chosen.getName());
            }
        });
        grid.add(new Label("Banner Image:"), 0, 3);
        grid.add(bannerBtn, 1, 3);
        grid.add(bannerStatus, 1, 4);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                JSONArray users = UserJsonService.loadUsers();
                JSONObject userObj = UserJsonService.findUserById(
                    users, currentUser.getId().toString());
                JSONObject studentData = UserJsonService.getStudentData(
                    users, currentUser.getId().toString());

                if (userObj == null || studentData == null) {
                    showError("Could not find your data in users.json.");
                    return;
                }

                // ── Save major ─────────────────────────────────────────
                String newMajor = majorField.getText().trim().toUpperCase();
                if (!newMajor.isEmpty()) {
                    studentData.put("major", newMajor);
                    try {
                        student.setMajor(Major.valueOf(newMajor));
                    } catch (IllegalArgumentException ignored) {}
                }

                // ── Save headshot ──────────────────────────────────────
                if (headShotFile[0] != null) {
                    String savedName = saveImage(headShotFile[0], "headshot_" +
                        currentUser.getId().toString());
                    userObj.put("headshot", savedName);
                    if (profileImageView != null) {
                        profileImageView.setImage(new Image(
                            new File(IMAGE_DIR + savedName).toURI().toString()));
                    }
                }

                // ── Save banner ────────────────────────────────────────
                if (bannerFile[0] != null) {
                    String savedName = saveImage(bannerFile[0], "banner_" +
                        currentUser.getId().toString());
                    userObj.put("banner", savedName);
                    if (bannerImageView != null) {
                        bannerImageView.setImage(new Image(
                            new File(IMAGE_DIR + savedName).toURI().toString()));
                    }
                }

                UserJsonService.saveUsers(users);
                showSuccess("Profile updated successfully.");
                populateProfile();

            } catch (Exception e) {
                showError("Failed to save: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // ── Edit About: skill level only ───────────────────────────────────

    @FXML
    private void handleEditAbout(ActionEvent event) {
        if (!(currentUser instanceof Student)) return;
        Student student = (Student) currentUser;

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit About");
        dialog.setHeaderText("Update your skill level");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Dropdown for skill level
        ChoiceBox<String> skillChoice = new ChoiceBox<>();
        skillChoice.getItems().addAll("BEGINNER", "INTERMEDIATE", "ADVANCED");
        skillChoice.setValue(student.getSkillLevel() != null
            ? student.getSkillLevel().name() : "BEGINNER");

        grid.add(new Label("Skill Level:"), 0, 0);
        grid.add(skillChoice, 1, 0);
        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String newSkill = skillChoice.getValue();
            try {
                JSONArray users = UserJsonService.loadUsers();
                JSONObject studentData = UserJsonService.getStudentData(
                    users, currentUser.getId().toString());

                if (studentData != null) {
                    studentData.put("skillLevel", newSkill);
                    UserJsonService.saveUsers(users);
                    try { student.setSkillLevel(SkillLevel.valueOf(newSkill)); }
                    catch (IllegalArgumentException ignored) {}
                    showSuccess("About updated successfully.");
                    populateProfile();
                } else {
                    showError("Could not find your student data in users.json.");
                }
            } catch (Exception e) {
                showError("Failed to save: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // ── Edit Achievements: equipped title ──────────────────────────────

    @FXML
    private void handleEditAchievements(ActionEvent event) {
        if (!(currentUser instanceof Student)) return;
        Student student = (Student) currentUser;

        Title equipped = student.getProgression().getEquippedTitle();
        List<?> unlocked = student.getProgression().getUnlockedTitles();

        List<String> titleNames = unlocked.stream()
            .map(t -> formatEnum((Enum<?>) t))
            .collect(Collectors.toList());

        if (titleNames.isEmpty()) {
            showError("You have no unlocked titles to equip.");
            return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(
            equipped != null ? formatEnum(equipped) : titleNames.get(0), titleNames);
        dialog.setTitle("Edit Achievements");
        dialog.setHeaderText("Choose your equipped title");
        dialog.setContentText("Equipped Title:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(chosen -> {
            String enumKey = chosen.toUpperCase().replace(" ", "_");
            try {
                JSONArray users = UserJsonService.loadUsers();
                JSONObject progression = UserJsonService.getProgression(
                    users, currentUser.getId().toString());

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

    // ── Edit Skills: free-text tech skills list ────────────────────────

    @FXML
    private void handleEditSkills(ActionEvent event) {
        if (!(currentUser instanceof Student)) return;
        Student student = (Student) currentUser;

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Skills");
        dialog.setHeaderText("Enter your tech skills");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Pre-fill with existing skills joined by comma
        String existing = student.getSkills() == null ? "" :
            String.join(", ", student.getSkills());

        TextArea skillsArea = new TextArea(existing);
        skillsArea.setPromptText("e.g. Python, Java, UML, SQL, Git");
        skillsArea.setPrefRowCount(4);
        skillsArea.setPrefWidth(300);
        skillsArea.setWrapText(true);

        grid.add(new Label("Skills\n(separate with commas):"), 0, 0);
        grid.add(skillsArea, 0, 1);
        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String input = skillsArea.getText().trim();

            // Split by comma, trim whitespace, remove blanks
            ArrayList<String> newSkills = new ArrayList<>();
            if (!input.isEmpty()) {
                for (String s : input.split(",")) {
                    String trimmed = s.trim();
                    if (!trimmed.isEmpty()) newSkills.add(trimmed);
                }
            }

            try {
                JSONArray users = UserJsonService.loadUsers();
                JSONObject studentData = UserJsonService.getStudentData(
                    users, currentUser.getId().toString());

                if (studentData != null) {
                    // Build a JSONArray from the skills list
                    JSONArray skillsJson = new JSONArray();
                    for (String skill : newSkills) {
                        skillsJson.add(skill);
                    }
                    studentData.put("skills", skillsJson);
                    UserJsonService.saveUsers(users);

                    // Update in-memory model
                    student.setSkills(newSkills);
                    showSuccess("Skills updated successfully.");
                    populateProfile();
                } else {
                    showError("Could not find your student data in users.json.");
                }
            } catch (Exception e) {
                showError("Failed to save: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // ── Edit Statistics ────────────────────────────────────────────────

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
                JSONObject progression = UserJsonService.getProgression(
                    users, currentUser.getId().toString());

                if (progression != null) {
                    progression.put("currentStreak", (long) newStreak);
                    progression.put("level",         (long) newLevel);
                    progression.put("points",        (long) newXP);
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

    // ── Image helpers ──────────────────────────────────────────────────

    /**
     * Opens a file chooser filtered to image files.
     */
    private File openImageChooser(String title, Dialog<?> parentDialog) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        Stage stage = (Stage) parentDialog.getDialogPane().getScene().getWindow();
        return chooser.showOpenDialog(stage);
    }

    /**
     * Copies the chosen image into the project images folder.
     * Returns the saved filename so it can be stored in JSON.
     */
    private String saveImage(File sourceFile, String baseName) throws IOException {
        String extension = sourceFile.getName()
            .substring(sourceFile.getName().lastIndexOf('.'));
        String fileName = baseName + extension;
        Path dest = Paths.get(IMAGE_DIR + fileName);
        Files.createDirectories(dest.getParent());
        Files.copy(sourceFile.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
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