package com.controllers;

import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Reads and writes users.json using json-simple.
 */
public class UserJsonService {

    public UserJsonService() {
        super();
    }

    private static final String JSON_FILE_PATH = "json/users.json";

    // ── Load the full array from disk ──────────────────────────────────
    public static JSONArray loadUsers() throws Exception {
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader(JSON_FILE_PATH);
        JSONArray users = (JSONArray) parser.parse(reader);
        reader.close();
        return users;
    }

    // ── Write the full array back to disk ──────────────────────────────
    public static void saveUsers(JSONArray users) throws Exception {
        FileWriter writer = new FileWriter(JSON_FILE_PATH);
        writer.write(users.toJSONString());
        writer.flush();
        writer.close();
    }

    // ── Find a user by their userId field ─────────────────────────────
    public static JSONObject findUserById(JSONArray users, String userId) {
        for (int i = 0; i < users.size(); i++) {
            JSONObject user = (JSONObject) users.get(i);
            if (userId.equals(user.get("userId"))) {
                return user;
            }
        }
        return null;
    }

    // ── Get the studentData block for a user ──────────────────────────
    public static JSONObject getStudentData(JSONArray users, String userId) {
        JSONObject user = findUserById(users, userId);
        if (user != null && user.containsKey("studentData")) {
            return (JSONObject) user.get("studentData");
        }
        return null;
    }

    // ── Get the progression block inside studentData ──────────────────
    public static JSONObject getProgression(JSONArray users, String userId) {
        JSONObject studentData = getStudentData(users, userId);
        if (studentData != null && studentData.containsKey("progression")) {
            return (JSONObject) studentData.get("progression");
        }
        return null;
    }
}