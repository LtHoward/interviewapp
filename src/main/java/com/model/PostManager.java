package com.model;

import java.util.ArrayList;

public class PostManager 
{
    private static PostManager postManager;
    private ArrayList<QuestionPost> questionPosts;
    private ArrayList<SolutionPost> solutionPosts;


    public PostManager()
    {
        questionPosts = new ArrayList<>();
        
    }

    public static PostManager getInstance() 
    {
        if(postManager == null) {
            postManager = new PostManager();
        }
        return postManager;
    }

    public ArrayList<QuestionPost> getQuestion(String title)
    {

        questionPosts = new ArrayList<>();
        return questionPosts;
    }

    public ArrayList<SolutionPost> getSolution()
    {
        solutionPosts= new ArrayList<>();
        return solutionPosts;
    }

    public boolean addQuestion(Contributor contributor, QuestionPost question)
    {
        return false;
    }

    
    public boolean addSolution(User user, SolutionPost solution)
    {
        return false;
    }

    
    public boolean editPost(User user, Post post)
    {
        return false;
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
        return questionPosts;
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