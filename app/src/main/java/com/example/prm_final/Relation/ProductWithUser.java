package com.example.prm_final.Relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.prm_final.Entity.Product;
import com.example.prm_final.Entity.User;

import java.util.List;

public class ProductWithUser {
    @Embedded
    public User user;

    @Relation(parentColumn = "id", entityColumn = "user_id")
    public List<Product> products;
}
