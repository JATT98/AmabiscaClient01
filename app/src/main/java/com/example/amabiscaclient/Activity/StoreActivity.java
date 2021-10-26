package com.example.amabiscaclient.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.amabiscaclient.Connect.GlobalVarLog;
import com.example.amabiscaclient.Connect.Product;
import com.example.amabiscaclient.Connect.ProductAdapter;
import com.example.amabiscaclient.R;

import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List productlist;
    GlobalVarLog var =   GlobalVarLog.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productlist = new ArrayList<>();

        fetchProducts();
    }

    private void fetchProducts() {

        String URL = var.get_URLconnection() + "products";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0 ; i < response.length() ; i ++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Integer codigo = jsonObject.getInt("pc_producto");
                                String nombre = jsonObject.getString("nombre");
                                Double precio = jsonObject.getDouble("precio");
                                String descripcion = jsonObject.getString("descripcion");
                                String imagen = jsonObject.getString("imagen");
                                Integer cantidad = jsonObject.getInt("cantidad");
                                String unidad = jsonObject.getString("rc_unidad");

                                Product product = new Product(nombre , imagen , descripcion , precio, unidad, codigo,cantidad);
                                productlist.add(product);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            ProductAdapter adapter = new ProductAdapter(StoreActivity.this , productlist);

                            recyclerView.setAdapter(adapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StoreActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

}