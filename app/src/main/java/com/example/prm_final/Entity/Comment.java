package com.example.prm_final.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "comment")
public class Comment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int user_id;
    private int product_id;
    private String contentComment;

    public Comment() {
    }

    public Comment(int id, int user_id, int product_id, String contentComment) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.contentComment = contentComment;
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

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getContentComment() {
        return contentComment;
    }

    public void setContentComment(String contentComment) {
        this.contentComment = contentComment;
    }
}
