package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.prm_final.R;

public class InviteFriendActivity extends AppCompatActivity {

    private static final String APP_DOWNLOAD_URL = "https://example.com/app-download-link";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);

        Button buttonShareMessenger = findViewById(R.id.buttonShareMessenger);
        buttonShareMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareLinkViaMessenger();
            }
        });

        Button buttonShareFacebook = findViewById(R.id.buttonShareFacebook);
        buttonShareFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareLinkViaFacebook();
            }
        });

        Button buttonShareGmail = findViewById(R.id.buttonShareGmail);
        buttonShareGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareLinkViaGmail();
            }
        });
    }

    private void shareLinkViaMessenger() {
        String packageName = "com.facebook.orca"; // Gói ứng dụng Messenger
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, APP_DOWNLOAD_URL);
            intent.setPackage(packageName); // Messenger package name
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }

    }

    private void shareLinkViaFacebook() {
        String packageName = "com.facebook.katana"; // Gói ứng dụng Messenger

        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, APP_DOWNLOAD_URL);
            intent.setPackage(packageName); // Facebook package name
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }

    }

    private void shareLinkViaGmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Mời bạn tải ứng dụng");
        intent.putExtra(Intent.EXTRA_TEXT, APP_DOWNLOAD_URL);
        startActivity(Intent.createChooser(intent, "Chia sẻ qua Gmail"));
    }
}