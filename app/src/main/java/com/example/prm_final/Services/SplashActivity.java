package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.prm_final.DAO.Product_DAO;
import com.example.prm_final.Entity.Product;
import com.example.prm_final.R;
import com.example.prm_final.database.PRM_DATABASE;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
Handler handler;
    private Product_DAO dao;
    private List<Product> productList;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao= PRM_DATABASE.getInstance(this).product_dao();
        productList=dao.product();
        setContentView(R.layout.activity_splash);

        progressBar=findViewById(R.id.progress_bar);

        progressBar.getIndeterminateDrawable().setColorFilter(0xff0000ff, android.graphics.PorterDuff.Mode.MULTIPLY);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },4000);
    }
}