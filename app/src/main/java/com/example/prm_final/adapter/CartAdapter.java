package com.example.prm_final.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm_final.Convert.ImageBitMapString;
import com.example.prm_final.DAO.Socical_DAO;
import com.example.prm_final.Entity.Cart;
import com.example.prm_final.Entity.Product;
import com.example.prm_final.R;
import com.example.prm_final.databinding.ItemCartBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private List<Cart> listCart=new ArrayList<>();
    public ICart iCart;
    public Socical_DAO socical_dao;
    public boolean unCheckAll=false;

    public CartAdapter(Context context, Socical_DAO socical_dao, ICart iCart) {
        this.context = context;
        this.iCart=iCart;
        this.socical_dao=socical_dao;
    }

    public void setUnCheckAll(boolean status){
        unCheckAll=status;
        notifyDataSetChanged();
    }
    public void setListCart(List<Cart> listCart) {
        this.listCart = listCart;
        notifyDataSetChanged();
    }

    public interface ICart{
        public void onCLick(int position,boolean status);
        void onCLickSL(Boolean status,int position,boolean sStatus);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_cart,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Cart cart=listCart.get(position);
        Product product=socical_dao.getProductById(cart.getpID());
        holder.binding.txtBookName.setText(product.getName());
        holder.binding.txtPriceD.setText(product.getPrice()+"");
        holder.binding.txtSL.setText(cart.getSl()+"");
        Bitmap pImg= ImageBitMapString.getBitMapFromStr(product.getImg());
        holder.binding.imgProduct.setImageBitmap(pImg);

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.btnCheck.setChecked(!holder.binding.btnCheck.isChecked());
                int status=(holder.binding.btnCheck.isChecked()==true) ?View.VISIBLE:View.GONE;
                holder.binding.btnCheck.setVisibility(status);
                iCart.onCLick(position,holder.binding.btnCheck.isChecked());
                notifyDataSetChanged();

            }
        });
        if(unCheckAll){
            holder.binding.btnCheck.setChecked(false);
            int status=(holder.binding.btnCheck.isChecked()==true) ?View.VISIBLE:View.GONE;
            holder.binding.btnCheck.setVisibility(status);

        }
        if(position==listCart.size()-1){
            unCheckAll=false;
        }
        holder.binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCart.onCLickSL(true,position,holder.binding.btnCheck.isChecked());
                notifyDataSetChanged();
            }
        });
        holder.binding.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCart.onCLickSL(false,position,holder.binding.btnCheck.isChecked());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ItemCartBinding binding;
        public ViewHolder(@NonNull ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
