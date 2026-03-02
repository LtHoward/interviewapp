package com.model;
import java.util.ArrayList;

public class InterviewApp {
    private PostManager postmanager;
    private QuestionManager questionmanager;
    private SavedQuestion savedQuestion;
    private UserManager usermanager;
    private User currentuser;

    public InterviewApp() 
    {
        this.postmanager = new PostManager();
        this.questionmanager = new QuestionManager();
        this.currentuser = new CurrentUser();
        this.savedQuestion = new SavedQuestion();

    }

    public boolean removeUser(User user)
    {
      if (user == null)
      return false; // nobody logged in then it failed 

     return usermanager.removeUser(); // someone is logged in, remove them
        
    }

    public ArrayList<User> getAllUsers()
    {
        return usermanager.getUser();
        
    }

    public boolean createUser(String username, String email, String password, String firstName, String lastName)
    {
       return usermanager.createUser(username, email, password, firstName, lastName);

    
    }

    public boolean login(String username, String email, String password)
    {
        return UserManager.login(username, email, password);

    }

    public boolean logout()
    {
        return UserManager.logout();

    }

    public boolean resetPassword(String username, String newPassword)
    {
        return UserManager.resetPassword(username, newPassword);

    }

    public boolean addQuestion (QuestionPost question)
    {
        return QuestionManager.addQuestion(question);

    }

    public boolean addSolution(SolutionPost solution)
    {
        return QuestionManager.addSolution(solution);

    }

    public ArrayList<Question> getQuestion(String title)
    {
        return QuestionManager.getQuestion(title);

    }

    public boolean addSavedQuestion()
    {
        return QuestionManger.addSavedQuestion();

    }

    public void comment(user User, postId UUID, String content)
    {
        return UserManager.comment(User, UUID, content);

    }

    public boolean removeSavedQuestion()
    {
        return QuestionManager.removeSavedQuestion();

    } 

}
