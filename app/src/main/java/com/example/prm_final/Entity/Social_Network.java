package com.example.prm_final.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName ="social_network" )
public class Social_Network implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int user_id;
    private String content;
    private int numberOfLike;
    private String image;

    public Social_Network() {
    }

    public Social_Network(int id, int user_id, String content, int numberOfLike, String image) {
        this.id = id;
        this.user_id = user_id;
        this.content = content;
        this.numberOfLike = numberOfLike;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNumberOfLike() {
        return numberOfLike;
    }

    public void setNumberOfLike(int numberOfLike) {
        this.numberOfLike = numberOfLike;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
