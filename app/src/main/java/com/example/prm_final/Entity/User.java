package com.example.prm_final.Entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(tableName = "user",
        indices = {@Index(value = {"email"}, unique = true),
                @Index(value = {"user_name"}, unique = true)})
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String dob;
    private String address;
    private Boolean gender;
    private String email;
    private String phone;
    private Boolean status;
    private int role_id;
    private byte[] image;
    private String user_name;
    private String password;

    public User() {
    }

    public User( String name, String dob, String address, Boolean gender, String email, String phone, Boolean status, int role_id, byte[] image, String user_name, String password) {
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.role_id = role_id;
        this.image = image;
        this.user_name = user_name;
        this.password = password;
    }

    public User(String name, String email,String user_name) {
        this.name = name;
        this.email = email;
        this.user_name=user_name;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
