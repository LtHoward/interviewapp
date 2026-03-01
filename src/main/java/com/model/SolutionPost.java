package com.model;

import java.util.UUID;

public class SolutionPost extends Post {

    private int solutionNumber;
    private UUID questionId;

    public SolutionPost(User author,
                        int solutionNumber,
                        UUID questionId) {

        super(author);
        this.solutionNumber = solutionNumber;
        this.questionId = questionId;
    }

    public int getSolutionNumber() {
        return solutionNumber;
    }

    public UUID getQuestionId() {
        return questionId;
    }
}