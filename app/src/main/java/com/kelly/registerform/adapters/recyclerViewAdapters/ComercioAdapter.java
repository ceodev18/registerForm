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
import com.kelly.registerform.model.Comercio;

import java.util.ArrayList;

/**
 * Created by italo on 18/03/18.
 */

public class ComercioAdapter extends RecyclerView.Adapter<ComercioAdapter.ObjectViewHolder> {
    private Context context;
    private ArrayList<Comercio> comercioArrayList;

    public ComercioAdapter(Context context,ArrayList<Comercio>comercioArrayList){
        this.context=context;
        this.comercioArrayList=comercioArrayList;
    }
    @Override
    public ObjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_especie,parent,false);
        ObjectViewHolder objectViewHolder = new ObjectViewHolder(v);
        return objectViewHolder;
    }

    @Override
    public void onBindViewHolder(ObjectViewHolder holder, int position) {
        holder.tv_chacra.setText("Producto #"+(position+1));
    }

    @Override
    public int getItemCount() {
        return comercioArrayList.size();
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