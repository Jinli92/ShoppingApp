package com.example.jinliyu.shoppingapp_1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinliyu.shoppingapp_1.R;
import com.example.jinliyu.shoppingapp_1.data.Order;

import java.util.ArrayList;

/**
 * Created by jinliyu on 4/17/18.
 */

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {

    ArrayList<Order> orderlist;
    Context context;

    public OrderHistoryAdapter(Context context, ArrayList<Order> orders ){
        this.orderlist = orders;
        this.context = context;
    }

    @Override
    public OrderHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderhistoryrowlayout, parent, false);

        OrderHistoryAdapter.MyViewHolder vh = new OrderHistoryAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(OrderHistoryAdapter.MyViewHolder holder, int position) {
          final Order order = orderlist.get(position);
          holder.oid.setText( "Order ID: "+ order.getOrderId());
          holder.pname.setText("Product Name: "+ order.getItemName() + "   Total:"+ order.getTotalprice());
          holder.status.setText("Order Status: "+ order.getOrderStatus() + "  Time: "+order.getPlacedon());

    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView oid, pname,status ;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            oid = (TextView)itemView.findViewById(R.id.orderId);
            pname = (TextView) itemView.findViewById(R.id.productname);
            status = (TextView)itemView.findViewById(R.id.status);

        }
    }
}
