package com.example.jinliyu.shoppingapp_1.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jinliyu.shoppingapp_1.database.MyDBHelper;
import com.example.jinliyu.shoppingapp_1.R;
import com.example.jinliyu.shoppingapp_1.adapter.CartAdapter;
import com.example.jinliyu.shoppingapp_1.data.Product;

import java.util.ArrayList;

public class ShoppingCartActivity extends AppCompatActivity {
    ArrayList<Product> products;
    MyDBHelper myDBHelper;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    String mobile;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    Button btnchekcout;
    TextView showtotal, noitem;
    int listtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle(R.string.shoppingcart);


        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        mobile = sharedPreferences.getString("mobile","");

        recyclerView = findViewById(R.id.cartrecyclerview);
        btnchekcout = findViewById(R.id.buttoncheckout);
        showtotal = findViewById(R.id.total);
        noitem = findViewById(R.id.tvnoitem);

        products = new ArrayList<Product>();
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);

        getProductList();

        showtotal.setText(getString(R.string.totalamount) + listtotal);

        btnchekcout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShoppingCartActivity.this, BillingDetailActivity.class);
                startActivity(i);
            }
        });

    }

    private void getProductList() {
        myDBHelper = new MyDBHelper(this);
        sqLiteDatabase = myDBHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ MyDBHelper.TABLE_NAME +" WHERE " + MyDBHelper.USER_MOBILE+"=" +mobile,null);
        cursor.moveToFirst();
        if(cursor.getCount()>0) {
            do {


                String p_name = cursor.getString(cursor.getColumnIndex(MyDBHelper.PRO_NAME));
                String p_id = cursor.getString(cursor.getColumnIndex(MyDBHelper.PRO_ID));
                String p_quantity = cursor.getString(cursor.getColumnIndex(MyDBHelper.QUANTITY));
                String p_price = cursor.getString(cursor.getColumnIndex(MyDBHelper.PRO_PRICE));
                String p_desc = cursor.getString(cursor.getColumnIndex(MyDBHelper.DESCRIPTION));
                String p_image = cursor.getString(cursor.getColumnIndex(MyDBHelper.IMAGE));
                Log.i("test", "search in dataabase get name: " + p_name);
                Log.i("test", "search in dataabase get price: " + p_price);
                Log.i("test", "search in dataabase getquantity: " + p_quantity);

                int price = Integer.valueOf(p_price);
                int num = Integer.valueOf(p_quantity);
                listtotal += price * num;

                Product p = new Product(p_id, p_name, p_quantity, p_price, p_desc, p_image);
                products.add(p);


            } while (cursor.moveToNext());

            recyclerView.setLayoutManager(staggeredGridLayoutManager); // set LayoutManager to RecyclerView

            CartAdapter cartAdapter = new CartAdapter(ShoppingCartActivity.this, products);
            cartAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(cartAdapter);
        }
        else
        {
            btnchekcout.setVisibility(View.GONE);
            showtotal.setVisibility(View.GONE);
            noitem.setVisibility(View.VISIBLE);

        }
    }
}
