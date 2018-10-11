package com.example.fedsev.feedback;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private Context mCtx;
    private int count;

//    private List<InfoCard> infolist;
    private List<CallStatEntity> recordlist;


    //getting the context and product list with constructor
//    public CardAdapter(Context mCtx, List<InfoCard> infolist) {
//        this.mCtx = mCtx;
//        this.infolist = infolist;
//    }
    public CardAdapter(Context mCtx, List<CallStatEntity> recordlist) {
        this.mCtx = mCtx;
        this.recordlist = recordlist;
    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.stats, null);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {

//        InfoCard il = infolist.get(position);

        CallStatEntity il = recordlist.get(position);

        holder.textName.setText(il.getName());
        holder.textServiceid.setText(String.valueOf(il.getServiceid()));
        holder.textNumber.setText(String.valueOf(il.getNumber()));
        holder.textTime.setText(il.getTime());
        holder.textDate1.setText(il.getDate1());

    }

    @Override
    public int getItemCount() {
        return recordlist.size();
    }

//    public int getItemCount() {
//        return infolist.size();
//    }


    class CardViewHolder extends RecyclerView.ViewHolder{

        TextView textName,textNumber, textServiceid, textTime, textDate1;

        public CardViewHolder(View itemView){
            super(itemView);

            textName = itemView.findViewById(R.id.textViewname);
            textNumber = itemView.findViewById(R.id.textViewnumber);
            textServiceid = itemView.findViewById(R.id.textViewservice);
            textTime = itemView.findViewById(R.id.textViewtime);
            textDate1 = itemView.findViewById(R.id.textViewdate1);
        }
    }

//    public void setFilter(ArrayList<InfoCard> newlist){
//
//        infolist = new ArrayList<>();
//        infolist.addAll(newlist);
//        //this will refresh the adapter
//        notifyDataSetChanged();
//
//    }

    public void setFilter(ArrayList<CallStatEntity> newlist){

        recordlist = new ArrayList<>();
        recordlist  .addAll(newlist);
        //this will refresh the adapter
        notifyDataSetChanged();

    }
}
