package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm_final.DAO.Product_DAO;
import com.example.prm_final.Entity.Product;
import com.example.prm_final.R;
import com.example.prm_final.adapter.ProductUserAdapter;
import com.example.prm_final.database.PRM_DATABASE;

import java.util.ArrayList;
import java.util.List;

public class ListProductActivity extends AppCompatActivity {
    private ImageView imageViewProduct;

    private TextView textViewProductName;
   // private EditText btnSearch;
    private TextView textViewUserName,tvDateProduct;
    private TextView textViewPrice;
    private Button buttonDetail;
    private Button deleteProduct;
    private ProductUserAdapter productAdapter;
    private List<Product> productList;
    private RecyclerView recyclerView;
    private Product_DAO product_dao;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        initUnit();
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        productList=new ArrayList<>();
        product_dao= PRM_DATABASE.getInstance(this).product_dao();

        Toast.makeText(this, "id la"+userId, Toast.LENGTH_SHORT).show();

        productAdapter=new ProductUserAdapter(new ProductUserAdapter.IClickItemProduct(){

            @Override
            public void updateProduct(Product p) {

            }

            @Override
            public void deleteProduct(Product product) {
                clickDeleteProduct(product);

            }
        });




        productList= product_dao.getProductForUser(userId);
        productAdapter.setData(productList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(productAdapter);




//        btnSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if(actionId== EditorInfo.IME_ACTION_SEARCH){
//                  //  handleSearchProduct();
//
//                }
//                return false;
//            }
//        });
    }
    private void initUnit(){
        textViewProductName=findViewById(R.id.textViewProductName);
        textViewUserName=findViewById(R.id.textViewUserName);
        textViewPrice=findViewById(R.id.textViewPrice);
        imageViewProduct=findViewById(R.id.imageViewProduct);
        tvDateProduct=findViewById(R.id.tvDateProduct);

        recyclerView=findViewById(R.id.recyclerViewProducts);
        deleteProduct=findViewById(R.id.deleteButton);
        //btnSearch=findViewById(R.id.searchEditText);
    }
//    private void   handleSearchProduct(){
//        String keyword=btnSearch.getText().toString().trim();
//        productList=new ArrayList<>();
//        productList= PRM_DATABASE.getInstance(this).product_dao().searchName(keyword);
//        productAdapter.setData(productList);
//    }
    private void clickDeleteProduct(Product product){
        new AlertDialog.Builder(this).setTitle("Confirm delete product").setMessage("Are you sure")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PRM_DATABASE.getInstance(ListProductActivity.this).product_dao().DeleteProduct(product);
                        Toast.makeText(ListProductActivity.this,"Delete product succesfully",Toast.LENGTH_SHORT).show();
                        productList= product_dao.getProductForUser(userId);
                        productAdapter.setData(productList);
                    }
                }).setNegativeButton("No",null).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        productList= product_dao.getProductForUser(userId);
        productAdapter.setData(productList);
    }
}