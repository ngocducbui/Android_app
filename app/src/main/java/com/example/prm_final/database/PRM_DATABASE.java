package com.example.prm_final.database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.prm_final.Convert.DateConverter;
import com.example.prm_final.DAO.Comment_DAO;
import com.example.prm_final.DAO.Product_DAO;
import com.example.prm_final.DAO.Socical_DAO;
import com.example.prm_final.DAO.User_DAO;
import com.example.prm_final.Entity.Bill;
import com.example.prm_final.Entity.Cart;
import com.example.prm_final.Entity.Comment;
import com.example.prm_final.Entity.Order_Detail;
import com.example.prm_final.Entity.Orders;
import com.example.prm_final.Entity.Product;
import com.example.prm_final.Entity.Promotion;
import com.example.prm_final.Entity.Role;
import com.example.prm_final.Entity.Social_Network;
import com.example.prm_final.Entity.User;

@Database(entities = {Product.class, Comment.class, Orders.class, Order_Detail.class, Promotion.class, Role.class, Social_Network.class, User.class, Cart.class, Bill.class},version = 1)
@TypeConverters(DateConverter.class)
public abstract class PRM_DATABASE extends RoomDatabase {
   private static final String DATABASE_NAME="PRM_DB_Latest_15";
   private static PRM_DATABASE instance;

   public static synchronized PRM_DATABASE getInstance(Context context){
          if(instance==null){
              instance= Room.databaseBuilder(context.getApplicationContext(),PRM_DATABASE.class,DATABASE_NAME)
                      .allowMainThreadQueries().build();
          }
          return  instance;
   }
   public abstract Product_DAO product_dao();
    public abstract Comment_DAO comment_dao();
    public abstract User_DAO user_dao();
    public abstract Socical_DAO socical_dao();

}
