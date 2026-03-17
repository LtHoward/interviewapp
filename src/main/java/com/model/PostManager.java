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
        return questionPosts;
    }

    public ArrayList<SolutionPost> getSolution()
    {
        return solutionPosts;
    }

    public boolean addQuestion(QuestionPost question)
    {
        return questionPosts.add(question);
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
        return new ArrayList<>(questionPosts);
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