package com.example.jinliyu.shoppingapp_1.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jinliyu.shoppingapp_1.database.MyDBHelper;
import com.example.jinliyu.shoppingapp_1.R;
import com.example.jinliyu.shoppingapp_1.data.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BillingDetailActivity extends AppCompatActivity {
EditText name, deliverAddr, billingAddr, delivmobileTxt, email;
Button btn;
ArrayList<Product> productlist;
int total;
SharedPreferences sharedPreferences;
String mobile, apikey, userid;
MyDBHelper myDBHelper;
SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_detail);

        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle("Add Billing Details");


        name = findViewById(R.id.editText2);
        deliverAddr = findViewById(R.id.editText3);
        billingAddr = findViewById(R.id.editText4);
        delivmobileTxt = findViewById(R.id.editText5);
        email = findViewById(R.id.editText6);
        btn = findViewById(R.id.button2);

         productlist = new ArrayList<Product>();


        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        mobile = sharedPreferences.getString("mobile","");


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //get input
                String shipname = name.getText().toString();
                String deliverAdd = deliverAddr.getText().toString();
                String billAddr = billingAddr.getText().toString();
                String delivMobile = delivmobileTxt.getText().toString();
                String emailval = email.getText().toString();


                if(shipname.equals("") || deliverAdd.equals("") || billAddr.equals("") || delivMobile.equals("")|| emailval.equals("")
                        )
                    Toast.makeText(getApplicationContext(),"Can't be empty!",Toast.LENGTH_SHORT).show();

                else {

                    sharedPreferences.edit().putString("shipname", shipname).commit();
                    sharedPreferences.edit().putString("deliverAddr", deliverAdd).commit();
                    sharedPreferences.edit().putString("billAddr", billAddr).commit();
                    sharedPreferences.edit().putString("delivMobile", delivMobile).commit();
                    sharedPreferences.edit().putString("email", emailval).commit();
                    getTotalandSave();

                    Intent i = new Intent(BillingDetailActivity.this, CheckOutActivity.class);
                    startActivity(i);
                }
            }
        });

    }

   private void getTotalandSave()
   {
       myDBHelper = new MyDBHelper(this);
       sqLiteDatabase = myDBHelper.getWritableDatabase();

       Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ MyDBHelper.TABLE_NAME +" WHERE " + MyDBHelper.USER_MOBILE+"=" +mobile,null);
       cursor.moveToFirst();

       do {

           String p_name = cursor.getString(cursor.getColumnIndex(MyDBHelper.PRO_NAME));
           String p_id = cursor.getString(cursor.getColumnIndex(MyDBHelper.PRO_ID));
           String p_quantity = cursor.getString(cursor.getColumnIndex(MyDBHelper.QUANTITY));
           String p_price = cursor.getString(cursor.getColumnIndex(MyDBHelper.PRO_PRICE));
           String p_desc = cursor.getString(cursor.getColumnIndex(MyDBHelper.DESCRIPTION));
           String p_image = cursor.getString(cursor.getColumnIndex(MyDBHelper.IMAGE));
           Product p = new Product(p_id,p_name, p_quantity, p_price, p_desc,p_image);
           productlist.add(p);
           int price = Integer.valueOf(p_price);
           int num = Integer.valueOf(p_quantity);
           total +=  price*num;
            Log.i("tes","total is :   "+ String.valueOf(total));

           sharedPreferences.edit().putString("totalamount",String.valueOf(total)).commit();
       }while (cursor.moveToNext());

   }



}
