package com.example.prm_final.Services;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm_final.Entity.Social_Network;
import com.example.prm_final.database.PRM_DATABASE;
import com.example.prm_final.databinding.ActivityInsertOrUpdateSocicalBinding;

public class InsertOrUpdateSocical extends AppCompatActivity {
    private ActivityInsertOrUpdateSocicalBinding binding;
    private PRM_DATABASE prm_database;
    private Uri uri;
    private Social_Network social_network;
    private int SELECT_PICTURE=1111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityInsertOrUpdateSocicalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent=getIntent();
        int insertorupdate=intent.getIntExtra("insertorupdate",1);
        prm_database=PRM_DATABASE.getInstance(this);
        if(insertorupdate==1){
            binding.btnUpdate.setVisibility(View.GONE);
            binding.btnInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insert();
                }
            });
        }else {
            binding.btnInsert.setVisibility(View.GONE);
            social_network= (Social_Network) intent.getSerializableExtra("social");
            uri=Uri.parse(social_network.getImage());
            binding.img.setImageURI(uri);
            binding.txtContent.setText(social_network.getContent());
            binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    social_network.setImage(uri.toString());
                    social_network.setContent(binding.txtContent.getText().toString());
                    prm_database.socical_dao().updateSocical(social_network);
                    Toast.makeText(InsertOrUpdateSocical.this,"Update success",Toast.LENGTH_SHORT).show();
                }
            });
        }

        binding.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });
    }

    private void insert(){
        int cID=1;
        String content=binding.txtContent.getText().toString();
        String image=uri.toString();
        social_network=new Social_Network(0,cID,content,0,image);
        prm_database.socical_dao().insertSocical(social_network);
        Toast.makeText(InsertOrUpdateSocical.this,"Update success",Toast.LENGTH_SHORT).show();
    }
    private void getImage(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, SELECT_PICTURE);
    }


    @SuppressLint("WrongConstant")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                uri = data.getData();
                ContentResolver contentResolver=getContentResolver();
                if(uri!=null){
                    this.grantUriPermission(this.getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    final int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    contentResolver.takePersistableUriPermission(uri,takeFlags);
                    binding.img.setImageURI(uri);
                }

            }
        }
    }
}