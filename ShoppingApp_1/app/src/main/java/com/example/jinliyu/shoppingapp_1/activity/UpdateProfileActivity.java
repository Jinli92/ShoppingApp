package com.example.jinliyu.shoppingapp_1.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jinliyu.shoppingapp_1.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.widget.Toast.LENGTH_SHORT;

public class UpdateProfileActivity extends AppCompatActivity {
Button btn;
TextView emailTxt, addressTxt, firstnameTxt, lastTxt,mobileTxt;
    String email, address, firstname, lastname, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        emailTxt = findViewById(R.id.emailTxt);
        addressTxt = findViewById(R.id.addressTxt);
        firstnameTxt  = findViewById(R.id.firstnameTxt);
        lastTxt = findViewById(R.id.lastnameTxt);
        mobileTxt = findViewById(R.id.mobileTxt);
        btn = findViewById(R.id.button);

         email = emailTxt.getText().toString();
        address = addressTxt.getText().toString();
         firstname = firstnameTxt.getText().toString();
         lastname = lastTxt.getText().toString();
        mobile = mobileTxt.getText().toString();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(firstname.equals("") || lastname.equals("")|| email.equals("") || address.equals("")|| mobile.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Can't be empty!",Toast.LENGTH_SHORT).show();

                }
                else if(checkName(firstname)== false || checkName(lastname)== false)
                {
                    Toast.makeText(getApplicationContext(),"It's not a valid name!",Toast.LENGTH_SHORT).show();

                }
                else if(checkEmail(email) == false)
                {
                    Toast.makeText(getApplicationContext(),"It's not a valid email!",Toast.LENGTH_SHORT).show();

                }
                else if(checkMobile(mobile) == false)
                {
                    Toast.makeText(getApplicationContext(),"It's not a valid phone number !",Toast.LENGTH_SHORT).show();

                }
             else
                    updateProfile();
            }


        });


    }



    private void updateProfile() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://rjtmobile.com/aamir/e-commerce/android-app/edit_profile.php?fname="+firstname+"&lname="+lastname+"&address=" + address+ "& email="+ email+ "&mobile="+ mobile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("successfully updated"))
                {
                    Toast.makeText(getApplicationContext(), "successfully updated!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Mobile no not found!", LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"fail to update!", LENGTH_SHORT).show();


            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(UpdateProfileActivity.this);
        requestQueue.add(stringRequest);
    }




    private boolean checkName(String name) {
        if(name.length()>20 || name.length()== 0)
            return false;
        else
            return true;
    }


    private boolean checkEmail(String email) {
        String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return false;
        }
        else
            return true;
    }


    private boolean checkMobile(String mobile)
    {
        String regex = "^[0-9]{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mobile);
        if(!matcher.matches())
        {
            return false;
        }

        return true;
    }


}
