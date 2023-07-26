package com.example.prm_final.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "promotion")
public class Promotion {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;
    private int product_id;

    public Promotion() {
    }

    public Promotion(int id, String content, int product_id) {
        this.id = id;
        this.content = content;
        this.product_id = product_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
