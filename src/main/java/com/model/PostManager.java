package com.model;

import java.util.ArrayList;

public class PostManager 
{
    private static PostManager postManager;
    private ArrayList<Post> posts;

    public PostManager()
    {
        return;
    }

    public static PostManager getInstance() 
    {
        
        return null;
    }

    
    public boolean addPost(Contributor contributor, Post post)
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
   
    public ArrayList<Post> getPostByKeyWord(String title)
    {
        return null;
    }

   
    public ArrayList<Post> getAllPost()
    {
        return null;
    }

   
    public boolean addComment()
    {
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