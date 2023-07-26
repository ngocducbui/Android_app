package com.example.prm_final.Services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm_final.DAO.Socical_DAO;
import com.example.prm_final.Entity.Social_Network;
import com.example.prm_final.Entity.User;
import com.example.prm_final.R;
import com.example.prm_final.databinding.ItemSocicalBinding;

import java.util.ArrayList;
import java.util.List;

public class SocicalAdapter extends RecyclerView.Adapter<SocicalAdapter.ViewHolder> {
    private Context context;
    private List<Social_Network> listSocical=new ArrayList<>();
    public ISocical iSocical;

    private Socical_DAO SocicalDAO;


    int dem=0;

    public SocicalAdapter(Context context, ISocical iSocical, Socical_DAO SocicalDAO) {
        this.context = context;
        this.iSocical = iSocical;
        this.SocicalDAO = SocicalDAO;
    }

    public void setListSocical(List<Social_Network> listSocical) {
        this.listSocical = listSocical;
        notifyDataSetChanged();
    }

    public interface ISocical{
        public void onLongClick(int position);
        void onCommnetClick(int position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSocicalBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_socical,parent,false);
        return new ViewHolder(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Social_Network socical=listSocical.get(position);
        String  user=SocicalDAO.getCusNameById(socical.getUser_id());

        holder.binding.txtLike.setText(socical.getNumberOfLike()+" like");
        Uri uri=Uri.parse(socical.getImage());
 //       context.getContentResolver().takePersistableUriPermission(Uri.parse(socical.getImage()), (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION));
        holder.binding.imgProduct.setImageURI(uri);
        holder.binding.txtContent.setText(socical.getContent());
        holder.binding.txtUname.setText(user);
        holder.binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                iSocical.onLongClick(position);
                return false;
            }
        });
        holder.binding.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iSocical.onCommnetClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listSocical.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ItemSocicalBinding binding;
        public ViewHolder(@NonNull ItemSocicalBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
