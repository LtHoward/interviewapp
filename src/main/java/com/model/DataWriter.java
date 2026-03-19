package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants 
{
    
/**
 * The main method for User to write the users to the JSON file.
 * Uses associated classes to get the necessary information to write to the file.
 * @author Dorian Rhone
 */
    public static void saveUsers()
    {
        UserManager users = UserManager.getInstance();
        ArrayList<User> userManager = users.getUsers();

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


    /**
     * Main method to get the JSON object for a User,
     *  which is used in the saveUsers method to write to the JSON file.
     * @param user The User object to be converted to a JSON object.
     * @return The JSON object representing the user.
     * @author Dorian Rhone
     */
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

            /**
             * if else statement to determine if the user is a Contributor, Student, or Administrator,
             * which is needed to write the necessary information to the file.
             * @author Dorian Rhone
             */
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
                studentDetails.put(LAST_ACTIVITY_DATE, student.getLastActivityDate().toString());

            
            /**
             * JSON Object needed for Progression, which is needed to determine the points, level, current streak,
             * longest streak, equipped title, and unlocked titles of the student, which is needed to write to the file.
             * @author Dorian Rhone
             */
                JSONObject progression = new JSONObject();
                progression.put(POINTS, student.getProgression().getPoints());
                progression.put(LEVEL, student.getProgression().getLevel());
                progression.put(CURRENT_STREAK, student.getProgression().getCurrentStreak());
                progression.put(LONGEST_STREAK, student.getProgression().getLongestStreak());
                progression.put(EQUIPPED_TITLE, student.getProgression().getEquippedTitle());
                progression.put(UNLOCKED_TITLES, student.getProgression().unlockTitles());
                usersDetails.put(PROGRESSION, progression);


            /**
             * Rewards Array needed to determine the type of reward, the amount of the reward,
             * and if the reward has been redeemed or not, which is needed to write to the file.
             * @author Dorian Rhone
             */
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

    /**
     * The main method for Post to write the posts to the JSON file.
     * Uses associated classes to get the necessary information to write to the file.
     * @author Dorian Rhone
     */
    
    public static void savePosts() 
    {
        PostManager postsInstance = PostManager.getInstance();
        ArrayList<Post> posts = postsInstance.getAllPosts();

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
    }

    /**
     * Main method to get the JSON object for a Post,
     * which is used in the savePosts method to write to the JSON file.
     * @param post The Post object to be converted to a JSON object.
     * @return The JSON object representing the Post.
     * @author Dorian Rhone
     */
        public static JSONObject getPostsJSON(Post post) 
        {
            JSONObject postDetails = new JSONObject();
            postDetails.put(POST_ID, post.getPostId().toString());
            postDetails.put(POST_TYPE, post.getType());
            postDetails.put(TITLE, post.getTitle());
            postDetails.put(AUTHOR_ID, post.getAuthor().getId().toString());
            postDetails.put(CREATED_AT, post.getCreatedAt().toString());
            postDetails.put(SCORE, post.getScore());

            /**
             * JSON Array needed for Tags to determine the tags associated with the post,
             * which is needed to write to the file.
             * @author Dorian Rhone
             */
            JSONArray tagsArray = new JSONArray();
            for(String tag : post.getTags())
            {
                tagsArray.add(tag);
            }
            postDetails.put(TAGS, tagsArray);


            /**
             * JSON Array needed for Comments, which also needs to get the
             * necessary information from the Comment class to write to the file.
             * @author Dorian Rhone
             */
            JSONArray commentsArray = new JSONArray();
            for(Comment comment : post.getComments())
            {
                JSONObject commentDetails = new JSONObject();
                commentDetails.put(POST_ID, comment.getCommentId().toString());
                commentDetails.put(AUTHOR_ID, comment.getAuthor().getId().toString());
                commentDetails.put(CONTENT, comment.getContent());
                commentDetails.put(CREATED_AT, comment.getCreatedAt().toString());
                commentsArray.add(commentDetails);
            }
            postDetails.put(COMMENTS, commentsArray);


            /**
             * Content Sections Array need to determine the type of 
             * content and the content itself, which is needed to write to the file.
             * @author Dorian Rhone
             */
            JSONArray sectionsArray = new JSONArray();
            for(PostContent section : post.getContentSections())
            {
                JSONObject sectionDetails = new JSONObject();
                sectionDetails.put(TYPE, section.getType().toString());
                sectionDetails.put(CONTENT, section.getContent());
                sectionsArray.add(sectionDetails);
            }
            postDetails.put(CONTENT_SECTIONS, sectionsArray);


            /**
             * if else statement to determine if the post is a QuestionPost or a SolutionPost,
             * which is needed to write the necessary information to the file.
             * @author Dorian Rhone
             */
            if(post instanceof QuestionPost) 
            {
                QuestionPost question = (QuestionPost) post;
                postDetails.put(DIFFICULTY, question.getDifficulty().toString());
                postDetails.put(HINT, question.getHint());
            } 
            else if (post instanceof SolutionPost) 
            {
                SolutionPost solution = (SolutionPost) post;
                postDetails.put(QUESTION_ID, solution.getQuestionId().toString());
                postDetails.put(SOLUTION_NUMBER, solution.getSolutionNumber());
            }
            return postDetails;
        }

        /**
         * Second Main method to load the posts from the JSON file and write them to the file again,
         * which is needed to test the savePosts method and ensure that the posts are being written to the file correctly.
         * @param args
         * @author Dorian Rhone
         */
       public static void main(String[] args) 
        {
            ArrayList<User> users = DataLoader.getUsers();

            UserManager.getInstance().getUsers().clear();
            UserManager.getInstance().getUsers().addAll(DataLoader.getUsers());
            DataWriter.saveUsers();

            PostManager.getInstance().getAllPosts().clear();
            PostManager.getInstance().getAllPosts().addAll(DataLoader.getPosts(users));
            DataWriter.savePosts();
        }
} 

