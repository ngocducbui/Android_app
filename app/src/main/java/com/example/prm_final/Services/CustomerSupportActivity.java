package com.example.prm_final.Services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.prm_final.R;

public class CustomerSupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_support);
    }

    // Phương thức được gọi khi nhấn vào nút "Gọi điện"
    public void callPhoneNumber(View view) {
        String phoneNumber = "0838920599"; // Số điện thoại để gọi
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    // Phương thức được gọi khi nhấn vào nút "Gửi email"
    public void sendEmail(View view) {
        String[] emailAddresses = {"tunthe150687@fpt.edu.vn"}; // Địa chỉ email người nhận
        String subject = "Hỗ trợ khách hàng"; // Tiêu đề email

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, emailAddresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Chọn ứng dụng email"));
        }

    }
}