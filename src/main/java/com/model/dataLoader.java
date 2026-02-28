package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class dataLoader extends dataConstants {

    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        try {
            FileReader reader = new FileReader(USERS_FILE);
            JSONArray usersJSON = (JSONArray) new JSONParser().parse(reader);

            for (int i = 0; i < usersJSON.size(); i++) {
                JSONObject userJSON = (JSONObject) usersJSON.get(i);

                UUID id = UUID.fromString((String) userJSON.get(USER_ID));
                String username = (String) userJSON.get(USERNAME);
                String email = (String) userJSON.get(EMAIL);
                String password = (String) userJSON.get(PASSWORD);
                String firstName = (String) userJSON.get(FIRST_NAME);
                String lastName = (String) userJSON.get(LAST_NAME);
                String role = (String) userJSON.get(ROLE);

                users.add(new User(id, username, email, password, firstName, lastName, Role.valueOf(role)));
            }

            return users;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public static ArrayList<Post> getPosts() {
        ArrayList<Post> posts = new ArrayList<>();

        try {
            FileReader reader = new FileReader(POSTS_FILE);
            JSONArray postsJSON = (JSONArray) new JSONParser().parse(reader);

            for (int i = 0; i < postsJSON.size(); i++) {
                JSONObject postJSON = (JSONObject) postsJSON.get(i);

                UUID id = UUID.fromString((String) postJSON.get(USER_ID));
                String type = (String) postJSON.get(POST_TYPE);
                UUID authorId = UUID.fromString((String) postJSON.get(AUTHOR_ID));
                String createdAt = (String) postJSON.get(CREATED_AT);
                long score = (Long) postJSON.get(SCORE);

                posts.add(new Post(id, authorId, createdAt, (int) score, type));
            }

            return posts;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts;
    }

    public static void main(String[] args) {
        ArrayList<User> users = getUsers();
        ArrayList<Post> posts = getPosts();

        System.out.println("Users loaded: " + users.size());
        System.out.println("Posts loaded: " + posts.size());
    }
}
}
