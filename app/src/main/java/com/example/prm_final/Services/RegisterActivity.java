package com.example.prm_final.Services;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm_final.Convert.ImageBitMapString;
import com.example.prm_final.DAO.User_DAO;
import com.example.prm_final.Entity.User;
import com.example.prm_final.R;
import com.example.prm_final.database.PRM_DATABASE;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 200;
    private Bitmap bitmap = null;

    User_DAO userDao;
    EditText etfullname;
    EditText etgmail;
    EditText etphone;
    EditText etAddress;
    TextView etdob;
    EditText etusername;
    EditText etpassword;
    EditText etrepassword;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button register_btn;
    Button choose_file_btn;
    private final int GALLERY_REQ_CODE = 1000;
    Button registerBtn;
    Button cancelBtn;
    TextView displayDate;
    TextView image_url;
    ImageView back_btn;
    byte[] image = null;
    DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userDao = PRM_DATABASE.getInstance(this).user_dao();
        image_url = findViewById(R.id.image);
        etfullname = findViewById(R.id.fullname);
        etgmail = findViewById(R.id.gmail);
        etphone = findViewById(R.id.phone);
        etAddress = findViewById(R.id.address);
        etdob = findViewById(R.id.dob);
        etusername = findViewById(R.id.username);
        etpassword = findViewById(R.id.password);
        etrepassword = findViewById(R.id.repassword);
        register_btn = findViewById(R.id.register_btn);
        radioGroup = findViewById(R.id.gender);
        choose_file_btn = findViewById(R.id.choose_file);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewUser();
            }
        });
        back_btn = findViewById(R.id.back_click);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        choose_file_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        displayDate = (TextView) findViewById(R.id.dob);
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
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
                displayDate.setText(date);
            }
        };
    }


    void AddNewUser() {
        try {
            String fullname = etfullname.getText().toString().trim();
            String gmail = etgmail.getText().toString().trim();
            String phone = etphone.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String dob = etdob.getText().toString().trim();
            String username = etusername.getText().toString().trim();
            String password = etpassword.getText().toString().trim();
            String repassword = etrepassword.getText().toString().trim();
            if (bitmap != null) {
                image = ImageBitMapString.getStringFromBitMap(bitmap);
            } else {
                image = null;
            }
            Boolean gender;
            Boolean status = false;
            int role_id = 0;
            int radio_id = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(radio_id);
            if (radioButton.getText().equals("Male")) {
                gender = true;
            } else {
                gender = false;
            }
//            if(fullname.isEmpty() && gmail.isEmpty() && phone.isEmpty() && address.isEmpty()&&
//            dob.isEmpty()&& username.isEmpty()&& password.isEmpty()){
//                openDialog("All fields must be entered completely.\nPlease try again");
//            }else if(fullname.isEmpty() && gmail.isEmpty() && phone.isEmpty() && address.isEmpty()&&
//                    dob.isEmpty()&& username.isEmpty()){
//                openDialog("All fields must be entered completely.\nPlease try again");
//            }

            if (fullname == null || fullname.isEmpty() ||
                    gmail == null || gmail.isEmpty() ||
                    phone == null || phone.isEmpty() ||
                    address == null || address.isEmpty() ||
                    dob == null || dob.isEmpty() ||
                    username == null || username.isEmpty() ||
                    repassword == null || repassword.isEmpty() ||
                    password == null || password.isEmpty()) {
                openDialog("All fields must be entered completely.\nPlease try again");
            } else {
                if (checkMail(gmail)) {
                    Integer id_check = userDao.selectUserIdByEmail(gmail);
                    if (id_check == 0) {
                        if (checkPhone(phone)) {
                            if (password.equals(repassword)) {
                                if(password.length()>6){
                                    try {
                                        User user = new User(fullname, dob, address, gender, gmail, phone,
                                                status, role_id, image, username, password);
                                        userDao.insertUser(user);
                                        openDialog2(String.format("Add New User Successfully!\n Welcome %s", fullname));
                                    } catch (Exception e) {
                                        openDialog("This username is registered. \nPlease use username!");
                                    }
                                }else{
                                    openDialog("Password must be more than 6 characters.\n Please try again");
                                }
                            } else {
                                openDialog("Passwords do not match.\n Please try again!");
                            }
                        } else {
                            openDialog("Wrong phone format.\n Please try again!");
                        }
                    } else {
                        openDialog("This email address is registered. \nPlease use another email!");
                    }
                } else {
                    openDialog("Wrong email format.\n Please try again!");
                }
            }


            // User user = new User(fullname, dob, address, gender, gmail, phone,
            //   status, role_id, image, username, password);
            //  userDao.insertUser(user);
            // openDialog2(String.format("Add New User Successfully!\n Welcome %s",fullname));
        } catch (Exception e) {
            openDialog(String.format("%s\n Please try again", e.getMessage()));
            Toast.makeText(this, "error" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void checkradiobutton(View v) {
        int radio_id = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radio_id);
    }

    void openDialog(String mess) {

        Button close_btn;
        TextView message;
        ImageView image_done;
//        Button closebtn;
//
//        closebtn=findViewById(R.id.close);
//        closebtn.setText("Return To Login");

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
        image_done = dialog.findViewById(R.id.image_done);
        image_done.setVisibility(View.INVISIBLE);
        image_done.getLayoutParams().height = 0;
        image_done.getLayoutParams().width = 0;
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                Uri selectImage = data.getData();
                if (null != selectImage) {
                    //imgProduct.setImageURI(selectImage);
                    try {
                        image_url.setText("Selected");
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectImage);
                        //  imgProduct.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }}catch (Exception e){
            openDialog2("An error occurred during processing.\n Please try again");
        }
    }

    void openDialog2(String mess) {
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
        close_btn.setText("Login");
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        message = dialog.findViewById(R.id.message);
        message.setText(mess);
        dialog.show();
    }

    boolean checkPhone(String phone) {
        try {
            String regex = "^(0)[0-9]{9}$";
            if (phone.matches(regex)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    boolean checkMail(String mail) {
        String regex = "^(.+)@(.+)$";
        return mail.matches(regex);
    }
}