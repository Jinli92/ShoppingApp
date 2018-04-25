package com.example.jinliyu.shoppingapp_1.activity;

import android.content.Context;
import android.content.Intent;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

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

public class ShowsubcategoryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    ArrayList<Subcategory> subcategories;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    ViewFlipper vf;
    TextView profilename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showsubcategory);

        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         toolbar.setTitle(sharedPreferences.getString("cname",""));
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

        vf = findViewById(R.id.flipper);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_left);
        vf.setInAnimation(animation);
        vf.setFlipInterval(3000);
        vf.startFlipping();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //get what we need to do JsonObjectRequest
        String apikey = sharedPreferences.getString("apikey","");
        String userid = sharedPreferences.getString("userid","");
        String cid = sharedPreferences.getString("cid","");


        //set profile mobile on left side
        String mobile = sharedPreferences.getString("mobile","");

//        View header = navigationView.getHeaderView(0);
//        profilename= header.findViewById(R.id.profilename);
//        profilename.setText(mobile);


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
                            SubCateAdapter subAdapter = new SubCateAdapter(ShowsubcategoryActivity.this, subcategories);
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
        getMenuInflater().inflate(R.menu.home, menu);
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

        if (id == R.id.nav_profile) {
            Intent i = new Intent(ShowsubcategoryActivity.this, UpdateProfileActivity.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_order) {
            Intent i = new Intent(ShowsubcategoryActivity.this, OrderHistoryActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(ShowsubcategoryActivity.this, ShoppingCartActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_gl) {

        }
        else if (id == R.id.nav_logout) {
            Intent i = new Intent(ShowsubcategoryActivity.this, MainActivity.class);
            startActivity(i);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
