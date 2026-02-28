package com.model;

import java.lang.reflect.Array;

public class PostManager 
{
    private static PostManager postManager;
    private ArrayList<Post> posts;

    /**
     * Constructor for PostManager
     */
    public PostManager()
    {
        posts = DataLoader.getPosts();
    }

    /**
     * PostManager is a singleton class
     * @return the instance of PostManager
     */
    public static PostManager getInstance() 
    {
        if (postManager == null) 
        {
            postManager = new PostManager();
        }
        return postManager;
    }

    /**
     * addPost allows a contributor to add a post
     * @param contributor the contributor adding the post
     * @param post the post being added
     * @return true if the post was added successfully, false otherwise
     */
    public boolean addPost(Contributor contributor, Post post)
    {
        if (contributor.getRole() == Role.CONTRIBUTOR) 
        {
            posts.add(post);
            DataWriter.savePosts();
            return true;
        }
        return false;
    }

    /**
     * addSolution allows a contributor to add a solution to a question
     * @param user the user adding the solution
     * @param solution the solution being added
     * @return true if the solution was added successfully, false otherwise
     */
    public boolean addSolution(User user, SolutionPost solution)
    {
        if (user.getRole() == Role.CONTRIBUTOR) 
        {
            for (int i = 0; i < posts.size(); i++)
            {
                if (posts.get(i).getId().equals(solution.getQuestionId())) 
                {
                    posts.get(i).addSolution(solution);
                    DataWriter.savePosts();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * editPost allows a contributor to edit a post
     * @param user the user editing the post
     * @param post the post being edited
     * @return true if the post was edited successfully, false otherwise
     */
    public boolean editPost(User user, Post post)
    {
        if(user.getRole() == Role.CONTRIBUTOR)
        {
            for(int i = 0; i < post.size(); i++)
            {
                if(post.get(i).getId().equals(post.getId()))
                {
                    post.set(i, post);
                    DataWriter.savePosts();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * deletePost allows a contributor to delete a post
     * @param user the user deleting the post
     * @param post the post being delete
     * @return true if the post was deleted successfully, false otherwise
     */
    public boolean deletePost(User user, Post post)
    {
        if(user.getRole() == Role.CONTRIBUTOR)
        {
            for(int i = 0; i < post.size(); i++)
            {
                if(post.get(i).getId().equals(post.getID()))
                {
                    post.remove(i);
                    DataWriter.savePosts();
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * getPostByKeyword allows a user to search for a post by title
     * @param title the title keyword to search for
     * @return a list of posts that contain the keyword in their title
     */
    public ArrayList<Post> getPostByKeyWord(String title)
    {
        ArrayList<Post> title = new ArrayList<>();
        for(int i = 0; i < posts.size(); i++)
        {
            if(posts.get(i).getTitle().contains(title))
            {
                title.add(posts.get(i));
            }
        }
        return title;
    }

    /**
     * getAllPost gets all posts in the system
     * @return a list of all posts
     */
    public ArrayList<Post> getAllPost()
    {
        ArrayList<Post> allPosts = new ArrayList<>();
        for(int i = 0; i < posts.size(); i++)
        {
            allPosts.add(posts.get(i));
        }
        return allPosts;
    }

    /**
     * addComment allows a user to add a comment to a post
     * @param user the user adding the comment
     * @param postId the ID of the post being commented on
     * @param content the content of the comment
     * @return true if the comment was added successfully, false otherwise
     */
    public boolean addComment(User user, UUID postId, String content)
    {
        for(int i = 0; i < posts.size(); i++)
        {
            if(posts.get(i).getId().equals(postId))
            {
                Comment comment = new Comment(user.getId(), content);
                posts.get(i).addComment(comment);
                DataWriter.savePosts();
                return true;
            }
        }
        return false;
    }

    /**
     * getComments gets all comments for all posts
     * @return a list of all comments
     */
    public ArrayList<Comment> getComments()
    {
        ArrayList<Comment> Comments = new ArrayList<>();

        for(int i = 0; i < posts.size(); i++)
        {
            Comments.addAll(posts.get(i).getComments());
        }
        return Comments;
    }

    /**
     * save method saves all post and users
     * @return true if the posts were saved successfully, false otherwise
     */
    public boolean save()
    {
        DataWriter.saveUsers();
        DataWriter.savePosts();
        return true;
    }
}