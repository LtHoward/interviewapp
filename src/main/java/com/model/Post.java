package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * A class that represents the post from the user. Is an abstract class that extends to questionpost and solutionpost.
 * 
 */
public abstract class Post {

    private UUID postId;
    private String title;
    private User author;
    private Date createdAt;
    private ArrayList<Comment> comments;
    private ArrayList<String> tags;
    private ArrayList<PostContent> contentSections;
    private int score;

    /**
     * Constructor for the Post class with the following parameters.
     * @param postId the id of the post 
     * @param title the title of the post
     * @param author the author of the post
     * @param createdAt the date the post was created
     * @param comments the comment for the post
     * @param tags the tags for the post
     * @param contentSections the content section for the post
     * @param score the score of the post
     */
    public Post(UUID postId, String title, User author, Date createdAt,
                ArrayList<Comment> comments, ArrayList<String> tags,
                ArrayList<PostContent> contentSections, int score) {

        this.postId = postId;
        this.title = title;
        this.author = author;
        this.createdAt = createdAt;
        this.comments = comments;
        this.tags = tags;
        this.contentSections = contentSections;
        this.score = score;
    }

    /**
     * Method for the author of the post
     * @param author the author 
     */
    public Post(User author) {
        this(UUID.randomUUID(),
            "Untitled",
            author,
             new Date(),
             new ArrayList<>(),
             new ArrayList<>(),
             new ArrayList<>(),
             0);
    }

    
    /**
     * Method to add a comment to the post
     * @param comment the comment to be added
     */
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    /**
     * Method to add the content section of the post
     * @param contentSection the content section of the post
     * @return true if the content was added, falseotherwise
     */
    public boolean addContentSection(PostContent contentSection) {
        if(contentSection == null) return false;
        contentSections.add(contentSection);
        return true;
    }

    /**
     * Method to get the post id
     * @return the id of the post
     */
    public UUID getPostId() {
        return postId;
    }

    /**
     * Method to get the comment of the post
     * @return the comment
     */
    public ArrayList<Comment> getComments() {
        return comments;
    }

    /**
     * Method to get the tags 
     * @return the tags
     */
    public ArrayList<String> getTags() {
        return tags;
    }

    /**
     * Method to get the type
     * @return the type of the post
     */
    public abstract String getType();

    /**
     * Method to get the title of the post
     * @return the title 
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method to get the author
     * @return the author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Method to get the content section of the post
     * @return the content sections
     */
    public ArrayList<PostContent> getContentSections() {
        return contentSections;
    }

    /**
     * Method to get the date the post was created at
     * @return the created date
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Method to get the score of the post
     * @return the score
     */
    public int getScore() {
        return score;
    }

    public int setScore(int score) {
        return this.score = score;
    }

    /**
     * Method to get the upvote
     * @param user the user that made the upvote
     * @return the score 
     */
    public int upvote(User user) {
        return ++score;
    }

    /**
     * Method to get the down vote of a post
     * @param user the user that made the down vote
     * @return the score
     */
    public int downvote(User user) {
        return --score;
    }

    /**
     * Method to get the score of the votes
     * @param upVote the upvote or like of a post
     * @param downVote the downvote or dislike of a post
     * @return the score or upvote - downvote
     */
    public int getScore(int upVote, int downVote) {
        return upVote - downVote;
    }
}