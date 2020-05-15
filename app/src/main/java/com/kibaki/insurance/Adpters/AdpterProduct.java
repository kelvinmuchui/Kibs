package com.kibaki.insurance.Adpters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;
import com.kibaki.insurance.Model.ProductModel;
import com.kibaki.insurance.DependantFormActivity;
import com.kibaki.insurance.R;

import java.util.List;

public class AdpterProduct  extends RecyclerView.Adapter<AdpterProduct.MyHolder>{


    Context context;
    List<ProductModel> productList;


    public AdpterProduct(Context context, List<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
    }


    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Inflate layour(row_product)

        View view = LayoutInflater.from(context).inflate(R.layout.row_product,parent, false);

        return new  MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {

        final String product_id = productList.get(i).getProduct_id();
        String productName = productList.get(i).getProduct_name();



        holder.mNameTv.setText(productName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent depenants = new Intent(context, DependantFormActivity.class);
                depenants.putExtra("product_id", product_id);
                context.startActivity(depenants);
            }
        });



    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView mNameTv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            mNameTv = itemView.findViewById(R.id.productName);
        }



    }
}
