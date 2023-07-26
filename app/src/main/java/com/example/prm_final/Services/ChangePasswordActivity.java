package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prm_final.DAO.User_DAO;
import com.example.prm_final.R;
import com.example.prm_final.database.PRM_DATABASE;

public class ChangePasswordActivity extends AppCompatActivity {

    ImageView back;
    EditText current_pass;
    EditText new_pass;
    EditText re_new_pass;
    Button change_pass;

    User_DAO userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        userDao = PRM_DATABASE.getInstance(this).user_dao();
        back = findViewById(R.id.back_btn_2);
        current_pass = findViewById(R.id.currentPass2);
        new_pass = findViewById(R.id.new_password2);
        re_new_pass = findViewById(R.id.new_password_retype2);
        change_pass = findViewById(R.id.btn_change_pass_2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassWord();
            }
        });
    }

    void changePassWord() {
        try {
            String currentPass = current_pass.getText().toString().trim();
            String newPass = new_pass.getText().toString().trim();
            String reNewPass = re_new_pass.getText().toString().trim();
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            int id = sharedPreferences.getInt("id", 0);
            String currentPassWord = userDao.getCurrentPass(id);
            if (currentPass == null || currentPass.isEmpty() ||
                    newPass == null || newPass.isEmpty() ||
                    reNewPass == null || reNewPass.isEmpty()) {
                openDialog("All fields must be entered completely.\nPlease try again");
            } else {
                if (currentPass.equals(currentPassWord)) {
                    if (newPass.equals(reNewPass)) {
                        if (newPass.length() > 6) {
                            if (!newPass.equals(currentPassWord)) {
                                userDao.resetPassword(id, newPass);
                                openDialog2("Change Password Successfully.");
                            } else {
                                openDialog("The new password cannot be the same as the current password.\n Please try again");
                            }
                        } else {
                            openDialog("Password must be more than 6 characters.\n Please try again");
                        }
                    } else {
                        openDialog("Passwords do not match.\n Please try again");
                    }
                } else {
                    openDialog("The current password is incorrect.\n Please try again");
                }
            }
        } catch (Exception e) {
            openDialog("There was an error during processing. \nPlease try again");
        }


    }

    void openDialog(String mess) {
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
        image_done = dialog.findViewById(R.id.image_done);
        image_done.setVisibility(View.INVISIBLE);
        image_done.getLayoutParams().height = 0;
        image_done.getLayoutParams().width = 0;
        dialog.show();
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
        close_btn.setText("OK");
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(ChangePasswordActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        message = dialog.findViewById(R.id.message);
        message.setText(mess);
        dialog.show();
    }

}