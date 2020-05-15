package com.kibaki.insurance.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kibaki.insurance.Adpters.AdpterPolicy;
import com.kibaki.insurance.Model.PolicyModel;
import com.kibaki.insurance.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashFragment extends Fragment {

    RecyclerView recyclerView;
    AdpterPolicy adpterPolicy;
    List<PolicyModel> policyModelList;

    TextView insured;


    public DashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dash, container, false);
        insured = view.findViewById(R.id.productName);

        recyclerView = view.findViewById(R.id.products_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        policyModelList = new ArrayList<>();

        if(policyModelList.equals(0)){
            insured.setText("Not Insured");
        }else{
            insured.setText("Insured");
        }

        getAllProducts();
        return view;
    }

    private void getAllProducts() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Policy");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                policyModelList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    PolicyModel productModel = ds.getValue(PolicyModel.class);

                    policyModelList.add(productModel);

                    adpterPolicy = new AdpterPolicy(getActivity(), policyModelList);
                    recyclerView.setAdapter(adpterPolicy);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
