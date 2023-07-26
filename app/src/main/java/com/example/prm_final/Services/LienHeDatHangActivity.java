package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;


import com.example.prm_final.DAO.Product_DAO;

import com.example.prm_final.Entity.Product;
import com.example.prm_final.R;
import com.example.prm_final.database.PRM_DATABASE;

public class LienHeDatHangActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button textPhoneNumber,buttonFacebook;
    private int productId;
    private TextView toolbarTitle;
    private Product_DAO product_dao;
    private Product product;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he_dat_hang);

        Intent intent = getIntent();
        productId = intent.getIntExtra("product_id", 0);
        product_dao= PRM_DATABASE.getInstance(this).product_dao();
        product=product_dao.getProductDetail(productId);
        textPhoneNumber=findViewById(R.id.textPhoneNumber);
        buttonFacebook=findViewById(R.id.buttonFacebook);
        textPhoneNumber.setText(product.getPhone());
        buttonFacebook.setText(product.getLinkFacebook());



        toolbar = findViewById(R.id.toolbarContact);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Contact to the restaurant");
        textPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber=textPhoneNumber.getText().toString();
                Uri callUri = Uri.parse("tel:" + phoneNumber);

                Intent callIntent = new Intent(Intent.ACTION_CALL, callUri);
                if (ActivityCompat.checkSelfPermission(LienHeDatHangActivity.this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                } else {
                    ActivityCompat.requestPermissions(LienHeDatHangActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                }
            }
        });
        buttonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=buttonFacebook.getText().toString();
                openWebPage( url);

            }
        });

        // ...
    }
    private void openWebPage(String url) {
        Uri webpage = Uri.parse("https://"+url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}