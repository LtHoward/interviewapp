package com.model;
import java.util.ArrayList;

public class InterviewApp {
    private PostManager postmanager;
    private QuestionManager questionmanager;
    private CurrentUser user;
    private SavedQuestion savedQuestion;
    private UserManager usermanager;

    public InterviewApp() 
    {
        this.postmanager = new postmanager();
        this.questionmanager = new questionmanager();
        this.currentuser = new user();
        this.savedQuestion = new savedQuestion();

    }

    public boolean removeUser(User user)
    {
      if (user == null)
    return false; // nobody logged in then it failed 

   return usermanager.removeUser(); // someone is logged in, remove them
        
    }

    public ArrayList<User> getAllUsers()
    {
        
        
    }

    public boolean createUser(String username, String email, String password, String firstName, String lastName)
    {

    
    }

    public boolean login(String username, String email, String password)
    {

    }

    public boolean logout()
    {

    }

    public boolean resetPassword(String username, String newPassword)
    {

    }

    public boolean addQuestion (QuestionPost question)
    {

    }

    public boolean addSolution(SolutionPost solution)
    {

    }

    public ArrayList<Question> getQuestion(String title)
    {

    }

    public boolean addSavedQuestion()
    {

    }

    public void comment(user User, postId UUID, String content)
    {

    }

    public boolean removeSavedQuestion()
    {

    } 

}
