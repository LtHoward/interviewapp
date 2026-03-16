package com.model;

import java.util.UUID;
import java.util.ArrayList;

public class InterviewApp {
    private PostManager postManager;
    private ArrayList<Post> savedPost;
    private UserManager userManager;
    private User currentUser;

    public InterviewApp() 
    {        
        postManager = new PostManager();
        savedPost = new ArrayList<>();
        userManager = UserManager.getInstance();
        currentUser = null;

    }

    public boolean removeUser(User user)
    {
      if (user == null) return false;
      return userManager.removeUser(user);
    }

    public ArrayList<User> getAllUsers()
    {
        return userManager.getAllUsers();
        
    }

    public User getUser(String username, String password) 
    {
        return userManager.getUser(username, password);
    }

    public boolean createUser(String username, String email, String password, String firstName, String lastName)
    {
       return userManager.addUser(username, email, password, firstName, lastName);

    
    }

    public boolean login(String username, String email, String password) {
        return userManager.login(username, email, password);
    }

    public boolean logout()
    {
        return userManager.logout();

    }

    public boolean resetPassword(String password)
    {
        return userManager.resetPassword(password);

    }

    public boolean addQuestion (Contributor contributor, QuestionPost question)
    {
        return postManager.addQuestion(contributor, question);

    }

    public boolean addSolution(User user, SolutionPost solution)
    {
        return postManager.addSolution(user, solution);

    }

    public ArrayList<QuestionPost> getQuestion(String title)
    {
        return postManager.getQuestion(title);

    }

    public ArrayList<QuestionPost> getAllQuestions()
    {
        return postManager.getAllQuestions();
    }
    
    public boolean addSavedQuestion()
    {
        return postManager.save();

    }

    public boolean addComment(Comment comment)
    {
        return postManager.addComment(comment);

    }

    public boolean removeSavedQuestion()
    {
        return postManager.save();

    } 

    public boolean haveUser(String username) 
    {
    return userManager.haveUser(username);
    }
}
