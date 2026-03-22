package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class SolutionPost extends Post {

    private int solutionNumber;
    private UUID questionId;

    public SolutionPost(UUID postId, String title, User author, Date createdAt,
                ArrayList<Comment> comments, ArrayList<String> tags,
                ArrayList<PostContent> contentSections, int score,
                int solutionNumber, UUID questionId) {

        super(postId, title, author, createdAt, comments, tags, contentSections, score);
        this.solutionNumber = solutionNumber;
        this.questionId = questionId;
    }

    public int getSolutionNumber() {
        return solutionNumber;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    @Override 
    public String getType() {
        return "SOLUTION";
    }
}