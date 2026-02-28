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
        ArrayList<User> userList = users.getUsers();

        JSONArray jasonUsers = new JSONArray();

        for(int i = 0; i < userList.size(); i++) 
        {
            jsonUsers.add(getUserJSON(userList.get(i)));
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

    public static void saveQuestions()
    {
        Questions questions = Questions.getInstance();
        ArrayList<Question> questionList = questions.getQuestions();

        JSONArray jsonQuestions = new JSONArray();

        for(int i = 0; i < questionList.size(); i++) 
        {
            jsonQuestions.add(getQuestionJSON(questionList.get(i)));
        }

        try (FileWriter file = new FileWriter(QUESTIONS_FILE)) 
        {
            file.write(jsonQuestions.toJSONString());
            file.flush();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }

        public static JSONObject getQuestionJSON(Question question) 
        {
            JSONObject questionDetails = new JSONObject();
            questionDetails.put(QUESTION_ID, question.getId());
            questionDetails.put(TITLE, question.getTitle());
            questionDetails.put(AUTHOR_ID, question.getAuthorId());
            questionDetails.put(CREATED_AT, question.getCreatedAt());
            questionDetails.put(DIFFICULTY, question.getDifficulty());
            questionDetails.put(HINT, question.getHint());
            questionDetails.put(TAGS, question.getTags());
            return questionDetails;
        }

        public static void main(String[] args) 
        {
            DataWriter.saveQuestions();
        }
    }

    public static void saveSolutions()
    {
        Solutions solutions = Solutions.getInstance();
        ArrayList<Solution> solutionList = solutions.getSolutions();

        JSONArray jsonSolutions = new JSONArray();

        for(int i = 0; i < solutionList.size(); i++) 
        {
            jsonSolutions.add(getSolutionJSON(solutionList.get(i)));
        }

        try (FileWriter file = new FileWriter(SOLUTIONS_FILE)) 
        {
            file.write(jsonSolutions.toJSONString());
            file.flush();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }

        public static JSONObject getSolutionJSON(Solution solution) 
        {
            JSONObject solutionDetails = new JSONObject();
            solutionDetails.put(QUESTION_ID, solution.getQuestionId());
            solutionDetails.put(SOLUTION_NUMBER, solution.getSolutionNumber());
            solutionDetails.put(AUTHOR_ID, solution.getAuthorId());
            solutionDetails.put(CREATED_AT, solution.getCreatedAt());
            return solutionDetails;
        }

        public static void main(String[] args) 
        {
            DataWriter.saveSolutions();
        }

}