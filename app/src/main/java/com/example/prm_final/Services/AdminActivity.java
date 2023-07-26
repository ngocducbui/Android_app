package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.prm_final.R;

public class AdminActivity extends AppCompatActivity {
    private Button btnUser;
    private Button btnProduct;
    private Button btnStatistic;
    private Button btnAboutUs;
    private Button btnPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnUser = findViewById(R.id.btnUserManagement);
        btnProduct = findViewById(R.id.btnProductManagement);
        btnStatistic = findViewById(R.id.btnStatistic);
        btnAboutUs = findViewById(R.id.btnAboutUs);
        btnPolicy = findViewById(R.id.btnPolicy);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ListUserActivity.class);
                startActivity(intent);
            }
        });

        btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ListNotApproveProductActivity.class);
                startActivity(intent);
            }
        });

        btnStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, StatisticActivity.class);
                startActivity(intent);
            }
        });

        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        btnPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, PolicyActivity.class);
                startActivity(intent);
            }
        });
    }
}