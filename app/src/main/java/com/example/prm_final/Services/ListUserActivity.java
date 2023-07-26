package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.prm_final.DAO.User_DAO;
import com.example.prm_final.Entity.User;
import com.example.prm_final.R;
import com.example.prm_final.adapter.UserAdapter;
import com.example.prm_final.database.PRM_DATABASE;

import java.util.ArrayList;
import java.util.List;

public class ListUserActivity extends AppCompatActivity {
    private List<User> userList;
    private User_DAO userDao;
    private UserAdapter userAdapter;
    private RecyclerView recyclerViewUserList;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        userList = new ArrayList<>();
        userDao = PRM_DATABASE.getInstance(this).user_dao();
        userList = userDao.listUser();
        recyclerViewUserList = findViewById(R.id.recyclerViewListUser);
        recyclerViewUserList.setLayoutManager(new LinearLayoutManager(ListUserActivity.this));
        userAdapter = new UserAdapter(ListUserActivity.this, userList);
        recyclerViewUserList.setAdapter(userAdapter);
        btnAdd = findViewById(R.id.btnAddUser);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListUserActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        userList = userDao.listUser();
//        userAdapter = new UserAdapter(ListUserActivity.this, userList);
//    }
}