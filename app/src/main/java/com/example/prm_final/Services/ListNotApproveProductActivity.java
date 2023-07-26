package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.prm_final.DAO.Product_DAO;
import com.example.prm_final.Entity.Product;
import com.example.prm_final.R;
import com.example.prm_final.adapter.NotApproveProductAdapter;
import com.example.prm_final.database.PRM_DATABASE;

import java.util.ArrayList;
import java.util.List;

public class ListNotApproveProductActivity extends AppCompatActivity {
    private List<Product> productList;
    private Product_DAO productDao;
    private NotApproveProductAdapter notApproveProductAdapter;
    private RecyclerView recyclerViewNotApproveProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_not_approve_product);

        productList = new ArrayList<>();
        productDao = PRM_DATABASE.getInstance(this).product_dao();
        productList = productDao.listProductNotApprove();
        recyclerViewNotApproveProduct = findViewById(R.id.recyclerViewNotApproveProduct);
        recyclerViewNotApproveProduct.setLayoutManager(new LinearLayoutManager(ListNotApproveProductActivity.this));
        notApproveProductAdapter = new NotApproveProductAdapter(ListNotApproveProductActivity.this, productList);
        recyclerViewNotApproveProduct.setAdapter(notApproveProductAdapter);
    }
}