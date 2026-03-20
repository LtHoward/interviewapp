package com.model;

import java.util.ArrayList;

public class InterviewApp {
    private PostManager postManager;
    private ArrayList<Post> savedPost;
    private UserManager userManager;
    private User currentUser;

    public InterviewApp() 
    {        
        postManager = PostManager.getInstance();
        savedPost = new ArrayList<>();
        userManager = UserManager.getInstance();
        currentUser = null;

    }

    public boolean removeUser(User user)
    {
      if (user == null) return false;
      return userManager.removeAccount(user);
    }

    public ArrayList<User> getAllUsers()
    {
        return userManager.getAllUsers();
        
    }

    public ArrayList<Post> getAllPosts() {
        return PostManager.getInstance().getAllPosts();
    }

    public User getUser(String username, String password) 
    {
        return userManager.getUser(username);
    }

    public boolean createUser(String username, String email, String password, String firstName, String lastName, Role role, Major major, Year year)
    {
       return userManager.addUser(username, email, password, firstName, lastName, role, major, year);
    }

    public boolean login(String username, String email, String password) {
        return userManager.login(username, email, password);
    }

    public boolean logout()
    {
        return userManager.logout();

    }

    /**
     * Method to reset the password for the current user if the old password is right
     * and the new password is different from the old password.
     * @param oldPass the users current password
     * @param newPass the users new password
     * @return true if the password was successfully reset, false otherwise.
     * @author Myila Howard
     */
    public boolean resetPassword(String oldPass, String newPass)
    {
        if (currentUser != null && currentUser.getPassword().equals(oldPass)) {
            return userManager.resetPassword(currentUser.getUsername(), newPass);
        }
        return false;
    }

    public boolean addQuestion (QuestionPost question)
    {
        return postManager.addQuestion(question);

    }

    public boolean addSolution(QuestionPost question, User user, SolutionPost solution)
    {
        return postManager.addSolution(question, user, solution);

    }

    public ArrayList<QuestionPost> getQuestion(String title)
    {
        return postManager.getQuestion(title);

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
    return userManager.userExist(username);
    }
}
