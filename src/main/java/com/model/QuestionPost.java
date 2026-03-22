package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * A class representation for the question post that extend the Post class.
 * Managaes the difficulty of a question, and the hint for a question and type
 */
public class QuestionPost extends Post {

    private Difficulty difficulty;
    private String hint;

    /**
     * Constructor for the QuestionPost class with the following parameters
     * @param postId the id for the post
     * @param title the title of the post
     * @param author the author of the post
     * @param createdAt the date the post was created at
     * @param comments the comment for the post
     * @param tags the tags of the questions
     * @param contentSections the content section of the post
     * @param score the score of the question 
     * @param difficulty the difficulty type of the question
     * @param hint the hint for the question
     */
    public QuestionPost(UUID postId, String title, User author, Date createdAt,
                ArrayList<Comment> comments, ArrayList<String> tags,
                ArrayList<PostContent> contentSections, int score,
                Difficulty difficulty, String hint) {

        super(postId, title, author, createdAt, comments, tags, contentSections, score);
        this.difficulty = difficulty;
        this.hint = hint;
    }

    /**
     * Method to get the difficulty of the question
     * @return difficulty
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Method to get the hint of the question
     * @return hint
     */
    public String getHint() {
        return hint;
    }

    /**
     * Method to get the type for the pquestion post 
     * @return "Question"
     */
    @Override
    public String getType() {
        return "QUESTION";
    }
}