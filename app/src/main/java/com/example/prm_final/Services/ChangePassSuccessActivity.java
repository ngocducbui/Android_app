package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.prm_final.R;

public class ChangePassSuccessActivity extends AppCompatActivity {
    ImageView back;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_success);

        back = findViewById(R.id.back_btn3);
        login = findViewById(R.id.logininsucess);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePassSuccessActivity.this, OtpVerifyActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePassSuccessActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}