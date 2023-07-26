package com.example.prm_final.Services;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prm_final.DAO.User_DAO;
import com.example.prm_final.R;
import com.example.prm_final.database.PRM_DATABASE;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ForgetPasswordActivity extends AppCompatActivity {
    ImageView back_btn;
    String phone, otp;
    String message = " is your verification code.";
    Button next_btn;
    EditText email_text;
    int code_otp;

    User_DAO userDao;

    ProgressBar progressBar;
    FirebaseAuth mAuth;
    String stiMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        userDao = PRM_DATABASE.getInstance(this).user_dao();
        back_btn = findViewById(R.id.back_click);
        next_btn = findViewById(R.id.next);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_text = findViewById(R.id.email_forgot);
                String email = email_text.getText().toString().trim();
                Integer id = userDao.selectUserIdByEmail(email);
                if (email.isEmpty()) {
                    openDialog("Please fill out this field.\n Please try again!");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    openDialog("Your email is not in the correct format.\n Please try again!");
                } else if (id.equals(0)) {
                    openDialog("Email is not registered.\n Please try again!");
                } else {
                    sendMail();
                }
            }
        });
    }

    void sendMail() {
        try {
            final String[] errors = new String[1];
            errors[0] = "1111";
            String hi=null;
            Random random = new Random();
            code_otp = random.nextInt(899999) + 100000;
            String url = "https://ngocduc-prm.000webhostapp.com/sendMail.php";
            //String url = "http://ngocduc.42web.io/sendMail.php";
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(ForgetPasswordActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    errors[0] = "errore3e3e";
                    Log.d("hi", errors[0]);
                    finish();
                    //openDialog("Email is not registered.\n Please try again!");
                    Toast.makeText(ForgetPasswordActivity.this, "loi" + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    String[] name = email_text.getText().toString().trim().split("@");
                    Map<String, String> mydata = new HashMap<>();
                    mydata.put("email", email_text.getText().toString().trim());
                    mydata.put("name", name[0]);
                    mydata.put("code", String.valueOf(code_otp));
                    return mydata;
                }
            };
            requestQueue.add(stringRequest);
            Intent intent = new Intent(ForgetPasswordActivity.this, OtpVerifyActivity.class);
            intent.putExtra("email", email_text.getText().toString().trim());
            intent.putExtra("otp", String.valueOf(code_otp));
            startActivity(intent);




        } catch (Exception e) {
            openDialog("An error occurred while running the program.\n Please try again!");
            Log.d("erroorrr", e.toString());
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
}

