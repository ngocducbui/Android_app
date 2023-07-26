package com.example.prm_final.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm_final.DAO.User_DAO;
import com.example.prm_final.Entity.User;
import com.example.prm_final.R;
import com.example.prm_final.Services.ListUserActivity;
import com.example.prm_final.Services.UpdateUserActivity;
import com.example.prm_final.database.PRM_DATABASE;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private List<User> userList;

    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.userList = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_custom_list_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        if (holder instanceof UserViewHolder) {
            ((UserViewHolder) holder).bindData(user);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        User_DAO userDao;
        private TextView textUserName;
        private TextView textUserEmail;
        private Button btnDelete;
        private Button btnUpdate;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            userDao = PRM_DATABASE.getInstance(itemView.getContext()).user_dao();
            this.textUserName = itemView.findViewById(R.id.txtUserName);
            this.textUserEmail = itemView.findViewById(R.id.txtUserEmail);
            this.btnDelete = itemView.findViewById(R.id.btnDeleteUser);
            this.btnUpdate = itemView.findViewById(R.id.btnUpdateUser);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textUserEmail.length() == 0) {
                        Toast.makeText(v.getContext(), "Error when try to delete user", Toast.LENGTH_SHORT).show();
                    } else {
                        userDao.deleteUserByEmail(textUserEmail.getText().toString());
                        Toast.makeText(v.getContext(), "Delete success", Toast.LENGTH_SHORT).show();
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ListUserActivity.class);
                        context.startActivity(intent);
                    }
                }
            });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textUserEmail.length() == 0) {
                        Toast.makeText(v.getContext(), "Error when try to update user", Toast.LENGTH_SHORT).show();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, UpdateUserActivity.class);
                        intent.putExtra("UserEmail", textUserEmail.getText().toString());
                        context.startActivity(intent);
                    }
                }
            });
        }

        public void bindData(User user) {
            textUserName.setText(user.getName());
            textUserEmail.setText(user.getEmail());
        }
    }
}

