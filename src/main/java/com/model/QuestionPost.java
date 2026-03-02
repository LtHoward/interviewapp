package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class QuestionPost extends Post {

    private String title;
    private Difficulty difficulty;
    private String hint;

    public QuestionPost(UUID postId, User author, Date createdAt,
                ArrayList<Comment> comments, ArrayList<String> tags,
                ArrayList<PostContent> contentSections, int score,
                String title,Difficulty difficulty, String hint) {

        super(postId, author, createdAt, comments, tags, contentSections, score);
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