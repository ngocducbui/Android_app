package com.example.prm_final.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm_final.Convert.ImageBitMapString;
import com.example.prm_final.Entity.Product;
import com.example.prm_final.R;
import com.example.prm_final.Services.UpdateProductActivity;

import java.util.List;

public class ProductUserAdapter  extends RecyclerView.Adapter<ProductUserAdapter.ProductViewHolder> {
    private List<Product> productList;
    private IClickItemProduct iClickItemProduct;

    public ProductUserAdapter(IClickItemProduct iClickItemProduct) {
        this.iClickItemProduct=iClickItemProduct;
    }
    public interface IClickItemProduct{
        void updateProduct(Product p);
        void deleteProduct(Product product);
    }

    public void setData(List<Product> productList) {
        this.productList = productList;

        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public ProductUserAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_user,parent,false);
        return new ProductUserAdapter.ProductViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ProductUserAdapter.ProductViewHolder holder, int position) {
            Product product =productList.get(position);
        if(product==null){
            return;
        }


            holder.imageViewProduct.setImageBitmap(ImageBitMapString.getBitMapFromStr(product.getImg()));
            holder.textViewPrice.setText(product.getPhone());
            holder.textViewProductName.setText(product.getName());
            holder.tvDateProduct.setText(product.getDateProduct());
            holder.textViewAddress.setText(product.getAddress());

            holder.editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, UpdateProductActivity.class);
                    intent.putExtra("product_id", product.getId()); // Truyền ID của sản phẩm sang Activity xem chi tiết
                    context.startActivity(intent);
                }
            });
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickItemProduct.deleteProduct(product);




                }
            });











        // Set item views based on your views and data model


    }

    @Override
    public int getItemCount() {

        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewProduct;

        public TextView textViewProductName,textViewAddress;
        public TextView textViewUserName;
        public TextView textViewPrice,tvDateProduct;
        public Button editBtn;
        public Button deleteBtn;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);

            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            editBtn = itemView.findViewById(R.id.editButton);
            deleteBtn = itemView.findViewById(R.id.deleteButton);
            tvDateProduct=itemView.findViewById(R.id.tvDateProduct);
            textViewAddress=itemView.findViewById(R.id.textViewAddress);
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
