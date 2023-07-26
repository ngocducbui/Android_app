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

public class AddUserActivity extends AppCompatActivity {
    private User_DAO userDao;
    private EditText editFullName;
    private EditText editEmail;
    private RadioButton rdMale;
    private EditText editAddress;
    private EditText editPhone;
    private EditText editUserName;
    private EditText editPassword;
    private TextView dob;
    private Button btnSave;
    DatePickerDialog.OnDateSetListener dateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userDao = PRM_DATABASE.getInstance(this).user_dao();
        setContentView(R.layout.activity_add_user);
        editFullName = findViewById(R.id.edtFullName);
        editEmail = findViewById(R.id.edtEmail);
        rdMale = findViewById(R.id.rdMale);
        editAddress = findViewById(R.id.edtAddress);
        editPhone = findViewById(R.id.edtPhone);
        editUserName = findViewById(R.id.edtUserName);
        editPassword = findViewById(R.id.edtPassword);
        btnSave = findViewById(R.id.btnAddSave);
        dob = findViewById(R.id.dpDateOfBirth);


        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddUserActivity.this,
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
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setName(editFullName.getText().toString());
                user.setEmail(editEmail.getText().toString());
                user.setDob(dob.getText().toString());
                user.setGender(rdMale.isChecked());
                user.setAddress(editAddress.getText().toString());
                user.setPhone(editPhone.getText().toString());
                user.setUser_name(editUserName.getText().toString());
                user.setPassword(editPassword.getText().toString());
                user.setStatus(true);
                user.setRole_id(1);
                user.setImage(null);

                userDao.insertUser(user);
                Context context = v.getContext();
                Intent intent = new Intent(context, ListUserActivity.class);
                context.startActivity(intent);
            }
        });

    }
}