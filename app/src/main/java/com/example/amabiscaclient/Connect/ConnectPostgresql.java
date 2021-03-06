package com.example.amabiscaclient.Connect;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class ConnectPostgresql {
    Connection con = null;

    public Connection conBD(){

        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }catch (Exception e){
            System.err.println("Error: "+ e.toString());
        }
        return con;
    }


    protected void closeConnection(Connection con) throws Exception{
        con.close();
    }
}
