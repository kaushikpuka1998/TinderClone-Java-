package com.kgstrivers.tinderc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kgstrivers.tinderc.Model.Cards;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Matchesadapter extends RecyclerView.Adapter<MatchviewHolder>  {

    List<Cards> listitem;
    Context context;


    public Matchesadapter(List<Cards> matchitem,Context cntxt)
    {
        this.context = cntxt;
        this.listitem = matchitem;
    }
    @NonNull
    @Override
    public MatchviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutview = LayoutInflater.from(parent.getContext()).inflate(R.layout.matchesitem,null,false);

        RecyclerView.LayoutParams lm = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutview.setLayoutParams(lm);

        MatchviewHolder matchviewHolder = new MatchviewHolder(layoutview);


        return matchviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchviewHolder holder, int position) {

        holder.name.setText(listitem.get(position).getName());

        Picasso.get().load(listitem.get(position).getImageurl()).into(holder.circleImageView);

    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }
}
