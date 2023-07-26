package com.example.prm_final.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm_final.Convert.ImageBitMapString;
import com.example.prm_final.DAO.Product_DAO;
import com.example.prm_final.DAO.User_DAO;
import com.example.prm_final.Entity.Product;
import com.example.prm_final.Entity.User;
import com.example.prm_final.R;
import com.example.prm_final.Services.ListNotApproveProductActivity;
import com.example.prm_final.database.PRM_DATABASE;

import java.util.List;

public class NotApproveProductAdapter extends RecyclerView.Adapter<NotApproveProductAdapter.NotApproveProductViewHolder> {
    private Context context;
    private List<Product> productList;
    private Product_DAO productDao;

    public NotApproveProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.productList = products;
        productDao = PRM_DATABASE.getInstance(context).product_dao();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotApproveProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.not_approve_product, parent, false);
        return new NotApproveProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotApproveProductViewHolder holder, int position) {
        Product product = productList.get(position);
        User user = productDao.getProductUserById(product.getUser_id());
        if (holder instanceof NotApproveProductViewHolder) {
            ((NotApproveProductViewHolder) holder).bindData(product, user);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class NotApproveProductViewHolder extends RecyclerView.ViewHolder {
        private TextView textProductName;
        private TextView textUserName;
        private TextView textProductDate;
        private TextView textProductPrice;
        private TextView textProductId;
        private Button btnDelete;
        private Button btnApprove;
        private ImageView imageViewProduct;

        public NotApproveProductViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textProductId = itemView.findViewById(R.id.textViewProductId);
            this.textProductName = itemView.findViewById(R.id.textViewProductName);
            this.textUserName = itemView.findViewById(R.id.textViewUserName);
            this.textProductDate = itemView.findViewById(R.id.textDateProduct);
            this.textProductPrice = itemView.findViewById(R.id.textViewPrice);
            this.btnDelete = itemView.findViewById(R.id.btnDeleteProduct);
            this.btnApprove = itemView.findViewById(R.id.btnApproveProduct);
            this.imageViewProduct=itemView.findViewById(R.id.imageViewProduct);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context contextView = v.getContext();
                    if (textProductId.length() != 0){
                        productDao.DeleteProductById(Integer.parseInt(textProductId.getText().toString()));
                        Toast.makeText(contextView, "Delete success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(contextView, ListNotApproveProductActivity.class);
                        contextView.startActivity(intent);
                    }else{
                        Toast.makeText(contextView, "Error while delete", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context contextView = v.getContext();
                    if (textProductId.length() != 0){
                        productDao.UpdateProductStatusById(Integer.parseInt(textProductId.getText().toString()));
                        Toast.makeText(contextView, "Approve success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(contextView, ListNotApproveProductActivity.class);
                        contextView.startActivity(intent);
                    }else{
                        Toast.makeText(contextView, "Error while approve", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        public void bindData(Product product, User user) {
            textProductId.setText(product.getId() + "");
            textProductName.setText(product.getName());
            textUserName.setText(user.getUser_name());
            textProductDate.setText(product.getDateProduct());
            textProductPrice.setText(product.getPrice());
            imageViewProduct.setImageBitmap(ImageBitMapString.getBitMapFromStr(product.getImg()));
        }
    }
}
