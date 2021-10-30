package com.example.amabiscaclient.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.amabiscaclient.Connect.ClickButtonListener;
import com.example.amabiscaclient.Connect.GlobalVarLog;
import com.example.amabiscaclient.Connect.Order;
import com.example.amabiscaclient.Connect.OrderAdapter;
import com.example.amabiscaclient.Connect.Product;
import com.example.amabiscaclient.Connect.ProductAdapter;
import com.example.amabiscaclient.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    GlobalVarLog var =   GlobalVarLog.getInstance();
    private RecyclerView recyclerView;
    private List orderList;
    private TextView phone2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        phone2 = findViewById(R.id.numberCall);
        phone2.setText(var.get_phone());
        recyclerView = findViewById(R.id.orderView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderList = new ArrayList<>();

        fetchProducts();

        TextView phone = findViewById(R.id.btnCall2);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallButton();
            }
        });
    }

    private void fetchProducts() {
        String URL = var.get_URLconnection() + "myorders/" + var.get_codigo();
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0 ; i < response.length() ; i ++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Integer codigo = jsonObject.getInt("orden");
                                String fecha = jsonObject.getString("fecha");
                                String descripcion = jsonObject.getString("detalle");
                                String proveedor = jsonObject.getString("repartidor");
                                String telefono = jsonObject.getString("telefono");

                                Order order = new Order(codigo , fecha , descripcion , proveedor, telefono);
                                orderList.add(order);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            OrderAdapter adapter = new OrderAdapter(OrderActivity.this, orderList, new ClickButtonListener() {
                                @Override
                                public void Click() {
                                    phone2.setText(var.get_phone());
                                }
                            });

                            recyclerView.setAdapter(adapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onBackPressed() {
        var.set_phone("");
        super.onBackPressed();
    }

    private void CallButton(){
        if (ContextCompat.checkSelfPermission(OrderActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(OrderActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }else {
            String dial = "tel:" + var.get_phone();
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

}