package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prm_final.DAO.Comment_DAO;
import com.example.prm_final.Entity.Comment;
import com.example.prm_final.R;
import com.example.prm_final.Relation.ProductWithComment;
import com.example.prm_final.adapter.CommentAdapter;
import com.example.prm_final.database.PRM_DATABASE;

import java.util.ArrayList;
import java.util.List;

public class ListCommentActivity extends AppCompatActivity {
   private TextView nameTextView;
   private  TextView contentTextView;
   private ImageView btnAddComment;
   private RecyclerView recyclerView;
   private EditText commentEditText;
   private Comment_DAO comment_dao;
   private CommentAdapter commentAdapter;

   private int productId;
   private List<Comment> productWithCommentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comment);
        initUnit();

        Intent intent = getIntent();
        productId = intent.getIntExtra("product_id", 0);
        comment_dao= PRM_DATABASE.getInstance(this).comment_dao();

        productWithCommentList=new ArrayList<>();
        productWithCommentList=comment_dao.PRODUCT_WITH_COMMENT_LIST(productId);

        commentAdapter=new CommentAdapter(comment_dao);


        commentAdapter.setData(productWithCommentList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(commentAdapter);
        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                int id = sharedPreferences.getInt("id", 0);
                Comment comment=new Comment();
                comment.setUser_id(id);
                comment.setProduct_id(productId);
                comment.setContentComment(commentEditText.getText().toString());
                comment_dao.AddComment(comment);
                List<Comment>comments=new ArrayList<>();
                comments=comment_dao.PRODUCT_WITH_COMMENT_LIST(productId);
                commentAdapter.setData(comments);




            }
        });

    }
    private void initUnit(){
        nameTextView = findViewById(R.id.nameTextView);
        contentTextView = findViewById(R.id.contentTextView);
        recyclerView=findViewById(R.id.recyclerView);
        btnAddComment=findViewById(R.id.btnAddComment);
        commentEditText=findViewById(R.id.commentEditText);
    }
}