package com.example.prm_final.Entity;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "product")
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int user_id;
    private String name;
    private String price;
    private String address;
    private String linkFacebook;
    private String dateProduct;
    private byte[] img;
    private String phone;
    private boolean status;
    public Product() {
    }

    public Product(int id, int user_id, String name, String price, String address, String linkFacebook, String dateProduct, byte[] img, String phone, boolean status) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.price = price;
        this.address = address;
        this.linkFacebook = linkFacebook;
        this.dateProduct = dateProduct;
        this.img = img;
        this.phone = phone;
        this.status = status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkFacebook() {
        return linkFacebook;
    }

    public void setLinkFacebook(String linkFacebook) {
        this.linkFacebook = linkFacebook;
    }

    public String getDateProduct() {
        return dateProduct;
    }

    public void setDateProduct(String dateProduct) {
        this.dateProduct = dateProduct;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}