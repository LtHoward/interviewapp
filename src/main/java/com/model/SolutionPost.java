package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * A class representing the post of a solution. Handles the number of the solution and the question id that 
 * goes with the solution. It also extends the Post class
 */
public class SolutionPost extends Post {

    private int solutionNumber;
    private UUID questionId;

    /**
     * Constructor for the SolutionPost class with the following parameters 
     * @param postId the id for the post 
     * @param title the title of the post 
     * @param author the author of the post
     * @param createdAt the date the post was created at
     * @param comments the comment for the post
     * @param tags the tags of the post
     * @param contentSections the content section of the post
     * @param score the score of the post
     * @param solutionNumber the number of the solution
     * @param questionId the id of the question that goes with the solution post
     */
    public SolutionPost(UUID postId, String title, User author, Date createdAt,
                ArrayList<Comment> comments, ArrayList<String> tags,
                ArrayList<PostContent> contentSections, int score,
                int solutionNumber, UUID questionId) {

        super(postId, title, author, createdAt, comments, tags, contentSections, score);
        this.solutionNumber = solutionNumber;
        this.questionId = questionId;
    }

    /**
     * Method to get the solution number
     * @return the solution number
     */
    public int getSolutionNumber() {
        return solutionNumber;
    }

    /**
     * Method to get the question id
     * @return the question id
     */
    public UUID getQuestionId() {
        return questionId;
    }

    /**
     * Method to get the type of the post
     * @return "Soltuion"
     */
    @Override 
    public String getType() {
        return "SOLUTION";
    }
}