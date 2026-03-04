package com.model;

import java.util.ArrayList;

public class PostManager 
{
    private static PostManager postManager;
    private ArrayList<QuestionPost> questionPosts;


    public PostManager()
    {
        questionPosts = new ArrayList<>();
        return;
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
        return questionPosts;
    }

    public ArrayList<SolutionPost> getSolution()
    {
        return null;
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