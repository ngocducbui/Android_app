package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prm_final.R;

public class OtpVerifyActivity extends AppCompatActivity {
    ImageView back_btn;
    TextView email;

    Button btn_verify;
    EditText inputCode1,inputCode2,inputCode3,inputCode4,inputCode5,inputCode6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);

        back_btn= findViewById(R.id.back_btn);
        btn_verify= findViewById(R.id.btn_verify);
        email=findViewById(R.id.email_address);
        inputCode1=findViewById(R.id.input1);
        inputCode2=findViewById(R.id.input2);
        inputCode3=findViewById(R.id.input3);
        inputCode4=findViewById(R.id.input4);
        inputCode5=findViewById(R.id.input5);
        inputCode6=findViewById(R.id.input6);



        email.setText(getIntent().getStringExtra("email"));
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpVerifyActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        setupOTPInputs();
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String opt = getIntent().getStringExtra("otp");
                    String opt_input_1 = inputCode1.getText().toString().trim();
                    String opt_input_2 = inputCode2.getText().toString().trim();
                    String opt_input_3 = inputCode3.getText().toString().trim();
                    String opt_input_4 = inputCode4.getText().toString().trim();
                    String opt_input_5 = inputCode5.getText().toString().trim();
                    String opt_input_6 = inputCode6.getText().toString().trim();
                    String opt_input = opt_input_1 + opt_input_2 + opt_input_3 + opt_input_4 + opt_input_5 + opt_input_6;
                    if (opt.equals(opt_input)) {
                        //Toast.makeText(OtpVerifyActivity.this,"dung",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OtpVerifyActivity.this, ChangePassInForgotActivity.class);
                        intent.putExtra("emailAddress", getIntent().getStringExtra("email"));
                        startActivity(intent);
                    } else {
                        openDialog("OTP code is incorrect.\n Please try again!");
                    }
                }catch (Exception e){
                    openDialog("\"An error occurred during processing.\\n Please try again\"");
                }
            }
        });
    }
    private void setupOTPInputs(){
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        image_done=dialog.findViewById(R.id.image_done);
        image_done.setVisibility(View.INVISIBLE);
        image_done.getLayoutParams().height = 0;
        image_done.getLayoutParams().width = 0;
        dialog.show();
    }
}