package com.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.interviewapp.App;
import com.model.Comment;
import com.model.ContentType;
import com.model.PostContent;
import com.model.PostManager;
import com.model.QuestionPost;
import com.model.SolutionPost;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateSolutionPostController {

    @FXML private TextField titleField;
    @FXML private TextField tagsField;
    @FXML private TextArea contentArea;

    private User currentUser;
    private QuestionPost parentQuestion;

    public void setData(User user, QuestionPost question) {
        this.currentUser = user;
        this.parentQuestion = question;
    }

    @FXML
    private void toggleBold(ActionEvent event) {
        wrapSelectedText("**", "**");
    }

    @FXML
    private void toggleItalic(ActionEvent event) {
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

    @FXML
    private void previewPost(ActionEvent event) {
        SolutionPost preview = buildSolutionPost();

        if (preview == null) {
            return;
        }

        try {
            FXMLLoader loader = App.setRootWithLoader("solutionPost");
            SolutionsController controller = loader.getController();
            controller.setData(currentUser, preview, parentQuestion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void createPost(ActionEvent event) {
        SolutionPost solution = buildSolutionPost();

        if (solution == null) {
            return;
        }

        boolean added;

        if (parentQuestion != null) {
            added = PostManager.getInstance().addSolution(parentQuestion, currentUser, solution);
        } else {
            added = PostManager.getInstance().addSolution(currentUser, solution);
        }

        if (added) {
            PostManager.getInstance().save();

            try {
                FXMLLoader loader = App.setRootWithLoader("questionPost");
                QuestionsController controller = loader.getController();

                if (parentQuestion != null) {
                    controller.setData(currentUser, parentQuestion);
                } else {
                    App.setRootWithLoader("search");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showError("Solution could not be created.");
        }
    }

    private SolutionPost buildSolutionPost() {
        if (currentUser == null) {
            showError("No logged-in user found.");
            return null;
        }

        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();

        if (title.isEmpty()) {
            showError("Please enter a title.");
            return null;
        }

        if (content.isEmpty()) {
            showError("Please enter solution content.");
            return null;
        }

        UUID questionId = parentQuestion == null
            ? UUID.randomUUID()
            : parentQuestion.getPostId();

        int solutionNumber = PostManager.getInstance().getAllSolutions().size() + 1;

        return new SolutionPost(
            UUID.randomUUID(),
            title,
            currentUser,
            new Date(),
            new ArrayList<Comment>(),
            parseTags(tagsField.getText()),
            parseContentSections(content),
            0,
            solutionNumber,
            questionId
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
                sections.add(new PostContent(ContentType.IMAGE, trimmed.substring(7, trimmed.length() - 1).trim()));
                continue;
            }

            if (trimmed.startsWith("[video:") && trimmed.endsWith("]")) {
                addTextSection(sections, buffer);
                sections.add(new PostContent(ContentType.VIDEO, trimmed.substring(7, trimmed.length() - 1).trim()));
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
    private void handleBack(ActionEvent event) {
        try {
            if (parentQuestion != null) {
                FXMLLoader loader = App.setRootWithLoader("questionPost");
                QuestionsController controller = loader.getController();
                controller.setData(currentUser, parentQuestion);
            } else {
                FXMLLoader loader = App.setRootWithLoader("search");
                SearchController controller = loader.getController();
                controller.setUser(currentUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Create Solution Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}