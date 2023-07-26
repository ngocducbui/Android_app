package com.example.prm_final.Services;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddProductActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 200;
    private Context context;
    private String imagePath;
    private Product_DAO dao;
 private Bitmap bitmap=null;

    private ImageView imgProduct;
    private EditText editProductName;
    private EditText editAddress;
    private EditText editPrice;
    private EditText editPhoneNumber;
    private EditText editFacebookLink;
    private Button btnAddProduct;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        dao=PRM_DATABASE.getInstance(this).product_dao();


        imgProduct = findViewById(R.id.imgProduct);
        editProductName = findViewById(R.id.editProductName);
        editAddress = findViewById(R.id.editAddress);
        editPrice = findViewById(R.id.editPrice);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editFacebookLink = findViewById(R.id.editFacebookLink);
        btnAddProduct=findViewById(R.id.btnAddProduct);

        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i= new Intent();
              i.setType("image/*");
              i.setAction(Intent.ACTION_GET_CONTENT);
              startActivityForResult(Intent.createChooser(i,"Select Picture"),PICK_IMAGE_REQUEST);
            }
        } );
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();

                // Định dạng ngày giờ
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

                // Chuyển đổi ngày giờ thành chuỗi
                String formattedDate = dateFormat.format(currentDate);




                Context context = v.getContext();
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                int id = sharedPreferences.getInt("id", 0);
                String productName = editProductName.getText().toString();
                String address = editAddress.getText().toString();
                String price = editPrice.getText().toString();
                String phoneNumber = editPhoneNumber.getText().toString();
                String facebookLink = editFacebookLink.getText().toString();
                Product p=new Product();
                p.setName(productName);
                p.setAddress(address);
                p.setPrice(price);
                p.setPhone(phoneNumber);
                p.setLinkFacebook(facebookLink);
                p.setUser_id(id);
                p.setImg(ImageBitMapString.getStringFromBitMap(bitmap));
                p.setDateProduct(formattedDate);
                p.setStatus(false);
                dao.AddProduct(p);
                Intent intent = new Intent(context, HomePageActivity.class);
                context.startActivity(intent);
                Toast.makeText(context,"Vui lòng đợi quản trị viên duyệt bài",Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if(resultCode==RESULT_OK){
           if(requestCode==PICK_IMAGE_REQUEST){
               Uri selectImage=data.getData();
               if(null!=selectImage){
                   imgProduct.setImageURI(selectImage);
                   try{
                       bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),selectImage);
                       imgProduct.setImageBitmap(bitmap);

                   }catch (FileNotFoundException e) {
                       throw new RuntimeException(e);
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }
               }
           }
       }
    }
    private String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(projection[0]);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();
            return imagePath;
        }
        return null;
    }

//    private void addProduct(){
//
//        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        int id = sharedPreferences.getInt("id", 0);
//        String productName = editProductName.getText().toString();
//        String address = editAddress.getText().toString();
//        String price = editPrice.getText().toString();
//        String phoneNumber = editPhoneNumber.getText().toString();
//        String facebookLink = editFacebookLink.getText().toString();
//        Product p=new Product();
//        p.setName(productName);
//        p.setAddress(address);
//        p.setPrice(price);
//        p.setPhone(phoneNumber);
//        p.setLinkFacebook(facebookLink);
//        p.setUser_id(1);
//        p.setImg(ImageBitMapString.getStringFromBitMap(bitmap));
//        dao.AddProduct(p);
//
//
//
//
//
//    }



}