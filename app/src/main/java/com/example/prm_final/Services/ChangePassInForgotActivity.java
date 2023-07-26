package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.prm_final.DAO.User_DAO;
import com.example.prm_final.R;
import com.example.prm_final.database.PRM_DATABASE;

public class ChangePassInForgotActivity extends AppCompatActivity {
    ImageView back_btn;
    Button change_btn;
    EditText pass;
    EditText re_pass;
    User_DAO userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_in_forgot);
        back_btn = findViewById(R.id.back_btn2);
        change_btn = findViewById(R.id.btn_change);
        pass = findViewById(R.id.new_password);
        re_pass = findViewById(R.id.new_password_retype);
        userDao = PRM_DATABASE.getInstance(this).user_dao();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePassInForgotActivity.this, OtpVerifyActivity.class);
                startActivity(intent);
            }
        });

        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String pass_new = pass.getText().toString();
                    String pass_new_retype = re_pass.getText().toString();
                    if (v == null || pass_new.isEmpty() ||
                            pass_new_retype == null || pass_new_retype.isEmpty() ){
                        openDialog("All fields must be entered completely.\nPlease try again");

                    }else{
                        if (pass_new_retype.equals(pass_new)) {
                            if (pass_new.length() > 6) {
                                String email = getIntent().getStringExtra("emailAddress");
                                int id = userDao.selectUserIdByEmail(email);
                                try {
                                    userDao.resetPassword(id, pass_new);
                                    Intent intent = new Intent(ChangePassInForgotActivity.this, ChangePassSuccessActivity.class);
                                    startActivity(intent);
                                } catch (Exception ex) {
                                    openDialog2("An error occurred during processing.\n Please try again");
                                }
                            } else {
                                openDialog2("Password must be more than 6 characters.\n Please try again");
                            }
                        } else {
                            openDialog2("Passwords do not match\nPlease try again");
                        }
                    }
                } catch (Exception e) {
                    openDialog2("An error occurred during processing.\n Please try again");
                }
            }
        });
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
        image_done = dialog.findViewById(R.id.image_done);
        image_done.setVisibility(View.INVISIBLE);
        image_done.getLayoutParams().height = 0;
        image_done.getLayoutParams().width = 0;
        dialog.show();
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
}