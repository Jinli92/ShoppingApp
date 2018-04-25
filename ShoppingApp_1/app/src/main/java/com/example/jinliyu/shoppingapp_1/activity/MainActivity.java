package com.example.jinliyu.shoppingapp_1.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.jinliyu.shoppingapp_1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
Button signupbtn, loginbtn;
ActionBar actionBar;
EditText mobileTxt, pwTxt;
TextView forgotpw;
String mobile, password;
SharedPreferences sharedPreferences;
CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        //if checkbox ischecked, show saved mobile and password
        String ischecked = sharedPreferences.getString("isChecked","");
        if(ischecked.equals("true"))
        {
            String savedpw = sharedPreferences.getString("pw","");
            pwTxt.setText(savedpw);
            String savedmobile = sharedPreferences.getString("mobile","");
            mobileTxt.setText(savedmobile);

        }

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);


            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("test","click!!!!!!!!");

                mobile = mobileTxt.getText().toString();
                password = pwTxt.getText().toString();

         //do StringRequest to Login
               login(mobile, password);


    }
});
            forgotpw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent i = new Intent(MainActivity.this, ForgetPWActivity.class);
                   startActivity(i);
                }
            });


    }



    private void initViews() {
        mobileTxt = findViewById(R.id.mobileTxtLogin);
        pwTxt = findViewById(R.id.passwordTxtLogin);
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        checkBox = findViewById(R.id.checkBox);
        checkBox.setChecked(true);

        actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.signin);

        signupbtn = findViewById(R.id.btnsignup);
        loginbtn = findViewById(R.id.btnlogin);

        forgotpw = findViewById(R.id.forgotpw);
    }




    private void login(String mobile, final String password)
    {
        JsonArrayRequest req = new JsonArrayRequest("http://rjtmobile.com/aamir/e-commerce/android-app/shop_login.php?mobile="+mobile+"&password="+password, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    Log.i("test", response.toString());
                    JSONObject user = (JSONObject) response.get(0);


                    String userfirstname = user.getString("firstname");
                    String userlastname = user.getString("lastname");
                    String useremail = user.getString("email");
                    String usermobile = user.getString("mobile");
                    String userid = user.getString("id");
                    String userkey = user.getString("appapikey ");

                    sharedPreferences.edit().putString("firstname", userfirstname).commit();
                    sharedPreferences.edit().putString("lastname", userlastname).commit();
                    sharedPreferences.edit().putString("email", useremail).commit();
                    sharedPreferences.edit().putString("mobile", usermobile).commit();
                    sharedPreferences.edit().putString("userid", userid).commit();
                    sharedPreferences.edit().putString("apikey", userkey).commit();
                    sharedPreferences.edit().putString("pw", password).commit();

                    if(checkBox.isChecked())
                        {
                            sharedPreferences.edit().putString("isChecked", "true").commit();

                        }
                    else
                        {
                            sharedPreferences.edit().putString("isChecked", "false").commit();
                        }

                    Intent loginsuccess = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(loginsuccess);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "Password or username wrong",
                        Toast.LENGTH_LONG).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(req);
    }
}