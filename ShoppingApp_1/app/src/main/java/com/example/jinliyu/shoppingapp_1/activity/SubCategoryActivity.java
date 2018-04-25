package com.example.jinliyu.shoppingapp_1.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jinliyu.shoppingapp_1.R;
import com.example.jinliyu.shoppingapp_1.data.Subcategory;
import com.example.jinliyu.shoppingapp_1.adapter.SubCateAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity {
SharedPreferences sharedPreferences;
RecyclerView recyclerView;
ArrayList<Subcategory> subcategories;
StaggeredGridLayoutManager staggeredGridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String apikey = sharedPreferences.getString("apikey","");
        String userid = sharedPreferences.getString("userid","");
        String cid = sharedPreferences.getString("cid","");

        subcategories = new ArrayList<Subcategory>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewsub);

        getSubCategories(cid, apikey, userid, subcategories);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);



    }

    private void getSubCategories(String cid, String apikey, String userid, final ArrayList<Subcategory> subcategories ) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_sub_category.php?Id="+cid +"&api_key="+ apikey+ "&user_id="+ userid, null,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray allsubcategory = response.getJSONArray("subcategory");
                            for(int i = 0; i < allsubcategory.length(); i ++)
                            {
                                JSONObject subcategory = (JSONObject) allsubcategory.get(i);
                                String scid = subcategory.getString("scid");
                                Log.i("","subcategory id is" +scid);
                                String scname = subcategory.getString("scname");
                                String scdescription = subcategory.getString("scdiscription");
                                String simage = subcategory.getString("scimageurl");


                                Subcategory c = new Subcategory(scid, scname,scdescription,simage);
                                subcategories.add(c);
                            }

                            recyclerView.setLayoutManager(staggeredGridLayoutManager); // set LayoutManager to RecyclerView
                            SubCateAdapter subAdapter = new SubCateAdapter(SubCategoryActivity.this, subcategories);
                            subAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(subAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("test", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("",error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}
