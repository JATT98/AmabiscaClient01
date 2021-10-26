package com.example.amabiscaclient.Connect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.amabiscaclient.Activity.DetailProductActivity;
import com.example.amabiscaclient.Activity.StoreActivity;
import com.example.amabiscaclient.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private Context context;
    private List productList;

    public ProductAdapter(Context context , List products){
        this.context = context;
        productList = products;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item , parent , false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = (Product) productList.get(position);


        holder.price.setText("Q. " + Double.toString(product.get_precio()) + "/" + product.get_unidad());
        holder.title.setText(product.get_titulo());
        holder.overview.setText(product.get_descripcion());
        Glide.with(context).load(product.get_imagen()).into(holder.imageView);




        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , DetailProductActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("title" , product.get_titulo());
                bundle.putString("overview" , product.get_descripcion());
                bundle.putString("image" , product.get_imagen());
                bundle.putDouble("price" , product.get_precio());
                bundle.putString("unit" , product.get_unidad());
                bundle.putInt("code" , product.get_codigo());

                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title , overview , price;
        ConstraintLayout constraintLayout;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageProduct);
            title = itemView.findViewById(R.id.title_product);
            overview = itemView.findViewById(R.id.overview);
            price = itemView.findViewById(R.id.price);
            constraintLayout = itemView.findViewById(R.id.item_Layout);
        }
    }
}
