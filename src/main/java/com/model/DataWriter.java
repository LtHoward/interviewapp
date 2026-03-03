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

            return usersDetails;
        }


        public static void main(String[] args) 
        {
            DataWriter.saveUsers();
        }

    }

   /**public static void savePosts() 
    {
        Posts postsInstance = Posts.getInstance();
        ArrayList<Posts> posts = postsInstance.getPosts();

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

        public static JSONObject getPostsJSON(Posts post) 
        {
            JSONObject postDetails = new JSONObject();
            postDetails.put(USER_ID, post.getId());
            postDetails.put(POST_TYPE, post.getType());
            postDetails.put(AUTHOR_ID, post.getAuthorId());
            postDetails.put(CREATED_AT, post.getCreatedAt());
            postDetails.put(SCORE, post.getScore());
            return postDetails;
        }

        public static void main(String[] args) 
        {
            DataWriter.savePosts();
        }

    } 
    */
} 