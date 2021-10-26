package com.example.amabiscaclient.Connect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.amabiscaclient.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    private ArrayList<Cart> cartDomain;
    private ManagementCart managementCart;
    private ChangeNumberItemsListener changeNumberItemsListener;
    private Context context;

    public CartAdapter(ArrayList<Cart> cartDomains, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.cartDomain = cartDomains;
        this.context = context;
        managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }


    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartHolder(inflate);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, @SuppressLint("RecyclerView") int position) {
        Cart cart = (Cart) cartDomain.get(position);

        holder.title.setText(cart.get_titulo());
        holder.price.setText(String.valueOf(cart.get_precio()));
        holder.totalprice.setText(String.valueOf(round((cart.get_cantidad() * cart.get_precio()),2 )));
        holder.quantity.setText(String.valueOf(cart.get_cantidad()));

        Glide.with(context).load(cart.get_imagen()).into(holder.imageView);

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.plusNumberCart(cartDomain, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.minusNumberCart(cartDomain, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartDomain.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder{


        ImageView imageView, plusItem, minusItem;
        TextView title, price, quantity, totalprice;
        ConstraintLayout constraintLayout;

        public CartHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageProductCart);
            title = itemView.findViewById(R.id.productTitle);
            price = itemView.findViewById(R.id.unitpriceTxt);
            totalprice = itemView.findViewById(R.id.totalItemCart);
            quantity = itemView.findViewById(R.id.quantityCartBtn);
            constraintLayout = itemView.findViewById(R.id.main_Layout);

            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minCartBtn);
        }
    }
}
