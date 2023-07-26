package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.prm_final.DAO.User_DAO;
import com.example.prm_final.Entity.User;
import com.example.prm_final.R;
import com.example.prm_final.database.PRM_DATABASE;

import java.sql.Date;
import java.util.Calendar;

public class UpdateUserActivity extends AppCompatActivity {
    private User_DAO userDao;
    private EditText editFullName;
    private EditText editEmail;
    private RadioButton rdMale;
    private RadioButton rdFemale;
    private EditText editAddress;
    private EditText editPhone;
    private EditText editUserName;
    private EditText editPassword;
    private TextView dob;
    private Button btnSave;
    DatePickerDialog.OnDateSetListener dateSetListener;
    private User userByEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        userDao = PRM_DATABASE.getInstance(this).user_dao();

        Intent intent = getIntent();
        String UserEmail = intent.getStringExtra("UserEmail");
        userByEmail = userDao.getUserByEmail(UserEmail);

        editFullName = findViewById(R.id.edtFullName);
        editEmail = findViewById(R.id.edtEmail);
        editAddress = findViewById(R.id.edtAddress);
        rdMale = findViewById(R.id.rdMale);
        rdFemale = findViewById(R.id.rdFemale);
        editPhone = findViewById(R.id.edtPhone);
        editUserName = findViewById(R.id.edtUserName);
        editPassword = findViewById(R.id.edtPassword);
        btnSave = findViewById(R.id.btnUpdateSave);
        dob = findViewById(R.id.dpDateOfBirth2);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateUserActivity.this,
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
                dob.setText(date);
            }
        };

        editFullName.setText(userByEmail.getName());
        editEmail.setText(userByEmail.getEmail());
        if (userByEmail.getGender()) {
            rdMale.setChecked(true);
        } else {
            rdFemale.setChecked(true);
        }
        editAddress.setText(userByEmail.getAddress());
        editPhone.setText(userByEmail.getPhone());
        editUserName.setText(userByEmail.getUser_name());
        editPassword.setText(userByEmail.getPassword());
        dob.setText(userByEmail.getDob());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userByEmail.setName(editFullName.getText().toString());
                userByEmail.setEmail(editEmail.getText().toString());
                userByEmail.setDob(dob.getText().toString());
                userByEmail.setGender(rdMale.isChecked());
                userByEmail.setAddress(editAddress.getText().toString());
                userByEmail.setPhone(editPhone.getText().toString());
                userByEmail.setUser_name(editUserName.getText().toString());
                userByEmail.setPassword(editPassword.getText().toString());
                userByEmail.setStatus(true);
                userByEmail.setRole_id(1);
                userByEmail.setImage(null);
                userDao.updateUser(userByEmail);
                Context context = v.getContext();
                Intent intent = new Intent(context, ListUserActivity.class);
                context.startActivity(intent);
            }
        });
    }
}