package com.example.amabiscaclient.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.amabiscaclient.Connect.GlobalVarLog;
import com.example.amabiscaclient.Connect.Product;
import com.example.amabiscaclient.Connect.ProductAdapter;
import com.example.amabiscaclient.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailProductActivity extends AppCompatActivity {

    private Integer _codigo;
    private int numberOrder = 1;
    private ImageView plusBtn, minusBtn;
    private TextView numberOrderTxt, sendOrderBtn;

    GlobalVarLog var =   GlobalVarLog.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        ImageView imageView = findViewById(R.id.poster);
        TextView priceTxt = findViewById(R.id.pricetxt);
        TextView nameTxt = findViewById(R.id.titletxt);
        TextView overviewTxt = findViewById(R.id.overviewtxt);

        Bundle bundle = getIntent().getExtras();
        String mTitle = bundle.getString("title");
        String mPoster = bundle.getString("image");
        String mOverView = bundle.getString("overview");
        double mPrice = bundle.getDouble("price");
        String mUnit = bundle.getString("unit");
        _codigo = bundle.getInt("code");

        Log.e("PRODUCTO: ",_codigo.toString());

        Glide.with(this).load(mPoster).into(imageView);
        priceTxt.setText("Q. " + Double.toString(mPrice) + "/" + mUnit);
        nameTxt.setText(mTitle);
        overviewTxt.setText(mOverView);

        addBtns();
        addOrderDetail();
    }

    private void addBtns(){
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        numberOrderTxt = findViewById(R.id.ordertxt);


        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });
    }

    private void addOrderDetail(){
        sendOrderBtn = findViewById(R.id.sendOrderBtn);

        sendOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOrder();
            }
        });
    }

    private void getOrder(){
        String URL = var.get_URLconnection() + "postOrder";

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("name",var.get_codigo());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, URL, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.d("RESULT: ", jsonObject.toString());
                            try {
                                Integer     _order     =jsonObject.getInt("_codigo");
                                var.set_actualOrder(_order);
                                setDetail();
                            }catch (JSONException e){Log.e("ERROR: ",e.toString());}
                        }
                    }, new Response.ErrorListener (){

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("ERROR: ",volleyError.toString());
                }
            }
            );
            requestQueue.add(jsonObjectRequest);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setDetail(){
        String URL = var.get_URLconnection() + "postDetail";

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("orden",var.get_actualOrder());
            jsonObject.put("producto",_codigo);
            jsonObject.put("cantidad",numberOrderTxt.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST, URL, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.d("RESULT: ", jsonObject.toString());
                            try {
                                String     _msj     = jsonObject.getString("_msj");
                                Toast.makeText(getApplicationContext(),_msj,Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(DetailProductActivity.this, StoreActivity.class));
                                finish();
                            }catch (JSONException e){Log.e("ERROR: ",e.toString());}
                        }
                    }, new Response.ErrorListener (){

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("ERROR: ",volleyError.toString());
                }
            }
            );
            requestQueue.add(jsonObjectRequest);

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}