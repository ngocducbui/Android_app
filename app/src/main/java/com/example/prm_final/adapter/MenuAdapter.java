package com.example.prm_final.adapter;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.prm_final.R;
import com.example.prm_final.Services.AboutUsActivity;
import com.example.prm_final.Services.AddProductActivity;
import com.example.prm_final.Services.CartActivity;
import com.example.prm_final.Services.ItemMenu;
import com.example.prm_final.Services.ListProductActivity;
import com.example.prm_final.Services.LoginActivity;
import com.example.prm_final.Services.PolicyActivity;
import com.example.prm_final.Services.ProfileActivity;
import com.example.prm_final.Services.SocicalActivity;

import java.util.List;

public class MenuAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ItemMenu> list;

    public MenuAdapter(Context context, int layout, List<ItemMenu> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView textView;
        ImageView imageView;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textItem);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imgicon);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position).tenItem);
        viewHolder.imageView.setImageResource(list.get(position).icon);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Xử lý khi chọn item tương ứng
                if (position == 0) {
                    Context context = v.getContext();
                    // Chuyển sang trang tương ứng với item 0
                    Intent intent = new Intent(context, ProfileActivity.class);
                    context.startActivity(intent);
                } else if (position == 1) {
                    Context context = v.getContext();
                    // Chuyển sang trang tương ứng với item 0
                    Intent intent = new Intent(context, AddProductActivity.class);
                    context.startActivity(intent);
                } else if (position == 2) {
                    Context context = v.getContext();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    int id = sharedPreferences.getInt("id", 0);


                    // Chuyển sang trang tương ứng với item 1
                    Intent intent = new Intent(context, ListProductActivity.class);
                    intent.putExtra("userId", id);
                    context.startActivity(intent);
                } else if (position == 3) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, SocicalActivity.class);
                    context.startActivity(intent);
                } else if (position == 4) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, CartActivity.class);
                    context.startActivity(intent);
                } else if (position == 5) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, AboutUsActivity.class);
                    context.startActivity(intent);
                } else if (position == 6) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, PolicyActivity.class);
                    context.startActivity(intent);
                } else if (position == 7) {
                    Context context = v.getContext();
//                    Intent intent =  new Intent(context, LoginActivity.class);
//                    context.startActivity(intent);
                    openAlerBox(context);
                }
                Context context = v.getContext();
                // Đóng DrawerLayout sau khi chọn item
                DrawerLayout drawerLayout = ((Activity) context).findViewById(R.id.drawerLayout);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        return convertView;
    }

    void openAlerBox(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Log Out!");
        builder.setIcon(R.drawable.baseline_logout_24);
        builder.setCancelable(false);

        builder.setMessage("Do you really want to log out of the program?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
