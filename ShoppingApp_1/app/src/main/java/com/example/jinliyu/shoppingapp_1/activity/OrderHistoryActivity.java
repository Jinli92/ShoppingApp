package com.example.jinliyu.shoppingapp_1.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jinliyu.shoppingapp_1.R;
import com.example.jinliyu.shoppingapp_1.adapter.OrderHistoryAdapter;
import com.example.jinliyu.shoppingapp_1.adapter.SubCateAdapter;
import com.example.jinliyu.shoppingapp_1.data.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {
ArrayList<Order> orderlist;
SharedPreferences sharedPreferences;
String apikey, userid, mobile;
StaggeredGridLayoutManager staggeredGridLayoutManager;
RecyclerView recyclerView;
ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        recyclerView = findViewById(R.id.recyclerviewhistory);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Order History");

        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        apikey = sharedPreferences.getString("apikey","");
        userid = sharedPreferences.getString("userid","");
        mobile = sharedPreferences.getString("mobile","");
        orderlist = new ArrayList<Order>();
        getOrderHistory();

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);



    }




    private void getOrderHistory(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://rjtmobile.com/aamir/e-commerce/android-app/"+
                "order_history.php?api_key="+apikey+"&user_id="+userid+"&mobile="+ mobile, null,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray orderhistory = response.getJSONArray("Order history");
                            for(int i= 0; i< orderhistory.length(); i++)
                            {
                                JSONObject order =(JSONObject) orderhistory.get(i);
                                String orderid = order.getString("orderid");
                                String orderstatus = order.getString("orderstatus");
                                String shippingName = order.getString("name");
                                String billingaddr = order.getString("billingadd");
                                String delivAddr = order.getString("deliveryadd");
                                String ordermobile = order.getString("mobile");
                                String orderemail = order.getString("email");
                                String itemid = order.getString("itemid");
                                String itemname = order.getString("itemname");
                                String itemquantity = order.getString("itemquantity");
                                String totalprice = order.getString("totalprice");
                                String paidprice = order.getString("paidprice");
                                String placedon = order.getString("placedon");
                                Log.i("test","Order placed on : "+ placedon);

                                Order o = new Order(orderid, orderstatus, shippingName, billingaddr, delivAddr, ordermobile, orderemail, itemid, itemname,itemquantity, totalprice, paidprice,placedon);
                                orderlist.add(o);
                            }

                            recyclerView.setLayoutManager(staggeredGridLayoutManager); // set LayoutManager to RecyclerView
                            OrderHistoryAdapter orderHistoryAdapter = new OrderHistoryAdapter(OrderHistoryActivity.this, orderlist);
                            orderHistoryAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(orderHistoryAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("test", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"System error",Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}
