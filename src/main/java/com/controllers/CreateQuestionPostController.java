package com.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.interviewapp.App;
import com.model.Comment;
import com.model.ContentType;
import com.model.Difficulty;
import com.model.InterviewApp;
import com.model.PostContent;
import com.model.PostManager;
import com.model.QuestionPost;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateQuestionPostController {

    @FXML private TextField titleField;
    @FXML private TextField tagsField;
    @FXML private TextField hintField;
    @FXML private TextArea contentArea;

    @FXML private Button easyButton;
    @FXML private Button mediumButton;
    @FXML private Button hardButton;

    private User currentUser;
    private Difficulty selectedDifficulty = Difficulty.EASY;
    private final InterviewApp app = new InterviewApp();

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    private void selectEasy(ActionEvent event) {
        selectedDifficulty = Difficulty.EASY;
        updateDifficultyStyles();
    }

    @FXML
    private void selectMedium(ActionEvent event) {
        selectedDifficulty = Difficulty.MEDIUM;
        updateDifficultyStyles();
    }

    @FXML
    private void selectHard(ActionEvent event) {
        selectedDifficulty = Difficulty.HARD;
        updateDifficultyStyles();
    }

    private void updateDifficultyStyles() {
        easyButton.getStyleClass().remove("selected-difficulty");
        mediumButton.getStyleClass().remove("selected-difficulty");
        hardButton.getStyleClass().remove("selected-difficulty");

        if (selectedDifficulty == Difficulty.EASY) {
            easyButton.getStyleClass().add("selected-difficulty");
        } else if (selectedDifficulty == Difficulty.MEDIUM) {
            mediumButton.getStyleClass().add("selected-difficulty");
        } else {
            hardButton.getStyleClass().add("selected-difficulty");
        }
    }

    @FXML
    private void toggleBold(ActionEvent event) {
        wrapSelectedText("**", "**");
    }

    @FXML
    private void toggleItalics(ActionEvent event) {
        wrapSelectedText("*", "*");
    }

    @FXML
    private void toggleUnderline(ActionEvent event) {
        wrapSelectedText("<u>", "</u>");
    }

    @FXML
    private void insertCodeBlock(ActionEvent event) {
        insertText("\n```java\n// enter code here\n```\n");
    }

    @FXML
    private void insertImagePlaceholder(ActionEvent event) {
        insertText("\n[image:path/to/image.png]\n");
    }

    @FXML
    private void insertVideoPlaceholder(ActionEvent event) {
        insertText("\n[video:path/to/video.mp4]\n");
    }

    private void wrapSelectedText(String before, String after) {
        String selected = contentArea.getSelectedText();

        if (selected == null || selected.isEmpty()) {
            insertText(before + after);
            return;
        }

        int start = contentArea.getSelection().getStart();
        int end = contentArea.getSelection().getEnd();

        contentArea.replaceText(start, end, before + selected + after);
    }

    private void insertText(String text) {
        int caretPosition = contentArea.getCaretPosition();
        contentArea.insertText(caretPosition, text);
        contentArea.requestFocus();
    }

    @FXML
    private void previewPost(ActionEvent event) {
        QuestionPost preview = buildQuestionPost();

        if (preview == null) {
            return;
        }

        try {
            FXMLLoader loader = App.setRootWithLoader("questionPost");
            QuestionsController controller = loader.getController();
            controller.setData(currentUser, preview);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void createPost(ActionEvent event) {
        QuestionPost question = buildQuestionPost();

        if (question == null) {
            return;
        }

        boolean added = app.addQuestion(question);

        if (added) {
            PostManager.getInstance().save();

            try {
                FXMLLoader loader = App.setRootWithLoader("search");
                SearchController controller = loader.getController();
                controller.setUser(currentUser);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showError("Post could not be created.");
        }
    }

    private QuestionPost buildQuestionPost() {
        if (currentUser == null) {
            showError("No logged-in user found.");
            return null;
        }

        String title = titleField.getText().trim();
        String hint = hintField.getText().trim();
        String content = contentArea.getText().trim();

        if (title.isEmpty()) {
            showError("Please enter a title.");
            return null;
        }

        if (content.isEmpty()) {
            showError("Please enter question content.");
            return null;
        }

        ArrayList<String> tags = parseTags(tagsField.getText());
        ArrayList<PostContent> contentSections = parseContentSections(content);

        return new QuestionPost(
            UUID.randomUUID(),
            title,
            currentUser,
            new Date(),
            new ArrayList<Comment>(),
            tags,
            contentSections,
            0,
            selectedDifficulty,
            hint
        );
    }

    private ArrayList<String> parseTags(String tagText) {
        ArrayList<String> tags = new ArrayList<>();

        if (tagText == null || tagText.trim().isEmpty()) {
            return tags;
        }

        String[] splitTags = tagText.split(",");

        for (String tag : splitTags) {
            String cleaned = tag.trim();

            if (!cleaned.isEmpty()) {
                tags.add(cleaned);
            }
        }

        return tags;
    }

    private ArrayList<PostContent> parseContentSections(String rawContent) {
        ArrayList<PostContent> sections = new ArrayList<>();

        if (rawContent == null || rawContent.trim().isEmpty()) {
            return sections;
        }

        String[] lines = rawContent.split("\\R");
        StringBuilder buffer = new StringBuilder();

        boolean inCodeBlock = false;
        StringBuilder codeBuffer = new StringBuilder();

        for (String line : lines) {
            String trimmed = line.trim();

            if (trimmed.startsWith("```")) {
                if (!inCodeBlock) {
                    addTextSection(sections, buffer);
                    inCodeBlock = true;
                    codeBuffer.setLength(0);
                } else {
                    sections.add(new PostContent(ContentType.CODE, codeBuffer.toString().trim()));
                    inCodeBlock = false;
                }
                continue;
            }

            if (inCodeBlock) {
                codeBuffer.append(line).append("\n");
                continue;
            }

            if (trimmed.startsWith("[image:") && trimmed.endsWith("]")) {
                addTextSection(sections, buffer);

                String path = trimmed.substring(7, trimmed.length() - 1).trim();
                sections.add(new PostContent(ContentType.IMAGE, path));
                continue;
            }

            if (trimmed.startsWith("[video:") && trimmed.endsWith("]")) {
                addTextSection(sections, buffer);

                String path = trimmed.substring(7, trimmed.length() - 1).trim();
                sections.add(new PostContent(ContentType.VIDEO, path));
                continue;
            }

            buffer.append(line).append("\n");
        }

        if (inCodeBlock) {
            sections.add(new PostContent(ContentType.CODE, codeBuffer.toString().trim()));
        } else {
            addTextSection(sections, buffer);
        }

        return sections;
    }

    private void addTextSection(ArrayList<PostContent> sections, StringBuilder buffer) {
        String text = buffer.toString().trim();

        if (!text.isEmpty()) {
            sections.add(new PostContent(ContentType.TEXT, text));
        }

        buffer.setLength(0);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = App.setRootWithLoader("contributorDashboard");
            ContributorDashboardController controller = loader.getController();
            controller.setUser(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Create Question Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}