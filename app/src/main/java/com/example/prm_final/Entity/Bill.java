package com.example.prm_final.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Bill",foreignKeys = {@ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "cID",
        onDelete = ForeignKey.CASCADE)
})



public class Bill {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "cID")
    public int cID;
    @ColumnInfo(name = "location")
    public String location;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "sdt")
    public String sdt;
    @ColumnInfo(name = "price")
    public double price;
    @ColumnInfo(name = "date")
    public String date;
    @ColumnInfo(name = "list_cart")
    public String listCart;
    @ColumnInfo(name = "status")
    public int status;

    public Bill(int id, int cID, String location, String name, String sdt, double price, String date, String listCart, int status) {
        this.id = id;
        this.cID = cID;
        this.location = location;
        this.name = name;
        this.sdt = sdt;
        this.price = price;
        this.date = date;
        this.listCart = listCart;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getcID() {
        return cID;
    }

    public void setcID(int cID) {
        this.cID = cID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getListCart() {
        return listCart;
    }

    public void setListCart(String listCart) {
        this.listCart = listCart;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

