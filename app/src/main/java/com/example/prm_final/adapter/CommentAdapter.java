package com.example.prm_final.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm_final.DAO.Comment_DAO;
import com.example.prm_final.Entity.Comment;
import com.example.prm_final.Entity.User;
import com.example.prm_final.R;
import com.example.prm_final.Relation.ProductWithComment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> comments;
    private Comment_DAO comment_dao;
    public CommentAdapter(Comment_DAO comment_dao){
        this.comment_dao=comment_dao;
    }

    public void setData(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        User u=comment_dao.getUserById(comment.getUser_id());
            holder.contentTextView.setText(comment.getContentComment());

        holder.nameTextView.setText(u.getName());
        // Remove the trailing comma and space






    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView contentTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
        }
    }
}