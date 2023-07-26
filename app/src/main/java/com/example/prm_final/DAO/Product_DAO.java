package com.example.prm_final.DAO;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prm_final.Entity.Product;
import com.example.prm_final.Entity.User;
import com.example.prm_final.Relation.ProductWithUser;

import java.util.List;

@Dao
public interface Product_DAO {
    @Query("select * from product where status=1")
    List<Product> product();

    @Query("select * from product ")
    List<Product> products();
    @Insert
    void AddProduct(Product product);
    @Update
    void UpdateProduct(Product product);
    @Query("select * from product where name like '%' || :productName || '%' ")
    List<Product> searchName(String productName);

    @Query("select * from  product where product.user_id=:userId ")
    List<Product> getProductForUser(int userId);
    @Query("select * from product where product.id=:productId ")
    Product getProductDetail(int productId);
    @Query("Select * from user where id=:uID")
    User getProductUserById(int uID);
    @Delete
    void DeleteProduct(Product product);

    @Query("select * from product where status = 0")
    List<Product> listProductNotApprove();

    @Query("delete from product where id=:id")
    void DeleteProductById(int id);

    @Query("update product set status = 1 where id=:id")
    void UpdateProductStatusById(int id);
}
