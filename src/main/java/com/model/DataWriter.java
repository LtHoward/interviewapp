package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants 
{
    
    public static void saveUsers()
    {
        UserManager users = UserManager.getInstance();
        ArrayList<User> userManager = users.getUser();
        users.addUser("testUser1", "testEmail1", "testPassword12", "testFirstName12", "testLastName1");
        

        

        JSONArray jasonUsers = new JSONArray();

        for(int i = 0; i < userManager.size(); i++) 
        {
            jasonUsers.add(getUsersJSON(userManager.get(i)));
        }

        try (FileWriter file = new FileWriter(USERS_TEMP_FILE)) 
        {
            file.write(jasonUsers.toJSONString());
            file.flush();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }

    }
        public static JSONObject getUsersJSON(User user) 
        {
            JSONObject usersDetails = new JSONObject();
            usersDetails.put(USER_ID, user.getId().toString());
            usersDetails.put(USERNAME, user.getUsername());
            usersDetails.put(EMAIL, user.getEmail());
            usersDetails.put(PASSWORD, user.getPassword());
            usersDetails.put(FIRST_NAME, user.getFirstName());
            usersDetails.put(LAST_NAME, user.getLastName());

            if(user.getStatus() == Role.CONTRIBUTOR) 
            {
                usersDetails.put(ROLE, "CONTRIBUTOR");
                JSONObject contributorDetails = new JSONObject();
                Contributor contributor = (Contributor) user;
                contributorDetails.put(EXPERIENCE, contributor.getExperience());
                usersDetails.put("contributorDetails", contributorDetails);
            } 
            else if (user.getStatus() == Role.STUDENT) 
            {
                usersDetails.put(ROLE, "STUDENT");
                JSONObject studentDetails = new JSONObject();
                Student student = (Student) user;
                studentDetails.put(CURRENT_CLASSES, student.getCurrentClasses());
                studentDetails.put(CLASSES_TAKEN, student.getClassesTaken());
                studentDetails.put(MAJOR, student.getMajor());
                studentDetails.put(YEAR, student.getYear());
                studentDetails.put(SKILL_LEVEL, student.getSkillLevel());
                studentDetails.put(SOLVED_QUESTIONS, student.getSolvedQuestions());
                studentDetails.put(POSTED_SOLUTIONS, student.getPostedSolutions());
                studentDetails.put(LAST_ACTIVITY_DATE, student.getLastActivityDate());
                studentDetails.put(REWARDS, student.getRewards());
                studentDetails.put(TYPE, student.getType());
                studentDetails.put(AMOUNT, student.getAmount());
                studentDetails.put(REDEEMED, student.getRedeemed());
                usersDetails.put("studentDetails", studentDetails);
            } 
            else if (user.getStatus() == Role.ADMINISTRATOR) 
            {
                usersDetails.put(ROLE, "ADMINISTRATOR");
            }

            

            return usersDetails;
        }


        public static void main(String[] args) 
        {
            DataWriter.saveUsers();
        }

}


   public static void savePosts() 
    {
        PostManager postsInstance = PostManager.getInstance();
        ArrayList<Post> posts = postsInstance.getAllPost();

        JSONArray jsonPosts = new JSONArray();

        for (int i = 0; i < posts.size(); i++) 
        {
            jsonPosts.add(getPostsJSON(posts.get(i)));
        }

        try (FileWriter file = new FileWriter(POSTS_FILE)) 
        {
            file.write(jsonPosts.toJSONString());
            file.flush();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }

        public static JSONObject getPostsJSON(Post post) 
        {
            JSONObject postDetails = new JSONObject();
            postDetails.put(USER_ID, post.getId());
            postDetails.put(POST_TYPE, post.getType());
            postDetails.put(AUTHOR_ID, post.getAuthor());
            postDetails.put(CREATED_AT, post.getCreatedAt());
            postDetails.put(SCORE, post.getScore());
            return postDetails;
        }

        public static void main(String[] args) 
        {
            DataWriter.savePosts();
        } 
}
