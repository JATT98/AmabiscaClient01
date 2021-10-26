package com.example.amabiscaclient.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.amabiscaclient.Connect.Cart;
import com.example.amabiscaclient.Connect.CartAdapter;
import com.example.amabiscaclient.Connect.ChangeNumberItemsListener;
import com.example.amabiscaclient.Connect.GlobalVarLog;
import com.example.amabiscaclient.Connect.Product;
import com.example.amabiscaclient.Connect.ProductAdapter;
import com.example.amabiscaclient.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Cart> cartList;
    GlobalVarLog var =   GlobalVarLog.getInstance();
    TextView txtTotal, ordenarBtn;
    RadioGroup radioGroup;
    RadioButton radioButton;
    private Dialog dialog;
    int radioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerCart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartList = new ArrayList<Cart>();
        txtTotal = findViewById(R.id.totaltxt);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button Okay = dialog.findViewById(R.id.btn_okay);
        Button Cancel = dialog.findViewById(R.id.btn_cancel);
        TextView dialogtitle = dialog.findViewById(R.id.dialogTitle);
        TextView dialogtext = dialog.findViewById(R.id.dialogText);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CartActivity.this, "ORDEN PUBLICADA", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                if(radioId == 2131231243){
                    publishOder("Pendiente");
                }else if(radioId == 2131231241){
                    publishOder("Contra Entrega");
                }

                startActivity(new Intent(CartActivity.this, AccountActivity.class));
                finish();
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(CartActivity.this, "CANCELAR", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        fetchCart();

        radioGroup = findViewById(R.id.radioGroupPrice);
        ordenarBtn = findViewById(R.id.sendOrderBtn2);

        ordenarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioId = radioGroup.getCheckedRadioButtonId();
                String msj;
                    if(radioId == 2131231243){
                        dialogtitle.setText("PAGO BANCARIO");
                        dialogtext.setText("No. de cuenta: 1234567890 \n" +
                                "Banco: Banrural \n" +
                                "Tipo de cuenta: Monetaria\n\n" +
                                "¿Está seguro de realizar su orden con este tipo de pago?");
                        dialog.show();
                    }else if(radioId == 2131231242){

                        startActivity(new Intent(CartActivity.this, CreditCardActivity.class));

                    }else{
                        dialogtitle.setText("PAGO CONTRA ENTREGA");
                        dialogtext.setText("¿Está seguro de realizar su orden con este tipo de pago?");
                        dialog.show();
                    }

                radioButton = findViewById(radioId);
            }
        });
    }


    private void fetchCart() {
        String URL = var.get_URLconnection() + "cart/" + var.get_actualOrder();
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("RESULT: ", response.toString());
                        for (int i = 0 ; i < response.length() ; i ++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Integer codigo = jsonObject.getInt("pc_producto");
                                String nombre = jsonObject.getString("nombre");
                                Double precio = jsonObject.getDouble("precio");
                                String imagen = jsonObject.getString("imagen");
                                Integer cantidad = jsonObject.getInt("cantidad");
                                Double total = cantidad * precio;
                                var.set_totalOrder(total + var.get_totalOrder());

                                Cart cart = new Cart(codigo, nombre , imagen, precio, cantidad);
                                cartList.add(cart);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            CartAdapter adapter = new CartAdapter(cartList,CartActivity.this , new ChangeNumberItemsListener() {
                                @Override
                                public void changed() {
                                    calculateTotal();
                                }
                            });;

                            recyclerView.setAdapter(adapter);
                        }
                        txtTotal.setText("Q. " + var.get_totalOrder());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CartActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void calculateTotal() {



        txtTotal.setText("Q. " + round(var.get_totalOrder(),2));


        if(var.get_deleteProduct() != 0){
            deleteQuantity(var.get_deleteProduct());
        }
    }

    private void deleteQuantity(int producto) {
        String URL = var.get_URLconnection() + "detail/delete";
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("orden",var.get_actualOrder());
            jsonObject.put("producto",producto);
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

    private void publishOder(String pago) {
        String URL = var.get_URLconnection() + "publish";
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("orden",var.get_actualOrder());
            jsonObject.put("pago",pago);
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