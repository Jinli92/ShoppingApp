package com.example.jinliyu.shoppingapp_1.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jinliyu.shoppingapp_1.data.Product;
import com.example.jinliyu.shoppingapp_1.R;
import com.example.jinliyu.shoppingapp_1.adapter.ProductListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    ArrayList<Product> products;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        String sctitle = sharedPreferences.getString("scname","");
        toolbar.setTitle(sctitle);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);






        String apikey = sharedPreferences.getString("apikey","");
        String userid = sharedPreferences.getString("userid","");
        String cid = sharedPreferences.getString("cid","");
        String scid = sharedPreferences.getString("scid","");


        products = new ArrayList<Product>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewlist);

        getProducts(cid, scid, apikey, userid, products);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);

    }

    private void getProducts(String cid, String scid, String apikey, String userid, final ArrayList<Product> products) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://rjtmobile.com/ansari/shopingcart/androidapp/product_details.php?cid="+cid+"&scid="+scid+"&api_key="+apikey+ "&user_id="+userid, null,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray allproducts = response.getJSONArray("products");
                            for(int i = 0; i < allproducts.length(); i ++)
                            {
                                JSONObject product = (JSONObject) allproducts.get(i);

                                String pid = product.getString("id");
                                String pname = product.getString("pname");
                                String quantity = product.getString("quantity");
                                String pprice= product.getString("prize");
                                String pdiscription  =product.getString("discription");
                                String pimage = product.getString("image");


                                Product p = new Product(pid,pname,quantity,pprice,pdiscription,pimage);
                                products.add(p);
                            }

                            recyclerView.setLayoutManager(staggeredGridLayoutManager); // set LayoutManager to RecyclerView
                            ProductListAdapter proAdapter = new ProductListAdapter(ProductListActivity.this, products);
                            proAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(proAdapter);

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
