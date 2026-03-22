package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class QuestionPost extends Post {

    private Difficulty difficulty;
    private String hint;

    public QuestionPost(UUID postId, String title, User author, Date createdAt,
                ArrayList<Comment> comments, ArrayList<String> tags,
                ArrayList<PostContent> contentSections, int score,
                Difficulty difficulty, String hint) {

        super(postId, title, author, createdAt, comments, tags, contentSections, score);
        this.difficulty = difficulty;
        this.hint = hint;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getHint() {
        return hint;
    }

    @Override
    public String getType() {
        return "QUESTION";
    }
}