package com.educharge.educharge.Models;

/**
 * Created by R Dinesh Kumar on 20-9-17.
 */

import java.util.Arrays;
import java.util.Date;

public class Post {

    private int pid;

    private int uid;
    private String postText;
    private Object[] likes;
    private Comment[] comments;
    private Date dateCreated;

    public Post() {
        super();
    }
    public Post(int pid, int uid, String postText, Object[] likes, Comment[] comments, Date dateCreated) {
        super();
        this.pid = pid;
        this.uid = uid;
        this.postText = postText;
        this.likes = likes;
        this.comments = comments;
        this.dateCreated = dateCreated;
    }
    @Override
    public String toString() {
        return "Post [pid=" + pid + ", uid=" + uid + ", postText=" + postText + ", likes=" + Arrays.toString(likes)
                + ", comments=" + Arrays.toString(comments) + ", dateCreated=" + dateCreated + "]";
    }

    public int getPid() {
        return pid;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getPostText() {
        return postText;
    }
    public void setPostText(String postText) {
        this.postText = postText;
    }
    public Object[] getLikes() {
        return likes;
    }
    public void setLikes(Object[] objects) {
        this.likes = objects;
    }
    public Comment[] getComments() {
        return comments;
    }
    public void setComments(Comment[] comments) {
        this.comments = comments;
    }
    public Date getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

}
