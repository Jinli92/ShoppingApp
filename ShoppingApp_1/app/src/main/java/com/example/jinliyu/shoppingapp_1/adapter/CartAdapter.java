package com.example.jinliyu.shoppingapp_1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinliyu.shoppingapp_1.R;
import com.example.jinliyu.shoppingapp_1.data.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jinliyu on 4/16/18.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
Context context;
ArrayList<Product> productlist;
    public CartAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productlist= new ArrayList<Product>();
        productlist = productList;
    }


    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopcartrowlayout, parent, false);

        CartAdapter.MyViewHolder vh = new CartAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CartAdapter.MyViewHolder holder, int position) {
       final  Product product = productlist.get(position);
       holder.name.setText(product.getPname());
       holder.price.setText("$" + product.getPrice());
       holder.number.setText("Quantity: "+product.getQuantity());
        Picasso.with(context).load(product.getPimage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return productlist.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, number;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            number = itemView.findViewById(R.id.cartnum);
            name = (TextView) itemView.findViewById(R.id.cartname);
            price = (TextView) itemView.findViewById(R.id.cartprice);
            image = (ImageView) itemView.findViewById(R.id.cartimage);
        }
    }
}
