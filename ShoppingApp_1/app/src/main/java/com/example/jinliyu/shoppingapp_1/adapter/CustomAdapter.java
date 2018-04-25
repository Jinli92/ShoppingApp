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

import com.example.jinliyu.shoppingapp_1.data.Category;
import com.example.jinliyu.shoppingapp_1.R;
import com.example.jinliyu.shoppingapp_1.activity.ShowsubcategoryActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinliyu on 4/14/18.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    List<Category> categoryList;
    Context context;
    SharedPreferences sharedPreferences;

    public CustomAdapter(Context context, ArrayList<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }


    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(CustomAdapter.MyViewHolder holder, int position) {
        final Category category = categoryList.get(position);

        holder.name.setText(category.getName());
        //holder.image.setImageResource((Integer) itemImages.get(position));
        Picasso.with(context).load(category.getImage()).into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences =  context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                String stored_api_key = sharedPreferences.getString("apikey","");
                String stored_id = sharedPreferences.getString("userid","");

                String cid = category.getCid();
                sharedPreferences.edit().putString("cid", cid).commit();
                sharedPreferences.edit().putString("cname",category.getName()).commit();


                //String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_sub_category.php?Id=107&api_key="+stored_api_key+"&user_id="+stored_id;

                Intent i = new Intent(context,ShowsubcategoryActivity.class);
                context.startActivity(i);


            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
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
