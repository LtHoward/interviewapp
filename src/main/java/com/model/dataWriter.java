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
        Users users = Users.getInstance();
        ArrayList<User> users = users.getUsers();

        JSONArray jasonUsers = new JSONArray();

        for(int i = 0; i < userList.size(); i++) 
        {
            jasonUsers.add(getUserJSON(users.get(i)));
        }

        try (FileWriter file = new FileWriter(USERS_FILE)) 
        {
            file.write(jasonUsers.toJSONString());
            file.flush();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }

        public static JSONObject getUserJSON(User user) 
        {
            JSONObject userDetails = new JSONObject();
            userDetails.put(USER_ID, user.getId());
            userDetails.put(USERNAME, user.getUsername());
            userDetails.put(EMAIL, user.getEmail());
            userDetails.put(PASSWORD, user.getPassword());
            userDetails.put(FIRST_NAME, user.getFirstName());
            userDetails.put(LAST_NAME, user.getLastName());
            userDetails.put(ROLE, user.getRole());
            return userDetails;
        }


        public static void main(String[] args) 
        {
            DataWriter.saveUsers();
        }

    }

    public static void savePosts() 
    {
        Posts postsInstance = Posts.getInstance();
        ArrayList<Post> posts = postsInstance.getPosts();

        JSONArray jsonPosts = new JSONArray();

        for (int i = 0; i < posts.size(); i++) 
        {
            jsonPosts.add(getPostJSON(posts.get(i)));
        }

        try (FileWriter file = new FileWriter(POSTS_FILE)) 
        {
            file.write(jsonPosts.toJSONString());
            file.flush();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }

        public static JSONObject getPostJSON(Post post) 
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
} 