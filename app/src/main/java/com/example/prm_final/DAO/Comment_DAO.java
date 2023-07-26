package com.example.prm_final.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.prm_final.Entity.Comment;
import com.example.prm_final.Entity.User;
import com.example.prm_final.Relation.ProductWithComment;

import java.util.List;
@Dao
public interface Comment_DAO {
    @Transaction
    @Query("Select * from comment where product_id=:productId")
    List<Comment> PRODUCT_WITH_COMMENT_LIST(int productId);
    @Query("Select * from user where id=:uID")
    User getUserById(int uID);
    @Insert
    void  AddComment(Comment comment);
}
