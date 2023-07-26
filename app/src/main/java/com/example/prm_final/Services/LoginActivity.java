package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
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
import com.example.prm_final.Entity.User;
import com.example.prm_final.R;
import com.example.prm_final.database.PRM_DATABASE;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    SignInButton googleBtn;
    ImageView facebook_login;
    TextView register_btn;
    TextView forget_password;
    Button login_btn;
    Button close_btn;
    ImageView image_done;

    EditText etusername;
    EditText etpassword;

    User_DAO userDao;
    private static int RC_SIGN_IN = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        userDao=PRM_DATABASE.getInstance(this).user_dao();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        googleBtn = findViewById(R.id.google_login);
        googleBtn.setSize(SignInButton.SIZE_STANDARD);

        register_btn = findViewById(R.id.register);
        login_btn = findViewById(R.id.login);
        forget_password= findViewById(R.id.forget);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String name= acct.getDisplayName();
            Log.d("eee",name);
            navigateToSecondActivity();

        }
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_open();
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forget_password_open();
            }
        });
    }

    void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }
    void register_open(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    void login(){

        try{
            etusername=findViewById(R.id.username_text);
            etpassword=findViewById(R.id.password_text);
            String username = etusername.getText().toString().trim();
            String password = etpassword.getText().toString().trim();
            int id= userDao.selectUserByUserNameAndPass(username,password);
            int role_id= userDao.selectRoleIdByUserNameAndPass(username,password);
            if(isNullOrEmpty(id)){
                openDialog2("Username or password is incorrect.\n Please try again!");
            }else{
                if(role_id==1){
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                    intent.putExtra("uID",id);
                    startActivity(intent);
                }
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("id",id);
                editor.apply();
            }
        }catch (Exception e){
            openDialog2(e.getMessage());
        }
//        if(true){
//
//        }else{
//            openDialogLogin();
//        }
    }
    void openDialogLogin(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dinalog_layout);

        Window window = dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity= Gravity.CENTER;
        window.setAttributes(windowAttributes);

        close_btn =  dialog.findViewById(R.id.close);
        image_done = dialog.findViewById(R.id.image_done);

        //image_done.setImageDrawable(getResources().getDrawable(R.drawable.close));
        image_done.setVisibility(View.INVISIBLE);
        image_done.getLayoutParams().height = 0;
        image_done.getLayoutParams().width = 0;

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    void forget_password_open(){
        Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Log.d("error","Something went wrong "+ e);
                //Toast.makeText(getApplicationContext(), "Something went wrong "+ e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    void navigateToSecondActivity() {
        try{ finish();
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            gsc = GoogleSignIn.getClient(this, gso);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);


            Integer id= userDao.selectUserIdByEmail(acct.getEmail());
            if(id.equals(0)){
                Toast.makeText(this, "Welcome:"+acct.getDisplayName(), Toast.LENGTH_SHORT).show();
                try {
                    String[] user_name = acct.getEmail().split("[@._]");
                    User user = new User(acct.getDisplayName(),acct.getEmail(),user_name[0]);
                    userDao.insertUser(user);
                    Integer id_new= userDao.selectUserIdByEmail(acct.getEmail());
                    if(id_new!=0){
                        openDialog2("Welcome");
                    }
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("id", id_new);
                    editor.apply();
                }catch (Exception e){
                    Toast.makeText(this, "error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }else{
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("id", id);
                editor.apply();
            }
            signOut();
            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
            startActivity(intent);
        }catch (Exception e){
            openDialog2("An error occurred during processing.\n Please try again");
        }

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
    public static boolean isNullOrEmpty(Integer number) {
        return number == null || number == 0;
    }
    void signOut() {
        try {
            gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    finish();
                    //startActivity(new Intent(SecondActivity.this, LoginActivity.class));
                }
            });
        }catch(Exception e){
            openDialog2("An error occurred during processing.\n Please try again");
        }
    }
}