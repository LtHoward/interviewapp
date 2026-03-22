package com.model;

import java.util.ArrayList;

/**
 * Class to represent the interview application.
 * This class manages the users, posts, questions, solutions, and comments.
 */
public class InterviewApp {
    private PostManager postManager;
    private ArrayList<Post> savedPost;
    private UserManager userManager;
    private User currentUser;

    /**
     * Construtor that initializes the managers and saved posts 
     */
    public InterviewApp() 
    {        
        postManager = PostManager.getInstance();
        savedPost = new ArrayList<>();
        userManager = UserManager.getInstance();
        currentUser = null;

    }

    /**
     * Method to remove a user
     * @param user the user to be removed 
     * @return true if the user was successfuly removed, false otherwise
     */
    public boolean removeUser(User user)
    {
      if (user == null) return false;
      return userManager.removeAccount(user);
    }

    /**
     * Method to get the user in the userlist
     * @return the users in the userlist
     */
    public ArrayList<User> getAllUsers()
    {
        return userManager.getAllUsers();
        
    }

    /**
     * Method to get all the post in the post list
     * @return all the post in the post list
     */
    public ArrayList<Post> getAllPosts() {
        return PostManager.getInstance().getAllPosts();
    }

    /**
     * Method to get the user
     * @param username the username of the user to be retrieved
     * @param password the password of the user to be retrieved
     * @return the user if found
     */
    public User getUser(String username, String password) {
        User user = userManager.getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return user;
        }
        return null;
    }

    /**
     * Method to create a user with the given attributes
     * @param username the username of the user to be created
     * @param email the email of the usr to be created
     * @param password the password of the user to be created
     * @param firstName the first name of the user to be created
     * @param lastName the last name of the user to be created
     * @param role the role of the user to be created
     * @param major the major of the user to be created
     * @param year the year of the user to be created 
     * @return true if the user was successfuly created, false otherwise
     */
    public boolean createUser(String username, String email, String password, String firstName, String lastName, Role role, Major major, Year year)
    {
       return userManager.addUser(username, email, password, firstName, lastName, role, major, year);
    }

    /**
     * Method to check if a user was logged in
     * @param username the username of the user to login
     * @param email the email of the user to login
     * @param password the password of the user to loin
     * @return true if the user was successfuly logged in
     */
    public boolean login(String username, String email, String password) {
        return userManager.login(username, email, password);
    }

    /**
     * Method to check if a user successfuly logged out
     * @return true if a user was seuccessfuly logged out, false otherwise
     */
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

    /**
     * Method to check of a question was add
     * @param question the question to be added
     * @return true if the question was successful added
     */
    public boolean addQuestion (QuestionPost question)
    {
        return postManager.addQuestion(question);

    }

    /**
     * Method to check if a solution was successfuly added
     * @param question the question to be added with the solution
     * @param user the user that wants to add a soltuion
     * @param solution the solution to be added
     * @return true if the solution was added
     */
    public boolean addSolution(QuestionPost question, User user, SolutionPost solution)
    {
        return postManager.addSolution(question, user, solution);

    }

    /**
     * Method to get the question
     * @param title the title of the question
     * @return the question
     */
    public ArrayList<QuestionPost> getQuestion(String title)
    {
        return postManager.getQuestion(title);

    }

    /**
     * Method to add a saved question
     * @return true if the question was saved
     */
    public boolean addSavedQuestion()
    {
        return postManager.save();

    }

    /**
     * Method to add a comment to a post
     * @param post the post the comment is to be added to 
     * @param comment the comment to be added
     * @return the comment
     */
    public boolean addComment(Post post,Comment comment)
    {
        return postManager.addComment(post, comment);
    }

    /**
     * Method to get the comment
     * @param post the post the comment is on
     * @return the comment
     */
    public ArrayList<Comment> getComments(Post post)
    {
        return postManager.getComments(post);
    }

    /**
     * Method to remove a saved question
     * @return true if the question was successfuly removed, false otherwise
     */
    public boolean removeSavedQuestion()
    {
        return postManager.save();

    } 

    /**
     * Method to check if the system has a user 
     * @param username the username of the user to check for
     * @return the user if the user is in the system
     */
    public boolean haveUser(String username) 
    {
    return userManager.userExist(username);
    }
}
