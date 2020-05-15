package com.kibaki.insurance.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kibaki.insurance.Adpters.AdpterProduct;
import com.kibaki.insurance.Model.ProductModel;
import com.kibaki.insurance.R;

import java.util.ArrayList;
import java.util.List;


public class ProductFragment extends Fragment {

    RecyclerView recyclerView;
    AdpterProduct adpterProduct;
    List<ProductModel> productModelList;


    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =   inflater.inflate(R.layout.fragment_product, container, false);

        //init recycleview
        recyclerView = view.findViewById(R.id.products_recyclerview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        productModelList = new ArrayList<>();

        getAllProducts();
        return view;
    }


    private void getAllProducts(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productModelList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    ProductModel productModel = ds.getValue(ProductModel.class);

                    productModelList.add(productModel);

                    adpterProduct = new AdpterProduct(getActivity(), productModelList);
                    recyclerView.setAdapter(adpterProduct);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
