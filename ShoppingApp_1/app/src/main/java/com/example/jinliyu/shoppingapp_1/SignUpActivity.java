package com.example.jinliyu.shoppingapp_1;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
Button button;
EditText firstnameTxt, lastnameTxt, emailTxt, addressTxt, passwordTxt,mobileTxt;
ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

actionBar = getSupportActionBar();
actionBar.hide();

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstnameTxt = findViewById(R.id.firstnameTxt);
                lastnameTxt = findViewById(R.id.lastnameTxt);
                emailTxt = findViewById(R.id.emailTxt);
                addressTxt = findViewById(R.id.addressTxt);
                passwordTxt = findViewById(R.id.passwordTxt);
                mobileTxt = findViewById(R.id.mobileTxt);



                String firstname = firstnameTxt.getText().toString();
                String lastname = lastnameTxt.getText().toString();
                String email = emailTxt.getText().toString();
                String address = addressTxt.getText().toString();
                String mobile = mobileTxt.getText().toString();
                String pw = passwordTxt.getText().toString();
                if(firstname == null || lastname == null|| email == null || address == null|| mobile == null
                        ||pw == null)
                {
                    Toast.makeText(getApplicationContext(),"Can't be empty!",Toast.LENGTH_SHORT).show();

                }
                if(checkName(firstname)== false || checkName(lastname)== false)
                {
                    Toast.makeText(getApplicationContext(),"It's not a valid name!",Toast.LENGTH_SHORT).show();

                }
                if(checkEmail(email) == false)
                {
                    Toast.makeText(getApplicationContext(),"It's not a valid email!",Toast.LENGTH_SHORT).show();

                }
if(checkMobile(mobile) == false)
{
    Toast.makeText(getApplicationContext(),"It's not a valid phone number !",Toast.LENGTH_SHORT).show();

}
else
{

                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://rjtmobile.com/aamir/e-commerce/android-app/" +
                        "shop_reg.php?fname=" + firstname + "&lname=" + lastname+"&address="+address+"& email="+ email+"&mobile="+mobile+"&password="+pw, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("successfully registered"))
                        {
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Mobile number already exsist!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Sign up fail!",Toast.LENGTH_SHORT).show();


                    }
                });








            }


        }

        });



    }

    private boolean checkName(String firstname) {

        if(firstname.length()>20 ||firstname.length()== 0)
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
        if(mobile.length() != 10)
            return  false;
        for(int i = 0; i < mobile.length(); i ++)
        {
            if(mobile.charAt(i) - '0' >9 && mobile.charAt(i) - '0' <0)
                return false;
        }
        return true;
    }


}
