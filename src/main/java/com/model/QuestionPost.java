package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class QuestionPost extends Post {

    private String title;
    private Difficulty difficulty;
    private String hint;

    public QuestionPost(User author,
                        String title,
                        Difficulty difficulty,
                        String hint) {

        super(author);
        this.title = title;
        this.difficulty = difficulty;
        this.hint = hint;
    }

    public String getTitle() {
        return title;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getHint() {
        return hint;
    }
}