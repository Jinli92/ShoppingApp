package com.example.jinliyu.shoppingapp_1.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jinliyu.shoppingapp_1.R;

public class AfterPaymentActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String mobile, shipname, deliverAddr,billAddr,delivMobile,email,amount;
    TextView tvname, tvmobile, tvaddress, tvamount;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_payment);


        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        mobile = sharedPreferences.getString("mobile","");
        shipname = sharedPreferences.getString("shipname","");
        deliverAddr = sharedPreferences.getString("deliverAddr","");
        billAddr = sharedPreferences.getString("billAddr","");
        delivMobile = sharedPreferences.getString("delivMobile","");
        email = sharedPreferences.getString("email","");
        amount = sharedPreferences.getString("totalamount","");


        tvname = findViewById(R.id.tvname);
        tvamount = findViewById(R.id.tvamount);
        tvaddress = findViewById(R.id.tvaddress);
        tvmobile = findViewById(R.id.tvmobile);
        btn = findViewById(R.id.gobackbtn);

        tvname.setText("Shipping Name: "+ shipname);
        tvaddress.setText("Shipping Address: " + deliverAddr);
        tvmobile.setText("Contact: "+ mobile);
        tvamount.setText("Total Amount: "+ amount);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterPaymentActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}
