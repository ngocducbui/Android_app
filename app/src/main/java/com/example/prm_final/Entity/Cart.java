package com.example.prm_final.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "Cart",primaryKeys = {"cID","pID"})
public class Cart implements Parcelable {
    @ColumnInfo(name = "cID")
    public int cID;
    @ColumnInfo(name = "pID")
    public int pID;
    @ColumnInfo(name = "sl")
    public int sl;
    @ColumnInfo(name = "price")
    public double price;

    public Cart(int cID, int pID, int sl, double price) {
        this.cID = cID;
        this.pID = pID;
        this.sl = sl;
        this.price = price;
    }

    public int getcID() {
        return cID;
    }

    public void setcID(int cID) {
        this.cID = cID;
    }

    public int getpID() {
        return pID;
    }

    public void setpID(int pID) {
        this.pID = pID;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    protected Cart(Parcel in) {
        cID = in.readInt();
        pID = in.readInt();
        sl = in.readInt();
        price=in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(cID);
        dest.writeInt(pID);
        dest.writeInt(sl);
        dest.writeDouble(price);
    }
    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };
}
