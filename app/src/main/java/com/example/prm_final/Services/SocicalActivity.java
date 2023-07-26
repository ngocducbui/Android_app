package com.example.prm_final.Services;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm_final.Entity.Comment;
import com.example.prm_final.Entity.Social_Network;
import com.example.prm_final.R;
import com.example.prm_final.database.PRM_DATABASE;
import com.example.prm_final.databinding.ActivitySocicalBinding;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SocicalActivity extends AppCompatActivity {
    private ActivitySocicalBinding binding;
    private SocicalAdapter socicalAdapter;
    private PRM_DATABASE prm_database;
    private LiveData<List<Social_Network>> listSo;
    private Dialog dialog;
    private int uID;

    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 41;
    public static final int GALLERY_INTENT_CALLED = 1;
    public static final int GALLERY_KITKAT_INTENT_CALLED = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySocicalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        uID=getIntent().getIntExtra("uID",0);
        if(!checkPermissionForReadExtertalStorage()){
            try {
                requestPermissionForReadExtertalStorage();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        binding.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SocicalActivity.this, CartActivity.class);
                intent.putExtra("uID",uID);
                startActivity(intent);
            }
        });

        binding.btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SocicalActivity.this,InsertOrUpdateSocical.class);
                intent.putExtra("insertorupdate",1);
                startActivity(intent);
            }
        });
        prm_database=PRM_DATABASE.getInstance(this);
        socicalAdapter=new SocicalAdapter(this, new SocicalAdapter.ISocical() {
            @Override
            public void onLongClick(int position) {
                Social_Network social_network=listSo.getValue().get(position);
                onDialogUD(social_network);
            }

            @Override
            public void onCommnetClick(int position) {
                Social_Network social_network=listSo.getValue().get(position);
                onOpenDialogComment(social_network.getId(),uID);
            }
        },prm_database.socical_dao());
        listSo=prm_database.socical_dao().getAllSocical();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.rcvSocial.setLayoutManager(layoutManager);
        binding.rcvSocial.setAdapter(socicalAdapter);
        listSo.observe(this, new Observer<List<Social_Network>>() {
            @Override
            public void onChanged(List<Social_Network> social_networks) {
                socicalAdapter.setListSocical(social_networks);
            }
        });
    }
    private void onOpenDialogComment(int sID,int uID){
        RecyclerView rcvCommnet;
        ImageView imgSend;
        EditText edtContent;
        Dialog dialog1;
        dialog1 = new Dialog(SocicalActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_comment);
        rcvCommnet=dialog1.findViewById(R.id.rcvComment);
        imgSend=dialog1.findViewById(R.id.btnComment);
        edtContent=dialog1.findViewById(R.id.txtComment);
        Window window= dialog1.getWindow();
        window.setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAtributes = window.getAttributes();
        windowAtributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAtributes);

        dialog1.setCancelable(true);
        dialog1.show();
    }
    private void onDialogUD(Social_Network social_network){
        Button btnDelete,btnUpdate;

        dialog = new Dialog(SocicalActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ud_social);
        btnDelete=dialog.findViewById(R.id.btnDelete);
        btnUpdate=dialog.findViewById(R.id.btnUpdate);
        Window window= dialog.getWindow();
        window.setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAtributes = window.getAttributes();
        windowAtributes.width= WindowManager.LayoutParams.MATCH_PARENT;
        windowAtributes.gravity = Gravity.BOTTOM;
        window.setAttributes(windowAtributes);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prm_database.socical_dao().deleteSocical(social_network);
                Toast.makeText(SocicalActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SocicalActivity.this,InsertOrUpdateSocical.class);
                intent.putExtra("insertorupdate",2);
                intent.putExtra("social",social_network);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }
    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    public String getCusNameById(int id){
        Callable<String> callable=new Callable<String>() {
            @Override
            public String call() throws Exception {
                String result = null;
                if(id!=0){
                    result =prm_database.socical_dao().getCusNameById(id);
                }
                return result;
            }
        };
        Future<String> future= Executors.newSingleThreadExecutor().submit(callable);
        try {
            return future.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private static class CommentAsy extends AsyncTask<Comment,Void,Void> {
        private PRM_DATABASE dao;
        private CommentAsy(PRM_DATABASE dao){
            this.dao=dao;
        }
        @Override
        protected Void doInBackground(Comment... comments) {
            dao.socical_dao().insertComment(comments[0]);
            return null;
        }
    }
}