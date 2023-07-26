package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.prm_final.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Button policyButton = findViewById(R.id.policyButton);
        Button customerSupportButton = findViewById(R.id.customerSupportButton);
        Button inviteFriendButton = findViewById(R.id.inviteFriendButton);
        inviteFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInviteFriendActivity(v);
            }
        });
        customerSupportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomerSupportActivity(v);
            }
        });
        policyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this, PolicyActivity.class);
                startActivity(intent);
            }
        });
    }
    public void openCustomerSupportActivity(View view){
        Intent intent = new Intent(this,CustomerSupportActivity.class);
        startActivity(intent);
    }
    public void openInviteFriendActivity(View view){
        Intent intent = new Intent(this, InviteFriendActivity.class);
        startActivity(intent);
    }
}