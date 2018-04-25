package com.example.jinliyu.shoppingapp_1.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.example.jinliyu.shoppingapp_1.R;
import com.example.jinliyu.shoppingapp_1.data.Product;
import com.example.jinliyu.shoppingapp_1.database.MyDBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckOutActivity extends AppCompatActivity {
    Button btnpay;
    TextView showamount;
    final int REQUEST_CODE = 1;
    final String get_token = "http://rjtmobile.com/aamir/braintree-paypal-payment/main.php?";
    final String send_payment_details = "http://rjtmobile.com/aamir/braintree-paypal-payment/mycheckout.php?";
    String token, amount;
    HashMap<String, String> paramHash;
    LinearLayout llHolder;
    Context context;
    ArrayList<Product> productlist;
    SharedPreferences sharedPreferences;
    String mobile, apikey, userid, shipname, deliverAddr,billAddr,delivMobile,email;
    MyDBHelper myDBHelper;
    SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        productlist = new ArrayList<Product>();
        btnpay = findViewById(R.id.button3);
        llHolder = findViewById(R.id.llHolder);
        showamount = findViewById(R.id.showamount);
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        mobile = sharedPreferences.getString("mobile","");
        apikey = sharedPreferences.getString("apikey","");
        userid = sharedPreferences.getString("userid","");
        shipname = sharedPreferences.getString("shipname","");
        deliverAddr = sharedPreferences.getString("deliverAddr","");
        billAddr = sharedPreferences.getString("billAddr","");
        delivMobile = sharedPreferences.getString("delivMobile","");
        email = sharedPreferences.getString("email","");
        amount = sharedPreferences.getString("totalamount","");


        showamount.setText("The total is :" + amount);
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBraintreeSubmit();

            }
        });
        new HttpRequest().execute();
    }


    private class HttpRequest extends AsyncTask {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(CheckOutActivity.this, android.R.style.Theme_DeviceDefault_Dialog);
            progress.setCancelable(false);
            progress.setMessage("We are contacting our servers for token, Please wait");
            progress.setTitle("Getting token");
            progress.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient client = new HttpClient();
            client.get(get_token, new HttpResponseCallback() {
                @Override
                public void success(String responseBody) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            // Toast.makeText(CheckOutActivity.this, "Successfully got token", Toast.LENGTH_SHORT).show();
                            llHolder.setVisibility(View.VISIBLE);
                        }
                    });
                    token = responseBody;
                    onBraintreeSubmit();
                    //Toast.makeText(CheckOutActivity.this, "Transaction successful", Toast.LENGTH_LONG).show();

                }

                @Override
                public void failure(Exception exception) {
                    final Exception ex = exception;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //  Toast.makeText(CheckOutActivity.this, "Failed to get token: " + ex.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progress.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String stringNonce = nonce.getNonce();
                // Log.d("mylog", "Result: " + stringNonce);
                // Send payment price with the nonce
                // use the result to update your UI and send the payment method nonce to your server


                paramHash = new HashMap<>();
                paramHash.put("amount", String.valueOf(amount));
                paramHash.put("nonce", stringNonce);
                sendPaymentDetails();
                getItemsandPlaceOrder();


            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Log.d("mylog", "user canceled");
            }

        }
    }



    private void getItemsandPlaceOrder(){



            myDBHelper = new MyDBHelper(this);
            sqLiteDatabase = myDBHelper.getWritableDatabase();

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ MyDBHelper.TABLE_NAME +" WHERE " + MyDBHelper.USER_MOBILE+"=" +mobile,null);
            cursor.moveToFirst();
            do {
                int eachtotal = 0;
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
                eachtotal  = price *num;
                placeOrder(p_id,p_name, p_quantity, String.valueOf(eachtotal));


            }while (cursor.moveToNext());



            String deletequery = "DELETE FROM "+ MyDBHelper.TABLE_NAME +" WHERE " + MyDBHelper.USER_MOBILE+"=" +mobile;

            sqLiteDatabase.execSQL(deletequery);
        }

    private void placeOrder(String pid, String pname, String quantity, String finalprice){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://rjtmobile.com/aamir/e-commerce/android-app"
                +"/orders.php?&item_id="+pid+"&item_names="+pname+"&item_quantity="+quantity+"&final_price="+finalprice+"&&api_key="+apikey+"&user_id="+userid+"&user_name="+shipname+
                "&billingadd="+ billAddr+"&deliveryadd="+deliverAddr +"&mobile="+delivMobile+"&email="+email, null,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray orderconfirmed = response.getJSONArray("Order confirmed");
                            if(orderconfirmed != null)
                            {
                               Intent intent = new Intent(CheckOutActivity.this, AfterPaymentActivity.class);
                               startActivity(intent);
                            }
                            else
                                Toast.makeText(getApplicationContext(),"Your order is not placed, please try again",Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("test", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"System error",Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }








    private void onBraintreeSubmit() {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(token);
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }

    private void sendPaymentDetails() {
        RequestQueue queue = Volley.newRequestQueue(CheckOutActivity.this);
        // Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(Request.Method.POST, send_payment_details,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Successful")) {

                            Toast.makeText(CheckOutActivity.this, "Transaction successful", Toast.LENGTH_LONG).show();
                        } else {
                          //  Toast.makeText(CheckOutActivity.this, "Transaction failed", Toast.LENGTH_LONG).show();
                            Log.d("mylog", "Final Response: " + response.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mylog", "Volley error : " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                if (paramHash == null)
                    return null;
                Map<String, String> params = new HashMap<>();
                for (String key : paramHash.keySet()) {
                    params.put(key, paramHash.get(key));
                    Log.d("mylog", "Key : " + key + " Value : " + paramHash.get(key));
                }

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
    }
}