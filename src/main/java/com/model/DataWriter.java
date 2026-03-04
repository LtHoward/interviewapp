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

        JSONArray jasonUsers = new JSONArray();

        for(int i = 0; i < userManager.size(); i++) 
        {
            jasonUsers.add(getUsersJSON(userManager.get(i)));
        }

        try (FileWriter file = new FileWriter(USERS_FILE)) 
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
            usersDetails.put(ROLE, user.getStatus().toString());

            if(user.getStatus() == Role.CONTRIBUTOR) 
            {
                JSONObject contributorDetails = new JSONObject();
                usersDetails.put(CONTRIBUTOR_DATA, contributorDetails);
                Contributor contributor = (Contributor) user;
                contributorDetails.put(EXPERIENCE, contributor.getExperience());
                
            } 
            else if (user.getStatus() == Role.STUDENT) 
            {
                JSONObject studentDetails = new JSONObject();
                usersDetails.put(STUDENT_DATA, studentDetails);
                Student student = (Student) user;
                studentDetails.put(CURRENT_CLASSES, student.getCurrentClasses().toString());
                studentDetails.put(CLASSES_TAKEN, student.getClassesTaken().toString());
                studentDetails.put(MAJOR, student.getMajor().toString());
                studentDetails.put(YEAR, student.getYear().toString());
                studentDetails.put(SKILL_LEVEL, student.getSkillLevel().toString());
                studentDetails.put(SOLVED_QUESTIONS, student.getSolvedQuestions());
                studentDetails.put(POSTED_SOLUTIONS, student.getPostedSolutions());
                studentDetails.put(LAST_ACTIVITY_DATE, student.getLastActivityDate().getTime());

                JSONObject progression = new JSONObject();
                progression.put(POINTS, student.getProgression().getPoints());
                progression.put(LEVEL, student.getProgression().getLevel());
                progression.put(CURRENT_STREAK, student.getProgression().getCurrentStreak());
                progression.put(LONGEST_STREAK, student.getProgression().getLongestStreak());
                progression.put(EQUIPPED_TITLE, student.getProgression().getEquippedTitle());
                progression.put(UNLOCKED_TITLES, student.getProgression().unlockTitles());
                usersDetails.put(PROGRESSION, progression);

                JSONArray rewardsArray = new JSONArray();
                for(Reward reward : student.getRewards()) 
                {
                JSONObject rewards = new JSONObject();
                rewards.put(TYPE, reward.getType());
                rewards.put(AMOUNT, reward.getAmount());
                rewards.put(REDEEMED, reward.isRedeemed());
                rewardsArray.add(rewards);
                }
                usersDetails.put(REWARDS, rewardsArray);
            } 
            else if (user.getStatus() == Role.ADMINISTRATOR) 
            {
                usersDetails.put(ROLE, "ADMINISTRATOR");
            }

            return usersDetails;
        }


        public static void main(String[] args) 
        {
            UserManager.getInstance().getUser().clear();
            UserManager.getInstance().getUser().addAll(DataLoader.getUsers());
            DataWriter.saveUsers();
        }

    }

/** 
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
}*/
