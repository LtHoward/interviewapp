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
<<<<<<< HEAD
        
=======
        solutionPosts = new ArrayList<>();
        return;
>>>>>>> 1932ee516e694e60dc5b3e73567df77ad94b7b6b
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
<<<<<<< HEAD

        questionPosts = new ArrayList<>();
        return questionPosts;
=======
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
>>>>>>> 1932ee516e694e60dc5b3e73567df77ad94b7b6b
    }

    public boolean addQuestion(QuestionPost question)
    {
<<<<<<< HEAD
        solutionPosts= new ArrayList<>();
        return solutionPosts;
    }

    public boolean addQuestion(Contributor contributor, QuestionPost question)
    {
        return false;
=======
        return questionPosts.add(question);
>>>>>>> 1932ee516e694e60dc5b3e73567df77ad94b7b6b
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
<<<<<<< HEAD
        return questionPosts;
=======
        return new ArrayList<>(questionPosts);
>>>>>>> 1932ee516e694e60dc5b3e73567df77ad94b7b6b
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