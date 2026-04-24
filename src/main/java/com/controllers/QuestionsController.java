package com.controllers;

import com.interviewapp.App;
import com.model.PostContent;
import com.model.QuestionPost;
import com.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

public class QuestionsController {

    @FXML private Label questionTitleLabel;
    @FXML private VBox leftCard;
    @FXML private VBox rightCard;
    @FXML private TextField answerField;

    private QuestionPost currentQuestion;
    private User currentUser;

    @FXML
    public void initialize() {
        // populated via setQuestion()
    }

    public void setQuestion(QuestionPost question) {
        this.currentQuestion = question;

        if (questionTitleLabel != null) {
            questionTitleLabel.setText(question.getTitle());
        }

        if (leftCard != null) {
            leftCard.getChildren().clear();

            String diff = question.getDifficulty() != null
                ? question.getDifficulty().toString() : "Unknown";
            Label diffLabel = new Label("Difficulty: " + diff);
            diffLabel.getStyleClass().add("search-section-label");
            diffLabel.setStyle("-fx-text-fill: white;");
            leftCard.getChildren().add(diffLabel);

            if (question.getTags() != null && !question.getTags().isEmpty()) {
                Label tagsLabel = new Label("Tags: " + String.join(", ", question.getTags()));
                tagsLabel.getStyleClass().add("search-row-label");
                tagsLabel.setStyle("-fx-text-fill: white;");
                tagsLabel.setWrapText(true);
                leftCard.getChildren().add(tagsLabel);
            }

            javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
            spacer.setPrefHeight(10);
            leftCard.getChildren().add(spacer);

            if (question.getContentSections() != null) {
                for (PostContent section : question.getContentSections()) {
                    Label contentLabel = new Label(section.toString());
                    contentLabel.setWrapText(true);
                    contentLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
                    VBox.setMargin(contentLabel, new Insets(4, 0, 4, 0));
                }}}}}