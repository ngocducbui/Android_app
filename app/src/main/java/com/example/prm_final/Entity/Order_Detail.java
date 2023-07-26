package com.example.prm_final.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "order_detail")
public class Order_Detail {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int order_id;
    private int product_id;

    private int quantity;

    private int unitPrice;

    private int discount;

    public Order_Detail() {
    }

    public Order_Detail(int id, int product_id, int quantity, int unitPrice, int discount,int order_id) {
        this.id = id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.order_id=order_id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
