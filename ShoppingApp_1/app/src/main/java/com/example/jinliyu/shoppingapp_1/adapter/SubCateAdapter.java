package com.example.jinliyu.shoppingapp_1.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinliyu.shoppingapp_1.activity.ProductListActivity;
import com.example.jinliyu.shoppingapp_1.R;
import com.example.jinliyu.shoppingapp_1.data.Subcategory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinliyu on 4/15/18.
 */

public class SubCateAdapter extends RecyclerView.Adapter<SubCateAdapter.MyViewHolder> {
    List<Subcategory> subcategoryList;
    Context context;
    SharedPreferences sharedPreferences;

    public SubCateAdapter(Context context, ArrayList<Subcategory> categoryList) {
        this.context = context;
        this.subcategoryList = categoryList;
    }


    @Override
    public SubCateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardrowlayout, parent, false);

        SubCateAdapter.MyViewHolder vh = new SubCateAdapter.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Subcategory subcategory = subcategoryList.get(position);

        holder.name.setText(subcategory.getSubcateNanme());
        //holder.image.setImageResource((Integer) itemImages.get(position));
        Picasso.with(context).load(subcategory.getSubcateImage()).into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences =  context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

                String scid = subcategory.getSubcateId();
                String scname = subcategory.getSubcateNanme();

                sharedPreferences.edit().putString("scid", scid).commit();
                sharedPreferences.edit().putString("scname", scname).commit();



                //String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_sub_category.php?Id=107&api_key="+stored_api_key+"&user_id="+stored_id;
//
                Intent i = new Intent(context, ProductListActivity.class);
                context.startActivity(i);


            }
        });
    }



    @Override
    public int getItemCount() {
        return subcategoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.categoryimage);
        }
    }
}
