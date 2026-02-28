package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract class Post {

    private UUID id;
    private User author;
    private Date createdAt;
    private ArrayList<Comment> comments;
    private ArrayList<String> tags;
    private ArrayList<PostContent> contentSections;
    private int score;

    public Post(UUID id,
                User author,
                Date createdAt,
                ArrayList<Comment> comments,
                ArrayList<String> tags,
                ArrayList<PostContent> contentSections,
                int score) {

    }

    public void addComment(Comment comment) {

    }

    public ArrayList<Comment> getComments() {
        return null;
    }

    public User getAuthor() {
        return null;
    }

    public Date getCreatedAt() {
        return null;
    }

    public int getScore() {
        return 0;
    }

    public int upvote(User user) {
        return 0;
    }

    public int downvote(User user) {
        return 0;
    }

    public int getScore(int upVote, int downVote) {
        return 0;
    }
}
