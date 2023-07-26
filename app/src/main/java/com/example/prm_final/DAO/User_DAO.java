package com.example.prm_final.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prm_final.Entity.User;

import java.util.List;

@Dao
public interface User_DAO {
    @Insert
    void insertUser(User user);

    @Query("select * from user")
    List<User> listUser();

    @Query("select id from user where user_name=:username and password=:password")
    int selectUserByUserNameAndPass(String username, String password);

    @Query("select role_id from user where user_name=:username and password=:password")
    int selectRoleIdByUserNameAndPass(String username, String password);
    @Query("select id from user where email=:email")
    int selectUserIdByEmail(String email);

    @Update
    void updateUser(User user);

    @Query("delete from user where email=:email")
    void deleteUserByEmail(String email);

    @Query("select * from user where email=:email")
    User getUserByEmail(String email);

    @Query("select * from user where id=:id")
    User getUserById(int id);

    @Query("update user set password=:newPass where id=:id")
    void resetPassword(int id, String newPass);

    @Query("select password from user where id=:id")
    String getCurrentPass(int id);

    @Query("update user set phone=:phone, dob=:date, address=:address, gender=:gender where id=:id")
    void updateProfile (int id, String phone, String date, String address, boolean gender);

    @Query("update user set phone=:phone, dob=:date, address=:address, gender=:gender, image=:image where id=:id")
    void updateProfilewithImage (int id, String phone, String date, String address, boolean gender,byte[] image);

}
