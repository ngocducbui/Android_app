package com.example.prm_final.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "role")
public class Role {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    public Role() {
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
