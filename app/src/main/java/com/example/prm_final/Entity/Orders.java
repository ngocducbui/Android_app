package com.example.prm_final.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "orders")
public class Orders {
    @PrimaryKey(autoGenerate = true)
   private int id;
   private int user_id;
   private Date order_date;

    public Orders() {
    }

    public Orders(int id, int user_id, Date order_date) {
        this.id = id;
        this.user_id = user_id;
        this.order_date = order_date;
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

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }
}
