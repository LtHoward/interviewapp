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
import com.model.Role;
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

    private User currentUser;

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
    @FXML private ImageView profileImageView;
    @FXML private ImageView bannerImageView;

    public void setUser(User user) {
        this.currentUser = user;
        populateProfile();
    }

    private void populateProfile() {
        if (currentUser == null) return;

        if (usernameLabel != null) usernameLabel.setText(currentUser.getUsername());
        if (roleLabel != null) roleLabel.setText(formatEnum(currentUser.getStatus()));

        String first = currentUser.getFirstName() != null ? currentUser.getFirstName() : "";
        String last = currentUser.getLastName() != null ? currentUser.getLastName() : "";
        if (nameLabel != null) nameLabel.setText((first + " " + last).trim());

        try {
            JSONArray users = UserJsonService.loadUsers();
            JSONObject userObj = UserJsonService.findUserById(users, currentUser.getId().toString());

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
                        "\nCurrent Streak: " + student.getCurrentStreak() +
                        "\nLevel: " + student.getProgression().getLevel() +
                        "\nXP: " + student.getProgression().getPoints()
                    );
                }

            } else {
                if (majorLabel != null) {
                    majorLabel.setText("");
                }

                String about = getString(userObj, "about");
                String achievements = getString(userObj, "achievements");
                String statistics = getString(userObj, "statistics");
                String skills = joinJsonArray(userObj != null ? userObj.get("skills") : null);

                if (aboutLabel != null) {
                    aboutLabel.setText(
                        about.isEmpty() ? "Contributor profile" : about
                    );
                }

                if (skillsLabel != null) {
                    skillsLabel.setText(
                        skills.isEmpty() ? "No contributor skills available." : skills
                    );
                }

                if (achievementsLabel != null) {
                    achievementsLabel.setText(
                        achievements.isEmpty()
                            ? "No contributor achievements available."
                            : achievements
                    );
                }

                if (statisticsLabel != null) {
                    statisticsLabel.setText(
                        statistics.isEmpty()
                            ? "No contributor statistics available."
                            : statistics
                    );
                }
            }

            if (userObj != null) {
                loadUserImages(userObj);
            }

        } catch (Exception e) {
            e.printStackTrace();

            if (!(currentUser instanceof Student)) {
                if (majorLabel != null) majorLabel.setText("");
                if (aboutLabel != null) aboutLabel.setText("Contributor profile");
                if (skillsLabel != null) skillsLabel.setText("No contributor skills available.");
                if (achievementsLabel != null) achievementsLabel.setText("No contributor achievements available.");
                if (statisticsLabel != null) statisticsLabel.setText("No contributor statistics available.");
            }
        }
    }

    @FXML
    private void handleEditProfile(ActionEvent event) {
        if (currentUser == null) return;

        Student student = (currentUser instanceof Student) ? (Student) currentUser : null;

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Profile");
        dialog.setHeaderText(
            student != null
                ? "Update your profile picture, banner, and major"
                : "Update your profile picture and banner"
        );
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);

        TextField majorField = null;
        if (student != null) {
            majorField = new TextField(
                student.getMajor() != null ? student.getMajor().name() : ""
            );
            grid.add(new Label("Major\n(e.g. COMPUTER_SCIENCE,\nCOMPUTER_ENGINEERING):"), 0, 0);
            grid.add(majorField, 1, 0);
        }

        int rowOffset = (student != null) ? 1 : 0;

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

        grid.add(new Label("Profile Picture:"), 0, rowOffset);
        grid.add(headShotBtn, 1, rowOffset);
        grid.add(headshotStatus, 1, rowOffset + 1);

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

        grid.add(new Label("Banner Image:"), 0, rowOffset + 2);
        grid.add(bannerBtn, 1, rowOffset + 2);
        grid.add(bannerStatus, 1, rowOffset + 3);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                JSONArray users = UserJsonService.loadUsers();
                JSONObject userObj = UserJsonService.findUserById(
                    users, currentUser.getId().toString()
                );

                if (userObj == null) {
                    showError("Could not find your data in users.json.");
                    return;
                }

                if (student != null && majorField != null) {
                    JSONObject studentData = UserJsonService.getStudentData(
                        users, currentUser.getId().toString()
                    );

                    if (studentData == null) {
                        showError("Could not find your student data in users.json.");
                        return;
                    }

                    String newMajor = majorField.getText().trim().toUpperCase();
                    if (!newMajor.isEmpty()) {
                        studentData.put("major", newMajor);
                        try {
                            student.setMajor(Major.valueOf(newMajor));
                        } catch (IllegalArgumentException ignored) {
                        }
                    }
                }

                if (headShotFile[0] != null) {
                    String savedName = saveImage(
                        headShotFile[0],
                        "headshot_" + currentUser.getId().toString()
                    );
                    userObj.put("headshot", savedName);
                }

                if (bannerFile[0] != null) {
                    String savedName = saveImage(
                        bannerFile[0],
                        "banner_" + currentUser.getId().toString()
                    );
                    userObj.put("banner", savedName);
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

    @FXML
    private void handleEditAbout(ActionEvent event) {
        if (currentUser == null) return;

        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Edit About");
            dialog.setHeaderText("Update your skill level");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            ChoiceBox<String> skillChoice = new ChoiceBox<>();
            skillChoice.getItems().addAll("BEGINNER", "INTERMEDIATE", "ADVANCED");
            skillChoice.setValue(
                student.getSkillLevel() != null
                    ? student.getSkillLevel().name()
                    : "BEGINNER"
            );

            grid.add(new Label("Skill Level:"), 0, 0);
            grid.add(skillChoice, 1, 0);
            dialog.getDialogPane().setContent(grid);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String newSkill = skillChoice.getValue();
                try {
                    JSONArray users = UserJsonService.loadUsers();
                    JSONObject studentData = UserJsonService.getStudentData(
                        users, currentUser.getId().toString()
                    );

                    if (studentData != null) {
                        studentData.put("skillLevel", newSkill);
                        UserJsonService.saveUsers(users);

                        try {
                            student.setSkillLevel(SkillLevel.valueOf(newSkill));
                        } catch (IllegalArgumentException ignored) {
                        }

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

        } else {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Edit About");
            dialog.setHeaderText("Update your about section");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            TextArea aboutArea = new TextArea();
            aboutArea.setPrefRowCount(4);
            aboutArea.setPrefWidth(320);
            aboutArea.setWrapText(true);

            String currentAbout =
                aboutLabel != null && !"Contributor profile".equals(aboutLabel.getText())
                    ? aboutLabel.getText()
                    : "";

            aboutArea.setText(currentAbout);
            aboutArea.setPromptText("Write a short bio about yourself");

            grid.add(new Label("About:"), 0, 0);
            grid.add(aboutArea, 0, 1);
            dialog.getDialogPane().setContent(grid);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    JSONArray users = UserJsonService.loadUsers();
                    JSONObject userObj = UserJsonService.findUserById(
                        users, currentUser.getId().toString()
                    );

                    if (userObj == null) {
                        showError("Could not find your user data in users.json.");
                        return;
                    }

                    userObj.put("about", aboutArea.getText().trim());
                    UserJsonService.saveUsers(users);
                    showSuccess("About updated successfully.");
                    populateProfile();

                } catch (Exception e) {
                    showError("Failed to save: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void handleEditSkills(ActionEvent event) {
        if (currentUser == null) return;

        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Edit Skills");
            dialog.setHeaderText("Enter your tech skills");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            String existing = student.getSkills() == null ? "" : String.join(", ", student.getSkills());

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
                ArrayList<String> newSkills = splitCommaList(input);

                try {
                    JSONArray users = UserJsonService.loadUsers();
                    JSONObject studentData = UserJsonService.getStudentData(
                        users, currentUser.getId().toString()
                    );

                    if (studentData != null) {
                        JSONArray skillsJson = new JSONArray();
                        skillsJson.addAll(newSkills);
                        studentData.put("skills", skillsJson);
                        UserJsonService.saveUsers(users);

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

        } else {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Edit Skills");
            dialog.setHeaderText("Enter your contributor skills");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            TextArea skillsArea = new TextArea();
            skillsArea.setPromptText("e.g. Java, Git, SQL, Question Writing");
            skillsArea.setPrefRowCount(4);
            skillsArea.setPrefWidth(300);
            skillsArea.setWrapText(true);

            String existing =
                skillsLabel != null && !"No contributor skills available.".equals(skillsLabel.getText())
                    ? skillsLabel.getText()
                    : "";
            skillsArea.setText(existing);

            grid.add(new Label("Skills\n(separate with commas):"), 0, 0);
            grid.add(skillsArea, 0, 1);
            dialog.getDialogPane().setContent(grid);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    JSONArray users = UserJsonService.loadUsers();
                    JSONObject userObj = UserJsonService.findUserById(
                        users, currentUser.getId().toString()
                    );

                    if (userObj == null) {
                        showError("Could not find your user data in users.json.");
                        return;
                    }

                    ArrayList<String> newSkills = splitCommaList(skillsArea.getText().trim());
                    JSONArray skillsJson = new JSONArray();
                    skillsJson.addAll(newSkills);
                    userObj.put("skills", skillsJson);

                    UserJsonService.saveUsers(users);
                    showSuccess("Skills updated successfully.");
                    populateProfile();

                } catch (Exception e) {
                    showError("Failed to save: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void handleEditAchievements(ActionEvent event) {
        if (currentUser == null) return;

        if (currentUser instanceof Student) {
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
                equipped != null ? formatEnum(equipped) : titleNames.get(0), titleNames
            );
            dialog.setTitle("Edit Achievements");
            dialog.setHeaderText("Choose your equipped title");
            dialog.setContentText("Equipped Title:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(chosen -> {
                String enumKey = chosen.toUpperCase().replace(" ", "_");
                try {
                    JSONArray users = UserJsonService.loadUsers();
                    JSONObject progression = UserJsonService.getProgression(
                        users, currentUser.getId().toString()
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

        } else {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Edit Achievements");
            dialog.setHeaderText("Update contributor achievements");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            TextArea achievementsArea = new TextArea();
            achievementsArea.setPrefRowCount(4);
            achievementsArea.setPrefWidth(320);
            achievementsArea.setWrapText(true);

            String existing =
                achievementsLabel != null &&
                !"No contributor achievements available.".equals(achievementsLabel.getText())
                    ? achievementsLabel.getText()
                    : "";
            achievementsArea.setText(existing);
            achievementsArea.setPromptText("Add achievements, badges, milestones, etc.");

            grid.add(new Label("Achievements:"), 0, 0);
            grid.add(achievementsArea, 0, 1);
            dialog.getDialogPane().setContent(grid);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    JSONArray users = UserJsonService.loadUsers();
                    JSONObject userObj = UserJsonService.findUserById(
                        users, currentUser.getId().toString()
                    );

                    if (userObj == null) {
                        showError("Could not find your user data in users.json.");
                        return;
                    }

                    userObj.put("achievements", achievementsArea.getText().trim());
                    UserJsonService.saveUsers(users);
                    showSuccess("Achievements updated successfully.");
                    populateProfile();

                } catch (Exception e) {
                    showError("Failed to save: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void handleEditStatistics(ActionEvent event) {
        if (currentUser == null) return;

        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Edit Statistics");
            dialog.setHeaderText("Update your streak, level, and XP");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            TextField streakField = new TextField(String.valueOf(student.getCurrentStreak()));
            TextField levelField = new TextField(String.valueOf(student.getProgression().getLevel()));
            TextField xpField = new TextField(String.valueOf(student.getProgression().getPoints()));

            grid.add(new Label("Current Streak:"), 0, 0);
            grid.add(streakField, 1, 0);
            grid.add(new Label("Level:"), 0, 1);
            grid.add(levelField, 1, 1);
            grid.add(new Label("XP (Points):"), 0, 2);
            grid.add(xpField, 1, 2);
            dialog.getDialogPane().setContent(grid);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    int newStreak = Integer.parseInt(streakField.getText().trim());
                    int newLevel = Integer.parseInt(levelField.getText().trim());
                    int newXP = Integer.parseInt(xpField.getText().trim());

                    JSONArray users = UserJsonService.loadUsers();
                    JSONObject progression = UserJsonService.getProgression(
                        users, currentUser.getId().toString()
                    );

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

        } else {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Edit Stats");
            dialog.setHeaderText("Update contributor statistics");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            TextArea statsArea = new TextArea();
            statsArea.setPrefRowCount(5);
            statsArea.setPrefWidth(320);
            statsArea.setWrapText(true);

            String existing =
                statisticsLabel != null &&
                !"No contributor statistics available.".equals(statisticsLabel.getText())
                    ? statisticsLabel.getText()
                    : "";
            statsArea.setText(existing);
            statsArea.setPromptText("Example:\nCreated Questions: 10\nReviewed Posts: 4");

            grid.add(new Label("Statistics:"), 0, 0);
            grid.add(statsArea, 0, 1);
            dialog.getDialogPane().setContent(grid);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    JSONArray users = UserJsonService.loadUsers();
                    JSONObject userObj = UserJsonService.findUserById(
                        users, currentUser.getId().toString()
                    );

                    if (userObj == null) {
                        showError("Could not find your user data in users.json.");
                        return;
                    }

                    userObj.put("statistics", statsArea.getText().trim());
                    UserJsonService.saveUsers(users);
                    showSuccess("Statistics updated successfully.");
                    populateProfile();

                } catch (Exception e) {
                    showError("Failed to save: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        returnToDashboard();
    }

    private File openImageChooser(String title, Dialog<?> parentDialog) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        Stage stage = (Stage) parentDialog.getDialogPane().getScene().getWindow();
        return chooser.showOpenDialog(stage);
    }

    private String saveImage(File sourceFile, String baseName) throws IOException {
        String extension = sourceFile.getName()
            .substring(sourceFile.getName().lastIndexOf('.'));
        String fileName = baseName + extension;
        Path dest = Paths.get(IMAGE_DIR + fileName);
        Files.createDirectories(dest.getParent());
        Files.copy(sourceFile.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    private void loadUserImages(JSONObject userObj) {
        try {
            String headshot = getString(userObj, "headshot");
            if (!headshot.isEmpty() && profileImageView != null) {
                File file = new File(IMAGE_DIR + headshot);
                if (file.exists()) {
                    profileImageView.setImage(new Image(file.toURI().toString()));
                }
            }

            String banner = getString(userObj, "banner");
            if (!banner.isEmpty() && bannerImageView != null) {
                File file = new File(IMAGE_DIR + banner);
                if (file.exists()) {
                    bannerImageView.setImage(new Image(file.toURI().toString()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> splitCommaList(String input) {
        ArrayList<String> items = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) return items;

        for (String s : input.split(",")) {
            String trimmed = s.trim();
            if (!trimmed.isEmpty()) {
                items.add(trimmed);
            }
        }
        return items;
    }

    private String joinJsonArray(Object value) {
        if (!(value instanceof JSONArray)) return "";
        JSONArray array = (JSONArray) value;
        if (array.isEmpty()) return "";
        ArrayList<String> items = new ArrayList<>();
        for (Object obj : array) {
            if (obj != null) items.add(String.valueOf(obj));
        }
        return String.join(", ", items);
    }

    private String getString(JSONObject obj, String key) {
        if (obj == null || key == null) return "";
        Object value = obj.get(key);
        return value == null ? "" : String.valueOf(value).trim();
    }

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

    private void returnToDashboard() {
        try {
            if (currentUser.getStatus() == Role.STUDENT) {
                FXMLLoader loader = App.setRootWithLoader("studentDashboard");
                StudentDashboardController controller = loader.getController();
                controller.setUser(currentUser);
            } else if (currentUser.getStatus() == Role.CONTRIBUTOR
                    || currentUser.getStatus() == Role.ADMINISTRATOR) {
                FXMLLoader loader = App.setRootWithLoader("contributorDashboard");
                ContributorDashboardController controller = loader.getController();
                controller.setUser(currentUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}