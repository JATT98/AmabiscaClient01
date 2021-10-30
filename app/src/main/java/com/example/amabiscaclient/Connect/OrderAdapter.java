package com.example.amabiscaclient.Connect;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.amabiscaclient.Activity.DetailProductActivity;
import com.example.amabiscaclient.Activity.MainActivity;
import com.example.amabiscaclient.Activity.OrderActivity;
import com.example.amabiscaclient.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder>{
    GlobalVarLog var =   GlobalVarLog.getInstance();

    private ManagementPhone managementPhone;
    private ClickButtonListener clickButtonListener;
    private Context context;
    private List ordersList;

    public OrderAdapter(Context context , List orders, ClickButtonListener clickButtonListener){
        this.context = context;
        ordersList = orders;
        managementPhone = new ManagementPhone(context);
        this.clickButtonListener = clickButtonListener;

    }

    @NonNull
    @Override
    public OrderAdapter.OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order , parent , false);
        return new OrderAdapter.OrderHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        Order order = (Order) ordersList.get(position);

        holder.title.setText("Orden #" + order.get_codigo());
        holder.date.setText(order.get_fecha());
        holder.state.setText(order.get_detalle());
        holder.customer.setText(order.get_proveedor());

        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementPhone.clickNumber(new ClickButtonListener() {
                    @Override
                    public void Click() {
                        if (order.get_telefono().equals("")){
                            var.set_phone("46463819");
                        }else{
                            var.set_phone(order.get_telefono());
                        }
                        clickButtonListener.Click();
                    }
                });


            }
        });
    }



    @Override
    public int getItemCount() {
        return ordersList.size();    }

    public class OrderHolder extends RecyclerView.ViewHolder{
        TextView title , state , date, customer, phone;
        ConstraintLayout constraintLayout;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.orderTxt);
            state = itemView.findViewById(R.id.detailTxt);
            date = itemView.findViewById(R.id.dateTxt);
            customer = itemView.findViewById(R.id.deliverTxt);
            phone = itemView.findViewById(R.id.callDeliverTxt);
            constraintLayout = itemView.findViewById(R.id.order_layout);
        }

    }



}
