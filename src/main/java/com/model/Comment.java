package com.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Class to represent a comment. It manages the users comments, replies, date of the comment/post. 
 */
public class Comment {
    private User commentAuthor;
    private LocalDate commentDate;
    private String commentContent;
    private UUID commentId;
    private UUID postId;
    private ArrayList<Comment> reply;

    /**
     * Constructor for the Comment class with the given parameters
     * @param commentId the id of the comment to be created
     * @param commentAuthor the author of the comment to be created
     * @param commentContent the content of the comment to be created
     * @param postId the id of the post to be created
     * @param commentDate the date the comment was made
     */
    public Comment (UUID commentId, User commentAuthor, String commentContent, UUID postId, LocalDate commentDate) {
        this.commentAuthor = commentAuthor;
        this.commentDate = commentDate;
        this.commentContent = commentContent;
        this.commentId = commentId;
        this.postId = postId;
        this.reply = new ArrayList<>();
    }

    /**
     * Method to get the author of the comment/post
     * @return the author of the comment
     */
    public User getAuthor() {
        return commentAuthor;
    }
    
    /**
     * Method to for the date the comment was made
     * @return the date of the comment
     */
    public LocalDate getCommentDate() {
        return commentDate;
    }

    /**
     * Method to get the contents of the comment
     * @return the content of the comment
     */
    public String getContent() {
        return commentContent;
    }

    /**
     * Method to get the id of the comment
     * @return the comment id 
     */
    public UUID getCommentId() {
        return commentId;
    }

    /**
     * Method to get the post id
     * @return the id of the post
     */
    public UUID getPostId() {
        return postId;
    }

    /**
     * Method to get the reply 
     * @return the reply
     */
    public ArrayList<Comment> getReply(){
        return reply;
    }

    /**
     * Method to add a reply to a comment
     * @param comment the comment for the reply
     * @return true if the reply was added to the comment, false if otherwise
     */
    public boolean addReply(Comment comment) {
        if(reply == null) return false;
        reply.add(comment);
        return true;
    }

}

