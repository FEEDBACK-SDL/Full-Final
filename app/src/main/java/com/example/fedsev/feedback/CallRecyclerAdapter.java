package com.example.fedsev.feedback;

import android.accessibilityservice.GestureDescription;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;

public class CallRecyclerAdapter extends RecyclerView.Adapter<CallRecyclerAdapter.ViewHolder>  {
    ArrayList<RecycleData> finalL;

    CallRecyclerAdapter(ArrayList<RecycleData> finalL){
        this.finalL = finalL;

    }
    @NonNull
    @Override
    public CallRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CallRecyclerAdapter.ViewHolder viewHolder, int i) {
        viewHolder.header.setText(finalL.get(i).getHeader());
        viewHolder.footer.setText(finalL.get(i).getFooter());
        String s = getColor(finalL.get(i).getColor());
        viewHolder.linearLayout.setBackgroundColor(Color.parseColor(s));
    }

    @Override
    public int getItemCount() {
        return finalL.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView header,footer;
        private LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
            footer = itemView.findViewById(R.id.footer);
            linearLayout = itemView.findViewById(R.id.linear);
        }
    }
    public String getColor(int value){
        switch (value){
            case 1:{
                return "#0066cc";
            }
            case 2:{
                return "#ffae42";
            }
            case 3:{
                return "#3f26bf";
            }
            case 4:{
                return "#ff5349";
            }

        }
        return "#4256f4";
    }
}
