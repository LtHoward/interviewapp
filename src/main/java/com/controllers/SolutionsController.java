package com.controllers;

import com.model.User;
import com.interviewapp.App;
import com.model.QuestionPost;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class SolutionsController {
    
    private User currentUser;

    private QuestionPost selectedQuestion;

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = App.setRootWithLoader("questionPost");
            QuestionsController controller = loader.getController();
            controller.setData(currentUser, selectedQuestion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   

}