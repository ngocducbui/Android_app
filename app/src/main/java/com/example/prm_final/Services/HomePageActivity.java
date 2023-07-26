package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm_final.DAO.Product_DAO;
import com.example.prm_final.Entity.Product;
import com.example.prm_final.R;
import com.example.prm_final.adapter.MenuAdapter;
import com.example.prm_final.adapter.ProductAdapter;
import com.example.prm_final.database.PRM_DATABASE;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity{
    private ImageView imageViewProduct;
    private ArrayList<ItemMenu> itemMenus;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ListView listView;
    private TextView textViewProductName;
    private EditText btnSearch;
    private TextView textViewUserName,textDateProduct;
    private TextView textViewPrice;
    private Button buttonDetail;
    private FloatingActionButton btnSocoal;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private int productId;
    private MenuAdapter menuAdapter;
    private RecyclerView recyclerView;
    private Product_DAO product_dao;
    private int uID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initUnit();
        actionToolBar();
        actionMenu();
        uID=getIntent().getIntExtra("uID",0);
        productList = new ArrayList<>();


        product_dao = PRM_DATABASE.getInstance(this).product_dao();
        productAdapter = new ProductAdapter(product_dao);
        productAdapter.setUID(uID);
        productList = product_dao.product();
        productAdapter.setData(productList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(productAdapter);

        btnSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    handleSearchProduct();
                    return true;
                }
                return false;
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", 0);
        //Toast.makeText(this, "id la"+id, Toast.LENGTH_SHORT).show();
        btnSocoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePageActivity.this,SocicalActivity.class);
                intent.putExtra("uID",uID);
                startActivity(intent);
            }
        });

    }

    private void actionMenu() {
        itemMenus = new ArrayList<>();
        itemMenus.add(new ItemMenu("Profile ", R.drawable.baseline_person_2_24));
        itemMenus.add(new ItemMenu("Post", R.drawable.ic_term_of_service));
        itemMenus.add(new ItemMenu("Posts Published", R.drawable.baseline_folder_24));
        itemMenus.add(new ItemMenu("Social Media", R.drawable.fb));
        itemMenus.add(new ItemMenu("Cart", R.drawable.baseline_shopping_cart_242));
        itemMenus.add(new ItemMenu("About us", R.drawable.ic_food_logo));
        itemMenus.add(new ItemMenu("Policy",R.drawable.baseline_lock_24));
        itemMenus.add(new ItemMenu("Sign out", R.drawable.baseline_logout_24));

        menuAdapter = new MenuAdapter(this, R.layout.dong_item, itemMenus);
        listView.setAdapter(menuAdapter);
    }

    private void initUnit() {
        textViewProductName = findViewById(R.id.textViewProductName);
        textViewUserName = findViewById(R.id.textViewUserName);
        textViewPrice = findViewById(R.id.textViewPrice);
        imageViewProduct = findViewById(R.id.imageViewProduct);
        textDateProduct=findViewById(R.id.textDateProduct);
        btnSocoal=findViewById(R.id.btnSocialN);
        recyclerView = findViewById(R.id.recyclerViewHomePageProducts);
        btnSearch = findViewById(R.id.searchEditText);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        listView = findViewById(R.id.listView);
    }

    private void handleSearchProduct() {
        String keyword = btnSearch.getText().toString().trim();
        productList = PRM_DATABASE.getInstance(this).product_dao().searchName(keyword);
        productAdapter.setData(productList);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


}
