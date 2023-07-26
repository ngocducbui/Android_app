package com.example.prm_final.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prm_final.Entity.Bill;
import com.example.prm_final.Entity.Cart;
import com.example.prm_final.Entity.Comment;
import com.example.prm_final.Entity.Product;
import com.example.prm_final.Entity.Social_Network;

import java.util.List;

@Dao
public interface Socical_DAO {
    @Query("Select * from social_network")
    LiveData<List<Social_Network>> getAllSocical();
    @Insert
    void insertSocical(Social_Network social_network);
    @Insert
    void insertProduct(Product product);
    @Update
    void updateSocical(Social_Network social_network);
    @Delete
    void deleteSocical(Social_Network social_network);
    @Query("Select * from product where id=:pID limit 1")
    Product getProductById(int pID);
    @Delete
    void deleteListCart(List<Cart> carts);

    @Query("Select * from Cart where cID=:cID")
    LiveData<List<Cart>> allCart(int cID);

    @Query("Select * from Comment where product_id=:id")
    public LiveData<List<Comment>> allComment(int id);

    @Query("Select name from user where id=:id")
    String getCusNameById(int id);

    @Insert
    void insertComment(Comment comment);

    @Insert
    void insertCart(Cart cart);
    @Update
    void updateCart(Cart cart);
    @Delete
    void deleteCart(Cart cart);
    @Insert
    void insertBill(Bill bill);
}
