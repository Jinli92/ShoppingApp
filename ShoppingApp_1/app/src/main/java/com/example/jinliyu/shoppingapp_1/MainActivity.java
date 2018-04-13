package com.example.jinliyu.shoppingapp_1;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
Button signupbtn, loginbtn;
ActionBar actionBar;
EditText mobileTxt, pwTxt;
String mobile, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mobileTxt = findViewById(R.id.mobileTxtLogin);
        pwTxt = findViewById(R.id.passwordTxtLogin);


        actionBar = getSupportActionBar();
        actionBar.hide();

        signupbtn = findViewById(R.id.btnsignup);
        loginbtn = findViewById(R.id.btnlogin);

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
                                  Intent loginsuccess = new Intent(MainActivity.this, HomepageActivity.class);
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

                //AppController.getInstance().addToRequestQueue(req);
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(req);


    }
});
    }
}