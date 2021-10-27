package com.example.amabiscaclient.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.amabiscaclient.Connect.GlobalVarLog;
import com.example.amabiscaclient.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;

    GlobalVarLog var =   GlobalVarLog.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        bottomNavigation();
    }

    private void bottomNavigation(){
        //Navigation Buttons
        NavMenu();

        //Menu Buttons
        Menu();

        getOrder();
    }

    private void Menu(){
        ConstraintLayout cartBtn = findViewById(R.id.cartBtn001);
        ConstraintLayout dataBtn = findViewById(R.id.dataBtn);
        ConstraintLayout orderBtn = findViewById(R.id.orderBtn001);

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                var.set_totalOrder(0.0);

                startActivity(new Intent(AccountActivity.this, CartActivity.class));
            }
        });

        dataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, EditAccountActivity.class));
            }
        });

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, OrderActivity.class));

            }
        });
    }



    private void NavMenu(){
        LinearLayout callBtn = findViewById(R.id.callBtn001);
        LinearLayout homeBtn = findViewById(R.id.homeBtn001);
        LinearLayout infoBtn = findViewById(R.id.infoBtn001);
        LinearLayout storeBtn = findViewById(R.id.storeBtn001);
        FloatingActionButton logBtn = findViewById(R.id.logbtnmain001);;

        if(var.get_codigo() == 0){
            logBtn.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(91,140,90)));
        }else{
            logBtn.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(219,29,39)));
        }

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(var.get_codigo() == 0) {
                    startActivity(new Intent(AccountActivity.this, LogInActivity.class));
                }else{
                    var.restart();
                    logBtn.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(91,140,90)));
                    Toast.makeText(getApplicationContext(),"SESIÓN CERRADA",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AccountActivity.this, MainActivity.class));
                }
            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallButton();
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
            }
        });

        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoButton();
            }
        });

        storeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(var.get_codigo() != 0) {
                    startActivity(new Intent(AccountActivity.this, StoreActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(),"INICIA SESIÓN PRIMERO",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void InfoButton(){
        Uri _link = Uri.parse(var.get_URLrepository());
        Intent i = new Intent(Intent.ACTION_VIEW,_link);
        startActivity(i);
    }

    private void CallButton(){
        String phoneNumber = "46463819";
        if (ContextCompat.checkSelfPermission(AccountActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AccountActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }else {
            String dial = "tel:"+phoneNumber;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if (requestCode == REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                CallButton();
            }else{
                Toast.makeText(this, "PERMISO DENEGADO", Toast.LENGTH_SHORT).show();
            }
        }
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