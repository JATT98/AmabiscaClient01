package com.example.amabiscaclient.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;


import com.example.amabiscaclient.Connect.GlobalVarLog;
import com.example.amabiscaclient.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    GlobalVarLog var =   GlobalVarLog.getInstance();
    private FloatingActionButton logBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigation();
    }

    private void bottomNavigation(){
        LinearLayout callBtn = findViewById(R.id.callBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        logBtn = findViewById(R.id.logbtnmain);


        if(var.get_codigo() == 0){
            logBtn.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(91,140,90)));
        }else{

            logBtn.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(219,29,39)));
        }



        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(var.get_codigo() == 0) {
                    startActivity(new Intent(MainActivity.this, LogInActivity.class));
                }else{
                    var.restart();
                    logBtn.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(91,140,90)));
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
                //startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
    }

    private void CallButton(){
        String phoneNumber = "46463819";
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
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
                Toast.makeText(this, "permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }




}