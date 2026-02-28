package com.interviewapp;
package com;

public class InterviewApp {
    private App app;
    private Post post;
    private QuestionManager questionmanager;
    private UserManager usermanager;
    private User user;
    private Student student;
    private Progression progression;
    private Reward reward;

    public InterviewApp(App app, Post post, QuestionManager questionmanager, UserManager usermanager, User user,Student student,Progression progression, Reward reward) 
    {
        this.app = App;
        this.post = Post;
        this.questionmanager = QuestionManager;
        this.usermanager = UserManager;
        this.student = Student;
        this.progression = Progression;
        this.reward = Reward;

    }

    public void StartApp(App app)
    {
        app.usermanager();
        app.postmanager();
        app.interviewapp();
        app.login();
        app.logout();
  

    }

    public void Post(Post post, User author, Date createdAt, ArrayList<comment>: comments, ArrayList<String>:tags, ArrayList<HashMap<String,String>>:contentSections, Int: score)
    {
        post.comments();
        post.id();
        post.createdAt();
        post.tags();
        post.contentSections();
        post.score();
    }

    public void UserManager(User user)
    {
        user.login();
        user.logout();
        user.createUser();
        user.removeUser();
        user.currentUser();
        user.resetPassword();

    
    }

    public void QuestionManager(QuestionManager qm)
    {
        qm.currentQuestion();
        qm.savedQuestion();
        qm.addQuestion();
        qm.removeQuestion();
        qm.addSolution();
        qm.getQuestion();

    }

    public void Student(Student student)
    {

    }

    public void Progression(Progression progression)
    {

    }

    public void Reward(Reward reward)
    {

    }

}
