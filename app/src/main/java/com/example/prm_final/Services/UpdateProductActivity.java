package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.prm_final.Convert.ImageBitMapString;
import com.example.prm_final.DAO.Product_DAO;
import com.example.prm_final.Entity.Product;
import com.example.prm_final.R;
import com.example.prm_final.database.PRM_DATABASE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class UpdateProductActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 200;
    private int productId;
    private Bitmap bitmap = null;

    private EditText et_product_name;
    private EditText et_address;
    private EditText et_phone_number;
    private EditText et_price;
    private ImageView imageViewProduct;
    private Button btn_update;
    private Product product;

    private Product productWithUser;
    private Product_DAO product_dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        initUnit();
        Intent intent = getIntent();
        productId = intent.getIntExtra("product_id", 0);
        product_dao = PRM_DATABASE.getInstance(this).product_dao();
        product = new Product();
        product = product_dao.getProductDetail(productId);


        et_product_name.setText(product.getName());
        et_price.setText(product.getPrice());
        et_address.setText(product.getAddress());
        et_phone_number.setText(product.getPhone());
        bitmap = ImageBitMapString.getBitMapFromStr(product.getImg());
        imageViewProduct.setImageBitmap(bitmap);


        imageViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProduct();
                Toast.makeText(UpdateProductActivity.this, "Update Successfully, please back to see product", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void UpdateProduct() {
        String ProductName = et_product_name.getText().toString();
        String Address = et_address.getText().toString();
        String Phone = et_phone_number.getText().toString();
        String Price = et_price.getText().toString();
        product.setImg(ImageBitMapString.getStringFromBitMap(bitmap));
        product.setName(ProductName);
        product.setAddress(Address);
        product.setPhone(Phone);
        product.setPrice(Price);
        PRM_DATABASE.getInstance(this).product_dao().UpdateProduct(product);
    }

    private void initUnit() {
        et_product_name = findViewById(R.id.et_product_name);
        et_address = findViewById(R.id.et_address);
        et_phone_number = findViewById(R.id.et_phone_number);
        et_price = findViewById(R.id.et_price);
        imageViewProduct = findViewById(R.id.imageViewProduct);
        btn_update = findViewById(R.id.btn_update);

    }

    private byte[] convertImageToByteArray(Uri imageUri) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        FileInputStream fileInputStream = new FileInputStream(new File(imageUri.getPath()));

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        return outputStream.toByteArray();
    }
}