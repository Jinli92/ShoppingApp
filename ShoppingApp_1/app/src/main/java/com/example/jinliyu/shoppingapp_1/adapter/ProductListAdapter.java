package com.example.jinliyu.shoppingapp_1.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinliyu.shoppingapp_1.data.Product;
import com.example.jinliyu.shoppingapp_1.activity.ProductDetailActivity;
import com.example.jinliyu.shoppingapp_1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinliyu on 4/15/18.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {
        List<Product> productList;
        Context context;
        SharedPreferences sharedPreferences;

    public ProductListAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;
    }


@Override
public ProductListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayoutforproducts, parent, false);

        ProductListAdapter.MyViewHolder vh = new ProductListAdapter.MyViewHolder(v);
        return vh;

        }

@Override
public void onBindViewHolder(MyViewHolder holder, int position) {
final Product product = productList.get(position);

        holder.name.setText(product.getPname());
        holder.price.setText("$"+ product.getPrice());
        //holder.image.setImageResource((Integer) itemImages.get(position));
        Picasso.with(context).load(product.getPimage()).into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
         @Override
           public void onClick(View v) {

        sharedPreferences =  context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

       sharedPreferences.edit().putString("productname",product.getPname()).commit();
       Log.i("test","product name is "+ product.getPname());
       sharedPreferences.edit().putString("productquantity", product.getQuantity()).commit();
       sharedPreferences.edit().putString("productprice",product.getPrice()).commit();
       sharedPreferences.edit().putString("productdesc", product.getPdescription()).commit();
       sharedPreferences.edit().putString("productimage",product.getPimage()).commit();
       sharedPreferences.edit().putString("productid",product.getPid()).commit();


//        sharedPreferences.edit().putString("scid", scid).commit();
//        sharedPreferences.edit().putString("scname", scname).commit();
//


        //String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_sub_category.php?Id=107&api_key="+stored_api_key+"&user_id="+stored_id;
//
                Intent i = new Intent(context, ProductDetailActivity.class);
                context.startActivity(i);


        }
        });
        }



@Override
public int getItemCount() {
        return productList.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    ImageView image;
    TextView price;
    public MyViewHolder(View itemView) {
        super(itemView);
        // get the reference of item view's
        name = (TextView) itemView.findViewById(R.id.pname);
        image = (ImageView) itemView.findViewById(R.id.pimage);
        price = (TextView) itemView.findViewById(R.id.pprice);
    }
}


}
