package com.kibaki.insurance.Adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kibaki.insurance.Model.PolicyModel;
import com.kibaki.insurance.R;

import java.util.List;

public class AdpterPolicy extends RecyclerView.Adapter<AdpterPolicy.MyHolder> {


    Context context;

    List<PolicyModel> policyModelList;

    public AdpterPolicy(Context context, List<PolicyModel> policyModelList) {
        this.context = context;
        this.policyModelList = policyModelList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_policy,parent, false);

        return new  MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {

        final String policy_id = policyModelList.get(i).getPolicy_id();
        String policyName = policyModelList.get(i).getPolicy_name();
        String policy_terms = policyModelList.get(i).getPolicy_term();
        String maturity = policyModelList.get(i).getPolicy_maturity_date();
        String sum = policyModelList.get(i).getPolicy_total_premium();
        holder.mNameTv.setText(policyName);
        holder.mterms.setText(policy_terms);
        holder.mMaturity.setText(maturity);
        holder.mSum.setText(sum);


    }

    @Override
    public int getItemCount() {
        return policyModelList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView mNameTv,mterms,mMaturity,mSum, insured;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            mNameTv = itemView.findViewById(R.id.policyName);
            mterms = itemView.findViewById(R.id.policyterms);
            mMaturity = itemView.findViewById(R.id.maturity);
            mSum = itemView.findViewById(R.id.policysum);
        }



    }
}
