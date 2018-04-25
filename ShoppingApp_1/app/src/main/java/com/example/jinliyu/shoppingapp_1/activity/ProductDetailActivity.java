package com.example.jinliyu.shoppingapp_1.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinliyu.shoppingapp_1.database.MyDBHelper;
import com.example.jinliyu.shoppingapp_1.R;
import com.example.jinliyu.shoppingapp_1.data.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView name, price,desciption;
    ImageView image;
    Button btn;
    EditText quantityTxt;
    MyDBHelper myDBHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);



            name = findViewById(R.id.proname);
            price = findViewById(R.id.proprice);
            desciption = findViewById(R.id.prodesc);
            image = findViewById(R.id.proimage);
            btn = findViewById(R.id.addtocartbtn);


            sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

            final String savedmobile = sharedPreferences.getString("mobile","");

            final String savedname = sharedPreferences.getString("productname","");

            final String savedprice = sharedPreferences.getString("productprice","");
            final String saveddesc = sharedPreferences.getString("productdesc","");
            final String savedimg = sharedPreferences.getString("productimage","");
            final String savedid = sharedPreferences.getString("productid","");


            name.setText(savedname);
            price.setText( "$"+ savedprice);
            desciption.setText(saveddesc);
           Picasso.with(this).load(savedimg).into(image);






           btn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  // Product product = new Product(savedid,savedname,quantity,savedprice,saveddesc,savedimg);
                   myDBHelper = new MyDBHelper(ProductDetailActivity.this);
                   sqLiteDatabase = myDBHelper.getWritableDatabase();

                   quantityTxt = findViewById(R.id.qtxt);
                   final String quantity = quantityTxt.getText().toString();
                   if(TextUtils.isEmpty(quantity) || quantity.equals("0"))
                   {
                       Toast.makeText(getApplicationContext(), "Invalid quantity!", Toast.LENGTH_LONG).show();

                   }
                   else {
                       ContentValues contentValues = new ContentValues();
                       contentValues.put(MyDBHelper.USER_MOBILE, savedmobile);
                       contentValues.put(MyDBHelper.PRO_ID, savedid);
                       contentValues.put(MyDBHelper.PRO_NAME, savedname);
                       contentValues.put(MyDBHelper.PRO_PRICE, savedprice);
                       contentValues.put(MyDBHelper.DESCRIPTION, saveddesc);
                       contentValues.put(MyDBHelper.IMAGE, savedimg);
                       contentValues.put(MyDBHelper.QUANTITY, quantity);

                       sqLiteDatabase.insert(MyDBHelper.TABLE_NAME, null, contentValues);

                       Toast.makeText(getApplicationContext(), "Successfully Add to Cart!", Toast.LENGTH_LONG).show();
                   }
               }
           });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(this != null)
        sqLiteDatabase.close();
    }
}
