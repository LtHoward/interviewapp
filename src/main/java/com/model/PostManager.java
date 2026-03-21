package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class PostManager 
{
    private static PostManager postManager;
    private ArrayList<QuestionPost> questionPosts;
    private ArrayList<SolutionPost> solutionPosts;


    public PostManager()
    {
        questionPosts = new ArrayList<>();
        solutionPosts = new ArrayList<>();
        return;
    }

    public static PostManager getInstance() 
    {
        if(postManager == null) {
            postManager = new PostManager();
        }
        return postManager;
    }

    public ArrayList<Post> getAllPosts() {
        ArrayList<Post> allPosts = new ArrayList<>();
        allPosts.addAll(questionPosts);
        allPosts.addAll(solutionPosts);
        return allPosts;
    }

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

    public boolean addQuestion(QuestionPost question)
    {
        return questionPosts.add(question);
    }

    
    public boolean addSolution(QuestionPost question, User user, SolutionPost solution)
    {
       if(solution == null || question == null)
        return false;
    if (!questionPosts.contains(question))
        return false;
    return solutionPosts.add(solution);


    }

    
    public boolean editPost(User user, Post post, PostContent newContent)
    {
       if(!questionPosts.contains(post) && !solutionPosts.contains(post))
        return false;
       if(!post.getAuthor().equals(user))
        return false;
         PostContent.setContent(newContent);
         return true;

    }

    
    public boolean deletePost(User user, Post post)
    {
        return false;
    }
   
    public ArrayList<QuestionPost> getQuestionsByKeyWord(String title)
    {
        return questionPosts; 
    }

   
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
       
    public boolean addComment(Comment comment) {
        return false;
    }

    
    public ArrayList<Comment> getComments()
    {
        return null;
    }
    
    public boolean save()  
    {
        return true;
    }
}