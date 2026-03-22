package com.model;

import java.util.ArrayList;
import java.util.UUID;
/*
author @Michelle Ihetu
*/
public class PostManager 
{
    private static PostManager postManager;
    private ArrayList<QuestionPost> questionPosts;
    private ArrayList<SolutionPost> solutionPosts;

/**
 * Constructs a new PostManager instance, initializing empty lists
 * for question posts and solution posts.
 */
    public PostManager()
    {
        questionPosts = new ArrayList<>();
        solutionPosts = new ArrayList<>();
        return;
    }
/**
 * Returns the singleton instance of PostManager, creating it if it does not yet exist.
 *
 * @return the single shared instance of {@link PostManager}
 */
    public static PostManager getInstance() 
    {
        if(postManager == null) {
            postManager = new PostManager();
        }
        return postManager;
    }
    /**
 * Returns a combined list of all question posts and solution posts.
 *
 * @return an {@link ArrayList} containing all {@link Post} objects
 */

    public ArrayList<Post> getAllPosts() {
        ArrayList<Post> allPosts = new ArrayList<>();
        allPosts.addAll(questionPosts);
        allPosts.addAll(solutionPosts);
        return allPosts;
    }
/**
 * Searches for question posts that match the given title (case-insensitive).
 *
 * @param title the title to search for
 * @return an {@link ArrayList} of {@link QuestionPost} objects whose titles match the given title,
 *         or an empty list if no matches are found
 */
public ArrayList<QuestionPost> getQuestion(String title)
{
    ArrayList<QuestionPost> matchingPosts = new ArrayList<>();

    for (QuestionPost question : questionPosts)
    {
        if (question.getTitle().equalsIgnoreCase(title))
        {
            matchingPosts.add(question);
        }
    }

    return matchingPosts;
}
    
/**
 * Returns a list of solution posts up to the specified count.
 *
 * @param solutionNumber the number of solution posts to retrieve
 * @return an {@link ArrayList} containing the first {@code solutionNumber} {@link SolutionPost} objects,
 *         or an empty list if {@code solutionNumber} exceeds the total number of solution posts
 */

    public ArrayList<SolutionPost> getSolution(int solutionNumber)
    {
        if(solutionPosts.size() < solutionNumber)
        {
            System.out.println("Error! System out of bounds");
            return new ArrayList<>();
        }
        else
            return new ArrayList<>(solutionPosts.subList(0, solutionNumber));
    }
/**
 * Adds a question post to the list of question posts.
 *
 * @param question the {@link QuestionPost} to add
 * @return {@code true} if the post was successfully added, {@code false} otherwise
 */
    public boolean addQuestion(QuestionPost question)
    {
        return questionPosts.add(question);
    }
/**
 * Adds a solution post associated with an existing question post and user.
 *
 * @param question the {@link QuestionPost} the solution is responding to
 * @param user     the {@link User} submitting the solution
 * @param solution the {@link SolutionPost} to add
 * @return {@code true} if the solution was successfully added,
 *         {@code false} if the solution or question is {@code null},
 *         or if the question does not exist in the system
 */
    
    public boolean addSolution(QuestionPost question, User user, SolutionPost solution)
    {
       if(solution == null || question == null)
        return false;
    if (!questionPosts.contains(question))
        return false;
    return solutionPosts.add(solution);

    }

    /**
 * Edits a specific content section of an existing post if the user is the author.
 *
 * @param user       the {@link User} attempting to edit the post
 * @param post       the {@link Post} to be edited
 * @param newContent the {@link PostContent} to replace the existing content section
 * @param index      the index of the content section to replace
 * @return {@code true} if the post was successfully edited,
 *         {@code false} if the post does not exist, the user is not the author,
 *         or the index is out of bounds
 */
    public boolean editPost(User user, Post post, PostContent newContent, int index)
    {
       if(!questionPosts.contains(post) && !solutionPosts.contains(post))
        return false;
       if(!post.getAuthor().equals(user))
        return false;
       if(index > post.getContentSections().size())
            return false;
         post.getContentSections().set(index, newContent);
         return true;
        
    }

/**
 * Deletes an existing post if the user is the author.
 *
 * @param user the {@link User} attempting to delete the post
 * @param post the {@link Post} to be deleted
 * @return {@code true} if the post was successfully deleted,
 *         {@code false} if the post does not exist or the user is not the author
 */
    public boolean deletePost(User user, Post post)
    {
        if(!questionPosts.contains(post) && !solutionPosts.contains(post))
        return false;
    if(!post.getAuthor().equals(user))
        return false;
    if(questionPosts.contains(post))
        return questionPosts.remove(post);
    return solutionPosts.remove(post);
    }
/**
 * Searches for question posts whose titles contain the given keyword (case-insensitive).
 *
 * @param title the keyword to search for within post titles
 * @return an {@link ArrayList} of {@link QuestionPost} objects whose titles contain the keyword,
 *         or an empty list if no matches are found
 */
   
    public ArrayList<QuestionPost> getQuestionsByKeyWord(String title)
    {
        ArrayList<QuestionPost> matchingPosts = new ArrayList<>();
    
    for(QuestionPost question : questionPosts)
    {
        if(question.getTitle().toLowerCase().contains(title.toLowerCase()))
        {
            matchingPosts.add(question);
        }
    }
    
    return matchingPosts;
    }
/**
 * Returns a copy of all question posts.
 *
 * @return an {@link ArrayList} containing all {@link QuestionPost} objects
 */ 
    public ArrayList<QuestionPost> getAllQuestions()
    {
        return new ArrayList<>(questionPosts);
    }

    /**
     * Gets a question by its unique identifier.
     * @param id The UUID of the question post to retrieve.
     * @return The QuestionPost with the specified UUID, or null if not found.
     * @author Dorian Rhone
     */
    public QuestionPost getQuestionById(UUID id)
    {
        for (QuestionPost question : questionPosts) 
            {
            if (question.getPostId().equals(id))
            {
                return question;
            }
        }
        return null; 
    }
     /**
 * Adds a comment to an existing post.
 *
 * @param post    the {@link Post} to add the comment to
 * @param comment the {@link Comment} to add
 * @return {@code true} if the comment was successfully added,
 *         {@code false} if the post or comment is {@code null},
 *         or if the post does not exist in the system
 */  
    public boolean addComment(Post post, Comment comment) {
        if(post == null || comment == null)
        return false;
        if(!questionPosts.contains(post) && !solutionPosts.contains(post))
        return false;
        post.addComment(comment);
        return true;
    
    }

/**
 * Returns all comments associated with the given post.
 *
 * @param post the {@link Post} to retrieve comments from
 * @return an {@link ArrayList} of {@link Comment} objects for the given post,
 *         or an empty list if the post is {@code null}
 */
    public ArrayList<Comment> getComments(Post post)
    {   
        if(post == null)
        return new ArrayList<>();
        return post.getComments();
    }
/**
 * Saves the current state of the PostManager.
 *
 * @return {@code true} if the save was successful
 */
    public boolean save()  
    {
        return true;
    }
}