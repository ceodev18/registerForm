package com.kelly.registerform.adapters.recyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelly.registerform.R;
import com.kelly.registerform.model.Chacra;
import com.kelly.registerform.model.TransformationOb;

import java.util.ArrayList;

/**
 * Created by italo on 16/03/18.
 */



public class TransformationAdapter extends RecyclerView.Adapter<TransformationAdapter.ObjectViewHolder> {
    private Context context;
    private ArrayList<TransformationOb> transformationObArrayList;

    public TransformationAdapter(Context context,ArrayList<TransformationOb>transformationObArrayList){
        this.context=context;
        this.transformationObArrayList=transformationObArrayList;
    }
    @Override
    public ObjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_transformation,parent,false);
        ObjectViewHolder objectViewHolder = new ObjectViewHolder(v);
        return objectViewHolder;
    }

    @Override
    public void onBindViewHolder(ObjectViewHolder holder, int position) {
        holder.tv_transformation.setText(transformationObArrayList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return transformationObArrayList.size();
    }
    public static class ObjectViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView tv_transformation;
        ObjectViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv_transformation);
            tv_transformation =itemView.findViewById(R.id.tv_transformation);

        }
    }
}
