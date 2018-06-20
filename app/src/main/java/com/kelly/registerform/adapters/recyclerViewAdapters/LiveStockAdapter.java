package com.kelly.registerform.adapters.recyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelly.registerform.R;
import com.kelly.registerform.model.AnimalCrianza;
import com.kelly.registerform.model.Chacra;

import java.util.ArrayList;

/**
 * Created by italo on 11/03/18.
 */


public class LiveStockAdapter extends RecyclerView.Adapter<LiveStockAdapter.ObjectViewHolder> {
    private Context context;
    private ArrayList<AnimalCrianza> chacraArrayList;

    public LiveStockAdapter(Context context,ArrayList<AnimalCrianza>chacraArrayList){
        this.context=context;
        this.chacraArrayList=chacraArrayList;
    }
    @Override
    public ObjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_especie,parent,false);
        ObjectViewHolder objectViewHolder = new ObjectViewHolder(v);
        return objectViewHolder;
    }

    @Override
    public void onBindViewHolder(ObjectViewHolder holder, int position) {
        if(chacraArrayList.get(position).getNombre().length()==0){
            holder.tv_chacra.setText("Especie Animal # "+(position+1));
        }else{
            holder.tv_chacra.setText(chacraArrayList.get(position).getNombre());
        }


    }

    @Override
    public int getItemCount() {
        return chacraArrayList.size();
    }
    public static class ObjectViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView tv_chacra;
        ObjectViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv_chacra);
            tv_chacra =itemView.findViewById(R.id.tv_chacra);

        }
    }
}