package com.example.prm_final.Services;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.TypeConverter;

import com.example.prm_final.Entity.Bill;
import com.example.prm_final.Entity.Cart;
import com.example.prm_final.Entity.Product;
import com.example.prm_final.R;
import com.example.prm_final.adapter.CartAdapter;
import com.example.prm_final.database.PRM_DATABASE;
import com.example.prm_final.databinding.ActivityCartBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private int uID;
    private CartAdapter cartAdapter;
    private final String TAG="CARTACTIVITY";
    private double sumSelect=0;
    private ActivityCartBinding binding;
    private PRM_DATABASE prm_database;
    private Dialog dialog;

    private MutableLiveData<ArrayList<Cart>> listSelect=new MutableLiveData<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        uID=getIntent().getIntExtra("uID",0);
        Log.d("User_id",uID+"");
        prm_database=PRM_DATABASE.getInstance(this);

//        if(){
//            Product product=new Product(0,1,"Orange",555,"Ha noi","kafkf",new Date(),new Date(),"https://vi.wikipedia.org/wiki/T%E1%BA%ADp_tin:Oranges_-_whole-halved-segment.jpg","024555555",false);
//            Product product2=new Product(0,1,"Orange",555,"Ha noi","kafkf",new Date(),new Date(),"https://vi.wikipedia.org/wiki/T%E1%BA%ADp_tin:Oranges_-_whole-halved-segment.jpg","024555555",false);
//            prm_database.socical_dao().insertProduct(product);
//            prm_database.socical_dao().insertProduct(product2);
//            prm_database.socical_dao().insertCart(new Cart(1,1,44,455));
//            prm_database.socical_dao().insertCart(new Cart(1,2,33,455));
//        }
        init();
    }
    private LiveData<ArrayList<Cart>> getListSelect(){
        return listSelect;
    }
    private void addListSelect(ArrayList list){
        listSelect.postValue(list);
    }
    private LiveData<List<Cart>> getAllCart(){
        return prm_database.socical_dao().allCart(uID);
    }
    public Cart getCartEqual(Cart cart){
        for (Cart c:getListSelect().getValue()
        ) {
            if(c.getpID()==cart.getpID()&&c.getcID()==cart.getcID()){
                return c;
            }
        }
        return cart;
    }
    private void init(){
        binding.txtPrice.setText(sumSelect+"");
        Intent intent=getIntent();
        LiveData<List<Cart>> getallCart=getAllCart();

        getListSelect().observe(this, new Observer<ArrayList<Cart>>() {
            @Override
            public void onChanged(ArrayList<Cart> carts) {
                sumSelect=0;
                for (Cart c:carts
                ) {
                    Product p=prm_database.socical_dao().getProductById(c.getpID());
                    sumSelect+= c.sl*Float.parseFloat(p.getPrice());
                }
                if(carts.size()>0){
                    binding.btnDelete.setVisibility(View.VISIBLE);
                    binding.btnCheckOut.setEnabled(true);
                }else {
                    binding.btnDelete.setVisibility(View.INVISIBLE);
                    binding.btnCheckOut.setEnabled(false);
                }
                binding.txtPrice.setText("$"+sumSelect);
                Log.d(TAG,"Select list"+carts.size()+"");
            }
        });
        cartAdapter=new CartAdapter(this, prm_database.socical_dao(), new CartAdapter.ICart() {
            @Override
            public void onCLick(int position,boolean status) {
                Cart cart=getallCart.getValue().get(position);
                ArrayList<Cart> listCartS=getListSelect().getValue();
                Cart sC=getCartEqual(cart);
                Log.d(TAG,"on click"+listCartS.size());
                if(status){
                    listCartS.add(cart);
                }else {
                    listCartS.remove(sC);
                }
                Log.d(TAG,"on click"+listCartS.size());
                addListSelect(listCartS);
            }

            @Override
            public void onCLickSL(Boolean status,int position,boolean sStatus) {
                ArrayList<Cart> listCartS=getListSelect().getValue();
                Cart c=getallCart.getValue().get(position);
                Cart sC=getCartEqual(c);
                if(sStatus){
                    if(status){
                        listCartS.remove(sC);
                        Log.d(TAG,"add "+listCartS.size());
                        c.setSl(c.getSl()+1);
                        sC.setSl(sC.getSl()+1);
                        prm_database.socical_dao().updateCart(c);
                        listCartS.add(sC);
                        addListSelect(listCartS);
                    }else {
                        listCartS.remove(sC);
                        c.setSl(c.getSl()-1);
                        sC.setSl(sC.getSl()-1);
                        if(c.getSl()>0){
                            prm_database.socical_dao().updateCart(c);
                            listCartS.add(sC);
                            addListSelect(listCartS);
                        }else {
                            prm_database.socical_dao().deleteCart(c);
                            listCartS.remove(sC);
                            addListSelect(listCartS);
                        }
                    }
                }else {
                    if(status){
                        c.setSl(c.getSl()+1);
                        prm_database.socical_dao().updateCart(c);
                    }else {
                        c.setSl(c.getSl()-1);
                        if(c.getSl()>0){
                            prm_database.socical_dao().updateCart(c);
                        }else {
                            prm_database.socical_dao().deleteCart(c);
                        }
                    }
                }

            }
        });
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        binding.rcvCart.setAdapter(cartAdapter);
        binding.rcvCart.setLayoutManager(layoutManager);
        binding.rcvCart.setHasFixedSize(true);
        getallCart.observe(this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                cartAdapter.setListCart(carts);
                addListSelect(new ArrayList<>());
                cartAdapter.setUnCheckAll(true);
            }
        });
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prm_database.socical_dao().deleteListCart(getListSelect().getValue());
                addListSelect(new ArrayList<>());
                cartAdapter.setListCart(getListSelect().getValue());
                cartAdapter.setUnCheckAll(true);
                cartAdapter.notifyDataSetChanged();
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowDialogBill(getListSelect().getValue());
            }
        });

    }

    private void onShowDialogBill(ArrayList<Cart> value) {
        Button cancel,save;
        EditText name,phone,address;
        dialog = new Dialog(CartActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_bill);
        cancel=dialog.findViewById(R.id.dialogAddApartment_btnCancel);
        save=dialog.findViewById(R.id.dialogAddApartment_btnSave);
        name=dialog.findViewById(R.id.name);
        phone=dialog.findViewById(R.id.phone);
        address=dialog.findViewById(R.id.address);
        Window window= dialog.getWindow();
        window.setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAtributes = window.getAttributes();
        windowAtributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAtributes);
        if (Gravity.CENTER == Gravity.CENTER) {
            dialog.setCancelable(false);
        } else {
            dialog.setCancelable(false);
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pName=name.getText().toString();
                String pPhone=phone.getText().toString();
                String pAddress=address.getText().toString();
                JSONArray jsonArray;
                String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
                try {
                    jsonArray=new JSONArray(value.toArray());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                String mlistCart=listToJsonString(jsonArray);
                if(pName.isEmpty()||pPhone.isEmpty()||pAddress.isEmpty()){
                    Toast.makeText(CartActivity.this, "Enter enough fields", Toast.LENGTH_SHORT).show();
                }else {
                    Bill bill=new Bill(0,uID,pAddress,pName,pPhone,sumSelect,timeStamp,mlistCart,1);
                    prm_database.socical_dao().insertBill(bill);
                    Toast.makeText(CartActivity.this, "Check out success", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    prm_database.socical_dao().deleteListCart(value);
                }
            }
        });
        dialog.show();
    }
    @TypeConverter
    private String listToJsonString(JSONArray values){
        return new Gson().toJson(values);
    }
}