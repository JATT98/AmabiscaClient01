package com.example.amabiscaclient.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amabiscaclient.Connect.ConnectPostgresql;
import com.example.amabiscaclient.Connect.GlobalVarLog;
import com.example.amabiscaclient.R;

import java.sql.CallableStatement;
import java.sql.Types;

public class LogInActivity extends AppCompatActivity {

    private static ConnectPostgresql con = new ConnectPostgresql();
    GlobalVarLog var =   GlobalVarLog.getInstance();

    private ConstraintLayout logBtn;
    EditText txtUser, txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);

        logBtn = findViewById(R.id.btnLog);

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogIn(txtUser.getText().toString(),txtPass.getText().toString());
            }
        });

    }

    public void LogIn(String User, String Pass){
        try {

            /*if (!User.equals("") && !Pass.equals("")){
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[9];
                        field[0] = "nombre";
                        field[1] = "";
                        field[2] = "";
                        field[3] = "";
                        field[4] = "";
                        field[5] = "";
                        field[6] = "";
                        field[7] = "";
                        field[8] = "";
                    }
                })

            }*/
            String storePCall = "CALL f_login_usuario(?,?,?,?,?,?,?,?,?,?,?)";
            CallableStatement cStmt = con.conBD().prepareCall(storePCall);

            cStmt.setString(1,User);
            cStmt.setString(2,Pass);

            cStmt.registerOutParameter(3, Types.INTEGER);
            cStmt.registerOutParameter(4, Types.VARCHAR);
            cStmt.registerOutParameter(5, Types.VARCHAR);
            cStmt.registerOutParameter(6, Types.VARCHAR);
            cStmt.registerOutParameter(7, Types.VARCHAR);
            cStmt.registerOutParameter(8, Types.VARCHAR);
            cStmt.registerOutParameter(9, Types.VARCHAR);
            cStmt.registerOutParameter(10, Types.VARCHAR);
            cStmt.registerOutParameter(11, Types.VARCHAR);

            cStmt.executeUpdate();

            Integer     _codigo     =cStmt.getInt(3);
            String      _nombre     =cStmt.getString(4);
            String      _dni        =cStmt.getString(5);
            String      _direccion  =cStmt.getString(6);
            String      _telefono   =cStmt.getString(7);
            String      _correo     =cStmt.getString(8);
            String      _nacimiento =cStmt.getString(9);
            String      _sexo       =cStmt.getString(10);
            String      _msj        =cStmt.getString(11);

            if(_msj.equals("OK")){
                /*
                var.set_codigousuario(_codigo);
                var.set_datos(_datos);
                var.set_direccion(_direccion);
                var.set_email(_email);
                var.set_telefono(_telefono);

                Intent menu=new Intent(this,activity_menu.class);
                startActivity(menu);
                */
            }else{
                Toast.makeText(getApplicationContext(),_msj,Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

}