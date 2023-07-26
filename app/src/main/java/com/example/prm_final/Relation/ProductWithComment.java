package com.example.prm_final.Relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.prm_final.Entity.Comment;
import com.example.prm_final.Entity.User;

public class ProductWithComment {
    @Embedded
    public User user;

    @Relation(parentColumn = "id",entityColumn = "user_id")
    public Comment comments;





}