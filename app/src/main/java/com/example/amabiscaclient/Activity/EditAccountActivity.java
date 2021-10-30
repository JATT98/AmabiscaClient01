package com.example.amabiscaclient.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.amabiscaclient.Connect.GlobalVarLog;
import com.example.amabiscaclient.R;

import org.json.JSONException;
import org.json.JSONObject;

public class EditAccountActivity extends AppCompatActivity {

    GlobalVarLog var =   GlobalVarLog.getInstance();
    EditText txtPass, txtNewPass, txtUser, txtName, txtLastName, txtAddress, txtPhone, txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        TextView actualizarBtn = findViewById(R.id.actualizarBtn);
        txtUser = findViewById(R.id.editUsertxt);
        txtName = findViewById(R.id.editNametxt);
        txtLastName = findViewById(R.id.editLastnametxt);
        txtNewPass = findViewById(R.id.editNewPasstxt);
        txtAddress = findViewById(R.id.editAddresstxt);
        txtPhone = findViewById(R.id.editPhonetxt);
        txtEmail = findViewById(R.id.editEmailtxt);

        txtUser.setText(var.get_usuario());
        txtName.setText(var.get_nombre());
        txtLastName.setText(var.get_apellido());
        txtAddress.setText(var.get_direccion());
        txtEmail.setText(var.get_correo());
        txtPhone.setText(var.get_telefono());

        actualizarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmacion();
            }
        });

    }


    private void confirmacion(){
        txtPass = findViewById(R.id.editPasstxt);

        if (var.get_clave().equals(txtPass.getText().toString())){
            actualizarDatos();
        }else{
            Toast.makeText(getApplicationContext(),"CONTRASEÑA INCORRECTA",Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarDatos(){
        String URL = var.get_URLconnection() + "updateUser";




        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("codigo",var.get_codigo());
            jsonObject.put("usuario",txtUser.getText());
            jsonObject.put("clave",txtNewPass.getText());
            jsonObject.put("nombre",txtName.getText());
            jsonObject.put("apellido",txtLastName.getText());
            jsonObject.put("direccion",txtAddress.getText());
            jsonObject.put("telefono",txtPhone.getText());
            jsonObject.put("correo",txtEmail.getText());
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
                                String      _msj        =jsonObject.getString("_msj");
                                if(_msj.equals("DATOS ACTUALIZADOS CON ÉXITO")){
                                    var.set_usuario(txtUser.getText().toString());
                                    var.set_clave(txtNewPass.getText().toString());
                                    var.set_nombre(txtName.getText().toString());
                                    var.set_apellido(txtLastName.getText().toString());
                                    var.set_correo(txtEmail.getText().toString());
                                    var.set_direccion(txtAddress.getText().toString());
                                    var.set_telefono(txtPhone.getText().toString());
                                }
                                Toast.makeText(getApplicationContext(),_msj,Toast.LENGTH_SHORT).show();

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