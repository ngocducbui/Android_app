package com.example.prm_final.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.prm_final.Convert.ImageBitMapString;
import com.example.prm_final.DAO.Comment_DAO;
import com.example.prm_final.DAO.Product_DAO;
import com.example.prm_final.Entity.Product;
import com.example.prm_final.Entity.User;
import com.example.prm_final.R;
import com.example.prm_final.Relation.ProductWithUser;
import com.example.prm_final.Services.DetailProductActivity;
import com.example.prm_final.Services.HomePageActivity;
import com.example.prm_final.database.PRM_DATABASE;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;

    private Product_DAO product_dao;
    private int uID;
    public ProductAdapter(Product_DAO product_dao){

        this.product_dao=product_dao;
    }
    public void setUID(int uID){
        this.uID=uID;
    }
    public void setData(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product,parent,false);
        return new ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product =productList.get(position);

           User u=product_dao.getProductUserById(product.getUser_id());

            holder.textViewUserName.setText(u.getUser_name());
            holder.imageViewProduct.setImageBitmap(ImageBitMapString.getBitMapFromStr(product.getImg()));
            holder.textViewPrice.setText(product.getPrice());
            holder.textViewProductName.setText(product.getName());
            holder.textDateProduct.setText(product.getDateProduct());


        product.setImg(product.getImg());
        // Set item views based on your views and data model

       holder.layoutHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("uID",uID);
                intent.putExtra("product_id", product.getId()); // Truyền ID của sản phẩm sang Activity xem chi tiết
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewProduct;
        private LinearLayout layoutHomePage;

        public TextView textViewProductName,textDateProduct;
        public TextView textViewUserName;
        public TextView textViewPrice;


        public ProductViewHolder(View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);

            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textDateProduct=itemView.findViewById(R.id.textDateProduct);

            layoutHomePage=itemView.findViewById(R.id.layoutHomePage);
        }
    }
//    private String getImagePathFromMediaStore(Uri imageUri) {
//        String[] projection = {MediaStore.Images.Media.DATA};
//
//        Cursor cursor = context.getContentResolver().query(imageUri, projection, null, null, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            String imagePath = cursor.getString(columnIndex);
//            cursor.close();
//            return imagePath;
//        }
//        return null;
//    }
}
