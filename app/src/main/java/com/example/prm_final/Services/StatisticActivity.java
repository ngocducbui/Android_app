package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.prm_final.DAO.Product_DAO;
import com.example.prm_final.DAO.User_DAO;
import com.example.prm_final.Entity.Product;
import com.example.prm_final.Entity.User;
import com.example.prm_final.R;
import com.example.prm_final.database.PRM_DATABASE;

import java.util.List;

public class StatisticActivity extends AppCompatActivity {
    private User_DAO userDao;
    private Product_DAO productDao;
    private TextView textNumberOfUsers;
    private TextView textNumberOfProducts;
    private List<User> userList;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        userDao = PRM_DATABASE.getInstance(this).user_dao();
        productDao = PRM_DATABASE.getInstance(this).product_dao();
        userList = userDao.listUser();
        productList = productDao.products();

        textNumberOfUsers = findViewById(R.id.txtNumberOfUsers);
        textNumberOfProducts = findViewById(R.id.txtNumberOfProducts);

        textNumberOfUsers.setText(userList.size() + "");
        textNumberOfProducts.setText(productList.size() + "");
    }
}