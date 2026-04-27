package com.controllers;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import com.interviewapp.App;
import com.model.PostManager;
import com.model.QuestionPost;
import com.model.SolutionPost;
import com.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class SearchSolutionsController {

    @FXML private TextField searchField;
    @FXML private TextField tagInputField;
    @FXML private FlowPane selectedTagsContainer;
    @FXML private VBox resultsContainer;

    private User currentUser;
    private QuestionPost parentQuestion;
    private final Set<String> selectedTags = new LinkedHashSet<>();

    @FXML
    public void initialize() {
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            loadSolutions(newVal.trim());
        });

        tagInputField.setOnAction(e -> handleAddTag(null));
        refreshSelectedTags();
    }

    public void setData(User user, QuestionPost question) {
        this.currentUser = user;
        this.parentQuestion = question;
        loadSolutions("");
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = App.setRootWithLoader("questionPost");
            QuestionsController controller = loader.getController();
            controller.setData(currentUser, parentQuestion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        loadSolutions(searchField.getText().trim());
    }

    @FXML
    private void handleAddTag(ActionEvent event) {
        String tag = normalizeTag(tagInputField.getText());

        if (tag.isEmpty() || selectedTags.contains(tag)) {
            return;
        }

        selectedTags.add(tag);
        tagInputField.clear();
        refreshSelectedTags();
        loadSolutions(searchField.getText().trim());
    }

    @FXML
    private void handleClearTags(ActionEvent event) {
        selectedTags.clear();
        refreshSelectedTags();
        loadSolutions(searchField.getText().trim());
    }

    private void loadSolutions(String keyword) {
        resultsContainer.getChildren().clear();

        if (parentQuestion == null) {
            addEmptyResult("No question selected.");
            return;
        }

        ArrayList<SolutionPost> allSolutions = PostManager.getInstance().getAllSolutions();

        for (SolutionPost solution : allSolutions) {
            if (!solution.getQuestionId().equals(parentQuestion.getPostId())) {
                continue;
            }

            if (!matchesKeyword(solution, keyword)) {
                continue;
            }

            if (!matchesTags(solution)) {
                continue;
            }

            Button btn = new Button(solution.getTitle());
            btn.getStyleClass().add("search-result-button");
            btn.setUserData(solution);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setPrefHeight(70);
            btn.setOnAction(this::handleSolutionClick);

            resultsContainer.getChildren().add(btn);
        }

        if (resultsContainer.getChildren().isEmpty()) {
            addEmptyResult("No solutions found.");
        }
    }

    private boolean matchesKeyword(SolutionPost solution, String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return true;
        }

        String lowerKeyword = keyword.toLowerCase();

        if (solution.getTitle() != null
                && solution.getTitle().toLowerCase().contains(lowerKeyword)) {
            return true;
        }

        if (solution.getTags() != null) {
            for (String tag : solution.getTags()) {
                if (tag != null && tag.toLowerCase().contains(lowerKeyword)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean matchesTags(SolutionPost solution) {
        if (selectedTags.isEmpty()) {
            return true;
        }

        if (solution.getTags() == null || solution.getTags().isEmpty()) {
            return false;
        }

        for (String selectedTag : selectedTags) {
            boolean foundMatch = false;

            for (String solutionTag : solution.getTags()) {
                if (solutionTag != null && solutionTag.equalsIgnoreCase(selectedTag)) {
                    foundMatch = true;
                    break;
                }
            }

            if (!foundMatch) {
                return false;
            }
        }

        return true;
    }

    private void handleSolutionClick(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        SolutionPost selectedSolution = (SolutionPost) clicked.getUserData();

        try {
            FXMLLoader loader = App.setRootWithLoader("solutionPost");
            SolutionsController controller = loader.getController();
            controller.setData(currentUser, selectedSolution, parentQuestion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshSelectedTags() {
        selectedTagsContainer.getChildren().clear();

        for (String tag : selectedTags) {
            Button tagButton = new Button("#" + tag + "  x");
            tagButton.getStyleClass().add("search-tag-chip");

            tagButton.setOnAction(e -> {
                selectedTags.remove(tag);
                refreshSelectedTags();
                loadSolutions(searchField.getText().trim());
            });

            selectedTagsContainer.getChildren().add(tagButton);
        }
    }

    private String normalizeTag(String tag) {
        if (tag == null) {
            return "";
        }

        String normalized = tag.trim().toLowerCase();

        if (normalized.startsWith("#")) {
            normalized = normalized.substring(1);
        }

        return normalized;
    }

    private void addEmptyResult(String message) {
        Button empty = new Button(message);
        empty.getStyleClass().add("search-result-empty");
        empty.setMaxWidth(Double.MAX_VALUE);
        empty.setDisable(true);
        resultsContainer.getChildren().add(empty);
    }
}