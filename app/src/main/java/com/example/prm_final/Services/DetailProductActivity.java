package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prm_final.Convert.ImageBitMapString;
import com.example.prm_final.DAO.Product_DAO;
import com.example.prm_final.Entity.Cart;
import com.example.prm_final.Entity.Product;
import com.example.prm_final.Entity.User;
import com.example.prm_final.R;
import com.example.prm_final.adapter.ProductAdapter;
import com.example.prm_final.database.PRM_DATABASE;

import java.util.List;

public class DetailProductActivity extends AppCompatActivity {
    private int productId;
    private int uID;
    private ImageView imageViewProduct;

    private TextView textViewProductName;

    private TextView textViewUserName;
    private TextView textViewPrice;
    private TextView textViewAddress;
    private Button comment;
    private Button contactButton,btnAddToCart;
    private ProductAdapter productAdapter;
    private List<Product> productList;


    private Product product;

    private Product_DAO product_dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        Intent intent = getIntent();
        productId = intent.getIntExtra("product_id", 0);
        initUnit();
        uID=getIntent().getIntExtra("uID",0);
        Log.d("User_id",uID+"");


        product_dao= PRM_DATABASE.getInstance(this).product_dao();
        product=new Product();
        product=product_dao.getProductDetail(productId);
        User u=product_dao.getProductUserById(product.getUser_id());


            textViewProductName.setText(u.getUser_name());
            textViewPrice.setText(product.getPrice());
            textViewAddress.setText(product.getAddress());
            imageViewProduct.setImageBitmap(ImageBitMapString.getBitMapFromStr(product.getImg()));



        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetailProductActivity.this, ListCommentActivity.class);
                intent.putExtra("product_id",productId ); // Truyền ID của sản phẩm sang Activity xem chi tiết
                startActivity(intent);

            }
        });
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProductActivity.this, LienHeDatHangActivity.class);
                intent.putExtra("product_id",productId ); // Truyền ID của sản phẩm sang Activity xem chi tiết
                startActivity(intent);

            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PRM_DATABASE.getInstance(DetailProductActivity.this).socical_dao().insertCart(new Cart(uID,productId,1,Double.parseDouble(product.getPrice())));
            }
        });


    }
    private void initUnit(){
        textViewProductName=findViewById(R.id.textViewProductName);
        textViewUserName=findViewById(R.id.textViewUserName);
        textViewPrice=findViewById(R.id.textViewPrice);
        imageViewProduct=findViewById(R.id.imageViewProduct);
        textViewAddress=findViewById(R.id.textViewAddress);
        contactButton=findViewById(R.id.contactButton);
        btnAddToCart=findViewById(R.id.btnAddToCart);
        comment=findViewById(R.id.cmtButton);



    }
}