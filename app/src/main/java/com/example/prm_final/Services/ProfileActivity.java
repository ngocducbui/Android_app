package com.example.prm_final.Services;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.prm_final.Convert.ImageBitMapString;
import com.example.prm_final.DAO.User_DAO;
import com.example.prm_final.Entity.User;
import com.example.prm_final.R;
import com.example.prm_final.database.PRM_DATABASE;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 200;
    private Bitmap bitmap=null;
    byte[] image;
    ImageView back_btn;
    Button edit;
    TextView changebtn;
    EditText username;
    EditText phone;
    Spinner gender;
    TextView dob;
    EditText address;
    TextView name;
    TextView email;
    CircleImageView imageView_avt;
    private User_DAO userDao;

    boolean isChangeImg=false;
    ImageView camera;
    private User userById;
    String[] gender_value = {"Male", "Female"};
    DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userDao = PRM_DATABASE.getInstance(this).user_dao();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", 0);
        userById = userDao.getUserById(id);

        back_btn = findViewById(R.id.back);
        edit = findViewById(R.id.edit);
        username = findViewById(R.id.user_name);
        phone = findViewById(R.id.phone_profile);
        dob = findViewById(R.id.dob_profile);
        gender = findViewById(R.id.gender_profile);
        address = findViewById(R.id.address_profile);
        name = findViewById(R.id.name);
        email = findViewById(R.id.mail);
        imageView_avt=findViewById(R.id.imageView);
        changebtn=findViewById(R.id.change_password);
        camera=findViewById(R.id.camera);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender_value);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(ad);
        setDisable();
        loadData();
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();

            }
        });
        changebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChangeImg=true;
                Intent i= new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Select Picture"),PICK_IMAGE_REQUEST);
            }
        });
    }

    void setDisable() {
        try {
            username.setFocusable(false);
            username.setFocusableInTouchMode(false);
            username.setClickable(false);
            phone.setFocusable(false);
            phone.setFocusableInTouchMode(false);
            phone.setClickable(false);
            address.setFocusable(false);
            address.setFocusableInTouchMode(false);
            address.setClickable(false);
            dob.setFocusable(false);
            dob.setFocusableInTouchMode(false);
            dob.setClickable(false);
            gender.setFocusable(false);
            gender.setFocusableInTouchMode(false);
            gender.setClickable(false);
            gender.setEnabled(false);

            username.setTextColor(getResources().getColor(R.color.blue, null));
            phone.setTextColor(getResources().getColor(R.color.blue, null));
            //gender.setTextColor(getResources().getColor(R.color.blue, null));
            dob.setTextColor(getResources().getColor(R.color.blue, null));
            address.setTextColor(getResources().getColor(R.color.blue, null));
        }catch (Exception e){
            openDialog2("An error occurred during processing.\n Please try again");
        }
    }

    void setEnable() {
//        username.setFocusable(true);
//        username.setFocusableInTouchMode(true);
//        username.setClickable(true);
        try {
            phone.setFocusable(true);
            phone.setFocusableInTouchMode(true);
            phone.setClickable(true);
            address.setFocusable(true);
            address.setFocusableInTouchMode(true);
            address.setClickable(true);
            dob.setFocusable(true);
            dob.setFocusableInTouchMode(true);
            dob.setClickable(true);
            gender.setFocusable(true);
            gender.setFocusableInTouchMode(true);
            gender.setClickable(true);
            gender.setEnabled(true);

            //username.setTextColor(getResources().getColor(R.color.black, null));
            phone.setTextColor(getResources().getColor(R.color.black, null));
            dob.setTextColor(getResources().getColor(R.color.black, null));
            //gender.setTextColor(getResources().getColor(R.color.black, null));
            address.setTextColor(getResources().getColor(R.color.black, null));
        }catch (Exception e){
            openDialog2("An error occurred during processing.\n Please try again");
        }
    }

    void editProfile() {
        try {
            edit.setText("SAVE CHANGE");
            setEnable();
            String[] date = dob.getText().toString().toString().split("/", 3);
            int year=Integer.parseInt(date[2]);
            int month=Integer.parseInt(date[1]);
            int day=Integer.parseInt(date[0]);


            dob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            ProfileActivity.this,
                            android.R.style.Theme_Holo_Dialog_MinWidth,
                            dateSetListener,
                            year, month,day);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
            });
            dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                    month = month + 1;
                    Log.d("RegisterActivity", "test" + year + " " + month + " " + dayOfMonth);
                    String date = dayOfMonth + "/" + month + "/" + year;
                    dob.setText(date);
                }
            };
//            userById.setName(name.getText().toString().trim());
//            userById.setEmail(email.getText().toString());
//            userById.setDob(dob.getText().toString());
//            if(gender.getSelectedItem().toString().trim().equals("Male")){
//                userById.setGender(true);
//            }else{
//                userById.setGender(false);
//            }
//            //userById.setGender(gender.getSelectedItem().toString());
//            userById.setAddress(address.getText().toString().trim());
//            userById.setPhone(phone.getText().toString());
//            userById.setUser_name(username.getText().toString());
//            //userById.setPassword(editPassword.getText().toString());
//            userById.setStatus(true);
//            userById.setRole_id(0);
//            userById.setImage(null);


        } catch (Exception e) {
            openDialog("An error occurred during processing.\n Please try again");

        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChange();
            }
        });
    }

    void saveChange() {
        try {
            edit.setText("EDIT PROFILE");
            setDisable();
            String phone_new = phone.getText().toString().trim();
            String dob_new = dob.getText().toString().trim();
            String address_new = address.getText().toString().trim();
            if(isChangeImg){
                image = ImageBitMapString.getStringFromBitMap(bitmap);
            }
            boolean gender_new = true;
            if (gender.getSelectedItem().toString().trim().equals("Female")) {
                gender_new = false;
            }
            if (checkPhone(phone_new)){
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                int id = sharedPreferences.getInt("id", 0);
                if(isChangeImg){
                    userDao.updateProfilewithImage(id, phone_new, dob_new, address_new, gender_new,image);
                }
                userDao.updateProfile(id, phone_new, dob_new, address_new, gender_new);
                openDialog("Successfully updated personal information.");
            }else{
                openDialog2("The phone number is not in the correct format.\n Please try again");
            }

        } catch (Exception e) {
            openDialog("An error occurred during processing. \nPlease try again");

        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });
    }

    void openDialog(String mess) {
        Button close_btn;
        TextView message;

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dinalog_layout);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        close_btn = dialog.findViewById(R.id.close);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        message = dialog.findViewById(R.id.message);
        message.setText(mess);
        dialog.show();
    }
    void openDialog2(String mess) {
        Button close_btn;
        TextView message;
        ImageView image_done;

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dinalog_layout);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        close_btn = dialog.findViewById(R.id.close);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        message = dialog.findViewById(R.id.message);
        message.setText(mess);
        image_done=dialog.findViewById(R.id.image_done);
        image_done.setVisibility(View.INVISIBLE);
        image_done.getLayoutParams().height = 0;
        image_done.getLayoutParams().width = 0;
        dialog.show();
    }
    void loadData() {
        try {
            name.setText(userById.getName());
            email.setText(userById.getEmail());
            username.setText(userById.getUser_name());
            phone.setText(userById.getPhone());
            dob.setText(userById.getDob());
            if (userById.getImage() == null) {
                //imageView_avt.setImageBitmap(ImageBitMapString.getBitMapFromStr(userById.getImage()));
            } else {
                imageView_avt.setImageBitmap(ImageBitMapString.getBitMapFromStr(userById.getImage()));

            }
            if (userById.getGender() == true) {
                // gender.getDisplay()
                gender.setSelection(0);

            } else {
                gender.setSelection(1);

            }
            address.setText(userById.getAddress());
        }catch (Exception e){
            openDialog2("An error occurred during processing.\n Please try again");
        }
    }
    boolean checkPhone(String phone){
        try{
            String regex="^[0-9]{10}$";
            if(phone.matches(regex)){
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
        if(resultCode==RESULT_OK){
            if(requestCode==PICK_IMAGE_REQUEST){
                Uri selectImage=data.getData();
                if(null!=selectImage){
                    imageView_avt.setImageURI(selectImage);
                    try{
                        bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),selectImage);
                        imageView_avt.setImageBitmap(bitmap);

                    }catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }}
        catch (Exception e){
            openDialog("An error occurred during processing.\n Please try again");
        }
    }
}