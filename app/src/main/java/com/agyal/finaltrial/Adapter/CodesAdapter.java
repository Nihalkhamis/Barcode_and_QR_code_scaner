package com.agyal.finaltrial.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agyal.finaltrial.Model.CodesModel;
import com.agyal.finaltrial.R;

import java.util.ArrayList;
import java.util.Date;

public class CodesAdapter extends RecyclerView.Adapter<CodesAdapter.CodesHolder>  {
     ArrayList<CodesModel> codesModels;

    public CodesAdapter(ArrayList<CodesModel> codesModels) {
        this.codesModels = codesModels;
    }

    @NonNull
    @Override
    public CodesAdapter.CodesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.codenum_item,parent,false);
        CodesAdapter.CodesHolder codesHolder = new CodesAdapter.CodesHolder(view);
        return codesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CodesAdapter.CodesHolder holder, int position) {
            CodesModel codesModel = codesModels.get(position);

            if(position == codesModels.size()-1 ){
                 holder.TV.setTextColor(Color.GREEN);
            }
           else {
               holder.TV.setTextColor(Color.BLUE);
            }

           holder.TV.setText(codesModel.getCodenum());
           holder.date.setText(codesModel.getCodedate().toString());
           holder.time.setText(codesModel.getCodetime().toString());
    }

    @Override
    public int getItemCount() {
        return codesModels.size();
    }

    public class CodesHolder extends RecyclerView.ViewHolder{
        TextView TV,date,time;

        public CodesHolder(@NonNull View itemView) {
            super(itemView);

            TV = itemView.findViewById(R.id.TV);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }
}
